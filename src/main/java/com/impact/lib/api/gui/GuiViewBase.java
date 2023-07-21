package com.impact.lib.api.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;

import java.util.Collection;
import java.util.Optional;

public interface GuiViewBase<E extends UiElementBase> {
    Player getPlayer();

    Gui getGui();

    InventoryView getInventoryView();

    int getGuiSize();

    Collection<E> getUiElements();

    Optional<E> getUiElement(int slotIndex);

    void close();
}
