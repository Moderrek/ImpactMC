package pl.impact.lib.api.util;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.impact.lib.api.gui.Gui;

import java.util.Collection;

public final class Players {

    private Players() {
    }

    public static void openGui(@Nullable final Collection<Player> playerCollection, @NotNull final Gui gui) {
        if (playerCollection == null) return;
        playerCollection.forEach(player -> openGui(player, gui));
    }

    public static void openGui(@NotNull final Player player, @NotNull final Gui gui) {
        gui.open(player);
    }

}
