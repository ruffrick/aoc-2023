package io.ruffrick.aoc23.day07;

import java.util.*;

public enum Type {
    HIGH_CARD,
    ONE_PAIR,
    TWO_PAIR,
    THREE_OF_A_KIND,
    FULL_HOUSE,
    FOUR_OF_A_KIND,
    FIVE_OF_A_KIND;

    public static Type fromCards(final List<Card> cards) {
        return variants(cards).stream()
                .map(Type::type)
                .max(Comparator.naturalOrder())
                .orElseThrow(() -> new RuntimeException(cards.toString()));
    }

    private static Type type(final List<Card> cards) {
        final Map<Card, Integer> cardCount = new HashMap<>();
        for (final Card card : cards) {
            final int count = cardCount.getOrDefault(card, 0);
            cardCount.put(card, count + 1);
        }

        if (cardCount.containsValue(5)) {
            return FIVE_OF_A_KIND;
        } else if (cardCount.containsValue(4)) {
            return FOUR_OF_A_KIND;
        } else if (cardCount.containsValue(3) && cardCount.containsValue(2)) {
            return FULL_HOUSE;
        } else if (cardCount.containsValue(3)) {
            return THREE_OF_A_KIND;
        } else if (cardCount.values().stream().filter(count -> count == 2).count() == 2) {
            return TWO_PAIR;
        } else if (cardCount.values().stream().filter(count -> count == 2).count() == 1) {
            return ONE_PAIR;
        }
        return HIGH_CARD;
    }

    private static List<List<Card>> variants(final List<Card> cards) {
        final long jokers = cards.stream().filter(card -> card == Card.CARD_X).count();
        if (jokers == 0 || jokers == cards.size()) {
            return Collections.singletonList(cards);
        }
        return cards.stream()
                .distinct()
                .filter(card -> card != Card.CARD_X)
                .map(card -> cards.stream()
                        .map(card1 -> card1 == Card.CARD_X ? card : card1)
                        .toList())
                .toList();
    }
}
