package com.impact.lib.api.gui.event;

import com.impact.lib.api.gui.GuiView;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;

public final class GuiOpenEvent extends GuiEvent implements Cancellable {

  private boolean isCancelled = false;

  public GuiOpenEvent(@NotNull final GuiView guiView) {
    super(guiView);
  }

  @Override
  public boolean isCancelled() {
    return isCancelled;
  }

  @Override
  public void setCancelled(boolean cancel) {
    isCancelled = cancel;
  }
}
