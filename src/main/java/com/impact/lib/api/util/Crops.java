package pl.impact.lib.api.block;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class Crops {

    public static final List<Material> CROP_MATERIALS = List.of(
            Material.WHEAT,
            Material.CARROTS,
            Material.POTATOES,
            Material.BEETROOTS,
            Material.MELON_STEM,
            Material.PUMPKIN_STEM,
            Material.NETHER_WART
    );

    public static final Map<Material, Material> CROP_SEEDS = Map.of(
            Material.WHEAT, Material.WHEAT_SEEDS,
            Material.CARROTS, Material.CARROT,
            Material.POTATOES, Material.POTATO,
            Material.BEETROOTS, Material.BEETROOT,
            Material.MELON_STEM, Material.MELON_SEEDS,
            Material.PUMPKIN_STEM, Material.PUMPKIN_SEEDS,
            Material.NETHER_WART, Material.NETHER_WART
    );

    private Crops() {
    }

    public static boolean isCrop(@Nullable final Block block) {
        if (block == null) return false;
        return isCrop(block.getType());
    }

    public static boolean isCrop(@Nullable final Material material) {
        if (material == null) return false;
        return Crops.CROP_MATERIALS.contains(material);
    }

    public static Optional<Material> getCropSeedItem(@Nullable final Material crop) {
        if (!isCrop(crop)) return Optional.empty();
        return Optional.ofNullable(CROP_SEEDS.get(crop));
    }

    public static boolean isReadyForHarvest(@Nullable final Block block) {
        if (block == null) return false;
        final Material material = block.getType();
        if (!isCrop(material)) return false;
        final Ageable ageable = (Ageable) block.getState().getBlockData();
        return ageable.getAge() == ageable.getMaximumAge();
    }

    public static boolean setReadyForHarvest(@Nullable final Block block) {
        if (block == null) return false;
        final Material material = block.getType();
        if (!isCrop(material)) return false;
        final Ageable ageable = (Ageable) block.getState().getBlockData();
        ageable.setAge(ageable.getMaximumAge());
        block.getState().update();
        return true;
    }

    public static Optional<Collection<ItemStack>> getDrops(@Nullable final Block block) {
        return getDrops(block, null);
    }

    public static Optional<Collection<ItemStack>> getDrops(@Nullable final Block block, @Nullable final ItemStack tool) {
        if (block == null) return Optional.empty();
        final Material material = block.getType();
        if (!isCrop(material)) return Optional.empty();
        return Optional.of(block.getDrops(tool));
    }

}
