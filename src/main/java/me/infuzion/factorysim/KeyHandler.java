package me.infuzion.fractorio;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.EnumSet;

public class KeyHandler {

    private final GameWorld world;
    private final EnumSet<Key> currentlyPressed = EnumSet.noneOf(Key.class);

    public KeyHandler(GameWorld world) {
        this.world = world;
    }

    public void onKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.A) {
            currentlyPressed.add(Key.LEFT);
        } else if (event.getCode() == KeyCode.D) {
            currentlyPressed.add(Key.RIGHT);
        } else if (event.getCode() == KeyCode.W) {
            currentlyPressed.add(Key.UP);
        } else if (event.getCode() == KeyCode.S) {
            currentlyPressed.add(Key.DOWN);
        }
    }

    public void onKeyRelease(KeyEvent event) {
        if (event.getCode() == KeyCode.A) {
            currentlyPressed.remove(Key.LEFT);
        } else if (event.getCode() == KeyCode.D) {
            currentlyPressed.remove(Key.RIGHT);
        } else if (event.getCode() == KeyCode.W) {
            currentlyPressed.remove(Key.UP);
        } else if (event.getCode() == KeyCode.S) {
            currentlyPressed.remove(Key.DOWN);
        }
    }

    public void tick() {
        for (Key e : currentlyPressed) {
            switch (e) {
                case LEFT:
                    world.panLeft();
                    break;
                case RIGHT:
                    world.panRight();
                    break;
                case UP:
                    world.panUp();
                    break;
                case DOWN:
                    world.panDown();
                    break;
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
