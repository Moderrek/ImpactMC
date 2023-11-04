package com.impact.lib.api.gui;

import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface GuiBase<T extends GuiViewBase<?>> {

  /**
   * Returns GUI display name.
   *
   * @return The GUI display name
   */
  @NotNull String getDisplayName();

  /**
   * Returns GUI slot count.
   *
   * @return The GUI slot count
   */
  int getSize();

  /**
   * Returns the GUI {@link Plugin} owner.
   *
   * @return The Plugin owner
   */
  @NotNull Plugin getPlugin();

  /**
   * Returns GUI {@link NamespacedKey} which should be unique.
   *
   * @return The NamespacedKey
   */
  @NotNull NamespacedKey getNamespacedKey();

  /**
   * Returns {@link Inventory} title which is show to player
   *
   * @return The {@link Component} GUI Title
   */
  @NotNull Component title();

  /**
   * Opens GUI for given player.
   *
   * @param player The Minecraft player
   * @return Optional GUI View if success
   */
  @NotNull Optional<T> open(@NotNull final Player player);

  /**
   * Invoked when player opens this gui.
   *
   * @param guiView The player Gui View
   */
  void onOpen(@NotNull final T guiView);

  /**
   * Invoked when player close this gui.
   *
   * @param guiView The player Gui View
   */
  void onClose(@NotNull final T guiView);
}
