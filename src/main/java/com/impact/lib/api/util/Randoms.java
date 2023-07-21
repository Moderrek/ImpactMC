package pl.impact.lib.api.util;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public final class Randoms {

    private Randoms() {
    }

    public static int inRange(Range<Integer> range, long seed) {
        return inRange(range, new Random(seed));
    }

    public static int inRange(@NotNull Range<Integer> range, @NotNull Random random) {
        final int min = range.min();
        final int max = range.max();
        return random.nextInt((max - min) + 1) + min;
    }

    public static int inRange(Range<Integer> range) {
        return inRange(range, new Random());
    }

}
