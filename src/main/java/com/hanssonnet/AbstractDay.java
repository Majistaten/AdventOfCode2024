package com.hanssonnet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AbstractDay implements Day {

    private final String input;
    private final String name;

    public AbstractDay(String day) {
        this.name = formatPrettyName(day);
        this.input = loadInput(day);
    }

    private String formatPrettyName(String day) {
        return day.substring(0, 1).toUpperCase() + day.substring(1);
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public String part1(String input) {
        return "Not implemented";
    }

    @Override
    public String part2(String input) {
        return "Not implemented";
    }

    public final String getInput() {
        return input;
    }

    private String loadInput(String day) {
        try {
            return new String(Files.readAllBytes(Paths.get("src/main/resources/" + day + ".txt")));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load input for " + day, e);
        }
    }
}
