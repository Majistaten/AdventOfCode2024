package com.hanssonnet.day01;

import com.hanssonnet.AbstractDay;
import com.hanssonnet.Util;

import java.util.*;
import java.util.stream.Collectors;

public class Day01 extends AbstractDay {
    public Day01() {
        super("day01");
    }

    @Override
    public String part1(String input) {
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        parseLocationIDs(input, left, right);

        Collections.sort(left);
        Collections.sort(right);

        int totalDistance = calculateTotalDistance(left, right);
        return String.format("Result: %s", totalDistance);
    }

    @Override
    public String part2(String input) {
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        parseLocationIDs(input, left, right);

        Map<Integer, Long> rightOccurrences = getOccurrences(right);

        long similarityScore = calculateSimilarityScore(left, rightOccurrences);
        return String.format("Result: %s", similarityScore);
    }

    private static void parseLocationIDs(String input, List<Integer> left, List<Integer> right) {
        Util.splitLines(input).forEach(line -> {
            if (!line.isEmpty()) {
                String[] content = line.split(" ");
                left.add(Integer.parseInt(content[0]));
                right.add(Integer.parseInt(content[content.length-1]));
            }
        });
    }

    private static Map<Integer, Long> getOccurrences(List<Integer> right) {
        return right.stream().collect(
                Collectors.groupingBy(value -> value, Collectors.counting()));
    }

    private static long calculateSimilarityScore(List<Integer> left, Map<Integer, Long> rightOccurrences) {
        long similarityScore = 0L;
        for (int value: left) {
            similarityScore += value * rightOccurrences.getOrDefault(value, 0L);
        }
        return similarityScore;
    }

    private static int calculateTotalDistance(List<Integer> left, List<Integer> right) {
        int distance = 0;
        for (int index = 0; index < left.size(); index++) {
            distance += Util.calculateDifference(left.get(index), right.get(index));
        }
        return distance;
    }
}
