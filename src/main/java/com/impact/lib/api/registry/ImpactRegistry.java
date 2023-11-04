package com.impact.lib.api.registry;

import com.impact.lib.Impact;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Objects;

public abstract class ImpactRegistry<T> {

  public static <T> T register(@NotNull ImpactRegistry<? super T> registry, @NotNull NamespacedKey key, T entry) {
    Impact.getLogger().info("Registering {} in {}", key, registry.getClass().getSimpleName());
    Plugin plugin = Impact.findPlugin(key.getNamespace(), true).orElseThrow();
    String id = key.getKey();
    return (T) Objects.requireNonNull(registry).add(plugin, id, entry);
  }

  public abstract T add(Plugin plugin, String key, T entry);

  public static <T> T register(@NotNull ImpactRegistry<? super T> registry, Plugin plugin, String key, T entry) {
    Impact.getLogger().info("Registering {} in {}", key, registry.getClass().getSimpleName());
    return (T) Objects.requireNonNull(registry).add(plugin, key, entry);
  }

  public abstract T get(NamespacedKey key);

  public abstract Collection<T> getAll();

  public abstract void remove(T entry);

  public abstract void remove(Plugin plugin, String key);

  public abstract void remove(NamespacedKey id);

  public abstract boolean isRegistered(NamespacedKey key);

  public abstract NamespacedKey getNamespacedKey(T entry);

  public abstract Plugin getPlugin(T entry);
}
