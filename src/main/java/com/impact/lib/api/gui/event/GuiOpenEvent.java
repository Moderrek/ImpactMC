package pl.impact.lib.api.gui.event;

import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;
import pl.impact.lib.api.gui.GuiView;

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
