package io.ruffrick.aoc23.day06;

import io.ruffrick.aoc23.Question;
import io.ruffrick.aoc23.Solution;
import io.ruffrick.aoc23.Title;

import java.util.ArrayList;
import java.util.List;

@Title("--- Day 6: Wait For It ---")
public class Day06 extends Solution {
    @Override
    @Question("What do you get if you multiply these numbers together?")
    public String partOne() {
        final List<Race> races = new ArrayList<>();
        final String[] durations = input.get(0).split("\\s+");
        final String[] records = input.get(1).split("\\s+");
        for (int i = 1; i < durations.length; i++) {
            races.add(new Race(Integer.parseInt(durations[i]), Integer.parseInt(records[i])));
        }
        final long total = races.stream()
                .mapToLong(Race::numWinningCharges)
                .reduce(1, (left, right) -> left * right);
        return Long.toString(total);
    }

    @Override
    @Question("How many ways can you beat the record in this one much longer race?")
    public String partTwo() {
        final long duration = Long.parseLong(input.get(0).replaceAll("\\s+", "").split(":")[1]);
        final long record = Long.parseLong(input.get(1).replaceAll("\\s+", "").split(":")[1]);
        final Race race = new Race(duration, record);
        return Long.toString(race.numWinningCharges());
    }
}
