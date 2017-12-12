package me.infuzion.factorysim.render;

import me.infuzion.factorysim.GameWorld;

public interface Renderer {
    void render(GameWorld world, Camera camera);
}
