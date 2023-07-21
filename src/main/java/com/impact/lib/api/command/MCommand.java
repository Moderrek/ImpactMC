package com.impact.lib.api.command;

import com.impact.lib.api.util.Components;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class MCommand<T> extends Command implements CommandBase<T> {

    private final Plugin owner;
    private final String key;

    public MCommand(@NotNull final Plugin plugin, @NotNull final String key) {
        super(key);
        this.owner = plugin;
        this.key = key;
    }

    protected @Nullable String label;
    protected @Nullable String[] args;
    protected @Nullable CommandSender sender;

    @Override
    public final boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if(!isRegistered()) return false;
        this.label = commandLabel;
        this.args = args;
        this.sender = sender;
        boolean response = onCommand(sender, this, commandLabel, args);
        this.label = null;
        this.args = null;
        this.sender = null;
        return response;
    }

    @Override
    public final @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        List<String> complete = onTabComplete(sender, this, alias, args);
        return complete == null ? List.of() : complete;
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

    public @Nullable String permission() {
        return null;
    }

    public final Plugin getOwner() {
        return owner;
    }

    public final String getKey() {
        return key;
    }

    protected final void checkScope() {
        if(label == null || sender == null || args == null) throw new RuntimeException("Out of scope!");
    }

    protected final String label() {
        checkScope();
        return label;
    }

    protected final int argc() {
        checkScope();
        return args.length;
    }

    protected final Optional<String> arg(int index) {
        checkScope();
        if(index < 0 || index >= args.length) return Optional.empty();
        return Optional.ofNullable(args[index]);
    }

    protected boolean argEqual(int index, String equal) {
        checkScope();
        if(equal == null) return false;
        Optional<String> optArg = arg(index);
        return optArg.map(s -> s.equalsIgnoreCase(equal)).orElse(false);
    }

    protected final boolean argNotEqual(int index, String notEqual) {
        return !argEqual(index, notEqual);
    }

    protected void ifArgPresent(int index, Consumer<String> action) {
        arg(index).ifPresent(action);
    }

    protected void ifArgPresentOrElse(int index, Consumer<String> action, Runnable emptyAction) {
        arg(index).ifPresentOrElse(action, emptyAction);
    }

    protected void ifArgEqual(int index, String equal, Runnable action) {
        arg(index).ifPresent(s -> {
            if(s.equalsIgnoreCase(equal)) {
                action.run();
            }
        });
    }

    protected void ifArgNotEqual(int index, String equal, Runnable action) {
        arg(index).ifPresent(s -> {
            if(!s.equalsIgnoreCase(equal)) {
                action.run();
            }
        });
    }

    protected void ifArgEqualOrElse(int index, String equal, Runnable action, Runnable emptyAction) {
        arg(index).ifPresent(s -> {
            if(s.equalsIgnoreCase(equal)) {
                action.run();
            } else {
                emptyAction.run();
            }
        });
    }

    protected void ifArgNotEqualOrElse(int index, String equal, Runnable action, Runnable emptyAction) {
        arg(index).ifPresent(s -> {
            if(!s.equalsIgnoreCase(equal)) {
                action.run();
            } else {
                emptyAction.run();
            }
        });
    }

    protected final boolean hasPerm(String permission) {
        if(permission == null) return true;
        checkScope();
        return sender.hasPermission(permission);
    }

    protected Server server() {
        checkScope();
        return sender.getServer();
    }

    protected Player asPlayer() {
        return (Player) sender;
    }

    protected CommandSender asSender() {
        return sender;
    }

    protected ConsoleCommandSender asConsole() {
        return (ConsoleCommandSender) sender;
    }

    protected final void response(Component content) {
        checkScope();
        sender.sendMessage(content);
    }

    protected final void responseFormatted(String format, Object... args) {
        checkScope();
        response(Components.simple(format, args));
    }

    protected final void response(String strContent, TextColor color) {
        response(Component.text(strContent).color(color));
    }

    protected final void responseSimple(String legacy) {
        response(Components.simple(legacy));
    }

    protected final void response(String strContent) {
        response(Component.text(strContent));
    }

    protected final void response(Object object) {
        response(String.valueOf(object));
    }

    protected final void error(int argIndex, @Nullable Component message) {
        checkScope();
        TextComponent.Builder builder = Component.text();
        builder.append(Component.text(label).color(NamedTextColor.GRAY));
        for(int i = 0; i < argIndex + 1; i += 1) {
            builder.append(Component.space());
            if(i == argIndex) {
                builder.append(Component.text(args[i]).decoration(TextDecoration.UNDERLINED, true).color(NamedTextColor.RED));
                continue;
            }
            builder.append(Component.text(args[i]).color(NamedTextColor.GRAY));
        }
        builder.append(Component.text(" <-- HERE").decoration(TextDecoration.ITALIC, true).color(NamedTextColor.RED));
        if(message == null) {
            sender.sendMessage(Component.text("Wystąpił błąd.").color(NamedTextColor.RED));
        } else {
            sender.sendMessage(Component.text().color(NamedTextColor.RED).append(message));
        }
        sender.sendMessage(builder.clickEvent(ClickEvent.clickEvent(ClickEvent.Action.SUGGEST_COMMAND, '/' + label + " " + String.join(" ", args))).build());
    }

    protected final void error(int argIndex, @Nullable String message) {
        error(argIndex, Component.text(message != null ? message : "null"));
    }

    protected final void error(int argIndex, @NotNull Exception exception) {
        error(argIndex, exception.getMessage());
    }

    protected final void error(int argIndex, @Nullable Object object) {
        error(argIndex, String.valueOf(object));
    }

}
