package me.infuzion.factorysim.render;

import me.infuzion.factorysim.input.InputHandler;
import me.infuzion.factorysim.sprite.SpriteLoader;

public interface Initializer {
    Renderer initRenderer(SpriteLoader loader);

    InputHandler initInput();
}
