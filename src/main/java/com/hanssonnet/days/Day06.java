package com.hanssonnet.days;

import com.hanssonnet.core.AbstractDay;
import com.hanssonnet.shared.Direction;
import com.hanssonnet.shared.Point;
import com.hanssonnet.util.Util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

public class Day06 extends AbstractDay {
    public Day06() {
        super("day06");
    }

    @Override
    public String part1(String input) {
        var map = Util.splitLines(input);
        var guardPoint = getGuardPoint(map);
        var visitedPoints = simulateMovement(map, guardPoint, Direction.NORTH);

        return String.format("Result: %d", visitedPoints.size());
    }

    @Override
    public String part2(String input) {
        var map = Util.splitLines(input);
        var guardStartPoint = getGuardPoint(map);
        var guardStartDirection = Direction.NORTH;

        var isWithinBounds = Util.isWithinBounds(map);

        Set<String> visitedPoints = simulateMovementWithState(guardStartPoint, guardStartDirection, isWithinBounds, map);
        Set<String> cyclePositions = generateCycles(visitedPoints, guardStartDirection, guardStartPoint, isWithinBounds, map);

        return String.format("Result: %d", cyclePositions.size());
    }

    private Point<Direction> getGuardPoint(List<String> map) {
        for (var line : map) {
            if (line.contains("^")) {
                return new Point<>(line.indexOf("^"), map.indexOf(line));
            }
        }
        throw new RuntimeException("No guard found");
    }

    private Set<Point<Direction>> simulateMovement(List<String> map, Point<Direction> startPoint, Direction startDirection) {
        var guardPoint = startPoint;
        var guardDirection = startDirection;
        var isWithinBounds = Util.isWithinBounds(map);

        Set<Point<Direction>> visitedPoints = new HashSet<>();
        visitedPoints.add(guardPoint);

        while (true) {
            var nextStep = guardPoint.navigate(guardDirection);
            if (!isWithinBounds.apply(nextStep.x, nextStep.y)) break;

            if (map.get(nextStep.y).charAt(nextStep.x) == '#') {
                guardDirection = guardDirection.turnRight90Deg();
            } else {
                guardPoint = nextStep;
                visitedPoints.add(guardPoint);
            }
        }
        return visitedPoints;
    }

    private Set<String> simulateMovementWithState(Point<Direction> guardPoint, Direction guardDirection, BiFunction<Integer, Integer, Boolean> isWithinBounds, List<String> map) {
        Set<String> visitedPoints = new HashSet<>();

        while (true) {
            var nextStep = guardPoint.navigate(guardDirection);
            var withinMap = isWithinBounds.apply(nextStep.x, nextStep.y);
            if (!withinMap) break;

            var mustTurn = map.get(nextStep.y).charAt(nextStep.x) == '#';
            if (mustTurn) {
                guardDirection = guardDirection.turnRight90Deg();
            } else {
                guardPoint = nextStep;
                visitedPoints.add(createStateKey(guardPoint, guardDirection));
            }
        }
        return visitedPoints;
    }

    private Set<String> generateCycles(Set<String> visitedPoints, Direction guardStartDirection, Point<Direction> guardStartPoint, BiFunction<Integer, Integer, Boolean> isWithinBounds, List<String> map) {
        Direction guardDirection;
        Point<Direction> guardPoint;
        var cyclePositions = new HashSet<String>();
        for (var state : visitedPoints) {
            Set<String> turningPoints = new HashSet<>();
            guardDirection = guardStartDirection;
            guardPoint = guardStartPoint;
            var prevMatched = false;

            while (true) {
                var nextStep = guardPoint.navigate(guardDirection);
                var nextState = createStateKey(nextStep, guardDirection);

                var withinMap = isWithinBounds.apply(nextStep.x, nextStep.y);
                if (!withinMap) break;

                var mustTurn = map.get(nextStep.y).charAt(nextStep.x) == '#' || removeDirection(nextState).equals(removeDirection(state));
                if (mustTurn) {
                    guardDirection = guardDirection.turnRight90Deg();
                    if (turningPoints.contains(nextState)) {
                        if (prevMatched) {
                            cyclePositions.add(removeDirection(state));
                            break;
                        } else {
                            prevMatched = true;
                        }
                    } else {
                        prevMatched = false;
                        turningPoints.add(nextState);
                    }
                } else {
                    guardPoint = nextStep;
                }
            }
        }
        return cyclePositions;
    }

    private String createStateKey(Point<?> point, Direction direction) {
        return point.x + "," + point.y + "," + direction;
    }

    private String removeDirection(String stateKey) {
        return stateKey.replaceAll("^(.*?,.*?),.*$", "$1");
    }
}
