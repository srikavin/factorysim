package me.infuzion.fractorio.render.lwjgl;

import me.infuzion.fractorio.GameWorld;
import me.infuzion.fractorio.render.Camera;
import me.infuzion.fractorio.render.Renderer;
import me.infuzion.fractorio.render.lwjgl.util.ShaderProgram;
import me.infuzion.fractorio.util.Utilities;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class LWJGLRenderer implements Renderer {
    private long window;
    private int vboId;
    private int vaoId;
    private ShaderProgram shader;

    public LWJGLRenderer(long window) {
        this.window = window;

        shader = new ShaderProgram();
        shader.createVertexShader(Utilities.getResource("shaders/vertex.glsl"));
        shader.createFragmentShader(Utilities.getResource("shaders/fragment.glsl"));
        shader.link();

        float[] vertices = new float[]{
                0.0f, 0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f
        };
        FloatBuffer verticesBuffer = null;

        try {
            verticesBuffer = MemoryUtil.memAllocFloat(vertices.length);
            verticesBuffer.put(vertices).flip();

            vaoId = glGenBuffers();
            glBindVertexArray(vaoId);

            vboId = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, vboId);
            glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);

            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

            // Unbind the VBO
            glBindBuffer(GL_ARRAY_BUFFER, 0);

            // Unbind the VAO
            glBindVertexArray(0);
        } finally {
            if (verticesBuffer != null) {
                MemoryUtil.memFree(verticesBuffer);
            }
        }
    }

    @Override
    public void render(GameWorld world, Camera camera) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        shader.bind();

        glBindVertexArray(vaoId);
        glEnableVertexAttribArray(0);

        glDrawArrays(GL_TRIANGLES, 0, 3);

        glViewport(0, 0, 640, 640);

        glDisableVertexAttribArray(0);
        glBindVertexArray(0);

        shader.unbind();
        glfwSwapBuffers(window);
    }
}
