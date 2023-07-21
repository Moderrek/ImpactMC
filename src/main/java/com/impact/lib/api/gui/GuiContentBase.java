package pl.impact.lib.api.gui;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;

public interface GuiContentBase<T extends UiElementBase<?>> {

    int getGuiSize();

    Collection<T> getUiElements();

    Optional<T> getUiElement(int slotIndex);

    Optional<T> addUiElement(@NotNull T value);

    T setUiElement(int slotIndex, @NotNull T value);

    UiElement removeUiElement(int slotIndex);

    void fillUiElements(@NotNull T value, @NotNull Consumer<T> action);

    void fillUiElements(@NotNull T value);

}
