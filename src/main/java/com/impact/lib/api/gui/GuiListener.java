package com.impact.lib.api.gui;

import com.impact.lib.api.gui.element.ClickableUiElement;
import com.impact.lib.api.gui.event.ClickType;
import com.impact.lib.api.gui.event.GuiClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public final class GuiListener implements Listener {

  private transient final GuiModule guiService;

  GuiListener(GuiModule guiService) {
    this.guiService = guiService;
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerCloseInventory(@NotNull InventoryCloseEvent event) {
    final UUID uuid = event.getPlayer().getUniqueId();
    if (guiService.isOpenGui(uuid)) guiService.closeGui(uuid);
  }

  @EventHandler
  public void onPlayerInventoryDrag(@NotNull final InventoryDragEvent event) {
    if (!(event.getWhoClicked() instanceof Player player)) return;
    if (!guiService.isOpenGui(player.getUniqueId())) return;
    final GuiView guiView = guiService.getOpenGui(player.getUniqueId()).getView(player.getUniqueId());
    if (!Objects.equals(event.getView(), guiView.getInventoryView())) return;
    event.setCancelled(true);
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerInventoryClick(@NotNull InventoryClickEvent event) {
    if (!(event.getWhoClicked() instanceof Player player)) return;
    if (event.getClickedInventory() == null) return;
    if (!guiService.isOpenGui(player.getUniqueId())) return;
    final GuiView guiView = guiService.getOpenGui(player.getUniqueId()).getView(player.getUniqueId());
    if (!Objects.equals(guiView.getInventoryView(), event.getView())) return;

    // if clicked in gui block
    event.setCancelled(true);
    if (event.getCurrentItem() == null) return;
    final int slotIndex = event.getRawSlot();
    // get clicked ui element
    final Optional<UiElement> uiElementOptional = guiView.getUiElement(slotIndex);
    if (uiElementOptional.isEmpty()) return;
    final UiElement uiElement = guiView.getUiElement(slotIndex).orElseThrow();
    // click type
    final Optional<ClickType> optionalClickType = convertInventoryAction(event.getAction());
    if (optionalClickType.isEmpty()) return;
    // create click event
    final GuiClickEvent clickEvent = new GuiClickEvent(optionalClickType.get(), guiView, slotIndex, player, uiElement);
    // call event to ui element
    uiElement.callClick(clickEvent);
    // if ui element is clickable ui element call event
    if (uiElement instanceof ClickableUiElement clickableUiElement) clickableUiElement.onClick(clickEvent);
    // set result of event
    event.setCancelled(!clickEvent.canPickup());
  }

  private Optional<ClickType> convertInventoryAction(@NotNull final InventoryAction action) {
    ClickType clickType;
    if (action == InventoryAction.PICKUP_ALL) {
      clickType = ClickType.LEFT_CLICK;
    } else if (action == InventoryAction.PICKUP_HALF) {
      clickType = ClickType.RIGHT_CLICK;
    } else if (action == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
      clickType = ClickType.SHIFT_CLICK;
    } else {
      return Optional.empty();
    }
    return Optional.of(clickType);
  }

}
