package me.infuzion.fractorio.render;

import me.infuzion.fractorio.input.InputHandler;
import me.infuzion.fractorio.render.Renderer;
import me.infuzion.fractorio.sprite.SpriteLoader;

public interface Initializer {
    Renderer initRenderer(SpriteLoader loader);

    InputHandler initInput();
}
