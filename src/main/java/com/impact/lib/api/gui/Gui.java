package com.impact.lib.api.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.impact.lib.Impact;
import com.impact.lib.ImpactLibPlugin;
import com.impact.lib.api.gui.element.EmptyUiElement;
import com.impact.lib.api.gui.event.GuiCloseEvent;
import com.impact.lib.api.gui.event.GuiOpenEvent;
import com.impact.lib.api.util.ItemBuilder;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Represents the Minecraft GUI Inventory.
 */
public abstract class Gui extends GuiContent implements GuiBase<GuiView> {

    private final transient Plugin owner;
    private final String key;

    private final Map<UUID, GuiView> views;

    /**
     * Constructs {@link Gui} instance with given size.
     *
     * @param size   The inventory slot count enum
     * @param plugin The plugin owner
     * @param key    The unique key
     */
    public Gui(@NotNull final GuiSize size, @NotNull final Plugin plugin, @NotNull final String key) {
        this(size.getSlotCount(), plugin, key);
    }

    /**
     * Constructs {@link Gui} instance with given size.
     *
     * @param size   The inventory slot count (1 - 54). Size must be a multiple of 9.
     * @param plugin The plugin owner
     * @param key    The unique key
     */
    public Gui(final int size, @NotNull final Plugin plugin, @NotNull final String key) {
        super(size, new ConcurrentHashMap<>(size));
        if (size > 54 || size < 1) throw new RuntimeException("GUI Size must be 1 - 54");
        if (size % 9 != 0) throw new RuntimeException("Invalid Gui Size!");
        views = new ConcurrentHashMap<>();
        owner = plugin;
        this.key = key.toLowerCase();
        initContent(this);
        createUiElementCache();
    }

    private void createUiElementCache() {
        content.clear();
        for (int i = 0; i < size; i += 1) {
            UiElement emptyElement = new EmptyUiElement();
            emptyElement.init(this, i);
            content.put(i, emptyElement);
        }
    }

    public static @NotNull Gui create(@NotNull final Plugin owner, @NotNull final GuiSize size, @NotNull final String key, @Nullable final Component title) {
        return new Gui(size, owner, key) {
            @Override
            public @NotNull Component title() {
                if(title != null) return title;
                return Component.text(String.valueOf(getNamespacedKey()));
            }
        };
    }

    public static @NotNull Gui create(@NotNull final Plugin owner, @NotNull final GuiSize size, @NotNull final String key) {
        return create(owner, size, key);
    }

    @Override
    public int getSize() {
        return size;
    }

    /**
     * Returns GUI size.
     *
     * @return The slot count
     */
    public final int getSlotCount() {
        return size;
    }

    /**
     * Called from Gui Listener.
     *
     * @param uuid The Minecraft player uuid
     */
    final void invokeClose(final UUID uuid) {
        if (!views.containsKey(uuid)) return;
        GuiView guiView = views.get(uuid);
        invokeClose(guiView);
    }

    /**
     * Called from Gui Listener.
     *
     * @param guiView The Gui View
     */
    final void invokeClose(final @NotNull GuiView guiView) {
        if (!views.containsKey(guiView.getPlayer().getUniqueId())) return;
        // fire event
        GuiCloseEvent closeEvent = new GuiCloseEvent(guiView);
        Impact.fireEvent(closeEvent);
        // destroy elements
        guiView.getUiElements().forEach(uiElement -> {
            uiElement.callDestroy();
            uiElement.onDestroy(guiView);
        });
        // remove from cache
        onClose(guiView);
        views.remove(guiView.getPlayer().getUniqueId());
        guiView.getInventoryView().close();
    }

    public final void setTitle(Component title) {
        // TODO packet
    }

    /**
     * Updates all {@link UiElement} of this {@link Gui} for all players with open gui.
     */
    public final void update() {
        views.keySet().forEach(this::update);
    }

    @Contract(pure = true)
    private void isValidSlotIndex(int slotIndex) {
        if (slotIndex > size - 1 || slotIndex < 0) throw new RuntimeException("Slot index must be 0 - " + (size - 1));
    }

    /**
     * Updates given {@link UiElement} for all players with open gui.
     *
     * @param element The {@link UiElement} to update
     */
    public final void update(UiElement element) {
        views.keySet().forEach(uuid -> update(element, uuid));
    }

    /**
     * Updates all {@link UiElement} in open gui view for player with given Minecraft player uuid.
     *
     * @param forWho The Minecraft player UUID
     */
    public final void update(UUID forWho) {
        GuiView view = views.get(forWho);
        for (UiElement uiElement : view.getUiElements()) {
            try {
                ItemStack itemStack = uiElement.construct(view);
                view.getInventoryView().setItem(uiElement.getSlotIndex(), itemStack);
            } catch (Exception exception) {
                exception.printStackTrace();
                view.getInventoryView().setItem(uiElement.getSlotIndex(), new ItemBuilder()
                        .material("barrier")
                        .name(Component
                                .text("Wystąpił problem podczas aktualizacji!")
                                .color(NamedTextColor.RED)
                                .decorate(TextDecoration.BOLD))
                        .build());
            }
        }
    }

