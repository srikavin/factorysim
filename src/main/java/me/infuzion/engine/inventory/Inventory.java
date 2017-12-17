package me.infuzion.engine.inventory;

import me.infuzion.engine.item.Item;
import me.infuzion.engine.item.ItemStack;

public interface Inventory {
    boolean hasItem(Item item);

    ItemStack[] getItems();

    boolean addItem(ItemStack stack, Direction insertionDirection);

    boolean isActiveInventory();
}
