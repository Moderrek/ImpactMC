package pl.impact.lib.api.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.inventory.ItemStack;
import pl.impact.lib.Impact;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public final class Items {

    private Items() {
    }

    public static ItemStack deserialize(byte[] bytes) {
        return Impact.getServer().getUnsafe().deserializeItem(bytes);
    }

    public static byte[] serialize(ItemStack itemStack) {
        return Impact.getServer().getUnsafe().serializeItem(itemStack);
    }

    public static Collection<ItemStack> singleItems(String... materialNames) {
        return Arrays.stream(materialNames).map(Items::singleItem).collect(Collectors.toList());
    }

    public static ItemStack singleItem(String materialName) {
        return new ItemBuilder().material(materialName).build();
    }

    public static Collection<ItemStack> stackItems(String... materialNames) {
        return Arrays.stream(materialNames).map(Items::stackItem).collect(Collectors.toList());
    }

    public static ItemStack stackItem(String materialName) {
        return new ItemBuilder().material(materialName).stack().build();
    }

    public static ItemStack namedItem(String materialName, String name) {
        return namedItem(materialName, name, NamedTextColor.WHITE);
    }

    public static ItemStack namedItem(String materialName, String name, TextColor nameColor) {
        return new ItemBuilder().material(materialName).name(Component.text(name).color(nameColor).decoration(TextDecoration.ITALIC, false)).build();
    }

}
