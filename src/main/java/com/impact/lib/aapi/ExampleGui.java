package com.impact.lib.aapi;

import com.impact.lib.api.gui.Gui;
import com.impact.lib.api.gui.GuiSize;
import com.impact.lib.api.gui.element.ButtonUiElement;
import com.impact.lib.api.util.Components;
import com.impact.lib.api.util.Items;
import net.kyori.adventure.text.Component;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class ExampleGui extends Gui {
    public ExampleGui(@NotNull Plugin plugin) {
        super(GuiSize.SLOTS_54, plugin, "example");
        addUiElement(new ButtonUiElement(Items.singleItem("stone"), button -> {

        }));
    }

    @Override
    public @NotNull Component title() {
        return Components.simple("&e&lZółty");
    }
}
