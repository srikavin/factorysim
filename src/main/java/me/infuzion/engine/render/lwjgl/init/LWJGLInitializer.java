package me.infuzion.engine.render.lwjgl.init;

import me.infuzion.engine.input.InputHandler;
import me.infuzion.engine.input.LWJGLInputHandler;
import me.infuzion.engine.render.Initializer;
import me.infuzion.engine.render.Renderer;
import me.infuzion.engine.render.lwjgl.LWJGLRenderer;
import me.infuzion.engine.render.lwjgl.Window;
import me.infuzion.engine.sprite.SpriteLoader;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.Configuration;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.system.MemoryUtil.NULL;

public class LWJGLInitializer implements Initializer {
    private long window;
    private Renderer renderer = null;
    private InputHandler inputHandler = null;

    private void initWindow() {
        glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));
        Configuration.DEBUG.set(true);

        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_SAMPLES, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);

        window = glfwCreateWindow(640, 480, "Factory Simulator", NULL, NULL);

        if (window == NULL) {
            throw new IllegalStateException("Unable to create GLFW Window");
        }

        // Get the resolution of the primary monitor
        GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Center our window
        glfwSetWindowPos(
                window,
                (videoMode.width() - 640) / 2,
                (videoMode.height() - 640) / 2
        );

        if (window == NULL) {
            initWindow();
        }

        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        glfwSwapInterval(1);
        glfwShowWindow(window);
        Window windowWrapper = new Window(window);
        renderer = new LWJGLRenderer(windowWrapper);
        inputHandler = new LWJGLInputHandler(windowWrapper);
    }

    @Override
    public Renderer initRenderer(SpriteLoader loader) {
        if (renderer == null) {
            initWindow();
        }
        return renderer;
    }

    @Override
    public InputHandler initInput() {
        if (inputHandler == null) {
            initWindow();
        }
        return inputHandler;
    }
}
