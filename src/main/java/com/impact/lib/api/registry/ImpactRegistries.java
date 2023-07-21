package com.impact.lib.api.registry;

import com.impact.lib.api.block.CustomBlock;
import com.impact.lib.api.gui.Gui;
import com.impact.lib.api.item.CustomItem;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class ImpactRegistries<T> {

    public static <T> T register(@NotNull ImpactRegistries<? super T> registry, NamespacedKey key, T entry) {
        return (T) Objects.requireNonNull(registry).add(key, entry);
    }

    public static final ImpactRegistries<CustomItem> CUSTOM_ITEM;
    public static final ImpactRegistries<CustomBlock> CUSTOM_BLOCK;
    public static final ImpactRegistries<Gui> GUI;

    static {
        CUSTOM_ITEM = new CustomItemRegistry();
        CUSTOM_BLOCK = new CustomBlockRegistry();
        GUI = new GuiRegistry();
    }

    public abstract boolean isRegistered(NamespacedKey key);
    public abstract T add(NamespacedKey key, T entry);
    public abstract T get(NamespacedKey key);
    public abstract NamespacedKey getNamespacedKey(T entry);

}
