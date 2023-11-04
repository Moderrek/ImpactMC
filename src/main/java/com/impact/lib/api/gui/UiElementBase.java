package com.impact.lib.api.gui;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface UiElementBase<V extends GuiViewBase<?>> {

  /**
   * Constructs {@link ItemStack} to show in Minecraft {@link Inventory}.
   *
   * @param where The view where element is constructed
   * @return The visible item in inventory which cannot be null
   */
  @NotNull ItemStack construct(@NotNull final V where);

  /**
   * Called when element is created in view
   *
   * @param where The view
   */
  void onCreate(@NotNull final V where);

  /**
   * Called when element is destroyed in view
   *
   * @param where The view
   */
  void onDestroy(@NotNull final V where);
}
