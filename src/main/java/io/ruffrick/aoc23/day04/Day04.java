package io.ruffrick.aoc23.day04;

import io.ruffrick.aoc23.Question;
import io.ruffrick.aoc23.Solution;
import io.ruffrick.aoc23.Title;

import java.util.Arrays;

@Title("--- Day 4: Scratchcards ---")
public class Day04 extends Solution {
    @Override
    @Question("How many points are they worth in total?")
    public String partOne() {
        final int sum = input.stream()
                .map(Card::fromLine)
                .mapToInt(Card::pointValue)
                .sum();
        return Integer.toString(sum);
    }

    @Override
    @Question("How many total scratchcards do you end up with?")
    public String partTwo() {
        final Card[] cards = input.stream()
                .map(Card::fromLine)
                .toArray(Card[]::new);
        for (int i = 0; i < cards.length; i++) {
            final Card card = cards[i];
            System.out.println(i + ", " + card.matchingNumbers());
            for (int j = i + 1; j < Math.min(card.matchingNumbers() + i + 1, cards.length); j++) {
                System.out.println(j);
                cards[j] = cards[j].copy(card.copies());
            }
            System.out.println();
        }
        final int count = Arrays.stream(cards)
                .mapToInt(Card::copies)
                .sum();
        return Integer.toString(count);
    }
}
