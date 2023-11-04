package com.impact.lib.plugin.module;

import com.impact.lib.Impact;
import com.impact.lib.ImpactLibPlugin;
import com.impact.lib.api.item.CustomItem;
import com.impact.lib.api.module.PluginModule;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

public class CustomItemModule extends PluginModule<ImpactLibPlugin> implements Listener {

  @Override
  public void enable(@NotNull ImpactLibPlugin plugin) {
    Impact.addListener(this, plugin);
  }

  @Override
  public void disable(@NotNull ImpactLibPlugin plugin) {
    Impact.removeListener(this);
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onPlayerInteract(@NotNull PlayerInteractEvent event) {
    ItemStack item = event.getItem();
    if (!CustomItem.isValid(item)) return;
    Optional<CustomItem> optionalCustomItem = CustomItem.getCustomItem(item);
    if (optionalCustomItem.isEmpty()) return;
    CustomItem customItem = optionalCustomItem.orElseThrow();

    Player player = event.getPlayer();
    Action action = event.getAction();

    // Performing action
    CustomItem.Result result = null;
    if (action == Action.LEFT_CLICK_BLOCK || action == Action.RIGHT_CLICK_BLOCK) {
      if (event.useInteractedBlock().equals(Event.Result.DENY)) return;
      result = customItem.onBlockUse(player, item, action.isLeftClick(), Objects.requireNonNull(event.getClickedBlock()), event.getBlockFace());
    } else if (action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_AIR)) {
      if (event.useItemInHand().equals(Event.Result.DENY)) return;
      result = customItem.onUse(player, item, action.isLeftClick());
    }
    // Consume
    if (Objects.equals(result, CustomItem.Result.CONSUME)) {
      item.setAmount(item.getAmount() - 1);
    }
    // Cancellation
    if (!Objects.equals(result, CustomItem.Result.ALLOW_VANILLA)) {
      event.setCancelled(true);
      event.setUseItemInHand(Event.Result.DENY);
      event.setUseInteractedBlock(Event.Result.DENY);
    }
  }

}
