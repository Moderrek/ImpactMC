package com.impact.lib.api.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class MinecraftTime {

    private final Unit unit;
    private final long value;

    @Contract(pure = true)
    public MinecraftTime(Unit unit, long value) {
        this.unit = unit;
        this.value = value;
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull MinecraftTime ticks(final long ticks) {
        return new MinecraftTime(Unit.TICK, ticks);
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull MinecraftTime seconds(final long seconds) {
        return new MinecraftTime(Unit.SECOND, seconds);
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull MinecraftTime minutes(final long minutes) {
        return new MinecraftTime(Unit.MINUTE, minutes);
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull MinecraftTime hours(final long hours) {
        return new MinecraftTime(Unit.HOUR, hours);
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull MinecraftTime days(final long days) {
        return new MinecraftTime(Unit.DAY, days);
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull MinecraftTime weeks(final long weeks) {
        return new MinecraftTime(Unit.WEEK, weeks);
    }

    public long getSeconds() {
        return switch (unit) {
            case TICK -> value / 20L;
            case SECOND -> value;
            case MINUTE -> value * 60L;
            case HOUR -> value * 60L * 60L;
            case DAY -> value * 60L * 60L * 24L;
            case WEEK -> value * 60L * 60L * 24L * 7L;
            default -> 0;
        };
    }

    public long getMinutes() {
        return switch (unit) {
            case TICK -> value / 20L / 60L;
            case SECOND -> value / 60L;
            case MINUTE -> value;
            case HOUR -> value * 60L;
            case DAY -> value * 60L * 24L;
            case WEEK -> value * 60L * 24L * 7L;
            default -> 0;
        };
    }

    public long getHours() {
        return switch (unit) {
            case TICK -> value / 20L / 60L / 60L;
            case SECOND -> value / 60L / 60L;
            case MINUTE -> value / 60L;
            case HOUR -> value;
            case DAY -> value * 24L;
            case WEEK -> value * 24L * 7L;
            default -> 0;
        };
    }

    public long getDays() {
        return switch (unit) {
            case TICK -> value / 20L / 60L / 60L / 24L;
            case SECOND -> value / 60L / 60L / 24L;
            case MINUTE -> value / 60L / 24L;
            case HOUR -> value / 24L;
            case DAY -> value;
            case WEEK -> value * 7L;
            default -> 0;
        };
    }

    public long getWeeks() {
        return switch (unit) {
            case TICK -> value / 20L / 60L / 60L / 24L / 7L;
            case SECOND -> value / 60L / 60L / 24L / 7L;
            case MINUTE -> value / 60L / 24L / 7L;
            case HOUR -> value / 24L / 7L;
            case DAY -> value / 7L;
            case WEEK -> value;
            default -> 0;
        };
    }

    @Override
    public int hashCode() {
        return Objects.hash(unit, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MinecraftTime that = (MinecraftTime) o;
        return getTicks() == that.getTicks();
    }

    public long getTicks() {
        return switch (unit) {
            case TICK -> value;
            case SECOND -> value * 20L;
            case MINUTE -> value * 20L * 60L;
            case HOUR -> value * 20L * 60L * 60L;
            case DAY -> value * 20L * 60L * 60L * 24L;
            case WEEK -> value * 20L * 60L * 60L * 24L * 7L;
            default -> 0;
        };
    }

    @Override
    public String toString() {
        return "MinecraftTime[%s=%d]".formatted(unit, value);
    }

    public enum Unit {
        TICK, SECOND, MINUTE, HOUR, DAY, WEEK
    }

}
