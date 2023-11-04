package com.impact.lib.api.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

public interface CommandBase<T> extends CommandExecutor, TabCompleter {

  void perform(@NotNull final T executor, @NotNull final Command command, @NotNull final int argc, @NotNull final String[] args);

}
