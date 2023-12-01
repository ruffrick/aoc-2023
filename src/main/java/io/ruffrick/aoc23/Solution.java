package io.ruffrick.aoc23;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

public abstract class Solution {
    protected final List<String> input = readInput();

    private List<String> readInput() {
        try (final InputStream is = this.getClass().getResourceAsStream("input.txt")) {
            return new BufferedReader(new InputStreamReader(Objects.requireNonNull(is))).lines().toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract String partOne();

    public abstract String partTwo();

    @Override
    public String toString() {
        final String title = Objects.requireNonNull(this.getClass().getAnnotation(Title.class)).value();
        final StringBuilder sb = new StringBuilder(title);
        try {
            final String questionOne = Objects.requireNonNull(this.getClass().getMethod("partOne").getAnnotation(Question.class)).value();
            sb.append("\nPart One - ").append(questionOne).append(' ').append(partOne());
            final String questionTwo = Objects.requireNonNull(this.getClass().getMethod("partTwo").getAnnotation(Question.class)).value();
            sb.append("\nPart Two - ").append(questionTwo).append(' ').append(partTwo());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }
}
