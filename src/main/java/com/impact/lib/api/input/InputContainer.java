package com.impact.lib.api.input;

import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public abstract class InputContainer {

    protected final Player player;
    protected final CompletableFuture<Optional<String>> completableFuture;

    protected InputContainer(Player player, CompletableFuture<Optional<String>> completableFuture) {
        this.player = player;
        this.completableFuture = completableFuture;
    }
}
