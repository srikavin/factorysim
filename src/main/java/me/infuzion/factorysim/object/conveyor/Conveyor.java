package me.infuzion.factorysim.object.conveyor;

import me.infuzion.engine.inventory.Direction;
import me.infuzion.engine.inventory.Inventory;
import me.infuzion.engine.item.Item;
import me.infuzion.engine.item.ItemStack;
import me.infuzion.engine.render.RenderInfo;
import me.infuzion.engine.sprite.SpriteIdentifier;
import me.infuzion.engine.sprite.SpriteLoader;
import me.infuzion.engine.world.GameObject;
import me.infuzion.engine.world.GameWorld;
import me.infuzion.factorysim.item.IronPlate;

public class Conveyor extends GameObject implements Inventory {

    private final ItemStack[] items = new ItemStack[6];
    private RenderInfo renderInfo;
    private int tick = 0;

    public Conveyor() {
        items[5] = new ItemStack(new IronPlate(), 1);
        items[4] = new ItemStack(new IronPlate(), 1);
        renderInfo = new RenderInfo(new SpriteIdentifier("base", "conveyor", "up"), true);
    }

    @Override
    public void init(SpriteLoader loader) {
        super.init(loader);
    }

    @Override
    public boolean hasItem(Item item) {
        for (ItemStack e : items) {
            if (e.getType().equals(item)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ItemStack[] getItems() {
        return items;
    }

    @Override
    public boolean addItem(ItemStack stack, Direction dir) {
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

    @Override
    public void tick(GameWorld world) {
        tick++;
        if (items[0] != null || items[1] != null) {
            GameObject object = world.getObjectAt(getPosition().above());
            if (object != null && object instanceof Inventory) {
                Inventory inventory = (Inventory) object;
                if (inventory.isActiveInventory()) {
                    // do nothing
                } else if (items[0] != null) {
                    if (inventory.addItem(items[0], Direction.BOTTOM_LEFT)) {
                        items[0] = null;
                    }
                } else if (items[1] != null) {
                    if (inventory.addItem(items[1], Direction.BOTTOM_RIGHT)) {
                        items[1] = null;
                    }
                }
            }
        }

        if (tick % 20 == 0) {
            shiftItems();
        }
    }

    protected void shiftItems() {
        shiftItems(true);
        shiftItems(false);
    }

    private void shiftItems(boolean leftSide) {
        boolean lastNull = false;
        for (int i = leftSide ? 0 : 1; i < 6; i += 2) {
            if (items[i] != null) {
                if (lastNull) {
                    items[i - 2] = items[i];
                    items[i] = null;
                    lastNull = true;
                }
            } else {
                lastNull = true;
            }
        }
    }

    protected final class ItemsToTransfer {
        private Item[] items;
        private Direction[] directions;

        public ItemsToTransfer(Item[] items, Direction[] directions) {
            this.items = items;
            this.directions = directions;
        }

        public Direction[] getDirections() {
            return directions;
        }

        public Item[] getItems() {
            return items;
        }
    }
}
