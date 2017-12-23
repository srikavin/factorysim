package me.infuzion.engine.render.lwjgl;

import me.infuzion.engine.render.*;
import me.infuzion.engine.render.lwjgl.util.ShaderProgram;
import me.infuzion.engine.util.Utilities;
import me.infuzion.engine.world.GameWorld;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.lwjgl.system.MemoryUtil;

import java.io.IOException;
import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray;
import static org.lwjgl.opengl.ARBVertexArrayObject.glGenVertexArrays;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class LWJGLRenderer implements Renderer {

    private final Vector3fc upVector = new Vector3f(0, 1, 0);
    private Mesh mesh;
    private FloatBuffer mvpMatrixBuffer;
    private int matrixId;
    private long window;
    private int vaoId;
    private ShaderProgram shader;

    public LWJGLRenderer(long window) {

        try {
            mesh = OBJLoader.loadMesh("assets/base/earth.obj");
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.window = window;

        shader = new ShaderProgram();
        shader.createVertexShader(Utilities.getResource("shaders/vertex.glsl"));
        shader.createFragmentShader(Utilities.getResource("shaders/fragment.glsl"));
        shader.link();

        int programId = shader.getProgramId();
        matrixId = glGetUniformLocation(programId, "MVP");

        //Initialize MVP buffer
        mvpMatrixBuffer = MemoryUtil.memAllocFloat(16);

        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glDepthFunc(GL_LESS);

        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);
        glBindVertexArray(0);
        mesh.setColor(new Vector3f(250, 250, 250));
        try {
            mesh.setTexture(new GLTexture(new Texture("assets/base/earth.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setMVPUniform(Camera camera) {
        Matrix4f projection = new Matrix4f();
        projection.setPerspective((float) (camera.getScale() * Math.toRadians(110)),
                4.0f / 3.0f, 0.1f, 100f);

        Matrix4f model = new Matrix4f().identity();

        float xOffset = (float) camera.getOffSetX();
        float yOffset = (float) camera.getOffSetY();
        float zOffset = (float) camera.getOffSetZ();

        Vector3fc eye = new Vector3f(xOffset, yOffset, zOffset);
        Vector3fc center = new Vector3f(xOffset, yOffset, 0);

        Matrix4f view = new Matrix4f().setLookAt(eye, center, upVector);

        Matrix4f MVP = projection.mul(view).mul(model);
        MVP.get(mvpMatrixBuffer);
    }

    @Override
    public void render(GameWorld world, Camera camera) {
//        glPolygonMode( GL_FRONT_AND_BACK, GL_LINE );
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        shader.bind();

        glUniformMatrix4fv(matrixId, false, mvpMatrixBuffer);

        glBindVertexArray(vaoId);

        setMVPUniform(camera);

        mesh.render();

        glViewport(0, 0, 640, 640);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);

        shader.unbind();
        glfwSwapBuffers(window);
    }
}
