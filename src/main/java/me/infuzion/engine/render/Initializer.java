package me.infuzion.engine.render;

import me.infuzion.engine.input.InputHandler;
import me.infuzion.engine.sprite.SpriteLoader;

public interface Initializer {
    Renderer initRenderer(SpriteLoader loader);

    InputHandler initInput();
}
