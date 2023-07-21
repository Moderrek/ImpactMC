package pl.impact.lib.api.gui.element;

import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import pl.impact.lib.Impact;
import pl.impact.lib.api.gui.GuiView;
import pl.impact.lib.api.gui.UiElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Represents GUI Element.
 * This element is animating item stacks which auto updates in gui view.
 */
public final class AnimatedUiElement extends UiElement {

    private final List<ItemStack> itemStackList;
    private final long periodTicks;

    private ItemStack currentItemStack;
    private int currentIndex = 0;

    private transient BukkitTask animatingTask;

    /**
     * Constructs Animated Ui Element with given item stack collection and period ticks.
     * <p><b>20 ticks = 1 second.</b></p>
     * <p>Item is animating in given order and auto updates in GUI View.</p>
     *
     * @param itemStackCollection The item stacks
     * @param periodTicks         The period ticks
     */
    public AnimatedUiElement(@NotNull final Collection<ItemStack> itemStackCollection, final long periodTicks) {
        this.itemStackList = new ArrayList<>(itemStackCollection);
        this.periodTicks = periodTicks;
        this.currentItemStack = itemStackList.get(currentIndex);
    }

    /**
     * Constructs Animated Ui Element with given item stack list and period ticks.
     * <p><b>20 ticks = 1 second.</b></p>
     * <p>Item is animating in given order and auto updates in GUI View.</p>
     *
     * @param itemStackList The item stacks
     * @param periodTicks   The period ticks
     */
    public AnimatedUiElement(@NotNull final List<ItemStack> itemStackList, final long periodTicks) {
        this.itemStackList = itemStackList;
        this.periodTicks = periodTicks;
        this.currentItemStack = itemStackList.get(currentIndex);
    }

    /**
     * Constructs Animated Ui Element with given item stack collection and period ticks.
     * <p><b>20 ticks = 1 second.</b></p>
     * <p>Item is animating in given order and auto updates in GUI View.</p>
     *
     * @param items       The item stacks
     * @param periodTicks The period ticks
     */
    @Contract("_, _ -> new")
    public static @NotNull AnimatedUiElement of(@NotNull final Collection<ItemStack> items, final long periodTicks) {
        return new AnimatedUiElement(items, periodTicks);
    }

    /**
     * Constructs Animated Ui Element with given item stack list and period ticks.
     * <p><b>20 ticks = 1 second.</b></p>
     * <p>Item is animating in given order and auto updates in GUI View.</p>
     *
     * @param items       The item stacks
     * @param periodTicks The period ticks
     */
    @Contract("_, _ -> new")
    public static @NotNull AnimatedUiElement of(@NotNull final List<ItemStack> items, final long periodTicks) {
        return new AnimatedUiElement(items, periodTicks);
    }

    @Override
    public @NotNull ItemStack construct(@NotNull final GuiView where) {
        // return cloned current item stack
        return currentItemStack.clone();
    }

    @Override
    public void onCreate(@NotNull final GuiView where) {
        // create animating task if null
        if (animatingTask != null) return;
        animatingTask = Impact.repeatingTask(Impact.getImpactLibraryPlugin(), () -> {
            currentItemStack = itemStackList.get(currentIndex);
            currentIndex += 1;
            if (currentIndex >= itemStackList.size()) currentIndex = 0;
            // update this element in gui view which is created
            update(where);
        }, periodTicks);
    }

    @Override
    public void onDestroy(@NotNull final GuiView where) {
        // cancel animating task
        Impact.cancelTask(animatingTask);
    }

    @Override
    public @NotNull String toString() {
        return "AnimatedUiElement[%s]".formatted(currentItemStack.getType());
    }
}
