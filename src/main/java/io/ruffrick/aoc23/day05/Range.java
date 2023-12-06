package io.ruffrick.aoc23.day05;

public record Range(long destinationStart, long sourceStart, long length) {
    public boolean includes(final long i) {
        return i >= sourceStart && i < sourceStart + length;
    }

    public long convert(final long sourceNumber) {
        return sourceNumber - sourceStart + destinationStart;
    }
}
