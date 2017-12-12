package me.infuzion.factorysim.object.base;

import me.infuzion.factorysim.GameWorld;
import me.infuzion.factorysim.inventory.Direction;
import me.infuzion.factorysim.inventory.Inventory;
import me.infuzion.factorysim.item.Item;
import me.infuzion.factorysim.item.ItemStack;
import me.infuzion.factorysim.object.GameObject;
import me.infuzion.factorysim.render.RenderInfo;
import me.infuzion.factorysim.sprite.SpriteIdentifier;

import java.util.Objects;

public class Furnace extends GameObject implements Inventory {
    //    0 -> input
    //    1 -> fuel
    //    2 -> output
    private ItemStack[] inventory = new ItemStack[3];
    private RenderInfo renderInfo;

    public Furnace() {
        renderInfo = new RenderInfo(new SpriteIdentifier("base", "furnace", "on"), true);
    }

    @Override
    public boolean hasItem(Item item) {
        return (Objects.nonNull(inventory[0]) && inventory[0].getType().equals(item))
                || (Objects.nonNull(inventory[1]) && inventory[1].getType().equals(item))
                || (Objects.nonNull(inventory[2]) && inventory[2].getType().equals(item));
    }

    @Override
    public ItemStack[] getItems() {
        return inventory;
    }

    @Override
    public boolean addItem(ItemStack stack, Direction insertionDirection) {
        if (stack.getType().isBurnable() && inventory[1].getType().equals(stack.getType())) {
            inventory[1] = stack;
            return true;
        }
        return false;
    }

    @Override
    public boolean isActiveInventory() {
        return false;
    }

    @Override
    public RenderInfo getRenderInfo() {
        return renderInfo;
    }


    int tick = 0;
    @Override
    public void tick(GameWorld world) {
        if(tick == 100){
            renderInfo.setSprite(new SpriteIdentifier("base", "furnace", "on"));
        }
    }
}
