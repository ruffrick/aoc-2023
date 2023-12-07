package io.ruffrick.aoc23.day07;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Hand(List<Card> cards, int bid, Type type) implements Comparable<Hand> {
    private static final Pattern HAND_PATTERN = Pattern.compile("([AKQJT98765432X]{5}) (\\d+)");

    public static Hand fromLine(final String line) {
        final Matcher matcher = HAND_PATTERN.matcher(line);
        if (matcher.matches()) {
            final List<Card> cards = matcher.group(1)
                    .chars()
                    .mapToObj(Card::fromChar)
                    .toList();
            final int bid = Integer.parseInt(matcher.group(2));
            final Type type = Type.fromCards(cards);
            return new Hand(cards, bid, type);
        }
        throw new IllegalArgumentException(line);
    }

    public static Hand fromLineWithJoker(final String line) {
        return fromLine(line.replace('J', 'X'));
    }

    @Override
    public int compareTo(final Hand o) {
        final int comparedType = type.compareTo(o.type);
        if (comparedType == 0) {
            for (int i = 0; i < cards.size(); i++) {
                final int comparedCard = cards.get(i).compareTo(o.cards.get(i));
                if (comparedCard != 0) {
                    return comparedCard;
                }
            }
        }
        return comparedType;
    }
}
