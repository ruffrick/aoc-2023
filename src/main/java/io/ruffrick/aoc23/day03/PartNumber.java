package io.ruffrick.aoc23.day03;

public record PartNumber(int number, int line, int start, int end) {
    public boolean isAdjacent(final int line, final int gear) {
        if (this.line == line && (end == gear || start == gear + 1)) {
            return true;
        }
        if (this.line == line - 1 || this.line == line + 1) {
            return end >= gear && start <= gear + 1;
        }
        return false;
    }
}