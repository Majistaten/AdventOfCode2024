package com.hanssonnet;

import com.hanssonnet.day01.Day01;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Day day = new Day01();

        System.out.println("Part 1");
        System.out.println(day.part1(day.getInput()));
        System.out.println("Part 2");
        System.out.println(day.part2(day.getInput()));
    }
}
