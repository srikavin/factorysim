package me.infuzion.engine.item;

import javafx.scene.image.Image;
import me.infuzion.engine.sprite.SpriteLoader;

public abstract class Item {
    public void init(SpriteLoader spriteLoader) {

    }

    public abstract Image draw();

    public abstract boolean isBurnable();

    @Override
    public boolean equals(Object obj) {
        return obj.getClass().equals(this.getClass());
    }
}
