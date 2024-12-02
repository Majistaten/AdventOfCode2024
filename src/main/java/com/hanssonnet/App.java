package com.hanssonnet;

import com.hanssonnet.day01.Day01;
import com.hanssonnet.day02.Day02;

public class App 
{
    public static void main( String[] args )
    {
        day01();
        day02();
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
        System.out.println("-*".repeat(5) + day.getName() + "*-".repeat(5));
        System.out.println("-".repeat(5) + " Part 1 " + "-".repeat(5));
        System.out.println(day.part1(day.getInput()));
        System.out.println("-".repeat(5) + " Part 2 " + "-".repeat(5));
        System.out.println(day.part2(day.getInput()));
    }
}
