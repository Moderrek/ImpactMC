package com.impact.lib.api.gui.element;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import com.impact.lib.api.gui.GuiView;
import com.impact.lib.api.gui.UiElement;
import com.impact.lib.api.util.ItemBuilder;

/**
 * Represents Empty GUI element.
 */
public final class EmptyUiElement extends UiElement {

    @Override
    public @NotNull ItemStack construct(@NotNull final GuiView where) {
        return new ItemBuilder().material("air").build();
    }

    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return "EmptyUiElement";
    }

    @Contract(" -> new")
    public static @NotNull EmptyUiElement create() {
        return new EmptyUiElement();
    }

}
