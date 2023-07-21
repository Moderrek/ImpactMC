package pl.impact.lib.api.input;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import pl.impact.lib.Impact;
import pl.impact.lib.ImpactLibPlugin;
import pl.impact.lib.api.service.PluginModule;
import pl.impact.lib.api.util.Items;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public final class InputModule extends PluginModule<ImpactLibPlugin> {

    private final Map<Player, InputContainer> inputContainer = new ConcurrentHashMap<>();
    private final InputListener listener = new InputListener(this);

    @Override
    public void enable(@NotNull ImpactLibPlugin plugin) {
        inputContainer.clear();
        Impact.addListener(listener, plugin);
    }

    @Override
    public void disable(@NotNull ImpactLibPlugin plugin) {
        inputContainer.clear();
        HandlerList.unregisterAll(listener);
    }

    @Contract(pure = true)
    boolean isWaitingForInput(Player player) {
        return inputContainer.containsKey(player);
    }

    InputContainer getContainer(Player player) {
        return inputContainer.get(player);
    }

    public @NotNull CompletableFuture<Optional<String>> requestChatInput(@NotNull final Player player) {
        CompletableFuture<Optional<String>> completableFuture = new CompletableFuture<>();
        inputContainer.put(player, new ChatInputContainer(player, completableFuture));
        return completableFuture;
    }

    public @NotNull CompletableFuture<Optional<String>> requestAnvilInput(@NotNull final Player player, @NotNull final String placeholder) {
        CompletableFuture<Optional<String>> completableFuture = new CompletableFuture<>();
        final InventoryView inventoryView = player.openAnvil(player.getLocation(), true);
        inventoryView.setItem(0, Items.namedItem("nametag", placeholder, NamedTextColor.WHITE));
        inputContainer.put(player, new AnvilInputContainer(player, completableFuture, inventoryView));
        return completableFuture;
    }

}
