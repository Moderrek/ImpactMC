package com.impact.lib._test;

import com.impact.lib.api.block.CustomBlock;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockIgniteEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CustomGrass extends CustomBlock {
  public CustomGrass() {
    super(Material.GRASS_BLOCK);
  }

  @Override
  public void onCreate(@NotNull Block block) {
    Bukkit.broadcastMessage("created grass");
  }

  @Override
  public void onRemove(@NotNull Block block) {
    Bukkit.broadcastMessage("removed grass");
  }

  @Override
  public void onPlace(@NotNull Player player, @NotNull Block block) {
    Bukkit.broadcastMessage("plracd grass");
  }

  @Override
  public void onBreak(@NotNull Player player, @NotNull Block block) {
    Bukkit.broadcastMessage("broke grass");
  }

  @Override
  public void onClick(@NotNull Player player, @NotNull Block block) {
    Bukkit.broadcastMessage("clicked grass");
  }

  @Override
  public boolean onMove(@NotNull Block block, @NotNull Block piston) {
    Bukkit.broadcastMessage("try to move grass");
    return false;
  }

  @Override
  public boolean onExplode(@NotNull Block block, float yield) {
    Bukkit.broadcastMessage("tried to explode grass");
    return false;
  }

  @Override
  public boolean onIgnite(@NotNull Block block, BlockIgniteEvent.@NotNull IgniteCause cause, @Nullable Player player) {
    Bukkit.broadcastMessage("tried to ignite grass");
    return false;
  }

  @Override
  public void onRedstone(@NotNull Block block, int signal) {
    Bukkit.broadcastMessage("received redstone signal grass");
  }

  @Override
  public boolean onEndermanGrief(@NotNull Block block, @NotNull Entity enderman) {
    Bukkit.broadcastMessage("enderman grief grass");
    return true;
  }

  @Override
  public boolean onEatGrass(@NotNull Block block, @NotNull Entity eater) {
    Bukkit.broadcastMessage("eated grass");
    return true;
  }
}
