package io.ruffrick.aoc23.day01;

import io.ruffrick.aoc23.Question;
import io.ruffrick.aoc23.Solution;
import io.ruffrick.aoc23.Title;

import java.util.HashMap;
import java.util.Map;

@Title("--- Day 1: Trebuchet?! ---")
public class Day01 extends Solution {
    private final Map<String, Integer> digits;

    public Day01() {
        digits = new HashMap<>();
        digits.put("one", 1);
        digits.put("two", 2);
        digits.put("three", 3);
        digits.put("four", 4);
        digits.put("five", 5);
        digits.put("six", 6);
        digits.put("seven", 7);
        digits.put("eight", 8);
        digits.put("nine", 9);
    }

    @Override
    @Question("What is the sum of all of the calibration values?")
    public String partOne() {
        final int sum = input.stream()
                .mapToInt(line -> findFirst(line, false) * 10 + findLast(line, false))
                .sum();
        return Integer.toString(sum);
    }

    @Override
    @Question("What is the sum of all of the calibration values?")
    public String partTwo() {
        final int sum = input.stream()
                .mapToInt(line -> findFirst(line, true) * 10 + findLast(line, true))
                .sum();
        return Integer.toString(sum);
    }

    private int findFirst(final String line, final boolean includeWords) {
        for (int i = 0; i < line.length(); i++) {
            final int digit = findDigit(line, i, includeWords);
            if (digit >= 0) {
                return digit;
            }
        }
        return -1;
    }

    private int findLast(final String line, final boolean includeWords) {
        for (int i = line.length() - 1; i >= 0; i--) {
            final int digit = findDigit(line, i, includeWords);
            if (digit >= 0) {
                return digit;
            }
        }
        return -1;
    }

    private int findDigit(final String line, final int i, final boolean includeWords) {
        final int digit = Character.digit(line.charAt(i), 10);
        if (digit >= 0) {
            return digit;
        }
        if (includeWords) {
            for (final Map.Entry<String, Integer> entry : digits.entrySet()) {
                if (line.startsWith(entry.getKey(), i)) {
                    return entry.getValue();
                }
            }
        }
        return -1;
    }
}
