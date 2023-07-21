package com.impact.lib.api.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class UniversalCommand extends MCommand<CommandSender> {

    public UniversalCommand(@NotNull Plugin plugin, @NotNull String key) {
        super(plugin, key);
    }

    @Override
    public final boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
            onPlayerExecute(player, args.length, args);
        }
        if(sender instanceof ConsoleCommandSender console) {
            onConsoleExecute(console, args.length, args);
        }
        return false;
    }

    @Override
    public final @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if(sender instanceof Player player) {
            return onPlayerTabComplete(player, args.length, args);
        }
        if(sender instanceof ConsoleCommandSender console) {
            return onConsoleTabComplete(console, args.length, args);
        }
        return null;
    }

    public abstract void onPlayerExecute(@NotNull Player player, int argc, @NotNull String[] args);
    public abstract @Nullable List<String> onPlayerTabComplete(@NotNull Player player, int argc, @NotNull String[] args);
    public abstract void onConsoleExecute(@NotNull ConsoleCommandSender console, int argc, @NotNull String[] args);
    public abstract @Nullable List<String> onConsoleTabComplete(@NotNull ConsoleCommandSender console, int argc, @NotNull String[] args);

    @Override
    public final void perform(@NotNull CommandSender sender, @NotNull Command command, int argc, @NotNull String @NotNull [] args) {
        // ignored
    }
}
