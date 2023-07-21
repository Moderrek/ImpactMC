package pl.impact.lib.api.input;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.impact.lib.Impact;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public final class Input {

    private Input() { }

    public static @NotNull CompletableFuture<Optional<String>> requestChatInput(@NotNull final Player player) {
        return Impact.getImpactLibraryPlugin().getInputModule().requestChatInput(player);
    }

    public static @NotNull CompletableFuture<Optional<String>> requestAnvilInput(@NotNull final Player player, @NotNull final String placeholder) {
        return Impact.getImpactLibraryPlugin().getInputModule().requestAnvilInput(player, placeholder);
    }

    public enum Type {
        ANVIL, CHAT
    }

}
