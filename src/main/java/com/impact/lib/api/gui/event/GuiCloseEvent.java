package com.impact.lib.api.gui.event;

import com.impact.lib.api.gui.GuiView;
import org.jetbrains.annotations.NotNull;

public final class GuiCloseEvent extends GuiEvent {
  public GuiCloseEvent(@NotNull final GuiView guiView) {
    super(guiView);
  }
}
