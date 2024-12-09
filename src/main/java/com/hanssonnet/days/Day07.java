package com.hanssonnet.days;

import com.hanssonnet.core.AbstractDay;
import com.hanssonnet.util.Util;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class Day07 extends AbstractDay {

    public Day07() {
        super("day07");
    }

    @Override
    public String part1(String input) {
        var calibrationEquations = Util.splitLines(input);
        long totalCalibrationResult = 0;
        for (var calibrationEquation : calibrationEquations) {
            var equationParts = calibrationEquation.split(": ");
            var testValue = Long.parseLong(equationParts[0]);
            ArrayDeque<Long> equationNumbers = Arrays.stream(equationParts[1].split(" ")).map(Long::parseLong).collect(Collectors.toCollection(ArrayDeque::new));

            if (canComputeTargetValue(testValue, equationNumbers, false)) totalCalibrationResult += testValue;
        }
        return String.format("Result: %d", totalCalibrationResult);
    }

    @Override
    public String part2(String input) {
        var calibrationEquations = Util.splitLines(input);
        long totalCalibrationResult = 0;
        for (var calibrationEquation : calibrationEquations) {
            var equationParts = calibrationEquation.split(": ");
            var testValue = Long.parseLong(equationParts[0]);
            ArrayDeque<Long> equationNumbers = Arrays.stream(equationParts[1].split(" ")).map(Long::parseLong).collect(Collectors.toCollection(ArrayDeque::new));

            if (canComputeTargetValue(testValue, equationNumbers, true)) totalCalibrationResult += testValue;
        }
        return String.format("Result: %d", totalCalibrationResult);
    }

    private boolean canComputeTargetValue(long targetValue, ArrayDeque<Long> numbersStack, boolean useConcatenate) {
        if (numbersStack.isEmpty()) return false;
        if (numbersStack.size() == 1) return numbersStack.peek() == targetValue;

        ArrayDeque<Long> clonedStack = new ArrayDeque<>(numbersStack);

        if (canComputeTargetValue(targetValue, clonedStack, Long::sum, useConcatenate)) return true;
        if (canComputeTargetValue(targetValue, clonedStack, Math::multiplyExact, useConcatenate)) return true;

        return useConcatenate && canComputeTargetValue(targetValue, clonedStack, this::concatenate, useConcatenate);
    }

    private boolean canComputeTargetValue(long targetValue, ArrayDeque<Long> numbersStack, BiFunction<Long, Long, Long> operation, boolean useConcatenate) {
        if (numbersStack.size() < 2) return false;

        ArrayDeque<Long> clonedStack = new ArrayDeque<>(numbersStack);

        var firstNumber = clonedStack.poll();
        var secondNumber = clonedStack.poll();

        long result;
        try {
            result = operation.apply(firstNumber, secondNumber);
        } catch (ArithmeticException e) {
            return false;
        }

        clonedStack.addFirst(result);

        return (clonedStack.size() == 1 && clonedStack.peek() == targetValue) ||
                canComputeTargetValue(targetValue, clonedStack, Long::sum, useConcatenate) ||
                canComputeTargetValue(targetValue, clonedStack, Math::multiplyExact, useConcatenate) ||
                (useConcatenate && canComputeTargetValue(targetValue, clonedStack, this::concatenate, useConcatenate));
    }

    private long concatenate(long firstNumber, long secondNumber) {
        String concatenated = firstNumber + Long.toString(secondNumber);
        return Long.parseLong(concatenated);
    }

}
