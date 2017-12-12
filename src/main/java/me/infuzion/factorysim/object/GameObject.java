package me.infuzion.factorysim.object;

import me.infuzion.factorysim.GameWorld;
import me.infuzion.factorysim.Position;
import me.infuzion.factorysim.render.RenderInfo;
import me.infuzion.factorysim.sprite.SpriteLoader;

import java.util.UUID;

public abstract class GameObject {
    protected Position gridPos = Position.positionFor(0, 0);
    private final UUID uuid = UUID.randomUUID();

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
