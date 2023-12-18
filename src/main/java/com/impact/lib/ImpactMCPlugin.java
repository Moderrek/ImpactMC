package com.impact.lib;

import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public abstract class ImpactMCPlugin extends JavaPlugin {

  protected Logger logger = getSLF4JLogger();
  private long start;

  @Override
  public void onDisable() {
    onPluginStop();
  }

  @Override
  public void onEnable() {
    start = System.currentTimeMillis();
    onPluginStart();
  }

  public abstract void onPluginStart();

  public abstract void onPluginStop();

  protected long getMillisFromStart() {
    return System.currentTimeMillis() - start;
  }

  protected NamespacedKey pluginKey(@NotNull String value) {
    return new NamespacedKey(this, value);
  }

  protected void registerEvents(@NotNull Listener listener) {
    Impact.registerListener(listener, this);
  }

}
