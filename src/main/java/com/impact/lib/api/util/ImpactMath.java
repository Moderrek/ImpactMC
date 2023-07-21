package pl.impact.lib.api.util;

import org.jetbrains.annotations.NotNull;

public class ImpactMath {

    public static <T extends Comparable<T>> T Clamp(@NotNull T val, T min, T max) {
        if (val.compareTo(min) < 0) return min;
        else if (val.compareTo(max) > 0) return max;
        else return val;
    }

}
