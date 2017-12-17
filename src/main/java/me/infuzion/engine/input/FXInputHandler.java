package me.infuzion.engine.input;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.EnumSet;
import java.util.Set;

public class FXInputHandler implements InputHandler {
    private EnumSet<KeyInput> currentlyPressed = EnumSet.noneOf(KeyInput.class);

    public FXInputHandler(Canvas canvas) {
        canvas.setOnKeyPressed(this::onKeyPress);
        canvas.setOnKeyReleased(this::onKeyRelease);
    }

    private void onKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.A) {
            currentlyPressed.add(KeyInput.MOVE_LEFT);
        } else if (event.getCode() == KeyCode.D) {
            currentlyPressed.add(KeyInput.MOVE_RIGHT);
        } else if (event.getCode() == KeyCode.W) {
            currentlyPressed.add(KeyInput.MOVE_UP);
        } else if (event.getCode() == KeyCode.S) {
            currentlyPressed.add(KeyInput.MOVE_DOWN);
        }
    }

    private void onKeyRelease(KeyEvent event) {
        if (event.getCode() == KeyCode.A) {
            currentlyPressed.remove(KeyInput.MOVE_LEFT);
        } else if (event.getCode() == KeyCode.D) {
            currentlyPressed.remove(KeyInput.MOVE_RIGHT);
        } else if (event.getCode() == KeyCode.W) {
            currentlyPressed.remove(KeyInput.MOVE_UP);
        } else if (event.getCode() == KeyCode.S) {
            currentlyPressed.remove(KeyInput.MOVE_DOWN);
        }
    }

    @Override
    public Set<KeyInput> getKeyEvents() {
        return currentlyPressed;
    }

    @Override
    public void tick() {
    }
}
