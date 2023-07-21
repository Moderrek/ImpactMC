package com.impact.lib.api.registry;

import com.impact.lib.api.item.CustomItem;
import org.bukkit.NamespacedKey;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class CustomItemRegistry extends ImpactRegistries<CustomItem> {
    private final Map<NamespacedKey, CustomItem> registry = new ConcurrentHashMap<>();

    @Override
    public boolean isRegistered(NamespacedKey key) {
        return registry.containsKey(key);
    }

    @Override
    public CustomItem add(NamespacedKey key, CustomItem entry) {
        if(registry.containsKey(key)) throw new RuntimeException("Cannot registry " + key + " because this key is already registered!");
        if(registry.containsValue(entry)) throw new RuntimeException("Cannot registry this item because this item is already registered!");
        return registry.put(key, entry);
    }

    @Override
    public CustomItem get(NamespacedKey key) {
        return registry.get(key);
    }

    @Override
    public NamespacedKey getNamespacedKey(CustomItem entry) {
        for(var entries : registry.entrySet()) {
            if(entry == entries.getValue()) {
                return entries.getKey();
            }
        }
        return null;
    }
}
