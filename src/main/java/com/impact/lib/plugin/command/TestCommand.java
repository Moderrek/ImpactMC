package com.impact.lib.plugin.command;

import com.impact.lib.api.command.MPlayerCommand;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class TestCommand extends MPlayerCommand {

    public TestCommand(@NotNull Plugin plugin, String key) {
        super(plugin, key);
    }

    @Override
    public void perform(@NotNull Player player, @NotNull Command command, int argc, @NotNull String @NotNull [] args) {
        // /find MODERR
        if(argNotEqual(0, "find")) {
            error(0, "find");
            return;
        }

        ifArgPresentOrElse(1, name -> player(name).ifPresentOrElse(plr -> {
            response("Gracz jest online i go znaleziono!", NamedTextColor.GREEN);
            responseSound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
            targetBlock(5).ifPresent(block -> block.setType(Material.STONE));
        }, () -> response("Nie znaleziono gracza.", NamedTextColor.RED)), () -> error(0, "Podaj argument!"));
    }

}
