package com.impact.lib.api.registry;

import com.impact.lib.api.block.CustomBlock;
import com.impact.lib.api.item.CustomItem;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class ImpactRegistry<T> {

    public static <T> T register(@NotNull ImpactRegistry<? super T> registry, NamespacedKey key, T entry) {
        return (T) Objects.requireNonNull(registry).add(key, entry);
    }

    public static final ImpactRegistry<CustomItem> CUSTOM_ITEM;
    public static final ImpactRegistry<CustomBlock> CUSTOM_BLOCK;

    static {
        CUSTOM_ITEM = new CustomItemRegistry();
        CUSTOM_BLOCK = new CustomBlockRegistry();
    }

    public abstract T add(NamespacedKey key, T entry);
    public abstract T get(NamespacedKey key);
    public abstract NamespacedKey getNamespacedKey(T entry);

}
