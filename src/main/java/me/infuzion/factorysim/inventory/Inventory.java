package me.infuzion.factorysim.inventory;

import me.infuzion.factorysim.item.Item;
import me.infuzion.factorysim.item.ItemStack;

public interface Inventory {
    boolean hasItem(Item item);

    ItemStack[] getItems();

    boolean addItem(ItemStack stack, Direction insertionDirection);

    boolean isActiveInventory();
}
