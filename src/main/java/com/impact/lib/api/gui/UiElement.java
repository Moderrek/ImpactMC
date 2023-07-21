package com.impact.lib.api.gui;

import com.impact.lib.Impact;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import com.impact.lib.api.gui.event.GuiClickEvent;

import java.util.*;
import java.util.function.Consumer;

/**
 * Represents Ui Element in {@link Gui}
 */
public abstract class UiElement implements UiElementBase<GuiView>, Cloneable {


    private transient Gui owner = null;
    private int slotIndex = -1;

    private transient Consumer<GuiClickEvent> clickConsumer = null;
    private transient Collection<BukkitTask> timers = new ArrayList<>();

    /**
     * Sets gui and slot
     *
     * @param owner     The Gui
     * @param slotIndex The slot index
     * @throws RuntimeException When try to re-init
     */
    final void init(@NotNull final Gui owner, final int slotIndex) {
        if (this.owner != null && this.slotIndex != -1) throw new RuntimeException("Cannot redefine ui element");
        this.owner = owner;
        this.slotIndex = slotIndex;
    }

    public final void onClick(@NotNull Consumer<GuiClickEvent> clickHandler) {
        // assign new handler
        clickConsumer = clickHandler;
    }

    final void callClick(@NotNull final GuiClickEvent event) {
        // call to handler
        Optional.ofNullable(clickConsumer).ifPresent(consumer -> consumer.accept(event));
    }

    final void callDestroy() {
        // clear timers
//        Iterator<BukkitTask> i = timers.iterator();
//        while(i.hasNext()) {
//            BukkitTask task = i.next();
//            Impact.cancelTask(task);
//            i.remove();
//        }
        timers.forEach(Impact::cancelTask);
        timers.clear();
    }

    protected final void delay(long ticks, Runnable action) {
        timers.add(Impact.laterTask(getPlugin(), action, ticks));
    }

    protected final void timer(long ticksPeriod, Runnable action) {
        timers.add(Impact.repeatingTask(getPlugin(), action, ticksPeriod));
    }

    protected final void tick(Runnable action) {
        timers.add(Impact.repeatingTask(getPlugin(), action, 1L));
    }

    /**
     * Gets the inventory slot index
     *
     * @return The inventory slot index
     */
    public final int getSlotIndex() {
        return slotIndex;
    }

    /**
     * Updates this ui element for specified player.
     *
     * @param player The specified player
     */
    public final void update(@NotNull final Player player) {
        getGui().update(this, player.getUniqueId());
    }

    /**
     * Gets the Gui
     *
     * @return The gui
     */
    public final Gui getGui() {
        return owner;
    }

    /**
     * Gets the Plugin
     *
     * @return The Plugin
     */
    @Contract(pure = true)
    public final @NotNull Plugin getPlugin() {
        return owner.getPlugin();
    }

    /**
     * Updates this ui element for specified player by given uuid.
     *
     * @param uuid The uuid
     */
    public final void update(@NotNull final UUID uuid) {
        getGui().update(this, uuid);
    }

    /**
     * Updates this ui element for everyone.
     */
    public final void update() {
        getGui().update(this);
    }

    /**
     * Updates this ui element for specified gui view.
     *
     * @param view The gui view
     */
    public final void update(@NotNull final GuiView view) {
        getGui().update(this, view.getPlayer().getUniqueId());
    }

    /**
     * Closes Gui View.
     *
     * @param guiView The Gui View
     */
    public final void closeGui(@NotNull final GuiView guiView) {
        guiView.close();
    }

    /**
     * Closes Gui View for given Minecraft player.
     *
     * @param player The Minecraft player
     */
    public final void closeGui(@NotNull final Player player) {
        closeGui(player.getUniqueId());
    }

    /**
     * Closes Gui View for given Minecraft player uuid.
     *
     * @param playerUuid The Minecraft player uuid
     */
    public final void closeGui(@NotNull final UUID playerUuid) {
        getGui().getView(playerUuid).close();
    }

    /**
     * Invoked at ui element show
     *
     * @param where The where element start
     * @return The visible ItemStack in inventory
     */
    @Override
    public abstract @NotNull ItemStack construct(@NotNull final GuiView where);

    /**
     * Invoked at ui element starts
     *
     * @param view The where element starts
     */
    @Override
    public void onCreate(@NotNull final GuiView view) {
    }

    /**
     * Invoked at ui element ends
     *
     * @param view The where element ends
     */
    @Override
    public void onDestroy(@NotNull final GuiView view) {
    }

    @Override
    public int hashCode() {
        return Objects.hash(slotIndex);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UiElement uiElement = (UiElement) o;
        return slotIndex == uiElement.slotIndex && Objects.equals(owner, uiElement.owner);
    }

    @Override
    public UiElement clone() {
        try {
            return (UiElement) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
