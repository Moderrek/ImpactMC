package com.impact.lib.aapi;

import com.impact.lib.ImpactLibPlugin;
import com.impact.lib.api.gui.Gui;
import com.impact.lib.api.module.PluginModule;
import com.impact.lib.api.registry.ImpactRegistries;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

public class CustomModule extends PluginModule<ImpactLibPlugin> {
    @Override
    public void enable(@NotNull ImpactLibPlugin plugin) {
        Gui exampleGui = ImpactRegistries.register(ImpactRegistries.GUI, new NamespacedKey(plugin, "example_gui"), new ExampleGui());
    }

    @Override
    public void disable(@NotNull ImpactLibPlugin plugin) {

    }
}
