package me.infuzion.fractorio.item.base;

import javafx.scene.image.Image;
import me.infuzion.fractorio.item.Item;
import me.infuzion.fractorio.sprite.Sprite;
import me.infuzion.fractorio.sprite.SpriteLoader;

public class IronPlate extends Item {
    private static Sprite sprite;

    @Override
    public void init(SpriteLoader loader) {
        super.init(loader);
        if (sprite == null) {
            sprite = loader.loadSprite("base", "item_iron_plate");
        }
    }

    @Override
    public Image draw() {
        return sprite.draw();
    }

    @Override
    public boolean isBurnable() {
        return false;
    }
}
