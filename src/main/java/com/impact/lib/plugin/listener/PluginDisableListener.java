package com.impact.lib.plugin.listener;

import com.impact.lib.ImpactLibPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class PluginDisableListener implements Listener {

    private final ImpactLibPlugin impact;

    public PluginDisableListener(ImpactLibPlugin plugin) {
        this.impact = plugin;
    }

    private void clearGuis(Plugin plugin) {
        impact.getGuiModule().getGuisByPlugin(plugin).forEach(gui -> impact.getGuiModule().removeGui(gui));
    }

    @EventHandler
    public void onPluginDisable(@NotNull PluginDisableEvent event) {
        clearGuis(event.getPlugin());
        impact.unregisterCommands(event.getPlugin());
    }

}
