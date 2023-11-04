package com.impact.lib.api.item;

import com.impact.lib.Impact;
import com.impact.lib.api.block.CustomBlock;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

public class CustomBlockItem extends CustomItem {

  private final CustomBlock block;

  public CustomBlockItem(@NotNull CustomBlock block) {
    this(block, CustomItemSettings.of(block.getMaterial()).setTranslatable(true));
  }

  public CustomBlockItem(@NotNull CustomBlock block, @NotNull CustomItemSettings settings) {
    super(settings.setMaterial(block.getMaterial()));
    this.block = block;
  }

  @Override
  protected Component preprocessDisplayName(@NotNull CustomItemSettings settings) {
    return Component
        .translatable(Impact.createBlockTranslationKey(getBlock()))
        .decoration(TextDecoration.ITALIC, false)
        .color(settings.getNameColor().orElse(NamedTextColor.WHITE));
  }

  public final NamespacedKey getBlock() {
    return block.getNamespacedKey();
  }

}
