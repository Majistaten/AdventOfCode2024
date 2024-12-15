package com.hanssonnet.days;

import com.hanssonnet.core.AbstractDay;
import com.hanssonnet.shared.Direction;
import com.hanssonnet.shared.Point;
import com.hanssonnet.util.Util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

public class Day10 extends AbstractDay {
    List<String> topographicMap;
    BiFunction<Integer, Integer, Boolean> isWithinBounds;
    private final Direction[] validDirections = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};

    public Day10() {
        super("day10");
    }

    @Override
    public String part1(String input) {
        topographicMap = Util.splitLines(input);
        isWithinBounds = Util.isWithinBounds(topographicMap);

        var startPoints = new HashSet<Point<Set<Point<Integer>>>>();
        extractStartPoints(topographicMap, startPoints);

        for (var startPoint : startPoints) {
            findTrailHeads(topographicMap, startPoint);
        }

        var trailheadPoints = countPoints(startPoints);

        return "Result: %d".formatted(trailheadPoints);
    }

    @Override
    public String part2(String input) {
        topographicMap = Util.splitLines(input);
        isWithinBounds = Util.isWithinBounds(topographicMap);

        var startPoints = new HashSet<Point<Set<List<Point<Integer>>>>>();
        extractStartPoints(topographicMap, startPoints);

        for (var startPoint : startPoints) {
            findDistinctTrails(topographicMap, startPoint);
        }

        var distinctTrailCount = countPoints(startPoints);

        return "Result: %d".formatted(distinctTrailCount);
    }

    private void findTrailHeads(List<String> topographicMap, Point<Set<Point<Integer>>> startPoint) {
        Set<Point<Integer>> visited = new HashSet<>();
        findTrailHeadsRecursive(topographicMap, startPoint, new Point<>(startPoint.x, startPoint.y), visited, validDirections);
    }

    private void findTrailHeadsRecursive(
            List<String> topographicMap,
            Point<Set<Point<Integer>>> startPoint,
            Point<Integer> currentPoint,
            Set<Point<Integer>> visited,
            Direction[] validDirections
    ) {
        visited.add(currentPoint);

        for (Direction direction : validDirections) {
            Point<Integer> nextPoint = currentPoint.clone().navigate(direction);

            if (!isWithinBounds.apply(nextPoint.x, nextPoint.y) || visited.contains(nextPoint)) continue;

            int currentValue = getValueFromPoint(topographicMap, currentPoint);
            int nextValue = getValueFromPoint(topographicMap, nextPoint);

            if (nextValue == currentValue + 1) {
                if (nextValue == 9) {
                    startPoint.content.add(nextPoint);
                } else {
                    findTrailHeadsRecursive(topographicMap, startPoint, nextPoint, visited, validDirections);
                }
            }
        }

        visited.remove(currentPoint);
    }

    private void findDistinctTrails(
            List<String> topographicMap,
            Point<Set<List<Point<Integer>>>> startPoint
    ) {
        var initialPath = new ArrayList<Point<Integer>>();
        initialPath.add(new Point<>(startPoint.x, startPoint.y));
        findDistinctTrailsRecursive(topographicMap, startPoint, new Point<>(startPoint.x, startPoint.y), initialPath);
    }

    private void findDistinctTrailsRecursive(
            List<String> topographicMap,
            Point<Set<List<Point<Integer>>>> startPoint,
            Point<Integer> currentPoint,
            List<Point<Integer>> currentPath
    ) {
        for (Direction direction : validDirections) {
            Point<Integer> nextPoint = currentPoint.clone().navigate(direction);

            if (!isWithinBounds.apply(nextPoint.x, nextPoint.y)) continue;

            int currentValue = getValueFromPoint(topographicMap, currentPoint);
            int nextValue = getValueFromPoint(topographicMap, nextPoint);

            if (nextValue == currentValue + 1) {
                var newPath = new ArrayList<Point<Integer>>(currentPath);
                newPath.add(nextPoint);

                if (nextValue == 9) {
                    startPoint.content.add(newPath);
                } else {
                    findDistinctTrailsRecursive(topographicMap, startPoint, nextPoint, newPath);
                }
            }
        }
    }

    private int getValueFromPoint(List<String> topographicMap, Point<?> currentPoint) {
        return Character.getNumericValue(topographicMap.get(currentPoint.y).charAt(currentPoint.x));
    }

    private static <T> void extractStartPoints(List<String> topographicMap, Set<Point<Set<T>>> startPoints) {
        for (int y = 0; y < topographicMap.size(); y++) {
            for (int x = 0; x < topographicMap.getFirst().length(); x++) {
                if (topographicMap.get(y).charAt(x) == '0') {
                    startPoints.add(new Point<>(x, y, new HashSet<>()));
                }
            }
        }
    }

    private static <T> Integer countPoints(HashSet<Point<Set<T>>> startPoints) {
        return startPoints.stream()
                .map(point -> point.content.size())
                .reduce(Integer::sum)
                .orElse(0);
    }
}
