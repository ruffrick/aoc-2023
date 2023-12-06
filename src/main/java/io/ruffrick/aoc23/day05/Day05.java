package io.ruffrick.aoc23.day05;

import io.ruffrick.aoc23.Question;
import io.ruffrick.aoc23.Solution;
import io.ruffrick.aoc23.Title;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Title("--- Day 5: If You Give A Seed A Fertilizer ---")
public class Day05 extends Solution {
    private final Almanac almanac = Almanac.fromLines(input);

    @Override
    @Question("What is the lowest location number that corresponds to any of the initial seed numbers?")
    public String partOne() {
        final long min = almanac.seeds()
                .stream()
                .mapToLong(almanac::seedToLocation)
                .min()
                .orElseThrow();
        return Long.toString(min);
    }

    @Override
    @Question("What is the lowest location number that corresponds to any of the initial seed numbers?")
    public String partTwo() {
        final ExecutorService executorService = Executors.newFixedThreadPool(almanac.seeds().size() / 2);
        final List<Long> mins = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < almanac.seeds().size(); i += 2) {
            final long start = almanac.seeds().get(i);
            final long length = almanac.seeds().get(i + 1);
            executorService.execute(() -> {
                long min = Long.MAX_VALUE;
                for (long j = 0; j < length; j++) {
                    final long seed = start + j;
                    final long location = almanac.seedToLocation(seed);
                    if (location < min) {
                        min = location;
                    }
                }
                mins.add(min);
            });
        }
        try {
            executorService.shutdown();
            if (!executorService.awaitTermination(1, TimeUnit.MINUTES)) {
                throw new TimeoutException();
            }
        } catch (InterruptedException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        return Long.toString(mins.stream().mapToLong(Long::longValue).min().orElseThrow());
    }
}
