package com.hanssonnet.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Point<T> {
    public Integer x;
    public Integer y;
    public T content;
    public Point<T> previous;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(int x, int y, Point<T> previous) {
        this.x = x;
        this.y = y;
        this.previous = previous;
    }

    public Point(int x, int y, T content) {
        this.x = x;
        this.y = y;
        this.content = content;
    }

    public Point(int x, int y, T content, Point<T> previous) {
        this.x = x;
        this.y = y;
        this.content = content;
        this.previous = previous;
    }

    public Point<T> navigate(Direction direction) {
        return switch (direction) {
            case NORTH -> new Point<>(x, y - 1, this);
            case NORTHEAST -> new Point<>(x + 1, y - 1, this);
            case EAST -> new Point<>(x + 1, y, this);
            case SOUTHEAST -> new Point<>(x + 1, y + 1, this);
            case SOUTH -> new Point<>(x, y + 1, this);
            case SOUTHWEST -> new Point<>(x - 1, y + 1, this);
            case WEST -> new Point<>(x - 1, y, this);
            case NORTHWEST -> new Point<>(x - 1, y - 1, this);
            default -> this;
        };
    }

    public Point<T> navigate(Direction direction, int steps) {
        Point<T> current = this;
        for (int i = 0; i < steps; i++) {
            current = current.navigate(direction);
        }
        return current;
    }

    public Point<T> moveTo(Point<T> other) {
        return new Point<>(other.x, other.y, this.content, this.previous);
    }

    public List<Point<T>> getNeighbors() {
        List<Point<T>> neighbors = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            neighbors.add(navigate(direction));
        }
        return neighbors;
    }

    public Map<Direction, List<Point<T>>> getXNeighborsInDirection(int steps) {
        Map<Direction, List<Point<T>>> map = new HashMap<>();
        for (Direction direction : Direction.values()) {
            List<Point<T>> points = new ArrayList<>();
            for (int i = 0; i < steps; i++) {
                points.add(navigate(direction, i + 1));
            }
            map.put(direction, points);
        }
        return map;
    }

    public Direction getDirection(Point<T> other) {
        int dx = other.x - x;
        int dy = other.y - y;
        if (dx == 0 && dy < 0) {
            return Direction.NORTH;
        } else if (dx > 0 && dy < 0) {
            return Direction.NORTHEAST;
        } else if (dx > 0 && dy == 0) {
            return Direction.EAST;
        } else if (dx > 0 && dy > 0) {
            return Direction.SOUTHEAST;
        } else if (dx == 0 && dy > 0) {
            return Direction.SOUTH;
        } else if (dx < 0 && dy > 0) {
            return Direction.SOUTHWEST;
        } else if (dx < 0 && dy == 0) {
            return Direction.WEST;
        } else if (dx < 0 && dy < 0) {
            return Direction.NORTHWEST;
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point<?> point = (Point<?>) o;
        return x.equals(point.x) && y.equals(point.y);
    }

    @Override
    public int hashCode() {
        return 31 * x.hashCode() + y.hashCode();
    }

    @Override
    public String toString() {
        return String.format("Point{x=%s, y=%s}", x, y);
    }

    @Override
    public Point<T> clone() {
        T clonedContent = null;
        if (content instanceof Cloneable) {
            try {
                var method = content.getClass().getMethod("clone");
                clonedContent = (T) method.invoke(content);
            } catch (Exception e) {
                clonedContent = content;
            }
        } else {
            clonedContent = content;
        }
        Point<T> clonedPrevious = previous != null ? previous.clone() : null;
        return new Point<>(x, y, clonedContent, clonedPrevious);
    }
}
