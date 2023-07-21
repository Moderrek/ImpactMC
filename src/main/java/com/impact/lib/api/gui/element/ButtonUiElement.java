package com.impact.lib.api.gui.element;

import com.impact.lib.api.gui.GuiView;
import com.impact.lib.api.gui.UiElement;
import com.impact.lib.api.gui.event.GuiClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public final class ButtonUiElement extends UiElement implements ClickableUiElement {

    private ItemStack itemStack;
    private final Consumer<ButtonUiElement> action;

    public ButtonUiElement(@NotNull ItemStack itemStack, @NotNull Consumer<ButtonUiElement> action) {
        this.itemStack = itemStack;
        this.action = action;
    }

    public @NotNull ItemStack getItemStack() {
        return this.itemStack;
    }

    public void setItemStack(@NotNull ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public @NotNull ItemStack construct(@NotNull GuiView where) {
        return itemStack;
    }

    @Override
    public void onClick(@NotNull GuiClickEvent event) {
        action.accept(this);
    }
}
