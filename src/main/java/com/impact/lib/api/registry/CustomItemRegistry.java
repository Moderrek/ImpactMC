package com.impact.lib.api.registry;

import com.impact.lib.api.item.CustomItem;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Contains all {@link CustomItem}.
 */
class CustomItemRegistry extends ImpactRegistry<CustomItem> {

  private final Map<NamespacedKey, RegistryObject<CustomItem>> registry = new ConcurrentHashMap<>();

  @Override
  public CustomItem add(Plugin plugin, String key, CustomItem entry) {
    NamespacedKey namespacedKey = new NamespacedKey(plugin, key);
    if (registry.containsKey(namespacedKey))
      throw new RuntimeException("Cannot registry " + key + " because this key is already registered!");
    registry.put(namespacedKey, new RegistryObject<>(plugin, entry));
    return registry.get(namespacedKey).value();
  }

  @Override
  public CustomItem get(NamespacedKey key) {
    return registry.get(key).value();
  }

  @Override
  public Collection<CustomItem> getAll() {
    return registry.values().stream().map(RegistryObject::value).collect(Collectors.toCollection(ArrayList::new));
  }

  @Override
  public void remove(CustomItem entry) {
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
  public NamespacedKey getNamespacedKey(CustomItem entry) {
    return registry.entrySet().stream()
        .filter(entries -> Objects.equals(entry, entries.getValue().value()))
        .findFirst()
        .map(Map.Entry::getKey)
        .orElseThrow();
  }

  @Override
  public Plugin getPlugin(CustomItem entry) {
    return registry.values().stream()
        .filter(entries -> Objects.equals(entries.value(), entry))
        .findFirst()
        .map(RegistryObject::owner)
        .orElseThrow();
  }

}
