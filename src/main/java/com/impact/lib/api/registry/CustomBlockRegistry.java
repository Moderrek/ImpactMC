package com.impact.lib.api.registry;

import com.impact.lib.api.block.CustomBlock;
import org.bukkit.NamespacedKey;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Contains all {@link CustomBlock}.
 */
class CustomBlockRegistry extends ImpactRegistries<CustomBlock> {

    private final Map<NamespacedKey, CustomBlock> registry = new ConcurrentHashMap<>();

    @Override
    public boolean isRegistered(NamespacedKey key) {
        return registry.containsKey(key);
    }

    @Override
    public CustomBlock add(NamespacedKey key, CustomBlock entry) {
        if(registry.containsKey(key)) throw new RuntimeException("Cannot registry " + key + " because this key is already registered!");
        if(registry.containsValue(entry)) throw new RuntimeException("Cannot registry this block because this block is already registered!");
        return registry.put(key, entry);
    }

    @Override
    public CustomBlock get(NamespacedKey key) {
        return registry.get(key);
    }

    @Override
    public NamespacedKey getNamespacedKey(CustomBlock entry) {
        for(var entries : registry.entrySet()) {
            if(entry == entries.getValue()) {
                return entries.getKey();
            }
        }
        return null;
    }

}
