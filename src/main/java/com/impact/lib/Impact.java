package pl.impact.lib;

import com.destroystokyo.paper.profile.PlayerProfile;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import pl.impact.lib.api.command.MCommand;
import pl.impact.lib.api.gui.Gui;
import pl.impact.lib.api.gui.GuiModule;
import pl.impact.lib.api.gui.GuiSize;
import pl.impact.lib.api.player.ImpactPlayer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Impact Server singleton handling
 */
public final class Impact {

    private static volatile Server server;
    private static volatile Logger logger;

    private Impact() {
    }

    public static void setServer(@NotNull final Server server, @NotNull final Logger logger) {
        if (Impact.server != null) {
            throw new UnsupportedOperationException("Cannot redefine singleton Impact Server");
        }
        Impact.server = server;
        Impact.logger = logger;
        logger.info("Impact {} initialized.", ImpactLibPlugin.getVersion());
    }

    public static @NotNull Logger getLogger() {
        return logger;
    }

    public static void addListener(@NotNull final Listener listener, @NotNull final Plugin plugin) {
        server.getPluginManager().registerEvents(listener, plugin);
    }

    public static void fireEvent(@NotNull final Event event) {
        server.getPluginManager().callEvent(event);
    }

    public static @NotNull Collection<World> getWorlds() {
        return server.getWorlds();
    }

    public static @NotNull Collection<World> getWorlds(World.Environment type) {
        return server.getWorlds()
                .stream()
                .filter(world -> world.getEnvironment() == type)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static @NotNull Collection<ImpactPlayer> getPlayersInDistance(@NotNull final Location location, final double maxDistance) {
        return getPlayersInWorld(location.getWorld())
                .stream()
                .filter(impactPlayer -> impactPlayer.getCurrentLocation().distance(location) <= maxDistance)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static @NotNull Collection<ImpactPlayer> getPlayersInWorld(@NotNull final World world) {
        return getPlayers()
                .stream()
                .filter(impactPlayer -> impactPlayer.getWorld().equals(impactPlayer.getWorld()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static @NotNull Collection<ImpactPlayer> getPlayers() {
        return server.getOnlinePlayers().stream().map(ImpactPlayer::new).toList();
    }

    public static @NotNull Optional<ImpactPlayer> getPlayerByName(@Nullable final String playerName) {
        if (playerName == null) return Optional.empty();
        final Player bukkitPlayer = server.getPlayer(playerName);
        if (bukkitPlayer == null) return Optional.empty();
        final ImpactPlayer player = new ImpactPlayer(bukkitPlayer);
        return Optional.of(player);
    }

    public static @NotNull Optional<ImpactPlayer> getPlayerByUUID(@Nullable final UUID playerUuid) {
        if (playerUuid == null) return Optional.empty();
        final Player bukkitPlayer = server.getPlayer(playerUuid);
        if (bukkitPlayer == null) return Optional.empty();
        final ImpactPlayer player = new ImpactPlayer(bukkitPlayer);
        return Optional.of(player);
    }

    public static @NotNull Optional<ImpactPlayer> getPlayer(@Nullable final Player bukkitPlayer) {
        if (bukkitPlayer == null) return Optional.empty();
        return Optional.of(new ImpactPlayer(bukkitPlayer));
    }

    public static @NotNull PlayerProfile createOfflineProfile(@NotNull final String name) {
        return server.createProfile(UUID.fromString(name), name);
    }

    public static @NotNull PlayerProfile createProfile(@NotNull final UUID playerUuid, @NotNull final String playerName) {
        return server.createProfile(playerUuid, playerName);
    }

    public static @NotNull GuiModule getGuiService() {
        return ImpactLibPlugin.getInstance().getGuiService();
    }

    public static @NotNull ImpactLibPlugin getImpactLibraryPlugin() {
        return ImpactLibPlugin.getInstance();
    }

    public static @NotNull BukkitTask repeatingTask(@NotNull final Plugin owner, @NotNull final Runnable task, final long periodTicks, final long delayTicks) {
        return getServer().getScheduler().runTaskTimer(owner, task, delayTicks, periodTicks);
    }

    public static @NotNull Server getServer() {
        return Impact.server;
    }

    public static @NotNull BukkitTask repeatingTask(@NotNull final Plugin owner, @NotNull final Runnable task, final long periodTicks) {
        return getServer().getScheduler().runTaskTimer(owner, task, 0, periodTicks);
    }

    public static @NotNull Gui createGui(@Nullable final Plugin owner, @NotNull final GuiSize size, @NotNull final String id) {
        return Gui.create((owner != null ? owner : getImpactLibraryPlugin()), size, id);
    }

    public static void registerCommand(@NotNull final MCommand<?> command) {
        PluginCommand pluginCommand = server.getPluginCommand(command.getKey());
        if(pluginCommand == null) server.getCommandMap().register(command.getKey(), command);
        pluginCommand = server.getPluginCommand(command.getKey());
        if(pluginCommand == null) throw new RuntimeException("PluginCommand is null");
        pluginCommand.setAliases(command.aliases());
        if(!pluginCommand.isRegistered()) pluginCommand.register(server.getCommandMap());
        pluginCommand.setExecutor(command);
    }

    public static void cancelTask(@Nullable final BukkitTask task) {
        if (task == null) return;
        if (task.isCancelled()) return;
        task.cancel();
    }

}
