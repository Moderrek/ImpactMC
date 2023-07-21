package pl.impact.lib.api.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public abstract class MConsoleCommand extends MCommand<ConsoleCommandSender> {

    public MConsoleCommand(@NotNull Plugin plugin, @NotNull String key) {
        super(plugin, key);
    }

    public Optional<Component> cantUseCommandMessage() {
        return Optional.of(Component
                .text()
                .content("You cannot execute this command!")
                .color(NamedTextColor.RED)
                .build()
        );
    }

    @Override
    public final boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof ConsoleCommandSender console)) {
            // not console
            cantUseCommandMessage().ifPresent(sender::sendMessage);
            return true;
        }
        // console
        perform(console, command, args.length, args);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return null;
    }
}
