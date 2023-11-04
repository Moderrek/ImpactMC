package com.impact.lib.api.server;

import com.destroystokyo.paper.entity.ai.MobGoals;
import io.papermc.paper.datapack.DatapackManager;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.block.data.BlockData;
import org.bukkit.boss.*;
import org.bukkit.command.*;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.help.HelpMap;
import org.bukkit.inventory.*;
import org.bukkit.loot.LootTable;
import org.bukkit.map.MapView;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.potion.PotionBrewer;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scheduler.BukkitWorker;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.structure.StructureManager;
import org.bukkit.util.CachedServerIcon;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.logging.Logger;

public final class FakeServer implements Server {
  private final List<World> worlds = new ArrayList<>();
  private final PluginManager pluginManager = new FakePluginManager();
  private final List<Player> players = new ArrayList<>();

  private FakeServer() {
    createWorld("testWorld", World.Environment.NORMAL);
  }

  public @NotNull World createWorld(final String string, final World.Environment e) {
    final World w = new FakeWorld(string, e);
    worlds.add(w);
    return w;
  }

  public static @NotNull FakeServer getInstance() {
    if (Bukkit.getServer() == null) {
      Bukkit.setServer(new FakeServer());
    }
    return (FakeServer) Bukkit.getServer();
  }

  @Override
  public <T extends Keyed> Tag<T> getTag(@NotNull String registry, @NotNull NamespacedKey tag, @NotNull Class<T> clazz) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull <T extends Keyed> Iterable<Tag<T>> getTags(@NotNull String registry, @NotNull Class<T> clazz) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @Nullable LootTable getLootTable(@NotNull NamespacedKey key) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull List<Entity> selectEntities(@NotNull CommandSender sender, @NotNull String selector) throws IllegalArgumentException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull StructureManager getStructureManager() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull UnsafeValues getUnsafe() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull Spigot spigot() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void reloadPermissions() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public boolean reloadCommandAliases() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public boolean suggestPlayerNamesWhenNullTabCompletions() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull String getPermissionMessage() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public com.destroystokyo.paper.profile.@NotNull PlayerProfile createProfile(@NotNull UUID uuid) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public com.destroystokyo.paper.profile.@NotNull PlayerProfile createProfile(@NotNull String name) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public com.destroystokyo.paper.profile.@NotNull PlayerProfile createProfile(@Nullable UUID uuid, @Nullable String name) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public int getCurrentTick() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public boolean isStopping() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull MobGoals getMobGoals() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull DatapackManager getDatapackManager() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull PotionBrewer getPotionBrewer() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull File getPluginsFolder() {
    return null;
  }

  @Override
  public @NotNull String getName() {
    return "Impact Fake Server";
  }

  @Override
  public @NotNull String getVersion() {
    return "1.0";
  }

  @Override
  public @NotNull String getBukkitVersion() {
    return "Impact FakeServer";
  }

  @Override
  public @NotNull String getMinecraftVersion() {
    return null;
  }

  @Override
  public @NotNull Collection<? extends Player> getOnlinePlayers() {
    return players;
  }

  @Override
  public int getMaxPlayers() {
    return 100;
  }

  @Override
  public void setMaxPlayers(int maxPlayers) {

  }

  @Override
  public int getPort() {
    return 25565;
  }

  @Override
  public int getViewDistance() {
    return 0;
  }

  @Override
  public int getSimulationDistance() {
    return 0;
  }

  @Override
  public @NotNull String getIp() {
    return "127.0.0.1";
  }

  @Override
  public @NotNull String getWorldType() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public boolean getGenerateStructures() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public int getMaxWorldSize() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public boolean getAllowEnd() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public boolean getAllowNether() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull String getResourcePack() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull String getResourcePackHash() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull String getResourcePackPrompt() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public boolean isResourcePackRequired() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public boolean hasWhitelist() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void setWhitelist(boolean value) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public boolean isWhitelistEnforced() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void setWhitelistEnforced(boolean value) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull Set<OfflinePlayer> getWhitelistedPlayers() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void reloadWhitelist() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public int broadcastMessage(@NotNull String message) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull String getUpdateFolder() {
    return "update";
  }

