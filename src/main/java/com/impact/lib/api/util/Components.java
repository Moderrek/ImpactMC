package com.impact.lib.api.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;

public final class Components {

  private Components() {
  }

  public static @NotNull Component simple(@Nullable String content, Object... args) {
    return legacy(new MessageFormat(String.valueOf(content)).format(args));
  }

  public static @NotNull Component legacy(@Nullable String content) {
    return LegacyComponentSerializer.legacyAmpersand().deserialize(String.valueOf(content));
  }

  public static @NotNull Component simple(@Nullable String content) {
    return legacy(content);
  }

  public static @NotNull String stringify(@Nullable Component component) {
    return PlainTextComponentSerializer.plainText().serializeOr(component, "null");
  }

  @Contract("_ -> new")
  public static @NotNull Component text(@Nullable String content) {
    return Component.text(String.valueOf(content));
  }

  public static @NotNull Component textGreen(@Nullable String content) {
    return text(String.valueOf(content), NamedTextColor.GREEN);
  }

  public static @NotNull Component text(@Nullable String content, @NotNull TextColor color) {
    return Component.text(String.valueOf(content)).color(color);
  }

  public static @NotNull Component textRed(@Nullable String content) {
    return text(String.valueOf(content), NamedTextColor.RED);
  }

}
