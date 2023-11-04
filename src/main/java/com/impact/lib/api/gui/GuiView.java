package com.impact.lib.api.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Represents visible Inventory window.
 */
public final class GuiView extends GuiContent implements GuiViewBase<UiElement> {

  private final transient Player player;
  private final transient Gui gui;
  private final transient InventoryView inventoryView;

  GuiView(Player player, Gui gui, @NotNull Map<Integer, UiElement> uiElementList, InventoryView inventoryView) {
    super(uiElementList.size(), uiElementList);
    initContent(gui);
    this.player = player;
    this.gui = gui;
    this.inventoryView = inventoryView;
  }

  @Override
  public Player getPlayer() {
    return player;
  }

  @Override
  public Gui getGui() {
    return gui;
  }

  @Override
  public InventoryView getInventoryView() {
    return inventoryView;
  }

  /**
   * Closes Gui View.
   */
  @Override
  public void close() {
    gui.invokeClose(this);
  }
}
