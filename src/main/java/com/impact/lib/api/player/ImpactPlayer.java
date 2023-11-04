package com.impact.lib.api.player;

import com.destroystokyo.paper.ClientOption;
import com.impact.lib.api.gui.Gui;
import com.impact.lib.api.world.ImpactWorld;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

public class ImpactPlayer {

  private final Player bukkitPlayer;

  public ImpactPlayer(@NotNull final Player player) {
    this.bukkitPlayer = player;
  }

  public ImpactWorld getImpactWorld() {
    return new ImpactWorld(bukkitPlayer.getWorld());
  }

  public String getName() {
    return bukkitPlayer.getName();
  }

  public String getClientName() {
    return bukkitPlayer.getClientBrandName();
  }

  public String getLocaleOption() {
    return bukkitPlayer.getClientOption(ClientOption.LOCALE);
  }

  public Locale getLocale() {
    return bukkitPlayer.locale();
  }

  public boolean canSee(@NotNull final Player other) {
    return bukkitPlayer.canSee(other);
  }

  public boolean hasChatEnabled() {
    return bukkitPlayer.getClientOption(ClientOption.CHAT_VISIBILITY) == ClientOption.ChatVisibility.FULL;
  }

  public void message(Component message) {
    bukkitPlayer.sendMessage(message);
  }

  public void playSound(Sound sound, float volume, float pitch) {
    bukkitPlayer.playSound(getCurrentLocation(), sound, volume, pitch);
  }

  public Location getCurrentLocation() {
    return bukkitPlayer.getLocation();
  }

  public void openGUI(@NotNull final Gui gui) {
    gui.open(bukkitPlayer);
  }

  public Optional<String> requestInput() {
    return Optional.empty();
  }

  public UUID getMinecraftUUID() {
    return bukkitPlayer.getUniqueId();
  }

  public World getWorld() {
    return bukkitPlayer.getWorld();
  }

  public void giveItems(@Nullable final ItemStack... items) {
    for (ItemStack item : items) giveItem(item);
  }

  public void giveItem(@Nullable final ItemStack item) {
    if (item == null) return;
    if (bukkitPlayer.getInventory().firstEmpty() == -1) {
      bukkitPlayer.getLocation().getWorld().dropItem(bukkitPlayer.getLocation(), item);
      return;
    }
    bukkitPlayer.getInventory().addItem(item);
  }

  public Player getBukkitPlayer() {
    return bukkitPlayer;
  }
}
