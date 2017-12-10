package me.infuzion.fractorio;

public enum FacingDirection {
    NORTH(0),
    SOUTH(180),
    EAST(90),
    WEST(270);

    private final int rotation;

    FacingDirection(int rotation) {
        this.rotation = rotation;
    }

    public int getRotation() {
        return rotation;
    }
}
