package com.impact.lib.api.world;

import com.impact.lib.Impact;
import com.impact.lib.ImpactLibPlugin;
import com.impact.lib.api.block.CustomBlock;
import com.impact.lib.api.registry.ImpactRegistries;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;

public record ImpactWorld(World world) {

  public void setBlock(NamespacedKey blockId, @NotNull ImpactWorld world, int x, int y, int z) {
    setBlock(blockId, new Location(world.world(), x, y, z));
  }

  public void setBlock(NamespacedKey block, @NotNull Location location) {
    if (isCustomBlock(location.getBlock())) removeCustomBlock(location.getBlock());
    if (block.getNamespace().equalsIgnoreCase(NamespacedKey.MINECRAFT_NAMESPACE)) {
      setBlock(Material.getMaterial(block.getKey()), location);
      return;
    }
    setCustomBlock(block, location);
  }

  public boolean isCustomBlock(Block block) {
//        return new ImpactBlock(block).isCustom();
    // TODO
    return true;
  }

  public void removeCustomBlock(@NotNull Block block) {
    // TODO
    block.setType(Material.AIR);
    block.removeMetadata("custom", ImpactLibPlugin.getInstance());
    block.removeMetadata("customid", ImpactLibPlugin.getInstance());
  }

  public void setBlock(Material material, @NotNull Location location) {
    if (isCustomBlock(location.getBlock())) removeCustomBlock(location.getBlock());
    location.getBlock().setType(material);
  }

  public void setCustomBlock(@NotNull NamespacedKey blockId, @NotNull Location location) {
    // TODO
    Block block = location.getBlock();
    CustomBlock customBlock = ImpactRegistries.CUSTOM_BLOCK.get(blockId);
    block.setType(customBlock.getMaterial());
    block.setMetadata("custom", new FixedMetadataValue(ImpactLibPlugin.getInstance(), true));
    block.setMetadata("customid", new FixedMetadataValue(ImpactLibPlugin.getInstance(), blockId));
  }

  public void setBlock(NamespacedKey blockId, @NotNull World world, int x, int y, int z) {
    setBlock(blockId, new Location(world, x, y, z));
  }

  public void setBlock(NamespacedKey blockId, @NotNull String worldName, int x, int y, int z) {
    setBlock(blockId, new Location(Impact.getWorldByName(worldName).orElseThrow().world(), x, y, z));
  }


  public @NotNull Block getBlock(@NotNull Location location) {
    return location.getBlock();
  }

}
