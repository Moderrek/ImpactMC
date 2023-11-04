package com.impact.lib._test;

import com.impact.lib.Impact;
import com.impact.lib.api.item.CustomItem;
import com.impact.lib.api.item.CustomItemSettings;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.GameMode;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PrzenosnyEnderchest extends CustomItem {

  public static final NamespacedKey IDENTIFIER = Impact.createKey("przenosny_enderchest");

  public PrzenosnyEnderchest() {
    super(CustomItemSettings
        .of("ender pearl")
        .setDisplayName("Przenośny enderchest")
        .setNameColor(NamedTextColor.LIGHT_PURPLE)
        .setGlow(true)
    );
  }

  @Override
  public Result onUse(@NotNull Player player, @NotNull ItemStack item, boolean isLeftClick) {
    player.openInventory(player.getEnderChest());
    return player.getGameMode() == GameMode.CREATIVE ? Result.DENY : Result.CONSUME;
  }

  @Override
  public Result onBlockUse(@NotNull Player player, @NotNull ItemStack item, boolean isLeftClick, @NotNull Block clickedBlock, @NotNull BlockFace clickedFace) {
    player.openInventory(player.getEnderChest());
    return player.getGameMode() == GameMode.CREATIVE ? Result.DENY : Result.CONSUME;
  }
}
