package com.impact.lib.plugin.command;

import com.impact.lib.Impact;
import com.impact.lib.api.command.UniversalCommand;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ImpactLibCommand extends UniversalCommand {


    public ImpactLibCommand(@NotNull Plugin plugin, @NotNull String key) {
        super(plugin, key);
    }

    @Override
    public void onPlayerExecute(@NotNull Player player, int argc, @NotNull String[] args) {
        ifArgPresentOrElse(0, (arg) -> {
            response(Impact.simpleMessage("&ahi"));
        }, () -> {
            error(0, "Brak argumentu!");
        });
    }

    @Override
    public @Nullable List<String> onPlayerTabComplete(@NotNull Player player, int argc, @NotNull String[] args) {
        return null;
    }

    @Override
    public void onConsoleExecute(@NotNull ConsoleCommandSender console, int argc, @NotNull String[] args) {

    }

    @Override
    public @Nullable List<String> onConsoleTabComplete(@NotNull ConsoleCommandSender console, int argc, @NotNull String[] args) {
        return null;
    }
}
