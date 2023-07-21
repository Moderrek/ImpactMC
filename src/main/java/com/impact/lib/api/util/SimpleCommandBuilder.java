package pl.impact.lib.api.util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class SimpleCommandBuilder {

    public SimpleCommandBuilder() {

    }

    public void setHandler() {

    }

    public CommandExecutor build() {
        return new CommandExecutor() {
            @Override
            public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
                return false;
            }
        };
    }

}
