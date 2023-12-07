package io.ruffrick.aoc23.day07;

public enum Card {
    CARD_X,
    CARD_2,
    CARD_3,
    CARD_4,
    CARD_5,
    CARD_6,
    CARD_7,
    CARD_8,
    CARD_9,
    CARD_T,
    CARD_J,
    CARD_Q,
    CARD_K,
    CARD_A;

    public static Card fromChar(final int codePoint) {
        final char ch = (char) codePoint;
        return valueOf("CARD_" + ch);
    }
}
