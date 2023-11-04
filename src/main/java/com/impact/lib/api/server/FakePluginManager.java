package com.impact.lib.api.server;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Logger;

public class FakePluginManager implements PluginManager {
  final ArrayList<RegisteredListener> listeners = new ArrayList<>();
  final ArrayList<Plugin> plugins = new ArrayList<>();

  @Override
  public void registerInterface(final Class<? extends PluginLoader> loader) throws IllegalArgumentException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public Plugin getPlugin(final String name) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public Plugin[] getPlugins() {
    return plugins.toArray(new Plugin[0]);
  }

  @Override
  public boolean isPluginEnabled(final String name) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public boolean isPluginEnabled(final Plugin plugin) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public Plugin loadPlugin(final File file) throws UnknownDependencyException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public Plugin[] loadPlugins(final File directory) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void disablePlugins() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void clearPlugins() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void callEvent(final @NotNull Event event) throws IllegalStateException {
    Logger.getLogger("Minecraft").info("Called event " + event.getEventName());
    if (event instanceof PlayerJoinEvent) {
      for (final RegisteredListener listener : listeners) {
        if (listener.getListener() instanceof FakeAccessor epl) {
          final PlayerJoinEvent jEvent = (PlayerJoinEvent) event;
          epl.onPlayerJoin(jEvent);
          Logger.getLogger("Essentials").info("Sending join event to Essentials");
          epl.getUser(jEvent.getPlayer());
        }
      }
    }
  }

  @Override
  public void registerEvents(final Listener listener, final Plugin plugin) {
    listeners.add(new RegisteredListener(listener, null, null, plugin, false));
  }

  @Override
  public void registerEvent(final Class<? extends Event> event, final Listener listener, final EventPriority priority, final EventExecutor executor, final Plugin plugin) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void registerEvent(final Class<? extends Event> event, final Listener listener, final EventPriority priority, final EventExecutor executor, final Plugin plugin, final boolean ignoreCancelled) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void enablePlugin(final Plugin plugin) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void disablePlugin(final Plugin plugin) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public Permission getPermission(final String name) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void addPermission(final Permission perm) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void removePermission(final Permission perm) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void removePermission(final String name) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public Set<Permission> getDefaultPermissions(final boolean op) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void recalculatePermissionDefaults(final Permission perm) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void subscribeToPermission(final String permission, final Permissible permissible) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void unsubscribeFromPermission(final String permission, final Permissible permissible) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public Set<Permissible> getPermissionSubscriptions(final String permission) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void subscribeToDefaultPerms(final boolean op, final Permissible permissible) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void unsubscribeFromDefaultPerms(final boolean op, final Permissible permissible) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public Set<Permissible> getDefaultPermSubscriptions(final boolean op) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public Set<Permission> getPermissions() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public boolean useTimings() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void addPlugin(Plugin plugin) {
    plugins.add(plugin);
  }
}
