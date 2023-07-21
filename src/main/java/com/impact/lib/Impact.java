package com.impact.lib;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.impact.lib.api.command.MCommand;
import com.impact.lib.api.gui.Gui;
import com.impact.lib.api.gui.GuiModule;
import com.impact.lib.api.gui.GuiSize;
import com.impact.lib.api.player.ImpactPlayer;
import com.impact.lib.api.util.Components;
import com.impact.lib.api.world.ImpactWorld;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Impact Server singleton handling
 */
public final class Impact {

    private static volatile Server server;
    private static volatile Logger logger;

    public static final Component SYMBOL;

    static {
        SYMBOL = Component.text("⚡")
                .color(TextColor.color(0xFFCC00))
                .decoration(TextDecoration.ITALIC, false)
                .decoration(TextDecoration.BOLD, true);
    }

    private Impact() { }

    public static void setServer(@NotNull final Server server, @NotNull final Logger logger) {
        if (Impact.server != null) {
            throw new UnsupportedOperationException("Cannot redefine singleton Impact Server");
        }
        Impact.server = server;
        Impact.logger = logger;
        logger.info("Impact {} initialized.", ImpactLibPlugin.getVersion());
        logger.info("Server Version {}", server.getMinecraftVersion());
    }

    public static @NotNull Logger getLogger() {
        return logger;
    }

    @Contract("_ -> new")
    public static @NotNull Component message(Component component) {
        return Component.text().append(SYMBOL).resetStyle().append(Component.space()).append(component).build();
    }

    public static @NotNull Component message(String content) {
        return Component.text().append(SYMBOL).resetStyle().append(Component.space()).append(Components.text(content)).build();
    }

    public static @NotNull Component simpleMessage(String content) {
        return Component.text().append(SYMBOL).resetStyle().append(Component.space()).append(Components.simple(content)).build();
    }

    public static @NotNull Component simpleMessage(String format, Object... args) {
        return Component.text().append(SYMBOL).resetStyle().append(Components.simple(format, args)).build();
    }

    public static void addListener(@NotNull final Listener listener, @NotNull final Plugin plugin) {
        server.getPluginManager().registerEvents(listener, plugin);
        logger.info("Registered new listener {}:{}", plugin.getName(), listener.getClass().getSimpleName());
        getImpactLibraryPlugin().impactRegisteredListeners.add(listener);
    }

    public static void registerListener(@NotNull final Listener listener, @NotNull final Plugin plugin) {
        addListener(listener, plugin);
    }

    public static void removeListener(@NotNull final Listener listener) {
        HandlerList.getHandlerLists()
                .forEach(handlerList -> Arrays.stream(handlerList.getRegisteredListeners())
                        .collect(Collectors.toCollection(ArrayList::new))
                        .stream()
                        .filter(registeredListener -> Objects.equals(registeredListener.getListener(), listener))
                        .forEach(registeredListener -> logger.info("Unregistered listener {}:{}", registeredListener.getPlugin().getName(), registeredListener.getListener().getClass().getSimpleName())));
        HandlerList.unregisterAll(listener);
        getImpactLibraryPlugin().impactRegisteredListeners.remove(listener);
    }

    public static void unregisterListener(@NotNull final Listener listener) {
        removeListener(listener);
    }

    public static void fireEvent(@NotNull final Event event) {
        server.getPluginManager().callEvent(event);
    }

    public static @NotNull ImpactWorld getMainWorld() {
        return new ImpactWorld(server.getWorlds().get(0));
    }

    public static @NotNull ImpactWorld getWorldByName(@NotNull String name) {
        return server.getWorlds()
                .stream()
                .filter(world -> world.getName().equalsIgnoreCase(name))
                .map(ImpactWorld::new)
                .findFirst()
                .orElseThrow();
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
        return ImpactLibPlugin.getInstance().getGuiModule();
    }

    public static @NotNull ImpactLibPlugin getImpactLibraryPlugin() {
        return ImpactLibPlugin.getInstance();
    }

    public static @NotNull BukkitTask repeatingTask(@NotNull final Plugin owner, @NotNull final Runnable task, final long periodTicks, final long delayTicks) {
        BukkitTask tsk = getServer().getScheduler().runTaskTimer(owner, task, delayTicks, periodTicks);
        logger.info("Created task {}:{}", owner.getName().toLowerCase(), tsk.getTaskId());
        return tsk;
    }

