package com.hanssonnet.shared;

public enum Direction {
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST;

    private static final Direction[] directions = values();

    public Direction turnRight() {
        return directions[(this.ordinal() + 1) % directions.length];
    }

    public Direction turnLeft() {
        return directions[(this.ordinal() - 1 + directions.length) % directions.length];
    }

    public Direction turnRight90Deg() { return turnRight().turnRight(); }

    public Direction turnLeft90Deg() { return turnLeft().turnLeft(); }
}
