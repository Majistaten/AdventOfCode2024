package com.hanssonnet.days;

import com.hanssonnet.core.AbstractDay;
import com.hanssonnet.util.Util;

import java.util.ArrayList;
import java.util.List;

public class Day02 extends AbstractDay {
    public Day02() {
        super("day02");
    }

    @Override
    public String part1(String input) {
        List<String> reports = Util.splitLines(input);
        String separator = " ";
        long result = reports.stream()
                .map(report -> Util.extractIntegers(report, separator))
                .filter(report -> !report.isEmpty())
                .filter(Util::isMonotonic)
                .filter(monotonicReports -> Util.hasValidStepChanges(monotonicReports, 1, 3)).count();
        return String.format("Result: %d", result);
    }

    @Override
    public String part2(String input) {
        List<String> reports = Util.splitLines(input);
        String separator = " ";

        var faultyReports = reports.stream()
                .map(report -> Util.extractIntegers(report, separator))
                .filter(report -> !report.isEmpty())
                .filter(report -> !Util.isMonotonic(report) || !Util.hasValidStepChanges(report, 1, 3)).toList();
        var dampenedReports = faultyReports.stream().filter(faultyReport -> !problemDampener(faultyReport)).count();
        int result = reports.size() - (int) dampenedReports;
        return String.format("Result: %d", result);
    }

    private static boolean isValidReport(List<Integer> report) {
        if (report.size() < 2) return true;

        boolean isIncreasing = report.get(1) > report.get(0);
        for (int i = 0; i < report.size() - 1; i++) {
            int current = report.get(i);
            int next = report.get(i + 1);

            int difference = Util.calculateDifference(current, next);
            if (difference < 1 || difference > 3) return false;

            boolean nextState = next > current;
            if (nextState != isIncreasing) return false;
        }
        return true;
    }

    private static boolean problemDampener(List<Integer> report) {
        for (int i = 0; i < report.size(); i++) {
            var modifiedList = new ArrayList<>(report);
            modifiedList.remove(i);
            if (isValidReport(modifiedList)) {
                return true;
            }
        }
        return false;
    }
}
