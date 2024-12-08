package com.hanssonnet.util;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

public class Util {
    public static List<String> splitLines(String input) {
        return Arrays.asList(input.split("\n"));
    }
    public static char[][] get2DCharArray(String input) {
        return Arrays.stream(input.split("\n")).map(String::toCharArray).toArray(char[][]::new);
    }

    public static int calculateDifference(int x, int y) {
        return Math.abs(x - y);
    }

    public static List<Integer> extractIntegers(String line, String separator) {
        return Arrays.stream(line.split(separator)).map(Integer::parseInt).toList();
    }

    public static boolean isMonotonic(List<Integer> numbers) {
        if (numbers.size() < 2) {
            return true;
        }
        boolean isNotDecreasing = IntStream.range(0, numbers.size() - 1).allMatch(index -> numbers.get(index) <= numbers.get(index + 1));
        boolean isNotIncreasing = IntStream.range(0, numbers.size() - 1).allMatch(index -> numbers.get(index) >= numbers.get(index + 1));
        return isNotDecreasing || isNotIncreasing;
    }

    public static boolean hasValidStepChanges(List<Integer> numbers, int minChange, int maxChange) {
        return IntStream.range(0, numbers.size() - 1).allMatch(index -> {
            int change = Util.calculateDifference(numbers.get(index), numbers.get(index + 1));
            return change >= minChange && change <= maxChange;
        });
    }

    public static BiFunction<Integer, Integer, Boolean> isWithinBounds(List<String> map) {
        var yLength = map.size();
        var xLength = map.getFirst().length();
        return (xCoord, yCoord) -> yCoord >= 0 && yCoord < yLength && xCoord >= 0 && xCoord < xLength;
    }
}
