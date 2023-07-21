package com.impact.lib.api.gui.event;

import org.jetbrains.annotations.NotNull;
import com.impact.lib.api.gui.GuiView;

public final class GuiCloseEvent extends GuiEvent {
    public GuiCloseEvent(@NotNull final GuiView guiView) {
        super(guiView);
    }
}
