package me.infuzion.engine.render.lwjgl;

import org.lwjgl.system.MemoryUtil;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Window {
    private final long pointer;
    private String title;
    private boolean resized;
    private int height;
    private int width;

    public Window(long pointer) {
        this.pointer = pointer;

        IntBuffer h = MemoryUtil.memAllocInt(1);
        IntBuffer w = MemoryUtil.memAllocInt(1);

        glfwGetFramebufferSize(pointer, h, w);

        height = h.get();
        width = w.get();

        MemoryUtil.memFree(h);
        MemoryUtil.memFree(w);

        glfwSetFramebufferSizeCallback(pointer, (window, width, height) -> {
            this.width = width;
            this.height = height;
            this.resized = true;
        });
    }

    public void update() {
        glfwSwapBuffers(pointer);
    }

    public long getPointer() {
        return pointer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        glfwSetWindowTitle(pointer, title);
        this.title = title;
    }

    public void restoreState() {
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_STENCIL_TEST);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_CULL_FACE);
    }

    public boolean isResized() {
        return resized;
    }

    public void setResized(boolean resized) {
        this.resized = resized;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
