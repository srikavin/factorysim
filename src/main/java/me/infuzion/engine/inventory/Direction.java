package me.infuzion.engine.inventory;

public enum Direction {
    TOP_LEFT,
    TOP_RIGHT,
    TOP,
    LEFT_TOP,
    LEFT_BOTTOM,
    LEFT,
    RIGHT_TOP,
    RIGHT_BOTTOM,
    RIGHT,
    BOTTOM_LEFT,
    BOTTOM_RIGHT,
    BOTTOM;

    public boolean isFromTop() {
        return this == TOP || this == TOP_LEFT || this == TOP_RIGHT;
    }

    public boolean isFromBottom() {
        return this == BOTTOM || this == BOTTOM_LEFT || this == BOTTOM_RIGHT;
    }

    public boolean isFromLeft() {
        return this == LEFT || this == LEFT_BOTTOM || this == LEFT_TOP;
    }

    public boolean isFromRight() {
        return this == RIGHT || this == RIGHT_BOTTOM || this == RIGHT_TOP;
    }
}
