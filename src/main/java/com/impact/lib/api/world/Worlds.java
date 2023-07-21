package com.impact.lib.api.world;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.jetbrains.annotations.NotNull;
import com.impact.lib.Impact;
import com.impact.lib.api.player.ImpactPlayer;
import com.impact.lib.api.util.Randoms;
import com.impact.lib.api.util.Range;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.stream.Collectors;

public final class Worlds {

    private Worlds() {
    }

    public static @NotNull Collection<World> get() {
        return Impact.getWorlds();
    }

    public static @NotNull Collection<World> get(World.Environment filter) {
        return Impact.getWorlds(filter);
    }

    public static @NotNull Collection<ImpactPlayer> getPlayersIn(@NotNull final World world) {
        return world.getPlayers().stream().map(ImpactPlayer::new).collect(Collectors.toCollection(ArrayList::new));
    }

    public static @NotNull Location getSpawn(@NotNull final World world) {
        return world.getSpawnLocation();
    }

    public static @NotNull Location getCenter(@NotNull final World world) {
        return world.getWorldBorder().getCenter();
    }

    public static @NotNull Location randomLocation(@NotNull final World world, final long seed) {
        return randomLocation(world, new Random(seed));
    }

    public static @NotNull Location randomLocation(@NotNull final World world, @NotNull final Random random) {
        final WorldBorder worldBorder = world.getWorldBorder();
        final Location center = worldBorder.getCenter();
        final double size = worldBorder.getSize();
        final double radius = size / 2;

        final int minX = (int) (center.getX() - radius);
        final int maxX = (int) (center.getX() + radius);

        final int minZ = (int) (center.getZ() - radius);
        final int maxZ = (int) (center.getZ() + radius);

        final double x = Randoms.inRange(Range.of(minX, maxX)) + 0.5;
        final double z = Randoms.inRange(Range.of(minZ, maxZ)) + 0.5;
        final double y = world.getHighestBlockYAt((int) x, (int) z) + 1;

        return new Location(world, x, y, z);
    }

    public static @NotNull Location randomLocation(@NotNull final World world) {
        return randomLocation(world, new Random());
    }

}
