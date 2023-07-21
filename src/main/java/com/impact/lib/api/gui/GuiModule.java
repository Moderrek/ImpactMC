package pl.impact.lib.api.gui;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import pl.impact.lib.Impact;
import pl.impact.lib.ImpactLibPlugin;
import pl.impact.lib.api.service.PluginModule;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class GuiModule extends PluginModule<ImpactLibPlugin> {

    private final Map<UUID, Gui> openGuiMap;

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
        final GuiListener guiListener = new GuiListener(this);
        Impact.addListener(guiListener, plugin);
    }

    @Override
    public void disable(@NotNull final ImpactLibPlugin plugin) {
        // closes all open gui's
        openGuiMap.keySet().forEach(this::closeGui);
    }

    void closeGui(UUID uuid) {
        if (!isOpenGui(uuid)) return;
        getOpenGui(uuid).invokeClose(uuid);
        openGuiMap.remove(uuid);
    }

    public boolean isOpenGui(UUID uuid) {
        return openGuiMap.containsKey(uuid);
    }

    public Gui getOpenGui(UUID uuid) {
        return openGuiMap.get(uuid);
    }
}
