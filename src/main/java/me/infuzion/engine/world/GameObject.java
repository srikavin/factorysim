package me.infuzion.engine.world;

import me.infuzion.engine.render.RenderInfo;
import me.infuzion.engine.sprite.SpriteLoader;

import java.util.Random;
import java.util.UUID;

public abstract class GameObject {
    private final Random r = new Random();
    private final UUID uuid = UUID.randomUUID();
    protected Position gridPos = Position.positionFor(r.nextInt(2), r.nextInt(3));

    public Position getPosition() {
        return gridPos;
    }

    public void setPosition(Position position) {
        this.gridPos = position;
    }

    public abstract RenderInfo getRenderInfo();

    public abstract void tick(GameWorld world);

    public void init(SpriteLoader loader) {

    }

    public final UUID getUuid() {
        return uuid;
    }
}
