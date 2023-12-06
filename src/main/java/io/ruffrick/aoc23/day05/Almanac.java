package io.ruffrick.aoc23.day05;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Almanac(
        List<Long> seeds,
        Map seedToSoil,
        Map soilToFertilizer,
        Map fertilizerToWater,
        Map waterToLight,
        Map lightToTemperature,
        Map temperatureToHumidity,
        Map humidityToLocation
) {
    private static final Pattern SEEDS_PATTERN = Pattern.compile("seeds: ((?:\\d+ ?)+)");
    private static final Pattern MAP_PATTERN = Pattern.compile("(\\w+-to-\\w+) map:");
    private static final Pattern RANGE_PATTERN = Pattern.compile("(\\d+) (\\d+) (\\d+)");

    public static Almanac fromLines(final List<String> lines) {
        final List<Long> seeds = new ArrayList<>();
        final Matcher seedsMatcher = SEEDS_PATTERN.matcher(lines.get(0));
        if (seedsMatcher.matches()) {
            Arrays.stream(seedsMatcher.group(1).split(" "))
                    .map(Long::parseLong)
                    .forEach(seeds::add);
        }

        Map seedToSoil = null;
        Map soilToFertilizer = null;
        Map fertilizerToWater = null;
        Map waterToLight = null;
        Map lightToTemperature = null;
        Map temperatureToHumidity = null;
        Map humidityToLocation = null;
        for (int i = 1; i < lines.size(); i++) {
            final Matcher mapMatcher = MAP_PATTERN.matcher(lines.get(i));
            if (mapMatcher.matches()) {
                final String sourceToDestination = mapMatcher.group(1);
                final List<Range> ranges = new ArrayList<>();
                final Matcher rangeMatcher = RANGE_PATTERN.matcher(lines.get(++i));
                while (rangeMatcher.matches()) {
                    final long destinationStart = Long.parseLong(rangeMatcher.group(1));
                    final long sourceStart = Long.parseLong(rangeMatcher.group(2));
                    final long length = Long.parseLong(rangeMatcher.group(3));
                    ranges.add(new Range(destinationStart, sourceStart, length));
                    if (i + 1 < lines.size()) {
                        rangeMatcher.reset(lines.get(++i));
                    } else {
                        break;
                    }
                }
                final Map map = new Map(ranges);
                switch (sourceToDestination) {
                    case "seed-to-soil" -> seedToSoil = map;
                    case "soil-to-fertilizer" -> soilToFertilizer = map;
                    case "fertilizer-to-water" -> fertilizerToWater = map;
                    case "water-to-light" -> waterToLight = map;
                    case "light-to-temperature" -> lightToTemperature = map;
                    case "temperature-to-humidity" -> temperatureToHumidity = map;
                    case "humidity-to-location" -> humidityToLocation = map;
                    default -> throw new IllegalStateException("Unexpected value: " + sourceToDestination);
                }
            }
        }
        return new Almanac(
                seeds,
                seedToSoil,
                soilToFertilizer,
                fertilizerToWater,
                waterToLight,
                lightToTemperature,
                temperatureToHumidity,
                humidityToLocation
        );
    }

    public long seedToLocation(long number) {
        number = seedToSoil.convert(number);
        number = soilToFertilizer.convert(number);
        number = fertilizerToWater.convert(number);
        number = waterToLight.convert(number);
        number = lightToTemperature.convert(number);
        number = temperatureToHumidity.convert(number);
        number = humidityToLocation.convert(number);
        return number;
    }
}
