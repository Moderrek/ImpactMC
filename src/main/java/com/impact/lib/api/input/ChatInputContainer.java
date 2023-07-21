package com.impact.lib.api.input;

import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public final class ChatInputContainer extends InputContainer {
    public ChatInputContainer(Player player, CompletableFuture<Optional<String>> completableFuture) {
        super(player, completableFuture);
    }
}
