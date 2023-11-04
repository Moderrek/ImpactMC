package com.impact.lib.api.registry;

import com.impact.lib.api.gui.Gui;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class GuiRegistry extends ImpactRegistry<Gui> {
  private final Map<NamespacedKey, RegistryObject<Gui>> registry = new ConcurrentHashMap<>();

  @Override
  public Gui add(Plugin plugin, String key, Gui entry) {
    NamespacedKey namespacedKey = new NamespacedKey(plugin, key);
    if (registry.containsKey(namespacedKey))
      throw new RuntimeException("Cannot registry " + key + " because this key is already registered!");
    registry.put(namespacedKey, new RegistryObject<>(plugin, entry));
    return registry.get(namespacedKey).value();
  }

  @Override
  public Gui get(NamespacedKey key) {
    return registry.get(key).value();
  }

  @Override
  public Collection<Gui> getAll() {
    return registry.values().stream().map(RegistryObject::value).collect(Collectors.toCollection(ArrayList::new));
  }

  @Override
  public void remove(Gui entry) {
    registry.remove(getNamespacedKey(entry));
  }

  @Override
  public void remove(Plugin plugin, String key) {
    registry.remove(new NamespacedKey(plugin, key));
  }

  @Override
  public void remove(NamespacedKey id) {
    registry.remove(id);
  }

  @Override
  public boolean isRegistered(NamespacedKey key) {
    return registry.containsKey(key);
  }

  @Override
  public NamespacedKey getNamespacedKey(Gui entry) {
    for (var entries : registry.entrySet()) {
      if (entry == entries.getValue().value()) {
        return entries.getKey();
      }
    }
    return null;
  }

  @Override
  public Plugin getPlugin(Gui entry) {
    for (var entries : registry.values()) {
      if (entries.value() == entry) {
        return entries.owner();
      }
    }
    return null;
  }
}
