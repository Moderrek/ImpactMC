package com.impact.lib._test;

import com.impact.lib.ImpactLibPlugin;
import com.impact.lib.api.item.CustomItem;
import com.impact.lib.api.item.CustomItemSettings;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ExampleItem extends CustomItem {

  public static final NamespacedKey IDENTIFIER = new NamespacedKey(ImpactLibPlugin.getInstance(), "example");

  public ExampleItem() {
    super(CustomItemSettings.of("red dye")
        .setTranslatable(true)
        .setNameColor(NamedTextColor.AQUA)
        .setCustomModelId(1234));
  }

  @Override
  public Result onUse(@NotNull Player player, @NotNull ItemStack item, boolean isLeftClick) {
    player.sendMessage("Oops!");
    return Result.DENY;
  }

  @Override
  public Result onBlockUse(@NotNull Player player, @NotNull ItemStack item, boolean isLeftClick, @NotNull Block clickedBlock, @NotNull BlockFace clickedFace) {
    if (isLeftClick) return Result.ALLOW_VANILLA;
    player.playSound(clickedBlock.getLocation(), clickedBlock.getSoundGroup().getBreakSound(), 1.0F, 1.0F);
    clickedBlock.setType(Material.RED_WOOL);
    return player.getGameMode() != GameMode.CREATIVE ? Result.CONSUME : Result.DENY;
  }
}
