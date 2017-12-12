package me.infuzion.factorysim.item.base;

import javafx.scene.image.Image;
import me.infuzion.factorysim.item.Item;
import me.infuzion.factorysim.sprite.Sprite;
import me.infuzion.factorysim.sprite.SpriteLoader;

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
