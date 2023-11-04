package com.impact.lib.api.item;

import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CustomItemSettings {

  private Material material;
  private Integer customModelId = null;
  private boolean translatable = false;
  private String displayName = null;
  private TextColor nameColor = null;
  private boolean glow = false;

  public CustomItemSettings(Material material) {
    this.material = material;
  }

  @Contract(value = "_ -> new", pure = true)
  public static @NotNull CustomItemSettings of(Material material) {
    return new CustomItemSettings(material);
  }

  public static @NotNull CustomItemSettings of(@NotNull String materialName) {
    final String name = materialName
        .replace(' ', '_')
        .trim()
        .toUpperCase();
    return new CustomItemSettings(Material.getMaterial(name));
  }

  public Material getMaterial() {
    return material;
  }

  public CustomItemSettings setMaterial(@NotNull Material material) {
    this.material = material;
    return this;
  }

  public Optional<Integer> getCustomModelId() {
    return Optional.ofNullable(customModelId);
  }

  public CustomItemSettings setCustomModelId(@Nullable Integer customModelId) {
    this.customModelId = customModelId;
    return this;
  }

  public boolean isTranslatable() {
    return translatable;
  }

  public CustomItemSettings setTranslatable(boolean translatable) {
    this.translatable = translatable;
    return this;
  }

  public Optional<TextColor> getNameColor() {
    return Optional.ofNullable(nameColor);
  }

  public CustomItemSettings setNameColor(TextColor nameColor) {
    this.nameColor = nameColor;
    return this;
  }

  public Optional<String> getDisplayName() {
    return Optional.ofNullable(displayName);
  }

  public CustomItemSettings setDisplayName(String displayName) {
    this.displayName = displayName;
    return this;
  }

  public boolean hasGlow() {
    return glow;
  }

  public CustomItemSettings setGlow(boolean glow) {
    this.glow = glow;
    return this;
  }
}
