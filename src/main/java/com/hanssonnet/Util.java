package com.hanssonnet;

import java.util.Arrays;
import java.util.List;

public class Util {
    public static List<String> splitLines(String input) {
        return Arrays.asList(input.split("\n"));
    }

    public static int calculateDifference(int x, int y) {
        return Math.abs(x - y);
    }
}
