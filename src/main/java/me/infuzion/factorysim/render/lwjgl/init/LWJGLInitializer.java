package me.infuzion.factorysim.render.lwjgl.init;

import me.infuzion.factorysim.input.InputHandler;
import me.infuzion.factorysim.input.LWJGLInputHandler;
import me.infuzion.factorysim.render.Initializer;
import me.infuzion.factorysim.render.Renderer;
import me.infuzion.factorysim.render.lwjgl.LWJGLRenderer;
import me.infuzion.factorysim.sprite.SpriteLoader;
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
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_SAMPLES, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);

        window = glfwCreateWindow(640, 640, "Factory Simulator", NULL, NULL);

        if (window == NULL) {
            throw new IllegalStateException("Unable to create GLFW Window");
        }

        // Get the resolution of the primary monitor
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Center our window
        glfwSetWindowPos(
                window,
                (vidmode.width() - 640) / 2,
                (vidmode.height() - 640) / 2
        );

        if (window == NULL) {
            initWindow();
        }

        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        glfwSwapInterval(1);
        glfwShowWindow(window);
        renderer = new LWJGLRenderer(window);
        inputHandler = new LWJGLInputHandler(window);
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
