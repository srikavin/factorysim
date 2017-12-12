package me.infuzion.factorysim.input;

import java.util.EnumSet;
import java.util.Set;

import static org.lwjgl.glfw.GLFW.*;

public class LWJGLInputHandler implements InputHandler {
    private EnumSet<KeyInput> set = EnumSet.noneOf(KeyInput.class);

    public LWJGLInputHandler(long windowP) {
        glfwSetScrollCallback(windowP, (window, xoffset, yoffset) -> {
            if (yoffset > 0) {
                process(KeyInput.ZOOM_OUT, false);
                process(KeyInput.ZOOM_IN, true);
            } else if (yoffset < 0) {
                process(KeyInput.ZOOM_IN, false);
                process(KeyInput.ZOOM_OUT, true);
            } else {
                process(KeyInput.ZOOM_IN, false);
                process(KeyInput.ZOOM_OUT, false);
            }
        });
        glfwSetKeyCallback(windowP, (window, key, scancode, action, mods) -> {
            switch (key) {
                case GLFW_KEY_ESCAPE:
                    process(KeyInput.QUIT, action);
                    break;
                case GLFW_KEY_W:
                    process(KeyInput.MOVE_UP, action);
                    break;
                case GLFW_KEY_A:
                    process(KeyInput.MOVE_LEFT, action);
                    break;
                case GLFW_KEY_S:
                    process(KeyInput.MOVE_DOWN, action);
                    break;
                case GLFW_KEY_D:
                    process(KeyInput.MOVE_RIGHT, action);
                    break;
            }
        });
    }

    private void process(KeyInput keyInput, boolean press) {
        System.out.println(keyInput + "" + press);
        if (press) {
            set.add(keyInput);
        } else {
            set.remove(keyInput);
        }
    }

    private void process(KeyInput keyInput, int action) {
        if (action == GLFW_PRESS || action == GLFW_REPEAT) {
            process(keyInput, true);
        } else {
            process(keyInput, false);
        }
    }

    @Override
    public Set<KeyInput> getKeyEvents() {
        return set;
    }

    @Override
    public void tick() {
        set.remove(KeyInput.ZOOM_IN);
        set.remove(KeyInput.ZOOM_OUT);
        glfwPollEvents();
    }
}
