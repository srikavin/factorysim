package me.infuzion.factorysim;

import me.infuzion.engine.input.InputHandler;
import me.infuzion.engine.input.KeyInput;
import me.infuzion.engine.world.GameWorld;

import java.util.List;
import java.util.Set;

public class KeyHandler {
    private final GameWorld world;
    private List<InputHandler> inputs;

    public KeyHandler(GameWorld world, List<InputHandler> inputs) {
        this.world = world;
        this.inputs = inputs;
    }

    public void tick() {
        for (InputHandler input : inputs) {
            Set<KeyInput> current = input.getKeyEvents();

            for (KeyInput e : current) {
                System.out.println("handling " + e);
                switch (e) {
                    case MOVE_LEFT:
                        world.panLeft();
                        break;
                    case MOVE_RIGHT:
                        world.panRight();
                        break;
                    case MOVE_UP:
                        world.panUp();
                        break;
                    case MOVE_DOWN:
                        world.panDown();
                        break;
                    case ZOOM_IN:
                        world.zoomIn();
                        break;
                    case ZOOM_OUT:
                        world.zoomOut();
                        break;
                }
            }
        }
    }

    private enum Key {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }
}
