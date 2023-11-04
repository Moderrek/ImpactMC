package com.impact.lib.api.item;

import com.impact.lib.Impact;
import com.impact.lib.api.registry.ImpactRegistries;
import com.impact.lib.api.registry.exception.UnregisteredObjectException;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CustomItem {

  public static final NamespacedKey CONTAINER_CUSTOM_KEY = Impact.createKey("custom");

  protected final CustomItemSettings settings;

  public CustomItem(@NotNull CustomItemSettings settings) {
    this.settings = settings;
  }

  public static boolean isCustom(@Nullable ItemStack item) {
    if (item == null) return false;
    return getNamespacedKey(item).isPresent();
  }

  public static Optional<String> getNamespacedKey(@NotNull ItemStack item) {
    PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
    return Optional.ofNullable(data.has(CONTAINER_CUSTOM_KEY) ? data.get(CONTAINER_CUSTOM_KEY, PersistentDataType.STRING) : null);
  }

  public static Optional<CustomItem> getCustomItem(@Nullable ItemStack item) {
    if (item == null) return Optional.empty();
    if (!isValid(item)) return Optional.empty();
    NamespacedKey key = NamespacedKey.fromString(getNamespacedKey(item).orElseThrow());
    return Optional.of(ImpactRegistries.CUSTOM_ITEM.get(key));
  }

  public static boolean isValid(@Nullable ItemStack item) {
    if (item == null) return false;
    Optional<String> key = getNamespacedKey(item);
    if (key.isEmpty()) return false;
    return ImpactRegistries.CUSTOM_ITEM.isRegistered(NamespacedKey.fromString(key.orElseThrow()));
  }

  public final Plugin getPlugin() {
    return ImpactRegistries.CUSTOM_ITEM.getPlugin(this);
  }

  public final @NotNull ItemStack getItemStack(int count) {
    // Material
    ItemStack itemStack = new ItemStack(getMaterial(), count);
    ItemMeta meta = itemStack.getItemMeta();
    // Custom Item Data
    NamespacedKey key = ImpactRegistries.CUSTOM_ITEM.getNamespacedKey(this);
    if (key == null) throw new UnregisteredObjectException(getClass());
    meta.getPersistentDataContainer().set(Impact.createKey("custom"), PersistentDataType.STRING, key.toString());
    // Display Name
    meta.displayName(preprocessDisplayName(settings));
    // Custom Model Id
    settings.getCustomModelId().ifPresent(meta::setCustomModelData);
    itemStack.setItemMeta(meta);
    // Glow
    if (settings.hasGlow()) {
      itemStack.addUnsafeEnchantment(Enchantment.CHANNELING, 1);
      itemStack.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    }
    itemStack.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

    // custom preprocess
    meta = itemStack.getItemMeta();
    preprocessItemStack(itemStack, meta);
    itemStack.setItemMeta(meta);

    return itemStack;
  }

  public final Material getMaterial() {
    return settings.getMaterial();
  }

  protected Component preprocessDisplayName(@NotNull CustomItemSettings settings) {
    TextColor color = settings.getNameColor().orElse(NamedTextColor.WHITE);
    if (settings.isTranslatable()) {
      return Component.translatable(Impact.createItemTranslationKey(getNamespacedKey()))
          .decoration(TextDecoration.ITALIC, false)
          .color(color);
    }
    if (settings.getDisplayName().isPresent()) {
      return Component.text(settings.getDisplayName().get())
          .decoration(TextDecoration.ITALIC, false)
          .color(color);
    }
    return Component.translatable(settings.getMaterial().translationKey())
        .color(color);
  }

  /**
   * Modifies {@link ItemStack} creation.
   * After every call of this method {@link ItemStack} is created for this {@link CustomItem}.
   * Override method if you want to edit {@link ItemStack}.
   *
   * @param item The item stack
   * @param meta The item stack meta
   */
  protected void preprocessItemStack(ItemStack item, ItemMeta meta) {

  }

  public final NamespacedKey getNamespacedKey() {
    return ImpactRegistries.CUSTOM_ITEM.getNamespacedKey(this);
  }

  public Result onUse(@NotNull Player player,
                      @NotNull ItemStack item,
                      boolean isLeftClick) {
    return Result.ALLOW_VANILLA;
  }

  public Result onBlockUse(@NotNull Player player,
                           @NotNull ItemStack item,
                           boolean isLeftClick,
                           @NotNull Block clickedBlock,
                           @NotNull BlockFace clickedFace) {
    return Result.ALLOW_VANILLA;
  }

  public Result onEat(@NotNull Player player,
                      @NotNull ItemStack item) {
    return Result.ALLOW_VANILLA;
  }

  public enum Result {
    CONSUME,
    DENY,
    ALLOW_VANILLA
  }


}
