package me.infuzion.engine.input;

import java.util.Set;

public interface InputHandler {
    Set<KeyInput> getKeyEvents();

    void tick();
}
