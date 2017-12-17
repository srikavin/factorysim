package me.infuzion.engine.item;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import me.infuzion.engine.sprite.SpriteLoader;

public class ItemStack {
    private final Item type;
    private int amount;
    SpriteLoader loader = new SpriteLoader();

    public ItemStack(Item type) {
        this(type, 1);
    }

    public ItemStack(Item type, int amount) {
        this.type = type;
        type.init(loader);
        this.amount = amount;
    }

    public Item getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public void draw(GraphicsContext context, int x, int y, double scale) {
        Image img = type.draw();
        context.drawImage(img, x, y, img.getWidth() * scale, img.getHeight() * scale);
        context.strokeText(String.valueOf(amount), x, y);
    }
}