    /**
     * Updates and construct given ui element.
     *
     * @param element The UI Element
     */
    public final void update(@NotNull UiElement element, UUID forWho) {
        GuiView view = views.get(forWho);
        try {
            ItemStack itemStack = element.construct(view);
            view.getInventoryView().setItem(element.getSlotIndex(), itemStack);
        } catch (Exception exception) {
            exception.printStackTrace();
            view.getInventoryView().setItem(element.getSlotIndex(), new ItemBuilder()
                    .material("barrier")
                    .name(Component
                            .text("Wystąpił problem podczas aktualizacji!")
                            .color(NamedTextColor.RED)
                            .decorate(TextDecoration.BOLD))
                    .build());
        }
    }

    /**
     * Updates given {@link UiElement} in given {@link UiElement}.
     *
     * @param element The Ui Element
     * @param view    The Gui View
     */
    public final void update(UiElement element, GuiView view) {
        try {
            ItemStack itemStack = element.construct(view);
            view.getInventoryView().setItem(element.getSlotIndex(), itemStack);
        } catch (Exception exception) {
            Impact.getLogger().warn(exception.getMessage());
            exception.printStackTrace();
            view.getInventoryView().setItem(element.getSlotIndex(), new ItemBuilder()
                    .material("barrier")
                    .name(Component
                            .text("Wystąpił problem podczas aktualizacji!")
                            .color(NamedTextColor.RED)
                            .decorate(TextDecoration.BOLD))
                    .build());
        }
    }

    /**
     * Gets {@link GuiView} of open {@link Gui} for given player.
     *
     * @param player The Minecraft player
     * @return The Gui View
     */
    public final GuiView getView(@NotNull Player player) {
        return getView(player.getUniqueId());
    }

    /**
     * Gets {@link GuiView} of open {@link Gui} for player with given Minecraft player uuid.
     *
     * @param uuid The Minecraft player uuid
     * @return The Gui View
     */
    public final GuiView getView(UUID uuid) {
        return views.get(uuid);
    }

    /**
     * Gets all {@link GuiView} of this {@link Gui}.
     *
     * @return The collection of {@link GuiView}
     */
    @Contract(pure = true)
    public final @NotNull Collection<GuiView> getViews() {
        return views.values();
    }

    @Override
    public @NotNull String getDisplayName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public final @NotNull Plugin getPlugin() {
        return owner;
    }

    @Override
    @Contract(" -> new")
    public final @NotNull NamespacedKey getNamespacedKey() {
        return new NamespacedKey(owner, key);
    }

    /**
     * Returns {@link Inventory} title which is show to player
     *
     * @return The {@link Component} GUI Title
     */
    @Override
    public abstract @NotNull Component title();

    /**
     * Opens this {@link Gui} for player.
     *
     * @param player The Minecraft player
     * @return The Gui View
     */
    @Override
    public final @NotNull Optional<GuiView> open(@NotNull Player player) {
        // create minecraft inventory view
        Inventory inventory = Bukkit.createInventory(null, size, title());
        InventoryView inventoryView = player.openInventory(inventory);
        if (inventoryView == null) throw new RuntimeException("Cannot create Minecraft Inventory View.");
        // create gui view
        GuiView guiView = createView(player, inventoryView);
        // add to view cache
        views.put(player.getUniqueId(), guiView);
        // construct item stacks
        for (UiElement uiElement : guiView.getUiElements()) {
            uiElement.onCreate(guiView);
            ItemStack itemStack = uiElement.construct(guiView);
            inventoryView.setItem(uiElement.getSlotIndex(), itemStack);
        }
        // Fire Event
        GuiOpenEvent guiOpenEvent = new GuiOpenEvent(guiView);
        Impact.fireEvent(guiOpenEvent);
        // if open gui is cancelled
        if (guiOpenEvent.isCancelled()) {
            inventoryView.close();
            views.remove(player.getUniqueId(), guiView);
            return Optional.empty();
        }
        // register open gui
        ImpactLibPlugin.getInstance().getGuiModule().openGui(player.getUniqueId(), this);
        onOpen(guiView);
        return Optional.of(guiView);
    }

    @Contract("_, _ -> new")
    private @NotNull GuiView createView(Player player, InventoryView inventoryView) {
        Map<Integer, UiElement> elementMap = new ConcurrentHashMap<>(content.size());
        for (UiElement element : content.values()) {
            elementMap.put(element.getSlotIndex(), element.clone());
        }
        return new GuiView(player, this, elementMap, inventoryView);
    }

    /**
     * Invoked when player opens this gui.
     *
     * @param view The player Gui View
     */
    @Override
    public void onOpen(@NotNull final GuiView view) {
    }

    /**
     * Invoked when player close this gui.
     *
     * @param view The player Gui View
     */
    @Override
    public void onClose(@NotNull final GuiView view) {
    }

}
