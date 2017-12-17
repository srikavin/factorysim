package me.infuzion.engine.render;

import me.infuzion.engine.world.GameWorld;

public interface Renderer {
    void render(GameWorld world, Camera camera);
}
