package pl.impact.lib.api.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.impact.lib.ImpactLibPlugin;
import pl.impact.lib.api.gui.Gui;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public abstract class MPlayerCommand extends MCommand<Player> {

    private transient Player last_perfomed_player;
    private transient String[] last_args;

    public MPlayerCommand(@NotNull Plugin plugin, @NotNull String key) {
        super(plugin, key);
        last_perfomed_player = null;
        last_args = null;
    }

    public @NotNull Optional<Component> cantUseCommandMessage() {
        return Optional.of(Component
                .text()
                .content("You cannot execute this command!")
                .color(NamedTextColor.RED)
                .build()
        );
    }

    @Override
    public final boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            // not player
            cantUseCommandMessage().ifPresent(sender::sendMessage);
            return true;
        }
        // player
        last_perfomed_player = player;
        last_args = args;
        perform(player, command, args.length, args);
        last_perfomed_player = null;
        last_args = null;
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return null;
    }

    private void checkScope() {
        if(last_perfomed_player == null || last_args == null) throw new RuntimeException("Out of scope!");
    }

    protected final Player player() {
        checkScope();
        return last_perfomed_player;
    }

    protected final Optional<Player> player(@Nullable final UUID mcid) {
        if(mcid == null) return Optional.empty();
        return Optional.ofNullable(Bukkit.getPlayer(mcid));
    }

    protected final Optional<Player> player(@Nullable final String mcname) {
        if(mcname == null) return Optional.empty();
        return Optional.ofNullable(Bukkit.getPlayer(mcname));
    }

    protected final boolean isOnline(@Nullable final Player player) {
        if(player == null) return false;
        return player.isOnline();
    }

    protected final boolean isOnline(@Nullable UUID mcid) {
        if(mcid == null) return false;
        return Bukkit.getPlayer(mcid) != null;
    }

    protected final boolean isOnline(@Nullable String mcname) {
        if(mcname == null) return false;
        return Bukkit.getPlayer(mcname) != null;
    }

    protected final Player plr() {
        checkScope();
        return last_perfomed_player;
    }

    protected final int argc() {
        checkScope();
        return last_args.length;
    }

    protected final int argsCount() {
        checkScope();
        return last_args.length;
    }

    protected final @NotNull Optional<String> getArg(final int index) {
        checkScope();
        if (index < 0) return Optional.empty();
        if (index >= last_args.length) return Optional.empty();
        return Optional.of(last_args[index]);
    }

    protected final @NotNull Optional<String> arg(final int index) {
        checkScope();
        return getArg(index);
    }

    protected final void ifArg(final int index, Consumer<String> action) {
        getArg(index).ifPresent(action);
    }

    protected final void ifArgOrElse(final int index, Consumer<String> action, Runnable emptyAction) {
        getArg(index).ifPresentOrElse(action, emptyAction);
    }

    protected final @NotNull Location location() {
        checkScope();
        return last_perfomed_player.getLocation();
    }

    protected final @NotNull World world() {
        checkScope();
        return last_perfomed_player.getWorld();
    }

    protected final @NotNull Optional<World> world(@Nullable final String name) {
        checkScope();
        if(name == null) return Optional.empty();
        return Optional.ofNullable(Bukkit.getWorld(name));
    }

    protected final boolean hasPerm(@Nullable final String permission) {
        checkScope();
        if(permission == null) return false;
        return last_perfomed_player.hasPermission(permission);
    }

    protected final void ifPerm(@Nullable final String permission, Runnable action) {
        checkScope();
        if(permission == null) return;
        if(last_perfomed_player.hasPermission(permission)) action.run();
    }

    protected final void ifPermOrElse(@Nullable final String permission, Runnable action, Runnable emptyAction) {
        checkScope();
        if(permission == null) {
            emptyAction.run();
            return;
        }
        if(last_perfomed_player.hasPermission(permission)) {
            action.run();
        } else {
            emptyAction.run();
        }
    }

    protected final void response(Component content) {
        checkScope();
        last_perfomed_player.sendMessage(content);
    }

    protected final void response(String strContent, TextColor color) {
        response(Component.text(strContent).color(color));
    }

    protected final void response(String strContent) {
        response(Component.text(strContent));
    }

    protected final void response(Object object) {
        response(String.valueOf(object));
    }

    protected final void responseActionBar(Component content) {
        checkScope();
        last_perfomed_player.sendActionBar(content);
    }

    protected final void responseActionBar(String strContent, TextColor color) {
        response(Component.text().content(strContent).color(color).build());
    }

    protected final void responseActionBar(String strContent) {
        response(strContent, NamedTextColor.WHITE);
    }

    protected final void responseActionBar(Object object) {
        response(String.valueOf(object));
    }

    protected final void responseSound(@Nullable final Sound sound, final float volume, final float pitch) {
        checkScope();
        if(sound == null) return;
        last_perfomed_player.playSound(location(), sound, volume, pitch);
    }

    protected final void responseSound(@Nullable final Sound sound, final float volume) {
        responseSound(sound, volume, 1.0F);
    }

    protected final void responseSound(@Nullable final Sound sound) {
        responseSound(sound, 1.0F, 1.0F);
    }

    protected final boolean openGui(@Nullable final Gui gui) {
        checkScope();
        if(gui == null) return false;
        gui.open(last_perfomed_player);
        return true;
    }

    protected final boolean openInventory(@Nullable final Inventory inventory) {
        checkScope();
        if(inventory == null) return false;
        last_perfomed_player.openInventory(inventory);
        return true;
    }

    protected final boolean openInventory(@Nullable final InventoryView inventory) {
        checkScope();
        if(inventory == null) return false;
        last_perfomed_player.openInventory(inventory);
        return true;
    }

    protected final boolean open(@Nullable final Gui gui) {
        return openGui(gui);
    }

    protected final boolean open(@Nullable final Inventory inventory) {
        return openInventory(inventory);
    }

    protected final boolean open(@Nullable final InventoryView inventory) {
        return openInventory(inventory);
    }

    protected final boolean teleport(@Nullable final Location to) {
        checkScope();
        if(to == null) return false;
        last_perfomed_player.teleport(to);
        return true;
    }

    protected final boolean move(@Nullable final Location to) {
        checkScope();
        if(to == null) return false;
        last_perfomed_player.teleport(to);
        return true;
    }

    protected final void giveItem(@Nullable ItemStack item) {
        checkScope();
        if(item == null) return;
        last_perfomed_player.getInventory().addItem(item);
    }

    protected final void give(@Nullable ItemStack item) {
        giveItem(item);
    }

    protected final @NotNull CompletableFuture<Optional<String>> inputChat() {
        checkScope();
        return ImpactLibPlugin.getInstance().getInputModule().requestChatInput(last_perfomed_player);
    }

}
