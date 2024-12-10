package com.hanssonnet.days;

import com.hanssonnet.core.AbstractDay;
import com.hanssonnet.util.Util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Day09 extends AbstractDay {
    public Day09() {
        super("day09");
    }

    @Override
    public String part1(String input) {
        var diskMap = Util.splitLines(input).getFirst().split("");
        var files = IntStream.range(0, (diskMap.length + 1) / 2).map(index -> Integer.parseInt(diskMap[index * 2])).toArray();
        var freeSpace = IntStream.range(0, diskMap.length / 2).map(index -> Integer.parseInt(diskMap[index * 2 + 1])).toArray();

        List<Integer> filesystem = new ArrayList<>();
        buildBaseFilesystem(files, filesystem, freeSpace);

        for (int i = 0; i < filesystem.size(); i++) {
            if (filesystem.get(i) != null) continue;
            if (!filesystem.contains(null)) break;
            var lastfileId = filesystem.removeLast();
            while (lastfileId == null) {
                lastfileId = filesystem.removeLast();
            }
            if (i > filesystem.size()) {
                filesystem.add(lastfileId);
            } else {
                filesystem.set(i, lastfileId);
            }
        }

        var result = calculateChecksum(filesystem);

        return String.format("Result: %d", result);
    }

    @Override
    public String part2(String input) {
        var diskMap = Util.splitLines(input).getFirst().split("");
        var files = IntStream.range(0, (diskMap.length + 1) / 2)
                .map(index -> Integer.parseInt(diskMap[index * 2])).toArray();
        var freeSpace = IntStream.range(0, diskMap.length / 2)
                .map(index -> Integer.parseInt(diskMap[index * 2 + 1])).toArray();

        List<Integer> filesystem = new ArrayList<>();
        buildBaseFilesystem(files, filesystem, freeSpace);

        var fileIds = filesystem.stream()
                .filter(Objects::nonNull)
                .distinct()
                .sorted(Comparator.reverseOrder())
                .toList();

        for (int fileId : fileIds) {
            var filePositions = IntStream.range(0, filesystem.size())
                    .filter(i -> filesystem.get(i) != null && filesystem.get(i).equals(fileId))
                    .boxed()
                    .toList();
            var fileLength = filePositions.size();
            var currentStart = filePositions.getFirst();

            var bestStart = -1;
            for (int i = 0; i <= currentStart; i++) {
                var fits = true;
                for (int j = 0; j < fileLength; j++) {
                    if (i + j >= filesystem.size() || filesystem.get(i + j) != null) {
                        fits = false;
                        break;
                    }
                }
                if (fits) {
                    bestStart = i;
                    break;
                }
            }

            if (bestStart != -1) {
                for (int pos : filePositions) {
                    filesystem.set(pos, null);
                }
                for (int j = 0; j < fileLength; j++) {
                    filesystem.set(bestStart + j, fileId);
                }
            }
        }

        var result = calculateChecksum(filesystem);

        return String.format("Result: %d", result);
    }

    private long calculateChecksum(List<Integer> filesystem) {
        return LongStream.range(0, filesystem.size())
                .map(i -> filesystem.get((int) i) == null ? 0 : i * filesystem.get((int) i))
                .sum();
    }

    private static void buildBaseFilesystem(int[] files, List<Integer> filesystem, int[] freeSpace) {
        for (int i = 0; i < files.length; i++) {
            for (int file = 0; file < files[i]; file++) {
                filesystem.add(i);
            }
            if (i >= freeSpace.length) break;
            for (int free = 0; free < freeSpace[i]; free++) {
                filesystem.add(null);
            }
        }
    }
}
