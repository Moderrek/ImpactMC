package com.impact.lib.api.input;

import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public final class InputListener implements Listener {

  private final InputModule inputModule;

  public InputListener(@NotNull final InputModule inputModule) {
    this.inputModule = inputModule;
  }

  // Global
  @EventHandler(priority = EventPriority.MONITOR)
  public void onLeave(@NotNull PlayerQuitEvent event) {
    final Player player = event.getPlayer();
    if (!inputModule.isWaitingForInput(player)) return;
    final InputContainer inputContainer = inputModule.getContainer(player);
    inputContainer.completableFuture.complete(Optional.empty());
  }

  // Chat input
  @EventHandler(priority = EventPriority.MONITOR)
  public void onChat(@NotNull AsyncChatEvent event) {
    final Player player = event.getPlayer();
    if (!inputModule.isWaitingForInput(player)) return;
    final InputContainer inputContainer = inputModule.getContainer(player);
    if (!(inputContainer instanceof ChatInputContainer container)) return;
    container.completableFuture.complete(Optional.of(event.message().toString()));
  }

  // Anvil input
  @EventHandler(priority = EventPriority.MONITOR)
  public void onInventoryClose(@NotNull InventoryCloseEvent event) {
    if (!(event.getPlayer() instanceof Player player)) return;
    if (!inputModule.isWaitingForInput(player)) return;
    final InputContainer inputContainer = inputModule.getContainer(player);
    if (!(inputContainer instanceof AnvilInputContainer container)) return;
    container.completableFuture.complete(Optional.empty());
  }

}
