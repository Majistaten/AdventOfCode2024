package com.hanssonnet.days;

import com.hanssonnet.core.AbstractDay;
import com.hanssonnet.shared.IntegerMatrix;
import com.hanssonnet.shared.Pair;
import com.hanssonnet.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day05 extends AbstractDay {
    public Day05() {
        super("day05");
    }

    @Override
    public String part1(String input) {
        var slsManual = Util.splitLines(input);
        var pageOrderingRules = getStructuredPageOrderingRules(slsManual);
        var updatePages = getUpdatePages(slsManual);
        var validUpdatePages = filterValidUpdatePages(updatePages, pageOrderingRules);

        var result = validUpdatePages.stream()
                .mapToInt(update -> update.get(update.size() / 2))
                .sum();

        return String.format("Result: %d", result);
    }

    @Override
    public String part2(String input) {
        var slsManual = Util.splitLines(input);
        var pageOrderingRules = getStructuredPageOrderingRules(slsManual);
        var updatePages = getUpdatePages(slsManual);
        updatePages.removeAll(filterValidUpdatePages(updatePages, pageOrderingRules));

        var sortedUpdatePages = sortUpdatePagesAccordingToRules(updatePages, pageOrderingRules);

        var result = sortedUpdatePages.stream()
                .mapToInt(update -> update.get(update.size() / 2))
                .sum();

        return String.format("Result: %d", result);
    }

    private IntegerMatrix sortUpdatePagesAccordingToRules(IntegerMatrix invalidUpdatePages, Map<Integer, List<Integer>> pageOrderingRules) {
        var sortedUpdatePages = new ArrayList<List<Integer>>();
        for (var update: invalidUpdatePages) {
            var correctlyOrderedPages = new ArrayList<Integer>();
            var incorrectlyOrderedPages = new ArrayList<Integer>();

            extractCorrectlyAndIncorrectlyOrderedPages(pageOrderingRules, update, incorrectlyOrderedPages, correctlyOrderedPages);
            assert correctlyOrderedPages.size() + incorrectlyOrderedPages.size() == update.size() : "Pages are missing";

            var sortedUpdate = new ArrayList<>(correctlyOrderedPages);
            for (var page: incorrectlyOrderedPages) {
                if (sortedUpdate.isEmpty()) {
                    sortedUpdate.add(page);
                    continue;
                }
                var newList = new ArrayList<>(sortedUpdate);
                for (int i = 0; i < sortedUpdate.size() + 1; i++) {
                    newList.add(i, page);
                    if (followsOrderingRules(newList, pageOrderingRules)) {
                        sortedUpdate = newList;
                        break;
                    } else {
                        newList.remove(page);
                    }
                }
            }
            assert followsOrderingRules(sortedUpdate, pageOrderingRules) : "Update does not follow ordering rules " + sortedUpdate;
            assert sortedUpdate.size() == update.size() : "Update size changed" + sortedUpdate.size() + " " + update.size();
            sortedUpdatePages.add(sortedUpdate);
        }
        return new IntegerMatrix(sortedUpdatePages);
    }

    private static void extractCorrectlyAndIncorrectlyOrderedPages(Map<Integer, List<Integer>> pageOrderingRules,
                                                                   List<Integer> update, ArrayList<Integer> incorrectlyOrderedPages,
                                                                   ArrayList<Integer> correctlyOrderedPages) {
        IntStream.range(0, update.size()).forEach(i -> {
                    var currentPage = update.get(i);
                    var succeedingPages = update.subList(i + 1, update.size());
                    var pageOrderingRule = pageOrderingRules.get(currentPage);
                    if (succeedingPages.stream().anyMatch(pageOrderingRule::contains)) {
                        incorrectlyOrderedPages.add(currentPage);
                    } else {
                        correctlyOrderedPages.add(currentPage);
                    }
                });
    }

    private IntegerMatrix filterValidUpdatePages(IntegerMatrix updatePages, Map<Integer, List<Integer>> pageOrderingRules) {
        return new IntegerMatrix(updatePages.stream()
                .filter(update -> followsOrderingRules(update, pageOrderingRules)).collect(Collectors.toCollection(ArrayList::new)));
    }

    private boolean followsOrderingRules(List<Integer> update, Map<Integer, List<Integer>> pageOrderingRules) {
        return IntStream.range(0, update.size() - 1)
                .noneMatch(i -> {
                    var currentPage = update.get(i);
                    var succeedingPages = update.subList(i + 1, update.size());
                    var pageOrderingRule = pageOrderingRules.get(currentPage);
                    return succeedingPages.stream().anyMatch(pageOrderingRule::contains);
                });
    }

    private IntegerMatrix getUpdatePages(List<String> slsManual) {
        return new IntegerMatrix(
                slsManual.stream()
                        .filter(s -> !s.matches("\\d{2}\\|\\d{2}") && !s.isBlank())
                        .map(s -> s.split(","))
                        .map(s -> Arrays.stream(s).map(Integer::parseInt).toList())
                        .collect(Collectors.toCollection(ArrayList::new)));

    }

    private Map<Integer, List<Integer>> getStructuredPageOrderingRules(List<String> slsManual) {
        var pageOrderingRulesMap = new HashMap<Integer, List<Integer>>();
        var pageOrderingRulePairs = slsManual.stream().filter(s -> s.matches("\\d{2}\\|\\d{2}")).map(s -> {
            var numbers = s.split("\\|");
            return new Pair<>(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]));
        }).toList();

        for (var rulePair: pageOrderingRulePairs) {
            var pageRuleList = pageOrderingRulesMap.getOrDefault(rulePair.y(), new ArrayList<>());
            pageRuleList.add(rulePair.x());
            pageOrderingRulesMap.put(rulePair.y(), pageRuleList);
        }
        return pageOrderingRulesMap;
    }
}
