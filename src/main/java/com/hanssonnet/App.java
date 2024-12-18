package com.hanssonnet;

import com.hanssonnet.core.Day;
import com.hanssonnet.days.*;

public class App 
{
    public static void main( String[] args )
    {
        day01();
        day02();
        day03();
        day04();
        day05();
//        day06(); // Slow...
        day07();
        day08();
//        day09(); // Slow...
        day10();
    }

    private static void day10() {
        Day day = new Day10();
        printDay(day);
    }

    private static void day09() {
        Day day = new Day09();
        printDay(day);
    }

    private static void day08() {
        Day day = new Day08();
        printDay(day);
    }

    private static void day07() {
        Day day = new Day07();
        printDay(day);
    }

    private static void day06() {
        Day day = new Day06();
        printDay(day);
    }

    private static void day05() {
        Day day = new Day05();
        printDay(day);
    }

    private static void day04() {
        Day day = new Day04();
        printDay(day);
    }

    private static void day03() {
        Day day = new Day03();
        printDay(day);
    }

    private static void day02() {
        Day day = new Day02();
        printDay(day);
    }


    private static void day01() {
        Day day = new Day01();
        printDay(day);
    }

    private static void printDay(Day day) {
        System.out.println("~* ".repeat(5) + day.getName() + " *~".repeat(5));
        System.out.println("-".repeat(5) + " Part 1 " + "-".repeat(5));
        System.out.println(day.part1(day.getInput()));
        System.out.println("-".repeat(5) + " Part 2 " + "-".repeat(5));
        System.out.println(day.part2(day.getInput()));
    }
}
