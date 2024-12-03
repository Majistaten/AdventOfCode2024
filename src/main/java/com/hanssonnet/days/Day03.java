package com.hanssonnet.days;

import com.hanssonnet.core.AbstractDay;
import com.hanssonnet.shared.Pair;

import java.util.regex.Pattern;
import java.util.ArrayList;

public class Day03 extends AbstractDay {
    public Day03() {
        super("day03");
    }

    @Override
    public String part1(String input) {
        var enabledInstructionNumberPairs = getMulNumbers(input);
        int result = getInstructionPairSum(enabledInstructionNumberPairs);
        return String.format("Result: %d", result);
    }

    @Override
    public String part2(String input) {
        var pattern = Pattern.compile("do\\(\\)|don't\\(\\)|(mul\\(\\d+,\\d+\\))");
        var matcher = pattern.matcher(input);

        var state = State.ENABLED;
        var sb = new StringBuilder();

        while (matcher.find()) {
            var match = matcher.group();

            if (match.equals("do()")) {
                state = State.ENABLED;
            } else if (match.equals("don't()")) {
                state = State.DISABLED;
            } else if (match.startsWith("mul(")) {
                if (state == State.ENABLED) {
                    sb.append(matcher.group(1));
                }
            }
        }
        var enabledInstructionNumberPairs = getMulNumbers(sb.toString());
        int result = getInstructionPairSum(enabledInstructionNumberPairs);
        return String.format("Result: %d", result);
    }

    private static ArrayList<Pair<Integer>> getMulNumbers(String input) {
        var instructionPattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)");
        var instructionMatcher = instructionPattern.matcher(input);
        var extractedInstructionNumberPairs = new ArrayList<Pair<Integer>>();
        while (instructionMatcher.find()) {
            int first = Integer.parseInt(instructionMatcher.group(1));
            int second = Integer.parseInt(instructionMatcher.group(2));
            extractedInstructionNumberPairs.add(new Pair<>(first, second));
        }
        return extractedInstructionNumberPairs;
    }

    private static Integer getInstructionPairSum(ArrayList<Pair<Integer>> extractedInstructionNumberPairs) {
        return extractedInstructionNumberPairs.stream().reduce(0, (sum, curr) -> sum + curr.x() * curr.y(), Integer::sum);
    }

    private enum State {
        ENABLED,
        DISABLED
    }
}
