package com.impact.lib.api.item;

import com.impact.lib.ImpactLibPlugin;
import com.impact.lib.api.registry.ImpactRegistries;
import org.bukkit.NamespacedKey;

public class ExamplePlugin {

//    public static final CustomItem CUSTOM_ITEM = ImpactRegistries.register(ImpactRegistries.CUSTOM_ITEM, new NamespacedKey(ImpactLibPlugin.getInstance(), "custom_item"), new CustomItem().setMaterial(Material.DIAMOND).createCustomItem());
    public static final CustomItem EXAMPLE_ITEM = ImpactRegistries.register(ImpactRegistries.CUSTOM_ITEM, new NamespacedKey(ImpactLibPlugin.getInstance(), "example_item"), new ExampleItem());

    static {

    }
}
