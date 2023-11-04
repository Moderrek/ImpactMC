package com.impact.lib.api.util;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Text {

  @Contract("_ -> new")
  public static @NotNull Component literal(@Nullable String text) {
    return Component.text().content(String.valueOf(text)).build();
  }

}
