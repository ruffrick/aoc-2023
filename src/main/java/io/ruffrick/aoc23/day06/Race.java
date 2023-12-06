package io.ruffrick.aoc23.day06;

public record Race(long duration, long record) {
    public long distance(final long charge) {
        return (duration - charge) * charge;
    }

    public long minCharge() {
        for (long charge = 1; charge < duration; charge++) {
            if (distance(charge) > record) {
                return charge;
            }
        }
        return -1;
    }

    public long numWinningCharges() {
        return duration - 2 * minCharge() + 1;
    }
}
