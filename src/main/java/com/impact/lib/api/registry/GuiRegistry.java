package com.impact.lib.api.registry;

import com.impact.lib.api.gui.Gui;
import org.bukkit.NamespacedKey;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GuiRegistry extends ImpactRegistries<Gui> {
    private final Map<NamespacedKey, Gui> registry = new ConcurrentHashMap<>();

    @Override
    public boolean isRegistered(NamespacedKey key) {
        return registry.containsKey(key);
    }

    @Override
    public Gui add(NamespacedKey key, Gui entry) {
        if(registry.containsKey(key)) throw new RuntimeException("Cannot registry " + key + " because this key is already registered!");
        if(registry.containsValue(entry)) throw new RuntimeException("Cannot registry this GUI because this item is already registered!");
        return registry.put(key, entry);
    }

    @Override
    public Gui get(NamespacedKey key) {
        return registry.get(key);
    }

    @Override
    public NamespacedKey getNamespacedKey(Gui entry) {
        for(var entries : registry.entrySet()) {
            if(entry == entries.getValue()) {
                return entries.getKey();
            }
        }
        return null;
    }
}
