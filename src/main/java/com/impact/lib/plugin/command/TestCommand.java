package pl.impact.lib.plugin.command;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import pl.impact.lib.api.command.MPlayerCommand;

import java.util.concurrent.ExecutionException;

public class TestCommand extends MPlayerCommand {

    public TestCommand(@NotNull Plugin plugin) {
        super(plugin, "test");
    }

    @Override
    public void perform(@NotNull Player player, @NotNull Command command, int argc, @NotNull String @NotNull [] args) {
        // /find MODERR
        arg(0).ifPresent(name -> player(name).ifPresentOrElse(plr -> {
            response("Gracz jest online i go znaleziono!", NamedTextColor.GREEN);
            responseSound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
        }, () -> response("Nie znaleziono gracza.", NamedTextColor.RED)));

        inputChat().thenAccept(optionalInput -> {
            optionalInput.ifPresentOrElse(input -> {
                // input success
                response(input);
            }, () -> {
                // cancelled
                response("cancelled");
            });
        });

        try {
            inputChat().get().ifPresentOrElse(s -> {
                response(s);
            }, () -> {
                response("cancelled");
            });
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

    }

}
