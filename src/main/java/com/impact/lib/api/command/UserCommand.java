package com.impact.lib.api.command;

import com.impact.lib.api.container.User;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public abstract class UserCommand<U extends User> extends MCommand<U> {

  public UserCommand(@NotNull String label) {
    super(label);
  }

  @Override
  public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
    return null;
  }

  @Override
  public final boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    if (!(sender instanceof Player player)) {
      // not player
      cantUseCommandMessage().ifPresent(sender::sendMessage);
      return true;
    }
    // user
    perform(getUser(player).orElseThrow(), command, args.length, args);
    return true;
  }

  public Optional<Component> cantUseCommandMessage() {
    return Optional.of(Component
        .text()
        .content("You cannot execute this command!")
        .color(NamedTextColor.RED)
        .build()
    );
  }

  public abstract Optional<? extends U> getUser(Player player);

}
