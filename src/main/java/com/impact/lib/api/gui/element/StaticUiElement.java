package pl.impact.lib.api.gui.element;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import pl.impact.lib.api.gui.GuiView;
import pl.impact.lib.api.gui.UiElement;
import pl.impact.lib.api.util.ItemBuilder;

/**
 * Represents static GUI element.
 */
public final class StaticUiElement extends UiElement {

    private final ItemStack staticItem;

    /**
     * Constructs empty static element.
     */
    public StaticUiElement() {
        staticItem = new ItemBuilder().material(Material.AIR).build();
    }

    /**
     * Constructs static element by material enum.
     *
     * @param material The {@link Material} enum
     */
    public StaticUiElement(@NotNull final Material material) {
        staticItem = new ItemBuilder().material(material).build();
    }

    /**
     * Constructs static element by material name.
     *
     * @param materialName The material name
     */
    public StaticUiElement(@NotNull final String materialName) {
        staticItem = new ItemBuilder().material(materialName).build();
    }

    /**
     * Builds static element by given {@link ItemBuilder}.
     *
     * @param builder The item builder
     */
    public StaticUiElement(@NotNull final ItemBuilder builder) {
        staticItem = builder.build();
    }

    /**
     * Clones given item stack.
     *
     * @param itemStack The item stack
     */
    public StaticUiElement(@NotNull final ItemStack itemStack) {
        staticItem = itemStack.clone();
    }

    @Contract("_ -> new")
    public static @NotNull StaticUiElement of(@NotNull final String materialName) {
        return new StaticUiElement(materialName);
    }

    @Contract("_ -> new")
    public static @NotNull StaticUiElement of(@NotNull final Material material) {
        return new StaticUiElement(material);
    }

    @Contract("_ -> new")
    public static @NotNull StaticUiElement of(@NotNull final ItemBuilder builder) {
        return new StaticUiElement(builder);
    }

    @Contract("_ -> new")
    public static @NotNull StaticUiElement of(@NotNull final ItemStack item) {
        return new StaticUiElement(item);
    }

    @Override
    public @NotNull ItemStack construct(@NotNull final GuiView where) {
        return staticItem.clone();
    }

    @Override
    public String toString() {
        return "StaticUiElement[%s]".formatted(staticItem.getType());
    }
}
