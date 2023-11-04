package com.impact.lib.api.gui.event;

import com.impact.lib.api.gui.GuiView;
import com.impact.lib.api.gui.UiElement;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public final class GuiClickEvent {

  private final ClickType clickType;
  private transient final GuiView guiView;
  private final int slotIndex;
  private transient final Player whoClicked;
  private final UiElement clickedElement;

  private boolean canPickup = false;

  public GuiClickEvent(ClickType clickType, GuiView guiView, int slotIndex, Player whoClicked, UiElement clickedElement) {
    this.clickType = clickType;
    this.guiView = guiView;
    this.slotIndex = slotIndex;
    this.whoClicked = whoClicked;
    this.clickedElement = clickedElement;
  }

  public ClickType getClickType() {
    return clickType;
  }

  public GuiView getGuiView() {
    return guiView;
  }

  public int getSlotIndex() {
    return slotIndex;
  }

  public int getSlot() {
    return slotIndex + 1;
  }

  public Player getWhoClicked() {
    return whoClicked;
  }

  public UiElement getClickedElement() {
    return clickedElement;
  }

  public boolean canPickup() {
    return canPickup;
  }

  public void allowPickup() {
    canPickup = true;
  }

  public void denyPickup() {
    canPickup = false;
  }

  public void setCanPickup(boolean canPickup) {
    this.canPickup = canPickup;
  }


  public void updateGui() {
    guiView.getGui().update();
  }

  public void updateView() {
    guiView.getGui().update(whoClicked.getUniqueId());
  }

  public void updateClickedElement() {
    guiView.getGui().update(clickedElement, whoClicked.getUniqueId());
  }

  public void playSound(Sound sound, float volume) {
    playSound(sound, volume, 1.0F);
  }

  public void playSound(Sound sound, float volume, float pitch) {
    whoClicked.playSound(whoClicked.getLocation(), sound, volume, pitch);
  }

  public void playSound(Sound sound) {
    playSound(sound, 1.0F, 1.0F);
  }

  public void closeGui() {
    clickedElement.closeGui(guiView);
  }

  public void closeView() {
    clickedElement.closeGui(guiView);
  }

}
