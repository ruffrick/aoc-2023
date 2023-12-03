package io.ruffrick.aoc23.day03;

import io.ruffrick.aoc23.Question;
import io.ruffrick.aoc23.Solution;
import io.ruffrick.aoc23.Title;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

@Title("--- Day 3: Gear Ratios ---")
public class Day03 extends Solution {
    private final Pattern numberPattern = Pattern.compile("(\\d+)");


    @Override
    @Question("What is the sum of all of the part numbers in the engine schematic?")
    public String partOne() {
        final int sum = IntStream.range(0, input.size())
                .map(this::parseLine)
                .sum();
        return Integer.toString(sum);
    }

    @Override
    @Question("What is the sum of all of the gear ratios in your engine schematic?")
    public String partTwo() {
        return null;
    }

    private int parseLine(final int i) {
        int sum = 0;
        final String line = input.get(i);
        final Matcher matcher = numberPattern.matcher(line);
        while (matcher.find()) {
            sum += parseNumber(i, line, matcher);
        }
        return sum;
    }

    private int parseNumber(final int i, final String line, final Matcher matcher) {
        final String prev = i > 0 ? input.get(i - 1) : null;
        final String next = i < input.size() - 1 ? input.get(i + 1) : null;
        final int start = matcher.start();
        final int end = matcher.end();
        final int number = Integer.parseInt(matcher.group(1));
        if (start > 0 && line.charAt(start - 1) != '.') {
            return number;
        }
        if (end < line.length() && line.charAt(end) != '.') {
            return number;
        }
        for (int j = Math.max(start - 1, 0); j < Math.min(end + 1, line.length()); j++) {
            if (prev != null && prev.charAt(j) != '.') {
                return number;
            } else if (next != null && next.charAt(j) != '.') {
                return number;
            }
        }
        return 0;
    }
}
