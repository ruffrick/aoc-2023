package io.ruffrick.aoc23.day07;

import io.ruffrick.aoc23.Question;
import io.ruffrick.aoc23.Solution;
import io.ruffrick.aoc23.Title;

import java.util.function.Function;

@Title("--- Day 7: Camel Cards ---")
public class Day07 extends Solution {
    @Override
    @Question("What are the total winnings?")
    public String partOne() {
        return Integer.toString(totalWinnings(Hand::fromLine));
    }

    @Override
    @Question("What are the new total winnings?")
    public String partTwo() {
        return Integer.toString(totalWinnings(Hand::fromLineWithJoker));
    }

    private int totalWinnings(final Function<String, Hand> mapper) {
        final Hand[] hands = input.stream()
                .map(mapper)
                .sorted()
                .toArray(Hand[]::new);
        int total = 0;
        for (int i = 0; i < hands.length; i++) {
            total += hands[i].bid() * (i + 1);
        }
        return total;
    }
}
