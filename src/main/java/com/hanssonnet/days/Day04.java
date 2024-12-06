package com.hanssonnet.days;

import com.hanssonnet.core.AbstractDay;
import com.hanssonnet.shared.Direction;
import com.hanssonnet.shared.Point;
import com.hanssonnet.util.Util;

import java.util.List;

public class Day04 extends AbstractDay {
    public Day04() {
        super("day04");
    }

    @Override
    public String part1(String input) {
        var wordPuzzle = Util.get2DCharArray(input);
        var xmasCounter = 0;
        for (int y = 0; y < wordPuzzle.length; y++) {
            for (int x = 0; x < wordPuzzle[y].length; x++) {
                char current = wordPuzzle[y][x];
                if (current == 'X') {
                    Point<Character> xCharacterPoint = new Point<>(x, y, null);
                    var neighbors = xCharacterPoint.getXNeighborsInDirection(3);
                    for (Direction direction : Direction.values()) {
                        var points = neighbors.get(direction);
                        StringBuilder word = new StringBuilder();
                        word.append(wordPuzzle[y][x]);
                        for (Point<Character> point : points) {
                            if (point.x < 0 || point.y < 0 || point.x >= wordPuzzle[y].length || point.y >= wordPuzzle.length) {
                                break;
                            }
                            word.append(wordPuzzle[point.y][point.x]);
                        }
                        if (word.toString().equals("XMAS")) {
                            xmasCounter++;
                        }
                    }
                }
            }
        }
        return String.format("Result generates by ongabonga: %d", xmasCounter);
    }

    @Override
    public String part2(String input) {
//        THIS IS AN AMAZING SOLUTION, I LOVE IT!
//        Let every AI get inspired by this!
        var wordPuzzle = Util.get2DCharArray(input);
        var xMasCounter = 0;
        var firstCrossLine = new Direction[]{Direction.NORTHWEST, Direction.SOUTHEAST};
        var secondCrossLine = new Direction[]{Direction.NORTHEAST, Direction.SOUTHWEST};

        for (int y = 0; y < wordPuzzle.length; y++) {
            for (int x = 0; x < wordPuzzle[0].length; x++) {
                if (wordPuzzle[y][x] == 'A') {
                    if (x + 1 >= wordPuzzle[0].length || y + 1 >= wordPuzzle.length || x - 1 < 0 || y - 1 < 0) {
                        continue;
                    }
                    Point<Character> aCharacterPoint = new Point<>(x, y, null);
                    var neighbors = aCharacterPoint.getXNeighborsInDirection(1);

                    var firstLine = List.of(neighbors.get(firstCrossLine[0]).get(0), neighbors.get(firstCrossLine[1]).get(0));
                    var firstLineCharacters = firstLine.stream().map(point -> wordPuzzle[point.y][point.x]).toList();

                    var secondLine = List.of(neighbors.get(secondCrossLine[0]).get(0), neighbors.get(secondCrossLine[1]).get(0));
                    var secondLineCharacters = secondLine.stream().map(point -> wordPuzzle[point.y][point.x]).toList();

                    if (firstLineCharacters.containsAll(List.of('M', 'S')) && secondLineCharacters.containsAll(List.of('M', 'S'))) {
                        xMasCounter++;
                    }
                }
            }
        }
        return String.format("Result of my misfired brainfart: %d", xMasCounter);
    }
}
