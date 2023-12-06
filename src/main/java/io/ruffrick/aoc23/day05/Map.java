package io.ruffrick.aoc23.day05;

import java.util.List;

public record Map(List<Range> ranges) {
    public long convert(final long sourceNumber) {
        for (final Range range : ranges) {
            if (range.includes(sourceNumber)) {
                return range.convert(sourceNumber);
            }
        }
        return sourceNumber;
    }
}
