package com.impact.lib.plugin.module;

import com.impact.lib.Impact;
import com.impact.lib.ImpactLibPlugin;
import com.impact.lib.api.block.CustomBlock;
import com.impact.lib.api.item.CustomBlockItem;
import com.impact.lib.api.item.CustomItem;
import com.impact.lib.api.module.PluginModule;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Optional;

public class CustomBlockModule extends PluginModule<ImpactLibPlugin> implements Listener {

  @Override
  public void enable(@NotNull ImpactLibPlugin plugin) {
    Impact.addListener(this, plugin);
  }

  @Override
  public void disable(@NotNull ImpactLibPlugin plugin) {
    Impact.removeListener(this);
  }

  @EventHandler
  public void onChunkLoad(@NotNull ChunkLoadEvent event) {
    //TODO load custom blocks
  }

  @EventHandler
  public void onChunkUnload(@NotNull ChunkUnloadEvent event) {
    //TODO save custom blocks
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onBlockPlace(@NotNull BlockPlaceEvent event) {
    if (event.isCancelled()) return;
    ItemStack hand = event.getItemInHand();
    if (!CustomItem.isValid(hand)) return;
    CustomItem customItem = CustomItem.getCustomItem(hand).get();
    if (!(customItem instanceof CustomBlockItem customBlockItem)) {
      event.setCancelled(true);
      event.setBuild(false);
      return;
    }
    Block block = event.getBlockPlaced();
    CustomBlock.set(customBlockItem.getBlock(), block.getLocation());
    CustomBlock.get(block).get().onPlace(event.getPlayer(), block);
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onBlockBreak(@NotNull BlockBreakEvent event) {
    if (event.isCancelled()) return;
    Block block = event.getBlock();
    if (!CustomBlock.isValid(block)) return;
    CustomBlock.get(block).get().onBreak(event.getPlayer(), block);
    CustomBlock.remove(block);
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onBlockClick(@NotNull PlayerInteractEvent event) {
    if (event.isCancelled()) return;
    if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
    if (event.getPlayer().isSneaking()) return;
    Block block = event.getClickedBlock();
    if (block == null) return;
    Optional<CustomBlock> optional = CustomBlock.get(block);
    if (optional.isEmpty()) return;
    CustomBlock customBlock = optional.get();
    customBlock.onClick(event.getPlayer(), block);
    event.setUseInteractedBlock(Event.Result.DENY);
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onBlockRetract(@NotNull BlockPistonRetractEvent event) {
    if (event.isCancelled()) return;
    for (Block block : event.getBlocks()) {
      if (CustomBlock.isValid(block)) {
        event.setCancelled(true);
        break;
      }
    }
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onBlockRetract(@NotNull BlockPistonExtendEvent event) {
    if (event.isCancelled()) return;
    for (Block block : event.getBlocks()) {
      if (CustomBlock.isValid(block)) {
        event.setCancelled(true);
        break;
      }
    }
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onBlockExplode(@NotNull BlockExplodeEvent event) {
    if (event.isCancelled()) return;
    Iterator<Block> explodedBlocks = event.blockList().iterator();
    while (explodedBlocks.hasNext()) {
      Block block = explodedBlocks.next();
      if (!CustomBlock.isValid(block)) continue;
      CustomBlock customBlock = CustomBlock.get(block).orElseThrow();
      if (customBlock.onExplode(block, event.getYield())) CustomBlock.remove(block);
      else explodedBlocks.remove();
    }
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onBlockExplode(@NotNull EntityExplodeEvent event) {
    if (event.isCancelled()) return;
    Iterator<Block> explodedBlocks = event.blockList().iterator();
    while (explodedBlocks.hasNext()) {
      Block block = explodedBlocks.next();
      if (!CustomBlock.isValid(block)) continue;
      CustomBlock customBlock = CustomBlock.get(block).orElseThrow();
      if (customBlock.onExplode(block, event.getYield())) CustomBlock.remove(block);
      else explodedBlocks.remove();
    }
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onBlockIgnite(@NotNull BlockIgniteEvent event) {
    if (event.isCancelled()) return;
    Block block = event.getBlock();
    Optional<CustomBlock> optional = CustomBlock.get(block);
    if (optional.isEmpty()) return;
    CustomBlock customBlock = optional.get();
    customBlock.onIgnite(block, event.getCause(), event.getPlayer());
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onBlockRedstone(@NotNull BlockRedstoneEvent event) {
    Block block = event.getBlock();
    Optional<CustomBlock> optional = CustomBlock.get(block);
    if (optional.isEmpty()) return;
    CustomBlock customBlock = optional.get();
    customBlock.onRedstone(block, event.getNewCurrent());
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onBlockChange(@NotNull EntityChangeBlockEvent event) {
    Block block = event.getBlock();
    Optional<CustomBlock> optional = CustomBlock.get(block);
    if (optional.isEmpty()) return;
    CustomBlock customBlock = optional.get();

    Entity entity = event.getEntity();
    if (entity.getType().equals(EntityType.ENDERMAN)) {
      // Enderman grief event
      boolean can_move = customBlock.onEndermanGrief(block, entity);
      event.setCancelled(!can_move);
      return;
    }
    if (entity.getType().equals(EntityType.SHEEP)) {
      // Sheep eat grass
      boolean can_eat = customBlock.onEatGrass(block, entity);
      event.setCancelled(!can_eat);
    }
  }

}
