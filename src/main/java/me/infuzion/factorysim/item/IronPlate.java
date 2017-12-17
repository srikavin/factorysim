package me.infuzion.factorysim.item;

import javafx.scene.image.Image;
import me.infuzion.engine.item.Item;
import me.infuzion.engine.sprite.Sprite;
import me.infuzion.engine.sprite.SpriteLoader;

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