    public static @NotNull Server getServer() {
        return Impact.server;
    }

    public static @NotNull BukkitTask repeatingTask(@NotNull final Plugin owner, @NotNull final Runnable task, final long periodTicks) {
        BukkitTask tsk = getServer().getScheduler().runTaskTimer(owner, task, 0, periodTicks);
        logger.info("Created task {}:{}", owner.getName().toLowerCase(), tsk.getTaskId());
        return tsk;
    }

    public static @NotNull Gui createGui(@Nullable final Plugin owner, @NotNull final GuiSize size, @NotNull final String id) {
        return Gui.create((owner != null ? owner : getImpactLibraryPlugin()), size, id);
    }

    public static void registerCommand(@NotNull final MCommand<?> command) {
        final String key = command.getKey().toLowerCase();
        if(server.getPluginCommand(key) != null) {
            // plugin command
            PluginCommand pluginCommand = server.getPluginCommand(key);
            pluginCommand.setAliases(command.aliases());
            pluginCommand.setExecutor(command);
            pluginCommand.setTabCompleter(command);
            getImpactLibraryPlugin().impactRegisteredCommands.add(command);
            logger.info("Assigned command {}:{} {}", command.getOwner().getName().toLowerCase(), command.getKey(), !command.aliases().isEmpty() ? Arrays.toString(command.aliases().toArray()) : "");
            return;
        }
        if(server.getCommandMap().getKnownCommands().containsKey(key)) {
            logger.warn("Command {}:{} is already registered! Overwriting..", command.getOwner().getName().toLowerCase(), command.getKey());
            server.getCommandMap().getKnownCommands().get(key).unregister(server.getCommandMap());
        }
        // unregistered command
        command.setPermission(command.permission());
        command.setAliases(command.aliases());
        server.getCommandMap().register(command.getKey(),  command.getOwner().getName().toLowerCase(), command);
        if(!command.isRegistered()) command.register(server.getCommandMap());
        getImpactLibraryPlugin().impactRegisteredCommands.add(command);
        logger.info("Registered new command {}:{} {}", command.getOwner().getName().toLowerCase(), command.getKey(), !command.aliases().isEmpty() ? Arrays.toString(command.aliases().toArray()) : "");
    }

    public static void unregisterCommand(@NotNull final MCommand<?> command) {
        final String key = command.getKey().toLowerCase();
        if(server.getPluginCommand(key) != null) {
            PluginCommand pluginCommand = server.getPluginCommand(key);
            pluginCommand.setAliases(List.of());
            pluginCommand.setExecutor(null);
            getImpactLibraryPlugin().impactRegisteredCommands.remove(command);
            logger.info("Unassigned command {}:{}", command.getOwner().getName().toLowerCase(), key);
            return;
        }
        command.unregister(server.getCommandMap());
        if(server.getCommandMap().getKnownCommands().containsKey(key)) {
            server.getCommandMap().getKnownCommands().get(key).unregister(server.getCommandMap());
            command.unregister(server.getCommandMap());
            server.getCommandMap().getKnownCommands().remove(key, command);
            getImpactLibraryPlugin().impactRegisteredCommands.remove(command);
            logger.warn("Unregistered command {}:{}", command.getOwner().getName().toLowerCase(), command.getKey());
        }
    }

    @Contract("_ -> new")
    public static @NotNull NamespacedKey createKey(String key) {
        return new NamespacedKey(ImpactLibPlugin.getInstance(), key);
    }

    public static void cancelTask(@Nullable final BukkitTask task) {
        if (task == null) return;
        if (task.isCancelled()) return;
        logger.info("Cancelled task {}:{}", task.getOwner().getName(), task.getTaskId());
        task.cancel();
    }

    public static @NotNull BukkitTask laterTask(Plugin plugin, Runnable action, long ticks) {
        BukkitTask tsk = getServer().getScheduler().runTaskLater(plugin, action, ticks);
        logger.info("Created task {}:{}", plugin.getName().toLowerCase(), tsk.getTaskId());
        return tsk;
    }
}
