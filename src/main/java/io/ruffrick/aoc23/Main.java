package io.ruffrick.aoc23;

import java.util.Calendar;

public class Main {
    public static void main(String[] args) {
        try {
            final String name = String.format("io.ruffrick.aoc23.day%1$02d.Day%1$02d", Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            System.out.println(Class.forName(name).getConstructor().newInstance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
