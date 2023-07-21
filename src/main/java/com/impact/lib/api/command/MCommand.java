package pl.impact.lib.api.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class MCommand<T> extends Command implements CommandBase<T> {

    private final Plugin owner;
    private final String key;

    public MCommand(@NotNull final Plugin plugin, @NotNull final String key) {
        super(key);
        this.owner = plugin;
        this.key = key;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        return onCommand(sender, this, commandLabel, args);
    }

    @Override
    public abstract boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args);

    @Override
    public abstract @Nullable List<String> onTabComplete(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String alias, @NotNull String[] args);

    @Override
    public abstract void perform(@NotNull final T sender, @NotNull final Command command, final int argc, @NotNull final String @NotNull [] args);

    public @NotNull List<String> aliases() {
        return List.of();
    }

    public final Plugin getOwner() {
        return owner;
    }

    public final String getKey() {
        return key;
    }
}
