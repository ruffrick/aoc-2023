package io.ruffrick.aoc23.day04;

import io.ruffrick.aoc23.InvalidInputException;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Card(int number, List<Integer> numbers, List<Integer> winningNumbers, int copies) {
    private static final Pattern CARD_PATTERN = Pattern.compile("Card +(\\d+): +((?:\\d+ *)+) \\| +((?:\\d+ *)+)");
    private static final Pattern WTHIESPACE_PATTERN = Pattern.compile("\\s+");

    public static Card fromLine(final String line) {
        final Matcher matcher = CARD_PATTERN.matcher(line);
        if (matcher.matches()) {
            final int number = Integer.parseInt(matcher.group(1));
            final List<Integer> numbers = Arrays.stream(WTHIESPACE_PATTERN.split(matcher.group(2)))
                    .map(Integer::parseInt)
                    .toList();
            final List<Integer> winningNumbers = Arrays.stream(WTHIESPACE_PATTERN.split(matcher.group(3)))
                    .map(Integer::parseInt)
                    .toList();
            return new Card(number, numbers, winningNumbers, 1);
        }
        throw new InvalidInputException(line);
    }

    public int matchingNumbers() {
        return (int) numbers.stream()
                .filter(winningNumbers::contains)
                .count();
    }

    public int pointValue() {
        return (int) Math.pow(2, matchingNumbers() - 1);
    }

    public Card copy(final int times) {
        return new Card(number, numbers, winningNumbers, copies + times);
    }
}
