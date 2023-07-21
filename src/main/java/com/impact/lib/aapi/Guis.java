package com.impact.lib.aapi;

import com.impact.lib.ImpactLibPlugin;
import com.impact.lib.api.gui.Gui;
import com.impact.lib.api.registry.ImpactRegistries;
import org.bukkit.NamespacedKey;

public class Guis {

    public static final Gui BAZAR = ImpactRegistries.register(ImpactRegistries.GUI, new NamespacedKey(ImpactLibPlugin.getInstance(), "bazar"), new ExampleGui());

}
