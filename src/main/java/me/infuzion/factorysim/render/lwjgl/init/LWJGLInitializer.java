package me.infuzion.fractorio.render.lwjgl.init;

import me.infuzion.fractorio.input.InputHandler;
import me.infuzion.fractorio.input.LWJGLInputHandler;
import me.infuzion.fractorio.render.Initializer;
import me.infuzion.fractorio.render.Renderer;
import me.infuzion.fractorio.render.lwjgl.LWJGLRenderer;
import me.infuzion.fractorio.sprite.SpriteLoader;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.Configuration;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
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
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        window = glfwCreateWindow(640, 640, "Fractorio", NULL, NULL);

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
