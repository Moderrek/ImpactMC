package com.impact.lib.api.util;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class ItemBuilder {


  private Material material = Material.AIR;
  private int count = 1;

  private Component displayName;
  private List<Component> lore;

  private boolean safeEnchant = true;
  private Map<Enchantment, Integer> enchantments = new HashMap<>();

  public ItemBuilder() {
  }

  public ItemBuilder(@NotNull ItemStack from) {
    final ItemStack item = from.clone();

    this.material = item.getType();
    this.count = item.getAmount();

    this.displayName = item.displayName();
    this.lore = item.lore();
    this.safeEnchant = true;
    this.enchantments = item.getEnchantments();
  }

  // Material

  public ItemBuilder material(@NotNull String materialName) {
    final String name = materialName
        .replace(' ', '_')
        .trim()
        .toUpperCase();
    return material(Objects.requireNonNull(Material.getMaterial(name)));
  }

  public ItemBuilder material(@NotNull Material material) {
    this.material = material;
    checkCount();
    return this;
  }

  // Count

  private void checkCount() {
//        if(count < 1) {
//            count = material.getMaxStackSize();
//            return;
//        }
//        if(count > material.getMaxStackSize()) {
//            count = material.getMaxStackSize();
//        }
  }

  public ItemBuilder count(int count) {
    this.count = count;
    checkCount();
    return this;
  }

  public ItemBuilder stack() {
    count = material.getMaxStackSize();
    return this;
  }

  // Display Name

  public ItemBuilder name(String displayName) {
    return name(Component.text(displayName));
  }

  public ItemBuilder name(Component displayName) {
    this.displayName = displayName;
    return this;
  }

  // Lore

  public ItemBuilder lore(Component... lore) {
    return lore(Arrays.stream(lore).toList());
  }

  public ItemBuilder lore(List<Component> lore) {
    this.lore = lore;
    return this;
  }

  public ItemBuilder lore(String @NotNull ... lore) {
    List<Component> components = new ArrayList<>();
    for (String string : lore) components.add(Component.text(string));
    return lore(components);
  }

  // Enchant

  public ItemBuilder enchant(@NotNull final Enchantment enchant) {
    enchantments.put(enchant, enchant.getStartLevel());
    return this;
  }

  public ItemBuilder enchant(@NotNull final Enchantment enchantment, final int level) {
    enchantments.put(enchantment, level);
    return this;
  }

  public ItemBuilder setSafeEnchant(final boolean safeEnchant) {
    this.safeEnchant = safeEnchant;
    return this;
  }

  public @NotNull ItemStack build() {
    // create item
    if (material == null) throw new NullPointerException("Material cannot be null!");
    if (material == Material.AIR) return new ItemStack(Material.AIR);
    if (count <= 0 || count >= 65)
      throw new IllegalArgumentException("Count cannot be lower than 1 or greater than 64!");
    ItemStack item = new ItemStack(material, count);
    ItemMeta meta = item.getItemMeta();

    // item doest have item meta, probably air
    if (meta == null) return item;

    // display name
    Optional.ofNullable(displayName).ifPresent(meta::displayName);
    Optional.ofNullable(lore).ifPresent(meta::lore);

    // set meta
    item.setItemMeta(meta);

    // enchantments
    for (var entry : enchantments.entrySet()) {
      if (!safeEnchant) {
        item.addUnsafeEnchantment(entry.getKey(), entry.getValue());
        continue;
      }
      item.addEnchantment(entry.getKey(), entry.getValue());
    }
    return item;
  }

}
