package com.impact.lib.api.registry;

import com.impact.lib.Impact;
import com.impact.lib.api.command.MCommand;
import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public final class CommandRegistry extends ImpactRegistry<MCommand<?>> {

  private final Map<NamespacedKey, RegistryObject<MCommand<?>>> registry = new ConcurrentHashMap<>();

  @Override
  public MCommand<?> add(Plugin plugin, String key, MCommand<?> command) {
    NamespacedKey namespacedKey = new NamespacedKey(plugin, key);

    if (registry.containsKey(namespacedKey))
      throw new RuntimeException("Cannot registry " + key + " because this key is already registered!");

    String label = key.toLowerCase();
    String pluginName = plugin.getName().toLowerCase();
    Server server = Impact.getServer();
    Logger logger = Impact.getLogger();
    registry.put(namespacedKey, new RegistryObject<>(plugin, command));
    MCommand<?> val = registry.get(namespacedKey).value();

    if (server.getPluginCommand(label) != null) {
      // plugin command
      PluginCommand pluginCommand = server.getPluginCommand(label);
      assert pluginCommand != null;
      pluginCommand.setAliases(command.aliases());
      pluginCommand.setExecutor(command);
      pluginCommand.setTabCompleter(command);
      logger.info("Assigned command {}:{} {}", pluginName, label, !command.aliases().isEmpty() ? Arrays.toString(command.aliases().toArray()) : "");
      return val;
    }

    if (server.getCommandMap().getKnownCommands().containsKey(label)) {
      logger.warn("Command {}:{} is already registered! Overwriting..", pluginName, label);
      server.getCommandMap().getKnownCommands().get(label).unregister(server.getCommandMap());
    }
    // unregistered command
    command.setPermission(command.permission());
    command.setAliases(command.aliases());
    server.getCommandMap().register(label, pluginName, command);
    if (!command.isRegistered()) command.register(server.getCommandMap());
    logger.info("Registered new command {}:{} {}", pluginName, label, !command.aliases().isEmpty() ? Arrays.toString(command.aliases().toArray()) : "");
    return val;
  }

  @Override
  public MCommand<?> get(NamespacedKey key) {
    return registry.get(key).value();
  }

  @Override
  public Collection<MCommand<?>> getAll() {
    return registry.values().stream().map(RegistryObject::value).collect(Collectors.toCollection(ArrayList::new));
  }

  @Override
  public void remove(MCommand<?> entry) {
    unregister(entry);
    registry.remove(getNamespacedKey(entry));
  }

  private void unregister(@NotNull MCommand<?> command) {
    Server server = Impact.getServer();
    Logger logger = Impact.getLogger();
    String pluginName = command.getOwner().getName().toLowerCase();
    String label = command.getKey().toLowerCase();

    if (server.getPluginCommand(pluginName) != null) {
      PluginCommand pluginCommand = server.getPluginCommand(pluginName);
      assert pluginCommand != null;
      pluginCommand.setAliases(List.of());
      pluginCommand.setExecutor(null);
      logger.info("Unassigned command {}:{}", pluginName, label);
      return;
    }

    command.unregister(server.getCommandMap());
    if (server.getCommandMap().getKnownCommands().containsKey(label)) {
      server.getCommandMap().getKnownCommands().get(label).unregister(server.getCommandMap());
      command.unregister(server.getCommandMap());
      server.getCommandMap().getKnownCommands().remove(label, command);
      logger.warn("Unregistered command {}:{}", pluginName, label);
    }
  }

  @Override
  public void remove(Plugin plugin, String key) {
    NamespacedKey namespacedKey = new NamespacedKey(plugin, key);
    unregister(get(namespacedKey));
    registry.remove(namespacedKey);
  }

  @Override
  public void remove(NamespacedKey id) {
    unregister(get(id));
    registry.remove(id);
  }

  @Override
  public boolean isRegistered(NamespacedKey key) {
    return registry.containsKey(key);
  }

  @Override
  public NamespacedKey getNamespacedKey(MCommand<?> entry) {
    return registry.entrySet().stream().filter(entries -> entry == entries.getValue().value()).findFirst().map(Map.Entry::getKey).orElse(null);
  }

  @Override
  public Plugin getPlugin(MCommand<?> entry) {
    return registry.values().stream().filter(entries -> entries.value() == entry).findFirst().map(RegistryObject::owner).orElse(null);
  }

}
