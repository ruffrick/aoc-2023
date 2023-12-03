package io.ruffrick.aoc23.day03;

import io.ruffrick.aoc23.Question;
import io.ruffrick.aoc23.Solution;
import io.ruffrick.aoc23.Title;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Title("--- Day 3: Gear Ratios ---")
public class Day03 extends Solution {
    private final List<PartNumber> partNumbers = new ArrayList<>();
    private final Pattern gearPattern = Pattern.compile("(\\*)");

    public Day03() {
        final Pattern numberPattern = Pattern.compile("(\\d+)");
        for (int line = 0; line < input.size(); line++) {
            final Matcher matcher = numberPattern.matcher(input.get(line));
            while (matcher.find()) {
                final int number = Integer.parseInt(matcher.group(1));
                final int start = matcher.start();
                final int end = matcher.end();
                if (isPartNumber(line, start, end)) {
                    partNumbers.add(new PartNumber(number, line, start, end));
                }
            }
        }
    }

    @Override
    @Question("What is the sum of all of the part numbers in the engine schematic?")
    public String partOne() {
        final int sum = partNumbers.stream()
                .mapToInt(PartNumber::number)
                .sum();
        return Integer.toString(sum);
    }

    @Override
    @Question("What is the sum of all of the gear ratios in your engine schematic?")
    public String partTwo() {
        int sum = 0;
        for (int line = 0; line < input.size(); line++) {
            final Matcher matcher = gearPattern.matcher(input.get(line));
            while (matcher.find()) {
                final int gear = matcher.start();
                final int gearRatio = parseGearRatio(line, gear);
                if (gearRatio > 0) {
                    sum += gearRatio;
                }
            }
        }
        return Integer.toString(sum);
    }

    private boolean isPartNumber(final int line, final int start, final int end) {
        final String prev = line > 0 ? input.get(line - 1) : null;
        final String curr = input.get(line);
        final String next = line < input.size() - 1 ? input.get(line + 1) : null;
        if (start > 0 && curr.charAt(start - 1) != '.') {
            return true;
        }
        if (end < curr.length() && curr.charAt(end) != '.') {
            return true;
        }
        for (int j = Math.max(start - 1, 0); j < Math.min(end + 1, curr.length()); j++) {
            if (prev != null && prev.charAt(j) != '.') {
                return true;
            }
            if (next != null && next.charAt(j) != '.') {
                return true;
            }
        }
        return false;
    }

    private int parseGearRatio(final int line, final int gear) {
        final List<PartNumber> adjacentPartNumbers = partNumbers.stream()
                .filter(partNumber -> partNumber.isAdjacent(line, gear))
                .toList();
        if (adjacentPartNumbers.size() == 2) {
            return adjacentPartNumbers.get(0).number() * adjacentPartNumbers.get(1).number();
        }
        return -1;
    }
}
