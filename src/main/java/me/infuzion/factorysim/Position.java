package me.infuzion.fractorio;

import java.util.ArrayList;
import java.util.List;

public class Position {
    private static List<Position> cache = new ArrayList<>();

    private final int x;
    private final int y;
    private final FacingDirection direction;

    Position(int x, int y, FacingDirection direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        cache.add(this);
    }

    public static Position positionFor(int x, int y) {
        return positionFor(x, y, FacingDirection.NORTH);
    }

    public static Position positionFor(int x, int y, FacingDirection dir) {
        for (Position e : cache) {
            if (e.x == x && e.y == y && e.direction == dir) {
                return e;
            }
        }
        return new Position(x, y, dir);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        return x == position.x && y == position.y && direction == position.direction;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + direction.hashCode();
        return result;
    }

    public int getX() {
        return x;
    }

    public Position above() {
        return positionFor(x, y - 1);
    }

    public Position below() {
        return positionFor(x, y + 1);
    }

    public Position left() {
        return positionFor(x - 1, y);
    }

    public Position right() {
        return positionFor(x + 1, y);
    }

    public int getY() {
        return y;
    }

    public FacingDirection getDirection() {
        return direction;
    }
}
