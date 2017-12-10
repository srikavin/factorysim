package me.infuzion.fractorio.inventory;

import me.infuzion.fractorio.item.Item;
import me.infuzion.fractorio.item.ItemStack;

public interface Inventory {
    boolean hasItem(Item item);

    ItemStack[] getItems();

    boolean addItem(ItemStack stack, Direction insertionDirection);

    boolean isActiveInventory();
}
