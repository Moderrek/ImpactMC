package pl.impact.lib.api.gui.element;

import org.jetbrains.annotations.NotNull;
import pl.impact.lib.api.gui.UiElement;
import pl.impact.lib.api.gui.event.GuiClickEvent;

public abstract class ClickableUiElement extends UiElement {
    public abstract void onClick(@NotNull final GuiClickEvent event);
}
