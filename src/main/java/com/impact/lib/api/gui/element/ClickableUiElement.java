package com.impact.lib.api.gui.element;

import com.impact.lib.api.gui.event.GuiClickEvent;
import org.jetbrains.annotations.NotNull;

public interface ClickableUiElement {
  void onClick(@NotNull final GuiClickEvent event);
}
