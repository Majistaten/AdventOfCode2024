package com.hanssonnet.days;

import com.hanssonnet.core.AbstractDay;
import com.hanssonnet.util.Util;

public class Day07 extends AbstractDay {

    public Day07() {
        super("day07");
    }

    @Override
    public String part1(String input) {
        var map = Util.splitLines(input);
        return String.format("Result: %d", 1);
    }

    @Override
    public String part2(String input) {
        var map = Util.splitLines(input);
        return String.format("Result: %d", 1);
    }
}