  @Override
  public @NotNull File getUpdateFolderFile() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public long getConnectionThrottle() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public int getTicksPerAnimalSpawns() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public int getTicksPerMonsterSpawns() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public int getTicksPerWaterSpawns() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public int getTicksPerWaterAmbientSpawns() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public int getTicksPerWaterUndergroundCreatureSpawns() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public int getTicksPerAmbientSpawns() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public int getTicksPerSpawns(@NotNull SpawnCategory spawnCategory) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @Nullable Player getPlayer(@NotNull String name) {
    for (Player player : players) {
      if (player.getName().equalsIgnoreCase(name)) return player;
    }
    return null;
  }

  @Override
  public @Nullable Player getPlayerExact(@NotNull String name) {
    for (final Player player : players) {
      if (player.getName().equals(name)) return player;
    }
    return null;
  }

  @Override
  public @NotNull List<Player> matchPlayer(@NotNull String name) {
    final List<Player> matches = new ArrayList<>();
    for (final Player player : players) {
      if (player.getName().substring(0, Math.min(player.getName().length(), name.length())).equalsIgnoreCase(name)) {
        matches.add(player);
      }
    }
    return matches;
  }

  @Override
  public @Nullable Player getPlayer(@NotNull UUID id) {
    for (Player player : players) {
      if (player.getUniqueId().equals(id)) return player;
    }
    return null;
  }

  @Override
  public @Nullable UUID getPlayerUniqueId(@NotNull String playerName) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull PluginManager getPluginManager() {
    return pluginManager;
  }

  @Override
  public @NotNull BukkitScheduler getScheduler() {
    return new BukkitScheduler() {
      @Override
      public int scheduleSyncDelayedTask(final Plugin plugin, final Runnable r, final long l) {
        throw new UnsupportedOperationException("Not supported yet.");
      }

      @Override
      public int scheduleSyncDelayedTask(final Plugin plugin, final BukkitRunnable bukkitRunnable, final long l) {
        throw new UnsupportedOperationException("Not supported yet.");
      }

      @Override
      public int scheduleSyncDelayedTask(final Plugin plugin, final Runnable r) {
        return -1;
      }

      @Override
      public int scheduleSyncDelayedTask(final Plugin plugin, final BukkitRunnable bukkitRunnable) {
        throw new UnsupportedOperationException("Not supported yet.");
      }

      @Override
      public int scheduleSyncRepeatingTask(final Plugin plugin, final Runnable r, final long l, final long l1) {
        throw new UnsupportedOperationException("Not supported yet.");
      }

      @Override
      public int scheduleSyncRepeatingTask(final Plugin plugin, final BukkitRunnable bukkitRunnable, final long l, final long l1) {
        throw new UnsupportedOperationException("Not supported yet.");
      }

      @Override
      public int scheduleAsyncDelayedTask(final Plugin plugin, final Runnable r, final long l) {
        throw new UnsupportedOperationException("Not supported yet.");
      }

      @Override
      public int scheduleAsyncDelayedTask(final Plugin plugin, final Runnable r) {
        throw new UnsupportedOperationException("Not supported yet.");
      }

      @Override
      public int scheduleAsyncRepeatingTask(final Plugin plugin, final Runnable r, final long l, final long l1) {
        throw new UnsupportedOperationException("Not supported yet.");
      }

      @Override
      public <T> Future<T> callSyncMethod(final Plugin plugin, final Callable<T> clbl) {
        throw new UnsupportedOperationException("Not supported yet.");
      }

      @Override
      public void cancelTask(final int i) {
        throw new UnsupportedOperationException("Not supported yet.");
      }

      @Override
      public void cancelTasks(final Plugin plugin) {
        throw new UnsupportedOperationException("Not supported yet.");
      }

      @Override
      public boolean isCurrentlyRunning(final int i) {
        throw new UnsupportedOperationException("Not supported yet.");
      }

      @Override
      public boolean isQueued(final int i) {
        throw new UnsupportedOperationException("Not supported yet.");
      }

      @Override
      public List<BukkitWorker> getActiveWorkers() {
        throw new UnsupportedOperationException("Not supported yet.");
      }

      @Override
      public List<BukkitTask> getPendingTasks() {
        throw new UnsupportedOperationException("Not supported yet.");
      }

      @Override
      public BukkitTask runTask(final Plugin plugin, final Runnable r) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
      }

      @Override
      public void runTask(@NotNull Plugin plugin, @NotNull Consumer<BukkitTask> task) throws IllegalArgumentException {

      }

      @Override
      public BukkitTask runTask(final Plugin plugin, final BukkitRunnable bukkitRunnable) throws IllegalArgumentException {
        return null;
      }

      @Override
      public BukkitTask runTaskAsynchronously(final Plugin plugin, final Runnable r) throws IllegalArgumentException {
        r.run();
        return null;
      }

      @Override
      public void runTaskAsynchronously(@NotNull Plugin plugin, @NotNull Consumer<BukkitTask> task) throws IllegalArgumentException {

      }

      @Override
      public BukkitTask runTaskAsynchronously(final Plugin plugin, final BukkitRunnable bukkitRunnable) throws IllegalArgumentException {
        return null;
      }

      @Override
      public BukkitTask runTaskLater(final Plugin plugin, final Runnable r, final long l) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
      }

