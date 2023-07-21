package com.impact.lib.api.gui;

import com.impact.lib.Impact;
import com.impact.lib.ImpactLibPlugin;
import com.impact.lib.api.module.PluginModule;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public final class GuiModule extends PluginModule<ImpactLibPlugin> {

    private final Map<UUID, Gui> openGuiMap;
    private final GuiListener guiListener = new GuiListener(this);

    public GuiModule() {
        this.openGuiMap = new ConcurrentHashMap<>();
    }

    public @Unmodifiable Set<Gui> getOpenGuis() {
        return Set.copyOf(openGuiMap.values());
    }

    void openGui(@NotNull final UUID uuid, @NotNull final Gui gui) {
        openGuiMap.put(uuid, gui);
    }

    /**
     * @param player
     * @param gui
     * @return
     */
    public GuiView openGui(@NotNull final Player player, @NotNull final Gui gui) throws NoSuchElementException {
        return gui.open(player).orElseThrow();
    }

    @Override
    public void enable(@NotNull final ImpactLibPlugin plugin) {
        Impact.addListener(guiListener, plugin);
    }

    @Override
    public void disable(@NotNull final ImpactLibPlugin plugin) {
        // closes all open gui's
        openGuiMap.keySet().forEach(this::closeGui);
        Impact.removeListener(guiListener);
    }

    void closeGui(UUID uuid) {
        if (!isOpenGui(uuid)) return;
        getOpenGui(uuid).invokeClose(uuid);
        openGuiMap.remove(uuid);
    }

    public Collection<Gui> getGuisByPlugin(Plugin plugin) {
        return openGuiMap.values().stream().filter(gui -> gui.getPlugin() == plugin).distinct().collect(Collectors.toCollection(ArrayList::new));
    }

    public void removeGui(Gui gui) {
        for(UUID key : openGuiMap.keySet()) {
            Gui other = openGuiMap.get(key);
            if(gui == other) {
                gui.getViews().forEach(gui::invokeClose);
                openGuiMap.remove(key, gui);
            }
        }
    }

    public boolean isOpenGui(UUID uuid) {
        return openGuiMap.containsKey(uuid);
    }

    public Gui getOpenGui(UUID uuid) {
        return openGuiMap.get(uuid);
    }
}
