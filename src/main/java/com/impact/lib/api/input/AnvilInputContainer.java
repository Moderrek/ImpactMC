package com.impact.lib.api.input;

import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public final class AnvilInputContainer extends InputContainer {

    private final InventoryView inventory;

    public AnvilInputContainer(Player player, CompletableFuture<Optional<String>> completableFuture, InventoryView inventory) {
        super(player, completableFuture);
        this.inventory = inventory;
    }
}
