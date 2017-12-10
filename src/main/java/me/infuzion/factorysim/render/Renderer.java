package me.infuzion.fractorio.render;

import me.infuzion.fractorio.GameWorld;

public interface Renderer {
    void render(GameWorld world, Camera camera);
}
