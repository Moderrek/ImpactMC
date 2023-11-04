package com.impact.lib._test;

import com.impact.lib.Impact;
import com.impact.lib.api.block.CustomBlock;
import com.impact.lib.api.util.Components;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockIgniteEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ExampleBlock extends CustomBlock {

  public static final NamespacedKey IDENTIFIER = Impact.createKey("example_block");

  public ExampleBlock() {
    super(Material.EMERALD_ORE);
  }

  @Override
  public void onCreate(@NotNull Block block) {
    super.onCreate(block);
    Bukkit.broadcastMessage("create");
  }

  @Override
  public void onRemove(@NotNull Block block) {
    super.onRemove(block);
    Bukkit.broadcastMessage("remove");
  }

  @Override
  public void onPlace(@NotNull Player player, @NotNull Block block) {
    super.onPlace(player, block);
    Bukkit.broadcastMessage("place");
  }

  @Override
  public void onBreak(@NotNull Player player, @NotNull Block block) {
    super.onBreak(player, block);
    Bukkit.broadcastMessage("break");
  }

  @Override
  public void onClick(@NotNull Player player, @NotNull Block block) {
    super.onClick(player, block);
    player.openInventory(Bukkit.createInventory(null, 54, Components.simple("&c{0}", getNamespacedKey())));
    player.playSound(block.getLocation(), Sound.BLOCK_CHEST_OPEN, 1, 1);
  }

  @Override
  public boolean onExplode(@NotNull Block block, float yield) {
    Bukkit.broadcastMessage("Cannot explode!");
    return false;
  }

  @Override
  public boolean onIgnite(@NotNull Block block, BlockIgniteEvent.@NotNull IgniteCause cause, @Nullable Player player) {
    Bukkit.broadcastMessage(player + " cannot ignite! " + cause);
    return false;
  }

  @Override
  public void onRedstone(@NotNull Block block, int signal) {
    Bukkit.broadcastMessage("Redstone: " + signal);
  }
}
