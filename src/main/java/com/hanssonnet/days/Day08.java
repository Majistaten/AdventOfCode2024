package com.hanssonnet.days;

import com.hanssonnet.core.AbstractDay;
import com.hanssonnet.shared.Point;
import com.hanssonnet.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

public class Day08 extends AbstractDay {


    public Day08() {
        super("day08");
    }

    @Override
    public String part1(String input) {
        var antennaMapSketch = Util.splitLines(input);
        var frequencyMap = populateFrequencyMap(antennaMapSketch);
        var isWithinBounds = Util.isWithinBounds(antennaMapSketch);

        var antinodes = calculateAntinodes(frequencyMap, isWithinBounds, false);

        return String.format("Result: %d", antinodes.size());
    }

    @Override
    public String part2(String input) {
        var antennaMapSketch = Util.splitLines(input);
        var frequencyMap = populateFrequencyMap(antennaMapSketch);
        var isWithinBounds = Util.isWithinBounds(antennaMapSketch);

        var antinodes = calculateAntinodes(frequencyMap, isWithinBounds, true);

        frequencyMap.values().stream()
                .filter(points -> points.size() > 1)
                .forEach(antinodes::addAll);

        return String.format("Result: %d", antinodes.size());
    }

    private Map<Character, List<Point<Character>>> populateFrequencyMap(List<String> antennaMapSketch) {
        var frequencyMap = new HashMap<Character, List<Point<Character>>>();
        for (int y = 0; y < antennaMapSketch.size(); y++) {
            for (int x = 0; x < antennaMapSketch.getFirst().length(); x++) {
                Character frequency = antennaMapSketch.get(y).charAt(x);
                if (frequency != '.') {
                    var antennaPoint = new Point<>(x, y, frequency);
                    frequencyMap.computeIfAbsent(frequency, key -> new ArrayList<>()).add(antennaPoint);
                }
            }
        }
        return frequencyMap;
    }

    private Set<Point<Character>> calculateAntinodes(
            Map<Character, List<Point<Character>>> frequencyMap,
            BiFunction<Integer, Integer, Boolean> isWithinBounds,
            boolean extendAntinodes
    ) {
        var antinodes = new HashSet<Point<Character>>();

        for (var frequency : frequencyMap.keySet()) {
            for (var frequencyPoint : frequencyMap.get(frequency)) {
                for (var anotherFrequencyPoint : frequencyMap.get(frequencyPoint.content)) {
                    if (anotherFrequencyPoint.equals(frequencyPoint)) continue;

                    var offset = Util.calculateOffset(frequencyPoint, anotherFrequencyPoint);
                    var antinodeX = frequencyPoint.x + offset.x() * 2;
                    var antinodeY = frequencyPoint.y + offset.y() * 2;

                    do {
                        if (!isWithinBounds.apply(antinodeX, antinodeY)) break;

                        var antinode = new Point<>(antinodeX, antinodeY, '#');
                        antinodes.add(antinode);

                        antinodeX += extendAntinodes ? offset.x() : 0;
                        antinodeY += extendAntinodes ? offset.y() : 0;
                    } while (extendAntinodes);
                }
            }
        }

        return antinodes;
    }
}