      @Override
      public void runTaskLater(@NotNull Plugin plugin, @NotNull Consumer<BukkitTask> task, long delay) throws IllegalArgumentException {

      }

      @Override
      public BukkitTask runTaskLater(final Plugin plugin, final BukkitRunnable bukkitRunnable, final long l) throws IllegalArgumentException {
        return null;
      }

      @Override
      public BukkitTask runTaskLaterAsynchronously(final Plugin plugin, final Runnable r, final long l) throws IllegalArgumentException {
        r.run();
        return null;
      }

      @Override
      public void runTaskLaterAsynchronously(@NotNull Plugin plugin, @NotNull Consumer<BukkitTask> task, long delay) throws IllegalArgumentException {

      }

      @Override
      public BukkitTask runTaskLaterAsynchronously(final Plugin plugin, final BukkitRunnable bukkitRunnable, final long l) throws IllegalArgumentException {
        return null;
      }

      @Override
      public BukkitTask runTaskTimer(final Plugin plugin, final Runnable r, final long l, final long l1) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
      }

      @Override
      public void runTaskTimer(@NotNull Plugin plugin, @NotNull Consumer<BukkitTask> task, long delay, long period) throws IllegalArgumentException {

      }

      @Override
      public BukkitTask runTaskTimer(final Plugin plugin, final BukkitRunnable bukkitRunnable, final long l, final long l1) throws IllegalArgumentException {
        return null;
      }

      @Override
      public BukkitTask runTaskTimerAsynchronously(final Plugin plugin, final Runnable r, final long l, final long l1) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
      }

      @Override
      public void runTaskTimerAsynchronously(@NotNull Plugin plugin, @NotNull Consumer<BukkitTask> task, long delay, long period) throws IllegalArgumentException {

      }

      @Override
      public BukkitTask runTaskTimerAsynchronously(final Plugin plugin, final BukkitRunnable bukkitRunnable, final long l, final long l1) throws IllegalArgumentException {
        return null;
      }

      @Override
      public @NotNull Executor getMainThreadExecutor(@NotNull Plugin plugin) {
        return null;
      }
    };
  }

  @Override
  public @NotNull ServicesManager getServicesManager() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull List<World> getWorlds() {
    return worlds;
  }

  @Override
  public @Nullable World createWorld(@NotNull WorldCreator creator) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public boolean unloadWorld(@NotNull String name, boolean save) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public boolean unloadWorld(@NotNull World world, boolean save) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @Nullable World getWorld(@NotNull String name) {
    for (World world : worlds) {
      if (world.getName().equals(name)) return world;
    }
    return null;
  }

  @Override
  public @Nullable World getWorld(@NotNull UUID uid) {
    for (World world : worlds) {
      if (world.getUID().equals(uid)) return world;
    }
    return null;
  }

  @Override
  public @Nullable World getWorld(@NotNull NamespacedKey worldKey) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @Nullable MapView getMap(int id) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull MapView createMap(@NotNull World world) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull ItemStack createExplorerMap(@NotNull World world, @NotNull Location location, @NotNull StructureType structureType) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull ItemStack createExplorerMap(@NotNull World world, @NotNull Location location, @NotNull StructureType structureType, int radius, boolean findUnexplored) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void reload() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void reloadData() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull Logger getLogger() {
    return Logger.getLogger("Minecraft");
  }

  @Override
  public @Nullable PluginCommand getPluginCommand(@NotNull String name) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void savePlayers() {
    // nothing
  }

  @Override
  public boolean dispatchCommand(@NotNull CommandSender sender, @NotNull String commandLine) throws CommandException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public boolean addRecipe(@Nullable Recipe recipe) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull List<Recipe> getRecipesFor(@NotNull ItemStack result) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @Nullable Recipe getRecipe(@NotNull NamespacedKey recipeKey) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @Nullable Recipe getCraftingRecipe(@NotNull ItemStack[] craftingMatrix, @NotNull World world) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull ItemStack craftItem(@NotNull ItemStack[] craftingMatrix, @NotNull World world, @NotNull Player player) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull Iterator<Recipe> recipeIterator() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void clearRecipes() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void resetRecipes() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public boolean removeRecipe(@NotNull NamespacedKey key) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull Map<String, String[]> getCommandAliases() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public int getSpawnRadius() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void setSpawnRadius(int value) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public boolean getHideOnlinePlayers() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public boolean getOnlineMode() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public boolean getAllowFlight() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public boolean isHardcore() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void shutdown() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public int broadcast(@NotNull String message, @NotNull String permission) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public int broadcast(@NotNull Component message) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public int broadcast(@NotNull Component message, @NotNull String permission) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull OfflinePlayer getOfflinePlayer(@NotNull String name) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @Nullable OfflinePlayer getOfflinePlayerIfCached(@NotNull String name) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull OfflinePlayer getOfflinePlayer(@NotNull UUID id) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull PlayerProfile createPlayerProfile(@Nullable UUID uniqueId, @Nullable String name) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull PlayerProfile createPlayerProfile(@NotNull UUID uniqueId) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull PlayerProfile createPlayerProfile(@NotNull String name) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull Set<String> getIPBans() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void banIP(@NotNull String address) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void unbanIP(@NotNull String address) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull Set<OfflinePlayer> getBannedPlayers() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull BanList getBanList(BanList.@NotNull Type type) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull Set<OfflinePlayer> getOperators() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull GameMode getDefaultGameMode() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void setDefaultGameMode(@NotNull GameMode mode) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull ConsoleCommandSender getConsoleSender() {
    return new ConsoleCommandSender() {
      @Override
      public boolean isPermissionSet(final String name) {
        throw new UnsupportedOperationException("Not supported yet.");
      }      @Override
      public void sendMessage(final String message) {
        System.out.println("Console message: " + message);
      }

      @Override
      public boolean isPermissionSet(final Permission perm) {
        throw new UnsupportedOperationException("Not supported yet.");
      }      @Override
      public void sendMessage(final String @NotNull [] messages) {
        for (final String message : messages) {
          System.out.println("Console message: " + message);
        }
      }

      @Override
      public boolean hasPermission(final String name) {
        return true;
      }      public void sendMessage(final UUID uuid, final String message) {
        sendMessage(message);
      }

      @Override
      public boolean hasPermission(final Permission perm) {
        throw new UnsupportedOperationException("Not supported yet.");
      }      public void sendMessage(final UUID uuid, final String[] messages) {
        sendMessage(messages);
      }

      @Override
      public PermissionAttachment addAttachment(final Plugin plugin, final String name, final boolean value) {
        throw new UnsupportedOperationException("Not supported yet.");
      }      @Override
      public Server getServer() {
        throw new UnsupportedOperationException("Not supported yet.");
      }

      @Override
      public PermissionAttachment addAttachment(final Plugin plugin) {
        throw new UnsupportedOperationException("Not supported yet.");
      }      @Override
      public String getName() {
        throw new UnsupportedOperationException("Not supported yet.");
      }

      @Override
      public PermissionAttachment addAttachment(final Plugin plugin, final String name, final boolean value, final int ticks) {
        throw new UnsupportedOperationException("Not supported yet.");
      }      @Override
      public @NotNull Spigot spigot() {
        throw new UnsupportedOperationException("Not supported yet.");
      }

      @Override
      public PermissionAttachment addAttachment(final Plugin plugin, final int ticks) {
        throw new UnsupportedOperationException("Not supported yet.");
      }      @Override
      public @NotNull Component name() {
        throw new UnsupportedOperationException("Not supported yet.");
      }

      @Override
      public void removeAttachment(final PermissionAttachment attachment) {
        throw new UnsupportedOperationException("Not supported yet.");
      }

      @Override
      public void recalculatePermissions() {
        throw new UnsupportedOperationException("Not supported yet.");
      }

      @Override
      public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        throw new UnsupportedOperationException("Not supported yet.");
      }

      @Override
      public boolean isOp() {
        return true;
      }

      @Override
      public void setOp(final boolean value) {
        throw new UnsupportedOperationException("Not supported yet.");
      }

      @Override
      public boolean isConversing() {
        throw new UnsupportedOperationException("Not supported yet.");
      }

      @Override
      public void acceptConversationInput(final String input) {
        throw new UnsupportedOperationException("Not supported yet.");
      }

      @Override
      public boolean beginConversation(final Conversation conversation) {
        throw new UnsupportedOperationException("Not supported yet.");
      }

      @Override
      public void abandonConversation(final Conversation conversation) {
        throw new UnsupportedOperationException("Not supported yet.");
      }

      @Override
      public void abandonConversation(final Conversation conversation, final ConversationAbandonedEvent details) {
        throw new UnsupportedOperationException("Not supported yet.");
      }

      @Override
      public void sendRawMessage(final String message) {
        throw new UnsupportedOperationException("Not supported yet.");
      }

      @Override
      public void sendRawMessage(@Nullable UUID sender, @NotNull String message) {

      }
















    };
  }

  @Override
  public @NotNull CommandSender createCommandSender(@NotNull Consumer<? super Component> feedback) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull File getWorldContainer() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull OfflinePlayer[] getOfflinePlayers() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull Messenger getMessenger() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull HelpMap getHelpMap() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull Inventory createInventory(@Nullable InventoryHolder owner, @NotNull InventoryType type) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull Inventory createInventory(@Nullable InventoryHolder owner, @NotNull InventoryType type, @NotNull Component title) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull Inventory createInventory(@Nullable InventoryHolder owner, @NotNull InventoryType type, @NotNull String title) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull Inventory createInventory(@Nullable InventoryHolder owner, int size) throws IllegalArgumentException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull Inventory createInventory(@Nullable InventoryHolder owner, int size, @NotNull Component title) throws IllegalArgumentException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull Inventory createInventory(@Nullable InventoryHolder owner, int size, @NotNull String title) throws IllegalArgumentException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull Merchant createMerchant(@Nullable Component title) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull Merchant createMerchant(@Nullable String title) {
    throw new UnsupportedOperationException("Not supported yet.");
  }  @Override
  public void setIdleTimeout(int threshold) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public int getMonsterSpawnLimit() {
    throw new UnsupportedOperationException("Not supported yet.");
  }  @Override
  public int getIdleTimeout() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public int getAnimalSpawnLimit() {
    throw new UnsupportedOperationException("Not supported yet.");
  }  @Override
  public ChunkGenerator.@NotNull ChunkData createChunkData(@NotNull World world) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public int getWaterAnimalSpawnLimit() {
    throw new UnsupportedOperationException("Not supported yet.");
  }  @Override
  @Deprecated
  public ChunkGenerator.@NotNull ChunkData createVanillaChunkData(@NotNull World world, int x, int z) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public int getWaterAmbientSpawnLimit() {
    throw new UnsupportedOperationException("Not supported yet.");
  }  @Override
  public @NotNull BossBar createBossBar(@Nullable String title, @NotNull BarColor color, @NotNull BarStyle style, @NotNull BarFlag... flags) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public int getWaterUndergroundCreatureSpawnLimit() {
    throw new UnsupportedOperationException("Not supported yet.");
  }  @Override
  public @NotNull KeyedBossBar createBossBar(@NotNull NamespacedKey key, @Nullable String title, @NotNull BarColor color, @NotNull BarStyle style, @NotNull BarFlag... flags) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public int getAmbientSpawnLimit() {
    throw new UnsupportedOperationException("Not supported yet.");
  }  @Override
  public @NotNull Iterator<KeyedBossBar> getBossBars() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public int getSpawnLimit(@NotNull SpawnCategory spawnCategory) {
    throw new UnsupportedOperationException("Not supported yet.");
  }  @Override
  public @Nullable KeyedBossBar getBossBar(@NotNull NamespacedKey key) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public boolean isPrimaryThread() {
    return true;
  }  @Override
  public boolean removeBossBar(@NotNull NamespacedKey key) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull Component motd() {
    throw new UnsupportedOperationException("Not supported yet.");
  }  @Override
  public @Nullable Entity getEntity(@NotNull UUID uuid) {
    return getPlayer(uuid);
  }

  @Override
  public @NotNull String getMotd() {
    throw new UnsupportedOperationException("Not supported yet.");
  }  @Override
  public @NotNull double[] getTPS() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @Nullable Component shutdownMessage() {
    throw new UnsupportedOperationException("Not supported yet.");
  }  @Override
  public @NotNull long[] getTickTimes() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @Nullable String getShutdownMessage() {
    throw new UnsupportedOperationException("Not supported yet.");
  }  @Override
  public double getAverageTickTime() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public Warning.@NotNull WarningState getWarningState() {
    return Warning.WarningState.DEFAULT;
  }  @Override
  public @NotNull CommandMap getCommandMap() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull ItemFactory getItemFactory() {
    throw new UnsupportedOperationException("Not supported yet.");
  }  @Override
  public @Nullable Advancement getAdvancement(@NotNull NamespacedKey key) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull ScoreboardManager getScoreboardManager() {
    throw new UnsupportedOperationException("Not supported yet.");
  }  @Override
  public @NotNull Iterator<Advancement> advancementIterator() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @Nullable CachedServerIcon getServerIcon() {
    throw new UnsupportedOperationException("Not supported yet.");
  }  @Override
  public @NotNull BlockData createBlockData(@NotNull Material material) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull CachedServerIcon loadServerIcon(@NotNull File file) throws Exception {
    throw new UnsupportedOperationException("Not supported yet.");
  }  @Override
  public @NotNull BlockData createBlockData(@NotNull Material material, @Nullable Consumer<BlockData> consumer) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull CachedServerIcon loadServerIcon(@NotNull BufferedImage image) throws Exception {
    throw new UnsupportedOperationException("Not supported yet.");
  }  @Override
  public @NotNull BlockData createBlockData(@NotNull String data) throws IllegalArgumentException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull Iterable<? extends Audience> audiences() {
    throw new UnsupportedOperationException("Not supported yet.");
  }  @Override
  public @NotNull BlockData createBlockData(@Nullable Material material, @Nullable String data) throws IllegalArgumentException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void sendPluginMessage(@NotNull Plugin source, @NotNull String channel, @NotNull byte[] message) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public @NotNull Set<String> getListeningPluginChannels() {
    throw new UnsupportedOperationException("Not supported yet.");
  }








































}
