package com.impact.lib.api.block;

import com.impact.lib.ImpactLibPlugin;
import com.impact.lib.api.registry.ImpactRegistries;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CustomBlock {

  public static final String METADATA_CUSTOM_KEY = "custom";

  private final Material material;

  public CustomBlock(@NotNull Material material) {
    if (!material.isBlock()) throw new IllegalArgumentException("Block cannot be an item!");
    this.material = material;
  }

  public static Optional<CustomBlock> get(@Nullable Block block) {
    if (block == null) return Optional.empty();
    if (!block.hasMetadata(METADATA_CUSTOM_KEY)) return Optional.empty();
    Optional<MetadataValue> optional = block.getMetadata(METADATA_CUSTOM_KEY)
        .stream()
        .filter(metadataValue -> metadataValue.getOwningPlugin() == ImpactLibPlugin.getInstance())
        .findFirst();
    if (optional.isEmpty()) return Optional.empty();
    NamespacedKey key = NamespacedKey.fromString(optional.get().asString());
    if (!ImpactRegistries.CUSTOM_BLOCK.isRegistered(key)) return Optional.empty();
    CustomBlock customBlock = ImpactRegistries.CUSTOM_BLOCK.get(key);
    if (!customBlock.getMaterial().equals(block.getType())) {
      remove(block);
      return Optional.empty();
    }
    return Optional.of(customBlock);
  }

  public static void set(@NotNull CustomBlock block, @NotNull Location location) {
    Block mBlock = location.getBlock();
    // remove custom
    remove(mBlock);

    mBlock.setType(block.getMaterial());
    mBlock.setMetadata(
        METADATA_CUSTOM_KEY,
        new FixedMetadataValue(
            ImpactLibPlugin.getInstance(),
            block.getNamespacedKey().toString()
        )
    );
    block.onCreate(mBlock);
  }

  public static void set(@NotNull NamespacedKey namespacedKey, @NotNull Location location) {
    set(ImpactRegistries.CUSTOM_BLOCK.get(namespacedKey), location);
  }

  public static void remove(@NotNull Block block) {
    if (block.hasMetadata(METADATA_CUSTOM_KEY)) {
      get(block).ifPresent(customBlock -> customBlock.onRemove(block));
      block.removeMetadata(METADATA_CUSTOM_KEY, ImpactLibPlugin.getInstance());
    }
  }

  public static boolean isValid(@Nullable Block block) {
    return get(block).isPresent();
  }

  public final NamespacedKey getNamespacedKey() {
    return ImpactRegistries.CUSTOM_BLOCK.getNamespacedKey(this);
  }

  public final Plugin getPlugin() {
    return ImpactRegistries.CUSTOM_BLOCK.getPlugin(this);
  }

  /**
   * Gets the <b>constant</b> material of {@link CustomBlock}.
   *
   * @return The {@link Material}
   */
  public final Material getMaterial() {
    return material;
  }

  /**
   * On set Minecraft Block a custom block.
   *
   * @param block The Minecraft Block
   */
  public void onCreate(@NotNull Block block) {

  }

  /**
   * On unset Minecraft Block a custom block.
   *
   * @param block The Minecraft Block
   */
  public void onRemove(@NotNull Block block) {

  }

  /**
   * Place by {@link Player} handle.
   *
   * @param player The player who placed block
   * @param block  The block which placed
   */
  public void onPlace(@NotNull Player player,
                      @NotNull Block block) {

  }

  /**
   * Break by {@link Player} handle.
   *
   * @param player The player who broke block
   * @param block  The block which broke
   */
  public void onBreak(@NotNull Player player,
                      @NotNull Block block) {

  }

  public void onClick(@NotNull Player player,
                      @NotNull Block block) {
    // click
  }

  /**
   * Move by piston handle.
   *
   * @param block  The block
   * @param piston The piston block
   * @return Can move
   */
  public boolean onMove(@NotNull Block block,
                        @NotNull Block piston) {
    return false;
  }

  /**
   * Explode handle
   *
   * @param block The block
   * @param yield The explosion yield
   * @return Can explode
   */
  public boolean onExplode(@NotNull Block block,
                           float yield) {
    return true;
  }

  public boolean onIgnite(@NotNull Block block,
                          @NotNull BlockIgniteEvent.IgniteCause cause,
                          @Nullable Player player) {
    return true;
  }

  public void onRedstone(@NotNull Block block,
                         int signal) {

  }

  public boolean onEndermanGrief(@NotNull Block block,
                                 @NotNull Entity enderman) {
    return true;
  }

  public boolean onEatGrass(@NotNull Block block,
                            @NotNull Entity eater) {
    return true;
  }

}
