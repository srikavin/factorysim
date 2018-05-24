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

import static org.lwjgl.nanovg.NanoVG.nvgBeginFrame;
import static org.lwjgl.nanovg.NanoVG.nvgEndFrame;
import static org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray;
import static org.lwjgl.opengl.ARBVertexArrayObject.glGenVertexArrays;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;

public class LWJGLRenderer implements Renderer {
    int fontID;
    UserInterface userInterface;
    private Transformations transformations;
    private Window window;
    private int vaoId;
    private ShaderProgram shader;
    private UIManager manager;

    public LWJGLRenderer(Window window) {
        this.window = window;

        reloadShaders();

        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glEnable(GL_STENCIL_TEST);
        glDepthFunc(GL_LESS);

        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);
        glBindVertexArray(0);
        transformations = new Transformations();
        manager = new UIManager(window);
        fontID = manager.getFont("assets/fonts/a.ttf", "a");

        userInterface = new UserInterface(manager, new Text(manager));
    }

    public void reloadShaders() {
        shader = new ShaderProgram();
        shader.createVertexShader(Utilities.getResource("shaders/vertex.glsl"));
        shader.createFragmentShader(Utilities.getResource("shaders/fragment.glsl"));
        shader.link();

        shader.createUniform("projectionMatrix");
        shader.createUniform("worldMatrix");
    }

    @Override
    public void render(GameWorld world, Camera camera) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);

        if (window.isResized()) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }
        shader.bind();

        glBindVertexArray(vaoId);

        shader.setUniform("projectionMatrix",
                transformations.getProjectionUniform(window.getWidth(), window.getHeight()));
        for (GameObject e : world.getObjects()) {
            shader.setUniform("worldMatrix",
                    transformations.getWorldMatrix(e.getPosition().asVector(), e.getRotation().asVector(), e.getScale()));
            e.getRenderInfo().getMesh().render();
        }

        glViewport(0, 0, 640, 640);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);

        shader.unbind();

        long vg = manager.getVgContext();

        nvgBeginFrame(vg, window.getWidth(), window.getHeight(), 1);
        userInterface.draw();
        nvgEndFrame(vg);

        window.restoreState();
        window.update();
    }
}
