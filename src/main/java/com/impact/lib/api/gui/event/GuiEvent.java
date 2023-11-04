package com.impact.lib.api.gui.event;

import com.impact.lib.api.gui.Gui;
import com.impact.lib.api.gui.GuiView;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class GuiEvent extends Event {

  private static final HandlerList HANDLERS = new HandlerList();

  protected transient final Player player;
  protected final Gui gui;
  protected final GuiView guiView;

  protected GuiEvent(@NotNull GuiView guiView) {
    this.player = guiView.getPlayer();
    this.gui = guiView.getGui();
    this.guiView = guiView;
  }

  public static HandlerList getHandlerList() {
    return HANDLERS;
  }

  public Player getPlayer() {
    return player;
  }

  public Gui getGui() {
    return gui;
  }

  public GuiView getView() {
    return guiView;
  }

  public InventoryView getInventoryView() {
    return guiView.getInventoryView();
  }

  public String getDisplayName() {
    return gui.getDisplayName();
  }

  public Plugin getPlugin() {
    return gui.getPlugin();
  }

  public NamespacedKey getNamespacedKey() {
    return gui.getNamespacedKey();
  }

  @Override
  public @NotNull HandlerList getHandlers() {
    return HANDLERS;
  }

}
