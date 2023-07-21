package com.impact.lib.api.block;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

public abstract class CustomBlock {

    private final Material material;

    public CustomBlock(@NotNull Material material) {
        this.material = material;
//        this.key = key.toLowerCase();
    }

//    void init(@NotNull final Location location) {
//        this.location = location;;
//        this.namespacedKey = new NamespacedKey(plugin, key);
//    }

    public Material getMaterial() {
        return material;
    }

//    public Location getLocation() {
//        return location;
//    }
//
//    public World getWorld() {
//        return location.getWorld();
//    }
//
//    public Chunk getChunk() {
//        return location.getChunk();
//    }
//
//    public Block getBlock() {
//        return location.getBlock();
//    }

    public abstract boolean canPlace();
    public abstract boolean canDestroy();
    public abstract boolean canExplode();
    public abstract boolean canPistonMove();

}
