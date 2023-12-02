package io.ruffrick.aoc23.day02;

import io.ruffrick.aoc23.InvalidInputException;
import io.ruffrick.aoc23.Question;
import io.ruffrick.aoc23.Solution;
import io.ruffrick.aoc23.Title;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Title("--- Day 2: Cube Conundrum ---")
public class Day02 extends Solution {
    private final Pattern gamePattern = Pattern.compile("Game (\\d+): (.+)");
    private final Pattern cubePattern = Pattern.compile("\\s?(\\d+) (\\w+)");

    @Override
    @Question("What is the sum of the IDs of those games?")
    public String partOne() {
        final int sum = input.stream()
                .map(this::parseGame)
                .filter(this::checkCount)
                .mapToInt(Game::id)
                .sum();
        return Integer.toString(sum);
    }

    @Override
    @Question("What is the sum of the power of these sets?")
    public String partTwo() {
        final int sum = input.stream()
                .map(this::parseGame)
                .mapToInt(this::calculatePower)
                .sum();
        return Integer.toString(sum);
    }

    private Game parseGame(final String line) {
        final Matcher gameMatcher = gamePattern.matcher(line);
        if (gameMatcher.matches()) {
            final int id = Integer.parseInt(gameMatcher.group(1));
            final Map<String, Integer> cubes = new HashMap<>();
            for (final String cube : gameMatcher.group(2).split("[,;]")) {
                final Matcher cubeMatcher = cubePattern.matcher(cube);
                if (cubeMatcher.matches()) {
                    final int count = Integer.parseInt(cubeMatcher.group(1));
                    final String color = cubeMatcher.group(2);
                    if (cubes.getOrDefault(color, 0) < count) {
                        cubes.put(color, count);
                    }
                }
            }
            return new Game(id, cubes);
        }
        throw new InvalidInputException(line);
    }

    private boolean checkCount(final Game game) {
        return game.cubes().getOrDefault("red", 0) <= 12 &&
                game.cubes().getOrDefault("green", 0) <= 13 &&
                game.cubes().getOrDefault("blue", 0) <= 14;
    }

    private int calculatePower(final Game game) {
        return game.cubes()
                .values()
                .stream()
                .mapToInt(Integer::intValue)
                .reduce(1, ((left, right) -> left * right));
    }
}
