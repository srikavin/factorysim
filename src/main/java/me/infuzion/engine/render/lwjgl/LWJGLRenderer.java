package me.infuzion.engine.render.lwjgl;

import me.infuzion.engine.render.Camera;
import me.infuzion.engine.render.Renderer;
import me.infuzion.engine.render.lwjgl.util.ShaderProgram;
import me.infuzion.engine.ui.UIManager;
import me.infuzion.engine.ui.UserInterface;
import me.infuzion.engine.ui.node.Text;
import me.infuzion.engine.util.Utilities;
import me.infuzion.engine.world.GameObject;
import me.infuzion.engine.world.GameWorld;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray;
import static org.lwjgl.opengl.ARBVertexArrayObject.glGenVertexArrays;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class LWJGLRenderer implements Renderer {
    int fontID;
    UserInterface userInterface;
    private Transformations transformations;
    private NVGColor a;
    private FloatBuffer mvpMatrixBuffer;
    private int matrixId;
    private Window window;
    private int vaoId;
    private ShaderProgram shader;
    private UIManager manager;

    public LWJGLRenderer(Window window) {
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
        glEnable(GL_STENCIL_TEST);
        glDepthFunc(GL_LESS);

        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);
        glBindVertexArray(0);
        a = NVGColor.create();
        transformations = new Transformations(window);
        manager = new UIManager(window);
        fontID = manager.getFont("assets/fonts/a.ttf", "a");

        userInterface = new UserInterface(manager, new Text(manager));
    }

    @Override
    public void render(GameWorld world, Camera camera) {
        glViewport(0, 0, window.getWidth(), window.getHeight());

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
        shader.bind();

        glBindVertexArray(vaoId);

        for (GameObject e : world.getObjects()) {
            transformations.setMVPUniform(camera, e, mvpMatrixBuffer);
            glUniformMatrix4fv(matrixId, false, mvpMatrixBuffer);
            e.getRenderInfo().getMesh().render();
        }

        glViewport(0, 0, 640, 640);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);

        shader.unbind();

        long vg = manager.getVgContext();

        nvgBeginFrame(vg, window.getWidth(), window.getHeight(), 1);
        nvgBeginPath(vg);
        nvgRect(vg, 0, window.getHeight() - (window.getHeight() / 2f), window.getWidth(), 50);
        nvgFillColor(vg, nvgRGBA((byte) 0x23, (byte) 0xa1, (byte) 0xf1, (byte) 200, a));
        nvgFill(vg);
        userInterface.draw();

        nvgFontSize(vg, 40.0f);
        nvgFontFaceId(vg, fontID);
        nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_TOP);
        nvgFillColor(vg, nvgRGBA((byte) 0x23, (byte) 0xa1, (byte) 0xf1, (byte) 200, a));
        nvgText(vg, window.getHeight() - (window.getHeight() / 2f), window.getHeight() - 150, "TESTING x: " + camera.getOffSetX());

        nvgEndFrame(vg);

        window.restoreState();
        window.update();
    }
}
