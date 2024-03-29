package com.impact.lib.api.player;

import com.destroystokyo.paper.ClientOption;
import com.destroystokyo.paper.Title;
import com.destroystokyo.paper.block.TargetBlockInfo;
import com.destroystokyo.paper.entity.TargetEntityInfo;
import com.destroystokyo.paper.profile.PlayerProfile;
import io.papermc.paper.entity.LookAnchor;
import io.papermc.paper.entity.TeleportFlag;
import io.papermc.paper.math.Position;
import io.papermc.paper.threadedregions.scheduler.EntityScheduler;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.util.TriState;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.sign.Side;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.entity.*;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.*;
import org.bukkit.map.MapView;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import org.jetbrains.annotations.UnmodifiableView;

import java.lang.ref.WeakReference;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.function.Consumer;

public class WrappedPlayer implements Player {

  private final WeakReference<Player> playerRef;

  public WrappedPlayer(Player player) {
    this.playerRef = new WeakReference<>(player);
  }

  /**
   * Gets the player handle.
   * @return The Bukkit player
   */
  public @NotNull Player getPlayer() {
    Player player = playerRef.get();
    if (player == null) throw new IllegalStateException("The player was unloaded and the reference is unavailable!");
    return player;
  }

  public void giveItem(@NotNull ItemStack item) {
    Player player = getPlayer();
    if (player.getInventory().firstEmpty() == -1) {
      player.getLocation().getWorld().dropItem(player.getLocation(), item);
    } else {
      player.getInventory().addItem(item);
    }
  }

  //<editor-fold> Player interface

  @Override
  public long getFirstPlayed() {
    return getPlayer().getFirstPlayed();
  }

  /**
   * @deprecated
   */
  @Override
  public long getLastPlayed() {
    return getPlayer().getLastPlayed();
  }

  @Override
  public boolean hasPlayedBefore() {
    return getPlayer().hasPlayedBefore();
  }

  @Override
  public @UnmodifiableView @NotNull Iterable<? extends BossBar> activeBossBars() {
    return getPlayer().activeBossBars();
  }

  @Override
  public @NotNull Component displayName() {
    return getPlayer().displayName();
  }

  @Override
  public void displayName(@Nullable Component component) {
    getPlayer().displayName(component);
  }

  @Override
  public boolean isOnline() {
    return getPlayer().isOnline();
  }

  @Override
  public boolean isConnected() {
    return getPlayer().isConnected();
  }

  @Override
  public double getEyeHeight() {
    return getPlayer().getEyeHeight();
  }

  @Override
  public double getEyeHeight(boolean b) {
    return getPlayer().getEyeHeight(b);
  }

  @Override
  public @NotNull Location getEyeLocation() {
    return getPlayer().getEyeLocation();
  }

  @Override
  public @NotNull List<Block> getLineOfSight(@Nullable Set<Material> set, int i) {
    return getPlayer().getLineOfSight(set, i);
  }

  @Override
  public @NotNull Block getTargetBlock(@Nullable Set<Material> set, int i) {
    return getPlayer().getTargetBlock(set, i);
  }

  /**
   * @param i
   * @param fluidMode
   * @deprecated
   */
  @SuppressWarnings("removal")
  @Override
  public @Nullable Block getTargetBlock(int i, TargetBlockInfo.FluidMode fluidMode) {
    return getPlayer().getTargetBlock(i, fluidMode);
  }

  /**
   * @param i
   * @param fluidMode
   * @deprecated
   */
  @SuppressWarnings("removal")
  @Override
  public @Nullable BlockFace getTargetBlockFace(int i, TargetBlockInfo.@NotNull FluidMode fluidMode) {
    return getPlayer().getTargetBlockFace(i, fluidMode);
  }

  @Override
  public @Nullable BlockFace getTargetBlockFace(int i, @NotNull FluidCollisionMode fluidCollisionMode) {
    return getPlayer().getTargetBlockFace(i, fluidCollisionMode);
  }

  /**
   * @param i
   * @param fluidMode
   * @deprecated
   */
  @SuppressWarnings("removal")
  @Override
  public @Nullable TargetBlockInfo getTargetBlockInfo(int i, TargetBlockInfo.@NotNull FluidMode fluidMode) {
    return getPlayer().getTargetBlockInfo(i, fluidMode);
  }

  @Override
  public @Nullable Entity getTargetEntity(int i, boolean b) {
    return getPlayer().getTargetEntity(i, b);
  }

  /**
   * @param i
   * @param b
   * @deprecated
   */
  @SuppressWarnings("removal")
  @Override
  public @Nullable TargetEntityInfo getTargetEntityInfo(int i, boolean b) {
    return getPlayer().getTargetEntityInfo(i, b);
  }

  @Override
  public @Nullable RayTraceResult rayTraceEntities(int i, boolean b) {
    return getPlayer().rayTraceEntities(i, b);
  }

  @Override
  public @NotNull List<Block> getLastTwoTargetBlocks(@Nullable Set<Material> set, int i) {
    return getPlayer().getLastTwoTargetBlocks(set, i);
  }

  @Override
  public @Nullable Block getTargetBlockExact(int i) {
    return getPlayer().getTargetBlockExact(i);
  }

  @Override
  public @Nullable Block getTargetBlockExact(int i, @NotNull FluidCollisionMode fluidCollisionMode) {
    return getPlayer().getTargetBlockExact(i, fluidCollisionMode);
  }

  @Override
  public @Nullable RayTraceResult rayTraceBlocks(double v) {
    return getPlayer().rayTraceBlocks(v);
  }

  @Override
  public @Nullable RayTraceResult rayTraceBlocks(double v, @NotNull FluidCollisionMode fluidCollisionMode) {
    return getPlayer().rayTraceBlocks(v, fluidCollisionMode);
  }

  @Override
  public int getRemainingAir() {
    return getPlayer().getRemainingAir();
  }

  @Override
  public void setRemainingAir(int i) {
    getPlayer().setRemainingAir(i);
  }

  @Override
  public int getMaximumAir() {
    return getPlayer().getMaximumAir();
  }

  @Override
  public void setMaximumAir(int i) {
    getPlayer().setMaximumAir(i);
  }

  @Override
  public int getArrowCooldown() {
    return getPlayer().getArrowCooldown();
  }

  @Override
  public void setArrowCooldown(int i) {
    getPlayer().setArrowCooldown(i);
  }

  @Override
  public int getArrowsInBody() {
    return getPlayer().getArrowsInBody();
  }

  @Override
  public void setArrowsInBody(int i, boolean b) {
    getPlayer().setArrowsInBody(i, b);
  }

  @Override
  public int getBeeStingerCooldown() {
    return getPlayer().getBeeStingerCooldown();
  }

  @Override
  public void setBeeStingerCooldown(int i) {
    getPlayer().setBeeStingerCooldown(i);
  }

  @Override
  public int getBeeStingersInBody() {
    return getPlayer().getBeeStingersInBody();
  }

  @Override
  public void setBeeStingersInBody(int i) {
    getPlayer().setBeeStingersInBody(i);
  }

  @Override
  public int getMaximumNoDamageTicks() {
    return getPlayer().getMaximumNoDamageTicks();
  }

  @Override
  public void setMaximumNoDamageTicks(int i) {
    getPlayer().setMaximumNoDamageTicks(i);
  }

  @Override
  public double getLastDamage() {
    return getPlayer().getLastDamage();
  }

  @Override
  public void setLastDamage(double v) {
    getPlayer().setLastDamage(v);
  }

  @Override
  public int getNoDamageTicks() {
    return getPlayer().getNoDamageTicks();
  }

  @Override
  public void setNoDamageTicks(int i) {
    getPlayer().setNoDamageTicks(i);
  }

  @Override
  public int getNoActionTicks() {
    return getPlayer().getNoActionTicks();
  }

  @Override
  public void setNoActionTicks(int i) {
    getPlayer().setNoActionTicks(i);
  }

  @Override
  public @Nullable Player getKiller() {
    return getPlayer().getKiller();
  }

  @Override
  public void setKiller(@Nullable Player player) {
    getPlayer().setKiller(player);
  }

  @Override
  public boolean addPotionEffect(@NotNull PotionEffect potionEffect) {
    return getPlayer().addPotionEffect(potionEffect);
  }

  /**
   * @param potionEffect
   * @param b
   * @deprecated
   */
  @Override
  public boolean addPotionEffect(@NotNull PotionEffect potionEffect, boolean b) {
    return getPlayer().addPotionEffect(potionEffect, b);
  }

  @Override
  public boolean addPotionEffects(@NotNull Collection<PotionEffect> collection) {
    return getPlayer().addPotionEffects(collection);
  }

  @Override
  public boolean hasPotionEffect(@NotNull PotionEffectType potionEffectType) {
    return getPlayer().hasPotionEffect(potionEffectType);
  }

  @Override
  public @Nullable PotionEffect getPotionEffect(@NotNull PotionEffectType potionEffectType) {
    return getPlayer().getPotionEffect(potionEffectType);
  }

  @Override
  public void removePotionEffect(@NotNull PotionEffectType potionEffectType) {
    getPlayer().removePotionEffect(potionEffectType);
  }

  @Override
  public @NotNull Collection<PotionEffect> getActivePotionEffects() {
    return getPlayer().getActivePotionEffects();
  }

  @Override
  public boolean clearActivePotionEffects() {
    return getPlayer().clearActivePotionEffects();
  }

  @Override
  public boolean hasLineOfSight(@NotNull Entity entity) {
    return getPlayer().hasLineOfSight(entity);
  }

  @Override
  public boolean hasLineOfSight(@NotNull Location location) {
    return getPlayer().hasLineOfSight(location);
  }

  @Override
  public boolean getRemoveWhenFarAway() {
    return getPlayer().getRemoveWhenFarAway();
  }

  @Override
  public void setRemoveWhenFarAway(boolean b) {
    getPlayer().setRemoveWhenFarAway(b);
  }

  @Override
  public @NotNull EntityEquipment getEquipment() {
    return getPlayer().getEquipment();
  }

  @Override
  public void setCanPickupItems(boolean b) {
    getPlayer().setCanPickupItems(b);
  }

  @Override
  public boolean getCanPickupItems() {
    return getPlayer().getCanPickupItems();
  }

  @Override
  public boolean isLeashed() {
    return getPlayer().isLeashed();
  }

  @Override
  public @NotNull Entity getLeashHolder() throws IllegalStateException {
    return getPlayer().getLeashHolder();
  }

  @Override
  public boolean setLeashHolder(@Nullable Entity entity) {
    return getPlayer().setLeashHolder(entity);
  }

  @Override
  public boolean isGliding() {
    return getPlayer().isGliding();
  }

  @Override
  public void setGliding(boolean b) {
    getPlayer().setGliding(b);
  }

  @Override
  public boolean isSwimming() {
    return getPlayer().isSwimming();
  }

  /**
   * @param b
   * @deprecated
   */
  @Override
  public void setSwimming(boolean b) {
    getPlayer().setSwimming(b);
  }

  @Override
  public boolean isRiptiding() {
    return getPlayer().isRiptiding();
  }

  @Override
  public boolean isSleeping() {
    return getPlayer().isSleeping();
  }

  @Override
  public boolean isClimbing() {
    return getPlayer().isClimbing();
  }

  @Override
  public void setAI(boolean b) {
    getPlayer().setAI(b);
  }

  @Override
  public boolean hasAI() {
    return getPlayer().hasAI();
  }

  @Override
  public void attack(@NotNull Entity entity) {
    getPlayer().attack(entity);
  }

  @Override
  public void swingMainHand() {
    getPlayer().swingMainHand();
  }

  @Override
  public void swingOffHand() {
    getPlayer().swingOffHand();
  }

  @Override
  public void playHurtAnimation(float v) {
    getPlayer().playHurtAnimation(v);
  }

  @Override
  public void setCollidable(boolean b) {
    getPlayer().setCollidable(b);
  }

  @Override
  public boolean isCollidable() {
    return getPlayer().isCollidable();
  }

  @Override
  public @NotNull Set<UUID> getCollidableExemptions() {
    return getPlayer().getCollidableExemptions();
  }

  @Override
  public <T> @Nullable T getMemory(@NotNull MemoryKey<T> memoryKey) {
    return getPlayer().getMemory(memoryKey);
  }

  @Override
  public <T> void setMemory(@NotNull MemoryKey<T> memoryKey, @Nullable T t) {
    getPlayer().setMemory(memoryKey, t);
  }

  @Override
  public @Nullable Sound getHurtSound() {
    return getPlayer().getHurtSound();
  }

  @Override
  public @Nullable Sound getDeathSound() {
    return getPlayer().getDeathSound();
  }

  @Override
  public @NotNull Sound getFallDamageSound(int i) {
    return getPlayer().getFallDamageSound(i);
  }

  @Override
  public @NotNull Sound getFallDamageSoundSmall() {
    return getPlayer().getFallDamageSoundSmall();
  }

  @Override
  public @NotNull Sound getFallDamageSoundBig() {
    return getPlayer().getFallDamageSoundBig();
  }

  @Override
  public @NotNull Sound getDrinkingSound(@NotNull ItemStack itemStack) {
    return getPlayer().getDrinkingSound(itemStack);
  }

  @Override
  public @NotNull Sound getEatingSound(@NotNull ItemStack itemStack) {
    return getPlayer().getEatingSound(itemStack);
  }

  @Override
  public boolean canBreatheUnderwater() {
    return getPlayer().canBreatheUnderwater();
  }

  @Override
  public @NotNull EntityCategory getCategory() {
    return getPlayer().getCategory();
  }

  @Override
  public void setInvisible(boolean b) {
    getPlayer().setInvisible(b);
  }

  @Override
  public boolean isInvisible() {
    return false;
  }

  /**
   * @deprecated
   */
  @Override
  public int getArrowsStuck() {
    return 0;
  }

  /**
   * @param i
   * @deprecated
   */
  @Override
  public void setArrowsStuck(int i) {

  }

  @Override
  public int getShieldBlockingDelay() {
    return 0;
  }

  @Override
  public void setShieldBlockingDelay(int i) {

  }

  @Override
  public @NotNull ItemStack getActiveItem() {
    return null;
  }

  @Override
  public void clearActiveItem() {

  }

  @Override
  public float getSidewaysMovement() {
    return 0;
  }

  @Override
  public float getUpwardsMovement() {
    return 0;
  }

  @Override
  public float getForwardsMovement() {
    return 0;
  }

  @Override
  public int getItemUseRemainingTime() {
    return 0;
  }

  @Override
  public int getHandRaisedTime() {
    return 0;
  }

  @Override
  public @NotNull String getName() {
    return getPlayer().getName();
  }

  @Override
  public @NotNull PlayerInventory getInventory() {
    return getPlayer().getInventory();
  }

  @Override
  public @NotNull Inventory getEnderChest() {
    return getPlayer().getEnderChest();
  }

  @Override
  public @NotNull MainHand getMainHand() {
    return getPlayer().getMainHand();
  }

  @Override
  public boolean setWindowProperty(InventoryView.@NotNull Property property, int i) {
    return false;
  }

  @Override
  public int getEnchantmentSeed() {
    return 0;
  }

  @Override
  public void setEnchantmentSeed(int i) {

  }

  @Override
  public @NotNull InventoryView getOpenInventory() {
    return null;
  }

  @Override
  public @Nullable InventoryView openInventory(@NotNull Inventory inventory) {
    return null;
  }

  @Override
  public @Nullable InventoryView openWorkbench(@Nullable Location location, boolean b) {
    return null;
  }

  @Override
  public @Nullable InventoryView openEnchanting(@Nullable Location location, boolean b) {
    return null;
  }

  @Override
  public void openInventory(@NotNull InventoryView inventoryView) {

  }

  @Override
  public @Nullable InventoryView openMerchant(@NotNull Villager villager, boolean b) {
    return null;
  }

  @Override
  public @Nullable InventoryView openMerchant(@NotNull Merchant merchant, boolean b) {
    return null;
  }

  @Override
  public @Nullable InventoryView openAnvil(@Nullable Location location, boolean b) {
    return null;
  }

  @Override
  public @Nullable InventoryView openCartographyTable(@Nullable Location location, boolean b) {
    return null;
  }

  @Override
  public @Nullable InventoryView openGrindstone(@Nullable Location location, boolean b) {
    return null;
  }

  @Override
  public @Nullable InventoryView openLoom(@Nullable Location location, boolean b) {
    return null;
  }

  @Override
  public @Nullable InventoryView openSmithingTable(@Nullable Location location, boolean b) {
    return null;
  }

  @Override
  public @Nullable InventoryView openStonecutter(@Nullable Location location, boolean b) {
    return null;
  }

  @Override
  public void closeInventory() {

  }

  @Override
  public void closeInventory(InventoryCloseEvent.@NotNull Reason reason) {

  }

  /**
   * @deprecated
   */
  @Override
  public @NotNull ItemStack getItemInHand() {
    return null;
  }

  /**
   * @param itemStack
   * @deprecated
   */
  @Override
  public void setItemInHand(@Nullable ItemStack itemStack) {

  }

  @Override
  public @NotNull ItemStack getItemOnCursor() {
    return null;
  }

  @Override
  public void setItemOnCursor(@Nullable ItemStack itemStack) {

  }

  @Override
  public boolean hasCooldown(@NotNull Material material) {
    return false;
  }

  @Override
  public int getCooldown(@NotNull Material material) {
    return 0;
  }

  @Override
  public void setCooldown(@NotNull Material material, int i) {

  }

  @Override
  public void setHurtDirection(float v) {

  }

  @Override
  public void knockback(double v, double v1, double v2) {

  }

  @Override
  public void broadcastSlotBreak(@NotNull EquipmentSlot equipmentSlot) {

  }

  @Override
  public void broadcastSlotBreak(@NotNull EquipmentSlot equipmentSlot, @NotNull Collection<Player> collection) {

  }

  @Override
  public @NotNull ItemStack damageItemStack(@NotNull ItemStack itemStack, int i) {
    return null;
  }

  @Override
  public void damageItemStack(@NotNull EquipmentSlot equipmentSlot, int i) {

  }

  @Override
  public float getBodyYaw() {
    return 0;
  }

  @Override
  public void setBodyYaw(float v) {

  }

  @Override
  public boolean isDeeplySleeping() {
    return false;
  }

  @Override
  public int getSleepTicks() {
    return 0;
  }

  @Override
  public @Nullable Location getPotentialBedLocation() {
    return null;
  }

  @Override
  public @Nullable FishHook getFishHook() {
    return null;
  }

  @Override
  public boolean sleep(@NotNull Location location, boolean b) {
    return false;
  }

  @Override
  public void wakeup(boolean b) {

  }

  @Override
  public @NotNull Location getBedLocation() {
    return null;
  }

  @Override
  public @NotNull GameMode getGameMode() {
    return null;
  }

  @Override
  public void setGameMode(@NotNull GameMode gameMode) {

  }

  @Override
  public boolean isBlocking() {
    return false;
  }

  @Override
  public boolean isHandRaised() {
    return false;
  }

  @Override
  public @NotNull EquipmentSlot getHandRaised() {
    return null;
  }

  @Override
  public boolean isJumping() {
    return false;
  }

  @Override
  public void setJumping(boolean b) {

  }

  @Override
  public void playPickupItemAnimation(@NotNull Item item, int i) {

  }

  @Override
  public float getHurtDirection() {
    return 0;
  }

  /**
   * @deprecated
   */
  @Override
  public @Nullable ItemStack getItemInUse() {
    return null;
  }

  @Override
  public int getExpToLevel() {
    return 0;
  }

  @Override
  public @Nullable Entity releaseLeftShoulderEntity() {
    return null;
  }

  @Override
  public @Nullable Entity releaseRightShoulderEntity() {
    return null;
  }

  @Override
  public float getAttackCooldown() {
    return 0;
  }

  @Override
  public boolean discoverRecipe(@NotNull NamespacedKey namespacedKey) {
    return false;
  }

  @Override
  public int discoverRecipes(@NotNull Collection<NamespacedKey> collection) {
    return 0;
  }

  @Override
  public boolean undiscoverRecipe(@NotNull NamespacedKey namespacedKey) {
    return false;
  }

  @Override
  public int undiscoverRecipes(@NotNull Collection<NamespacedKey> collection) {
    return 0;
  }

  @Override
  public boolean hasDiscoveredRecipe(@NotNull NamespacedKey namespacedKey) {
    return false;
  }

  @Override
  public @NotNull Set<NamespacedKey> getDiscoveredRecipes() {
    return null;
  }

  /**
   * @deprecated
   */
  @Override
  public @Nullable Entity getShoulderEntityLeft() {
    return null;
  }

  /**
   * @param entity
   * @deprecated
   */
  @Override
  public void setShoulderEntityLeft(@Nullable Entity entity) {

  }

  /**
   * @deprecated
   */
  @Override
  public @Nullable Entity getShoulderEntityRight() {
    return null;
  }

  /**
   * @param entity
   * @deprecated
   */
  @Override
  public void setShoulderEntityRight(@Nullable Entity entity) {

  }

  /**
   * @deprecated
   */
  @Override
  public @NotNull String getDisplayName() {
    return null;
  }

  /**
   * @param s
   * @deprecated
   */
  @Override
  public void setDisplayName(@Nullable String s) {

  }

  @Override
  public void playerListName(@Nullable Component component) {

  }

  @Override
  public @NotNull Component playerListName() {
    return null;
  }

  @Override
  public @Nullable Component playerListHeader() {
    return null;
  }

  @Override
  public @Nullable Component playerListFooter() {
    return null;
  }

  /**
   * @deprecated
   */
  @Override
  public @NotNull String getPlayerListName() {
    return null;
  }

  /**
   * @param s
   * @deprecated
   */
  @Override
  public void setPlayerListName(@Nullable String s) {

  }

  /**
   * @deprecated
   */
  @Override
  public @Nullable String getPlayerListHeader() {
    return null;
  }

  /**
   * @deprecated
   */
  @Override
  public @Nullable String getPlayerListFooter() {
    return null;
  }

  /**
   * @param s
   * @deprecated
   */
  @Override
  public void setPlayerListHeader(@Nullable String s) {

  }

  /**
   * @param s
   * @deprecated
   */
  @Override
  public void setPlayerListFooter(@Nullable String s) {

  }

  /**
   * @param s
   * @param s1
   * @deprecated
   */
  @Override
  public void setPlayerListHeaderFooter(@Nullable String s, @Nullable String s1) {

  }

  @Override
  public void setCompassTarget(@NotNull Location location) {

  }

  @Override
  public @NotNull Location getCompassTarget() {
    return null;
  }

  @Override
  public @Nullable InetSocketAddress getAddress() {
    return null;
  }

  @Override
  public int getProtocolVersion() {
    return 0;
  }

  @Override
  public @Nullable InetSocketAddress getVirtualHost() {
    return null;
  }

  @Override
  public boolean isConversing() {
    return false;
  }

  @Override
  public void acceptConversationInput(@NotNull String s) {

  }

  @Override
  public boolean beginConversation(@NotNull Conversation conversation) {
    return false;
  }

  @Override
  public void abandonConversation(@NotNull Conversation conversation) {

  }

  @Override
  public void abandonConversation(@NotNull Conversation conversation, @NotNull ConversationAbandonedEvent conversationAbandonedEvent) {

  }

  @Override
  public void sendRawMessage(@NotNull String s) {

  }

  /**
   * @param uuid
   * @param s
   * @deprecated
   */
  @Override
  public void sendRawMessage(@Nullable UUID uuid, @NotNull String s) {
    getPlayer().sendRawMessage(uuid, s);
  }

  /**
   * @param s
   * @deprecated
   */
  @Override
  public void kickPlayer(@Nullable String s) {
    getPlayer().kickPlayer(s);
  }

  @Override
  public void kick() {
    getPlayer().kick();
  }

  @Override
  public void kick(@Nullable Component component) {
    getPlayer().kick(component);
  }

  @Override
  public void kick(@Nullable Component component, PlayerKickEvent.@NotNull Cause cause) {
    getPlayer().kick(component, cause);
  }

  @Override
  public <E extends BanEntry<? super PlayerProfile>> @Nullable E ban(@Nullable String s, @Nullable Date date, @Nullable String s1, boolean b) {
    return null;
  }

  @Override
  public <E extends BanEntry<? super PlayerProfile>> @Nullable E ban(@Nullable String s, @Nullable Instant instant, @Nullable String s1, boolean b) {
    return null;
  }

  @Override
  public <E extends BanEntry<? super PlayerProfile>> @Nullable E ban(@Nullable String s, @Nullable Duration duration, @Nullable String s1, boolean b) {
    return null;
  }

  @Override
  public @Nullable BanEntry<InetAddress> banIp(@Nullable String s, @Nullable Date date, @Nullable String s1, boolean b) {
    return null;
  }

  @Override
  public @Nullable BanEntry<InetAddress> banIp(@Nullable String s, @Nullable Instant instant, @Nullable String s1, boolean b) {
    return null;
  }

  @Override
  public @Nullable BanEntry<InetAddress> banIp(@Nullable String s, @Nullable Duration duration, @Nullable String s1, boolean b) {
    return null;
  }

  @Override
  public void chat(@NotNull String s) {
    getPlayer().chat(s);
  }

  @Override
  public boolean performCommand(@NotNull String s) {
    return getPlayer().performCommand(s);
  }

  @Override
  public @NotNull Location getLocation() {
    return getPlayer().getLocation();
  }

  @Override
  public @Nullable Location getLocation(@Nullable Location location) {
    return getPlayer().getLocation(location);
  }

  @Override
  public void setVelocity(@NotNull Vector vector) {
    getPlayer().setVelocity(vector);
  }

  @Override
  public @NotNull Vector getVelocity() {
    return getPlayer().getVelocity();
  }

  @Override
  public double getHeight() {
    return getPlayer().getHeight();
  }

  @Override
  public double getWidth() {
    return getPlayer().getWidth();
  }

  @Override
  public @NotNull BoundingBox getBoundingBox() {
    return getPlayer().getBoundingBox();
  }

  /**
   * @deprecated
   */
  @Override
  public boolean isOnGround() {
    return getPlayer().isOnGround();
  }

  @Override
  public boolean isInWater() {
    return getPlayer().isInWater();
  }

  @Override
  public @NotNull World getWorld() {
    return getPlayer().getWorld();
  }

  @Override
  public boolean isSneaking() {
    return getPlayer().isSneaking();
  }

  @Override
  public void setSneaking(boolean b) {
    getPlayer().setSneaking(b);
  }

  @Override
  public void setPose(@NotNull Pose pose, boolean b) {
    getPlayer().setPose(pose, b);
  }

  @Override
  public boolean hasFixedPose() {
    return getPlayer().hasFixedPose();
  }

  @Override
  public @NotNull SpawnCategory getSpawnCategory() {
    return getPlayer().getSpawnCategory();
  }

  @Override
  public boolean isInWorld() {
    return getPlayer().isInWorld();
  }

  @Override
  public @Nullable EntitySnapshot createSnapshot() {
    return null;
  }

  @Override
  public @NotNull Entity copy() {
    return getPlayer().copy();
  }

  @Override
  public @NotNull Entity copy(@NotNull Location location) {
    return getPlayer().copy(location);
  }

  @Override
  public boolean isSprinting() {
    return getPlayer().isSprinting();
  }

  @Override
  public void setSprinting(boolean b) {
    getPlayer().setSprinting(b);
  }

  @Override
  public void saveData() {
    getPlayer().saveData();
  }

  @Override
  public void loadData() {
    getPlayer().loadData();
  }

  @Override
  public void setSleepingIgnored(boolean b) {
    getPlayer().setSleepingIgnored(b);
  }

  @Override
  public boolean isSleepingIgnored() {
    return getPlayer().isSleepingIgnored();
  }

  /**
   * @deprecated
   */
  @Override
  public @Nullable Location getBedSpawnLocation() {
    return getPlayer().getBedSpawnLocation();
  }

  @Override
  public long getLastLogin() {
    return getPlayer().getLastLogin();
  }

  @Override
  public long getLastSeen() {
    return getPlayer().getLastSeen();
  }

  @Override
  public @Nullable Location getRespawnLocation() {
    return getPlayer().getRespawnLocation();
  }

  @Override
  public void incrementStatistic(@NotNull Statistic statistic) throws IllegalArgumentException {

  }

  @Override
  public void decrementStatistic(@NotNull Statistic statistic) throws IllegalArgumentException {

  }

  @Override
  public void incrementStatistic(@NotNull Statistic statistic, int i) throws IllegalArgumentException {

  }

  @Override
  public void decrementStatistic(@NotNull Statistic statistic, int i) throws IllegalArgumentException {

  }

  @Override
  public void setStatistic(@NotNull Statistic statistic, int i) throws IllegalArgumentException {

  }

  @Override
  public int getStatistic(@NotNull Statistic statistic) throws IllegalArgumentException {
    return getPlayer().getStatistic(statistic);
  }

  @Override
  public void incrementStatistic(@NotNull Statistic statistic, @NotNull Material material) throws IllegalArgumentException {
    getPlayer().incrementStatistic(statistic, material);
  }

  @Override
  public void decrementStatistic(@NotNull Statistic statistic, @NotNull Material material) throws IllegalArgumentException {
    getPlayer().decrementStatistic(statistic, material);
  }

  @Override
  public int getStatistic(@NotNull Statistic statistic, @NotNull Material material) throws IllegalArgumentException {
    return getPlayer().getStatistic(statistic, material);
  }

  @Override
  public void incrementStatistic(@NotNull Statistic statistic, @NotNull Material material, int i) throws IllegalArgumentException {
    getPlayer().incrementStatistic(statistic, material, i);
  }

  @Override
  public void decrementStatistic(@NotNull Statistic statistic, @NotNull Material material, int i) throws IllegalArgumentException {
    getPlayer().decrementStatistic(statistic, material, i);
  }

  @Override
  public void setStatistic(@NotNull Statistic statistic, @NotNull Material material, int i) throws IllegalArgumentException {
    getPlayer().setStatistic(statistic, material, i);
  }

  @Override
  public void incrementStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType) throws IllegalArgumentException {
    getPlayer().incrementStatistic(statistic, entityType);
  }

  @Override
  public void decrementStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType) throws IllegalArgumentException {
    getPlayer().decrementStatistic(statistic, entityType);
  }

  @Override
  public int getStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType) throws IllegalArgumentException {
    return getPlayer().getStatistic(statistic, entityType);
  }

  @Override
  public void incrementStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType, int i) throws IllegalArgumentException {
    getPlayer().incrementStatistic(statistic, entityType, i);
  }

  @Override
  public void decrementStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType, int i) {
    getPlayer().decrementStatistic(statistic, entityType, i);
  }

  @Override
  public void setStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType, int i) {
    getPlayer().setStatistic(statistic, entityType, i);
  }

  /**
   * @param location
   * @deprecated
   */
  @Override
  public void setBedSpawnLocation(@Nullable Location location) {
    getPlayer().setBedSpawnLocation(location);
  }

  @Override
  public void setRespawnLocation(@Nullable Location location) {
    getPlayer().setRespawnLocation(location);
  }

  /**
   * @param location
   * @param b
   * @deprecated
   */
  @Override
  public void setBedSpawnLocation(@Nullable Location location, boolean b) {
    getPlayer().setBedSpawnLocation(location, b);
  }

  @Override
  public void setRespawnLocation(@Nullable Location location, boolean b) {
    getPlayer().setRespawnLocation(location, b);
  }

  /**
   * @param location
   * @param b
   * @param b1
   * @deprecated
   */
  @Override
  public void playNote(@NotNull Location location, byte b, byte b1) {
    getPlayer().playNote(location, b, b1);
  }

  @Override
  public void playNote(@NotNull Location location, @NotNull Instrument instrument, @NotNull Note note) {
    getPlayer().playNote(location, instrument, note);
  }

  @Override
  public void playSound(@NotNull Location location, @NotNull Sound sound, float v, float v1) {
    getPlayer().playSound(location, sound, v, v1);
  }

  @Override
  public void playSound(@NotNull Location location, @NotNull String s, float v, float v1) {
    getPlayer().playSound(location, s, v, v1);
  }

  @Override
  public void playSound(@NotNull Location location, @NotNull Sound sound, @NotNull SoundCategory soundCategory, float v, float v1) {
    getPlayer().playSound(location, sound, soundCategory, v, v1);
  }

  @Override
  public void playSound(@NotNull Location location, @NotNull String s, @NotNull SoundCategory soundCategory, float v, float v1) {
    getPlayer().playSound(location, s, soundCategory, v, v1);
  }

  @Override
  public void playSound(@NotNull Location location, @NotNull Sound sound, @NotNull SoundCategory soundCategory, float v, float v1, long l) {
    getPlayer().playSound(location, sound, soundCategory, v, v1, l);
  }

  @Override
  public void playSound(@NotNull Location location, @NotNull String s, @NotNull SoundCategory soundCategory, float v, float v1, long l) {
    getPlayer().playSound(location, s, soundCategory, v, v1, l);
  }

  @Override
  public void playSound(@NotNull Entity entity, @NotNull Sound sound, float v, float v1) {
    getPlayer().playSound(entity, sound, v, v1);
  }

  @Override
  public void playSound(@NotNull Entity entity, @NotNull String s, float v, float v1) {
    getPlayer().playSound(entity, s, v, v1);
  }

  @Override
  public void playSound(@NotNull Entity entity, @NotNull Sound sound, @NotNull SoundCategory soundCategory, float v, float v1) {
    getPlayer().playSound(entity, sound, soundCategory, v, v1);
  }

  @Override
  public void playSound(@NotNull Entity entity, @NotNull String s, @NotNull SoundCategory soundCategory, float v, float v1) {
    getPlayer().playSound(entity, s, soundCategory, v, v1);
  }

  @Override
  public void playSound(@NotNull Entity entity, @NotNull Sound sound, @NotNull SoundCategory soundCategory, float v, float v1, long l) {
    getPlayer().playSound(entity, sound, soundCategory, v, v1, l);
  }

  @Override
  public void playSound(@NotNull Entity entity, @NotNull String s, @NotNull SoundCategory soundCategory, float v, float v1, long l) {
    getPlayer().playSound(entity, s, soundCategory, v, v1, l);
  }

  @Override
  public void stopSound(@NotNull Sound sound) {
    getPlayer().stopSound(sound);
  }

  @Override
  public void stopSound(@NotNull String s) {
    getPlayer().stopSound(s);
  }

  @Override
  public void stopSound(@NotNull Sound sound, @Nullable SoundCategory soundCategory) {
    getPlayer().stopSound(sound, soundCategory);
  }

  @Override
  public void stopSound(@NotNull String s, @Nullable SoundCategory soundCategory) {
    getPlayer().stopSound(s, soundCategory);
  }

  @Override
  public void stopSound(@NotNull SoundCategory soundCategory) {
    getPlayer().stopSound(soundCategory);
  }

  @Override
  public void stopAllSounds() {
    getPlayer().stopAllSounds();
  }

  /**
   * @param location
   * @param effect
   * @param i
   * @deprecated
   */
  @Override
  public void playEffect(@NotNull Location location, @NotNull Effect effect, int i) {
    getPlayer().playEffect(location, effect, i);
  }

  @Override
  public <T> void playEffect(@NotNull Location location, @NotNull Effect effect, @Nullable T t) {
    getPlayer().playEffect(location, effect, t);
  }

  @Override
  public boolean breakBlock(@NotNull Block block) {
    return getPlayer().breakBlock(block);
  }

  /**
   * @param location
   * @param material
   * @param b
   * @deprecated
   */
  @Override
  public void sendBlockChange(@NotNull Location location, @NotNull Material material, byte b) {
    getPlayer().sendBlockChange(location, material, b);
  }

  @Override
  public void sendBlockChange(@NotNull Location location, @NotNull BlockData blockData) {
    getPlayer().sendBlockChange(location, blockData);
  }

  @Override
  public void sendBlockChanges(@NotNull Collection<BlockState> collection) {
    getPlayer().sendBlockChanges(collection);
  }

  /**
   * @param collection
   * @param b
   * @deprecated
   */
  @Override
  public void sendBlockChanges(@NotNull Collection<BlockState> collection, boolean b) {
    getPlayer().sendBlockChanges(collection, b);
  }

  @Override
  public void sendBlockDamage(@NotNull Location location, float v) {
    getPlayer().sendBlockDamage(location, v);
  }

  /**
   * Send multiple block changes. This fakes a multi block change packet for each
   * chunk section that a block change occurs. This will not actually change the world in any way.
   *
   * @param blockChanges A map of the positions you want to change to their new block data
   */
  @Override
  public void sendMultiBlockChange(@NotNull Map<? extends Position, BlockData> blockChanges) {

  }

  @Override
  public void sendBlockDamage(@NotNull Location location, float v, @NotNull Entity entity) {
    getPlayer().sendBlockDamage(location, v, entity);
  }

  @Override
  public void sendBlockDamage(@NotNull Location location, float v, int i) {
    getPlayer().sendBlockDamage(location, v, i);
  }

  @Override
  public void sendEquipmentChange(@NotNull LivingEntity livingEntity, @NotNull EquipmentSlot equipmentSlot, @Nullable ItemStack itemStack) {
    getPlayer().sendEquipmentChange(livingEntity, equipmentSlot, itemStack);
  }

  @Override
  public void sendEquipmentChange(@NotNull LivingEntity livingEntity, @NotNull Map<EquipmentSlot, ItemStack> map) {
    getPlayer().sendEquipmentChange(livingEntity, map);
  }

  /**
   * @param location
   * @param list
   * @param dyeColor
   * @param b
   * @deprecated
   */
  @Override
  public void sendSignChange(@NotNull Location location, @Nullable List<? extends Component> list, @NotNull DyeColor dyeColor, boolean b) throws IllegalArgumentException {
    getPlayer().sendSignChange(location, list, dyeColor, b);
  }

  /**
   * @param location
   * @param strings
   * @deprecated
   */
  @Override
  public void sendSignChange(@NotNull Location location, @Nullable String[] strings) throws IllegalArgumentException {
    getPlayer().sendSignChange(location, strings);
  }

  /**
   * @param location
   * @param strings
   * @param dyeColor
   * @deprecated
   */
  @Override
  public void sendSignChange(@NotNull Location location, @Nullable String[] strings, @NotNull DyeColor dyeColor) throws IllegalArgumentException {
    getPlayer().sendSignChange(location, strings, dyeColor);
  }

  /**
   * @param location
   * @param strings
   * @param dyeColor
   * @param b
   * @deprecated
   */
  @Override
  public void sendSignChange(@NotNull Location location, @Nullable String[] strings, @NotNull DyeColor dyeColor, boolean b) throws IllegalArgumentException {
    getPlayer().sendSignChange(location, strings, dyeColor, b);
  }

  @Override
  public void sendBlockUpdate(@NotNull Location location, @NotNull TileState tileState) throws IllegalArgumentException {
    getPlayer().sendBlockUpdate(location, tileState);
  }

  @Override
  public void sendMap(@NotNull MapView mapView) {
    getPlayer().sendMap(mapView);
  }

  @Override
  public void showWinScreen() {
    getPlayer().showWinScreen();
  }

  @Override
  public boolean hasSeenWinScreen() {
    return getPlayer().hasSeenWinScreen();
  }

  @Override
  public void setHasSeenWinScreen(boolean b) {
    getPlayer().setHasSeenWinScreen(b);
  }

  /**
   * @param s
   * @deprecated
   */
  @Override
  public void sendActionBar(@NotNull String s) {
    getPlayer().sendActionBar(s);
  }

  /**
   * @param c
   * @param s
   * @deprecated
   */
  @Override
  public void sendActionBar(char c, @NotNull String s) {
    getPlayer().sendActionBar(c, s);
  }

  /**
   * @param baseComponents
   * @deprecated
   */
  @Override
  public void sendActionBar(@NotNull BaseComponent... baseComponents) {
    getPlayer().sendActionBar(baseComponents);
  }

  /**
   * @param baseComponents
   * @param baseComponents1
   * @deprecated
   */
  @Override
  public void setPlayerListHeaderFooter(@Nullable BaseComponent[] baseComponents, @Nullable BaseComponent[] baseComponents1) {
    getPlayer().setPlayerListHeaderFooter(baseComponents, baseComponents1);
  }

  /**
   * @param baseComponent
   * @param baseComponent1
   * @deprecated
   */
  @Override
  public void setPlayerListHeaderFooter(@Nullable BaseComponent baseComponent, @Nullable BaseComponent baseComponent1) {
    getPlayer().setPlayerListHeaderFooter(baseComponent, baseComponent1);
  }

  /**
   * @param i
   * @param i1
   * @param i2
   * @deprecated
   */
  @Override
  public void setTitleTimes(int i, int i1, int i2) {
    getPlayer().setTitleTimes(i, i1, i2);
  }

  /**
   * @param baseComponents
   * @deprecated
   */
  @Override
  public void setSubtitle(BaseComponent[] baseComponents) {
    getPlayer().setSubtitle(baseComponents);
  }

  /**
   * @param baseComponent
   * @deprecated
   */
  @Override
  public void setSubtitle(BaseComponent baseComponent) {
    getPlayer().setSubtitle(baseComponent);
  }

  /**
   * @param baseComponents
   * @deprecated
   */
  @Override
  public void showTitle(@Nullable BaseComponent[] baseComponents) {
    getPlayer().showTitle(baseComponents);
  }

  /**
   * @param baseComponent
   * @deprecated
   */
  @Override
  public void showTitle(@Nullable BaseComponent baseComponent) {
    getPlayer().showTitle(baseComponent);
  }

  /**
   * @param baseComponents
   * @param baseComponents1
   * @param i
   * @param i1
   * @param i2
   * @deprecated
   */
  @Override
  public void showTitle(@Nullable BaseComponent[] baseComponents, @Nullable BaseComponent[] baseComponents1, int i, int i1, int i2) {
    getPlayer().showTitle(baseComponents, baseComponents1, i, i1, i2);
  }

  /**
   * @param baseComponent
   * @param baseComponent1
   * @param i
   * @param i1
   * @param i2
   * @deprecated
   */
  @Override
  public void showTitle(@Nullable BaseComponent baseComponent, @Nullable BaseComponent baseComponent1, int i, int i1, int i2) {
    getPlayer().showTitle(baseComponent, baseComponent1, i, i1, i2);
  }

  /**
   * @param title
   * @deprecated
   */
  @Override
  public void sendTitle(@NotNull Title title) {
    getPlayer().sendTitle(title);
  }

  /**
   * @param title
   * @deprecated
   */
  @Override
  public void updateTitle(@NotNull Title title) {
    getPlayer().updateTitle(title);
  }

  /**
   * @deprecated
   */
  @Override
  public void hideTitle() {
    getPlayer().hideTitle();
  }

  @Override
  public void sendHurtAnimation(float v) {
    getPlayer().sendHurtAnimation(v);
  }

  @Override
  public void addCustomChatCompletions(@NotNull Collection<String> collection) {
    getPlayer().addCustomChatCompletions(collection);
  }

  @Override
  public void removeCustomChatCompletions(@NotNull Collection<String> collection) {
    getPlayer().removeCustomChatCompletions(collection);
  }

  @Override
  public void setCustomChatCompletions(@NotNull Collection<String> collection) {
    getPlayer().setCustomChatCompletions(collection);
  }

  @Override
  public void updateInventory() {
    getPlayer().updateInventory();
  }

  @Override
  public @Nullable GameMode getPreviousGameMode() {
    return getPlayer().getPreviousGameMode();
  }

  @Override
  public void setPlayerTime(long l, boolean b) {
    getPlayer().setPlayerTime(l, b);
  }

  @Override
  public long getPlayerTime() {
    return getPlayer().getPlayerTime();
  }

  @Override
  public long getPlayerTimeOffset() {
    return getPlayer().getPlayerTimeOffset();
  }

  @Override
  public boolean isPlayerTimeRelative() {
    return getPlayer().isPlayerTimeRelative();
  }

  @Override
  public void resetPlayerTime() {
    getPlayer().resetPlayerTime();
  }

  @Override
  public void setPlayerWeather(@NotNull WeatherType weatherType) {
    getPlayer().setPlayerWeather(weatherType);
  }

  @Override
  public @Nullable WeatherType getPlayerWeather() {
    return getPlayer().getPlayerWeather();
  }

  @Override
  public void resetPlayerWeather() {
    getPlayer().resetPlayerWeather();
  }

  @Override
  public int getExpCooldown() {
    return getPlayer().getExpCooldown();
  }

  @Override
  public void setExpCooldown(int i) {
    getPlayer().setExpCooldown(i);
  }

  @Override
  public void giveExp(int i, boolean b) {
    getPlayer().giveExp(i, b);
  }

  @Override
  public int applyMending(int i) {
    return getPlayer().applyMending(i);
  }

  @Override
  public void giveExpLevels(int i) {
    getPlayer().giveExpLevels(i);
  }

  @Override
  public float getExp() {
    return getPlayer().getExp();
  }

  @Override
  public void setExp(float v) {
    getPlayer().setExp(v);
  }

  @Override
  public int getLevel() {
    return getPlayer().getLevel();
  }

  @Override
  public void setLevel(int i) {
    getPlayer().setLevel(i);
  }

  @Override
  public int getTotalExperience() {
    return getPlayer().getTotalExperience();
  }

  @Override
  public void setTotalExperience(int i) {
    getPlayer().setTotalExperience(i);
  }

  @Override
  public @Range(from = 0L, to = 2147483647L) int calculateTotalExperiencePoints() {
    return getPlayer().calculateTotalExperiencePoints();
  }

  @Override
  public void setExperienceLevelAndProgress(@Range(from = 0L, to = 2147483647L) int i) {
    getPlayer().setExperienceLevelAndProgress(i);
  }

  @Override
  public int getExperiencePointsNeededForNextLevel() {
    return getPlayer().getExperiencePointsNeededForNextLevel();
  }

  @Override
  public void sendExperienceChange(float v) {
    getPlayer().sendExperienceChange(v);
  }

  @Override
  public void sendExperienceChange(float v, int i) {
    getPlayer().sendExperienceChange(v, i);
  }

  @Override
  public boolean getAllowFlight() {
    return getPlayer().getAllowFlight();
  }

  @Override
  public void setAllowFlight(boolean b) {
    getPlayer().setAllowFlight(b);
  }

  @Override
  public void setFlyingFallDamage(@NotNull TriState triState) {
    getPlayer().setFlyingFallDamage(triState);
  }

  @Override
  public @NotNull TriState hasFlyingFallDamage() {
    return getPlayer().hasFlyingFallDamage();
  }

  /**
   * @param player
   * @deprecated
   */
  @Override
  public void hidePlayer(@NotNull Player player) {
    getPlayer().hidePlayer(player);
  }

  @Override
  public void hidePlayer(@NotNull Plugin plugin, @NotNull Player player) {
    getPlayer().hidePlayer(plugin, player);
  }

  /**
   * @param player
   * @deprecated
   */
  @Override
  public void showPlayer(@NotNull Player player) {
    getPlayer().showPlayer(player);
  }

  @Override
  public void showPlayer(@NotNull Plugin plugin, @NotNull Player player) {
    getPlayer().showPlayer(plugin, player);
  }

  @Override
  public boolean canSee(@NotNull Player player) {
    return getPlayer().canSee(player);
  }

  @Override
  public void hideEntity(@NotNull Plugin plugin, @NotNull Entity entity) {
    getPlayer().hideEntity(plugin, entity);
  }

  @Override
  public void showEntity(@NotNull Plugin plugin, @NotNull Entity entity) {
    getPlayer().showEntity(plugin, entity);
  }

  @Override
  public boolean canSee(@NotNull Entity entity) {
    return getPlayer().canSee(entity);
  }

  @Override
  public boolean isListed(@NotNull Player player) {
    return getPlayer().isListed(player);
  }

  @Override
  public boolean unlistPlayer(@NotNull Player player) {
    return false;
  }

  @Override
  public boolean listPlayer(@NotNull Player player) {
    return false;
  }

  @Override
  public boolean isFlying() {
    return false;
  }

  @Override
  public void setFlying(boolean b) {

  }

  @Override
  public void setFlySpeed(float v) throws IllegalArgumentException {

  }

  @Override
  public void setWalkSpeed(float v) throws IllegalArgumentException {

  }

  @Override
  public float getFlySpeed() {
    return 0;
  }

  @Override
  public float getWalkSpeed() {
    return 0;
  }

  /**
   * @param s
   * @deprecated
   */
  @Override
  public void setTexturePack(@NotNull String s) {
    getPlayer().setTexturePack(s);
  }

  /**
   * @param s
   * @deprecated
   */
  @Override
  public void setResourcePack(@NotNull String s) {
    getPlayer().setResourcePack(s);
  }

  /**
   * @param s
   * @param bytes
   * @deprecated
   */
  @Override
  public void setResourcePack(@NotNull String s, @Nullable byte[] bytes) {
    getPlayer().setResourcePack(s, bytes);
  }

  /**
   * @param s
   * @param bytes
   * @param s1
   * @deprecated
   */
  @Override
  public void setResourcePack(@NotNull String s, @Nullable byte[] bytes, @Nullable String s1) {
    getPlayer().setResourcePack(s, bytes, s1);
  }

  /**
   * @param s
   * @param bytes
   * @param b
   * @deprecated
   */
  @Override
  public void setResourcePack(@NotNull String s, @Nullable byte[] bytes, boolean b) {
    getPlayer().setResourcePack(s, bytes, b);
  }

  /**
   * @param s
   * @param bytes
   * @param s1
   * @param b
   * @deprecated
   */
  @Override
  public void setResourcePack(@NotNull String s, @Nullable byte[] bytes, @Nullable String s1, boolean b) {
    getPlayer().setResourcePack(s, bytes, s1, b);
  }

  /**
   * @param uuid
   * @param s
   * @param bytes
   * @param s1
   * @param b
   * @deprecated
   */
  @Override
  public void setResourcePack(@NotNull UUID uuid, @NotNull String s, @Nullable byte[] bytes, @Nullable String s1, boolean b) {
    getPlayer().setResourcePack(uuid, s, bytes, s1, b);
  }

  @Override
  public void setResourcePack(@NotNull UUID uuid, @NotNull String s, byte @Nullable [] bytes, @Nullable Component component, boolean b) {
    getPlayer().setResourcePack(uuid, s, bytes, component, b);
  }

  @Override
  public PlayerResourcePackStatusEvent.@Nullable Status getResourcePackStatus() {
    return getPlayer().getResourcePackStatus();
  }

  @Override
  public void removeResourcePack(@NotNull UUID uuid) {
    getPlayer().removeResourcePack(uuid);
  }

  @Override
  public void removeResourcePacks() {
    getPlayer().removeResourcePacks();
  }

  @Override
  public @NotNull Scoreboard getScoreboard() {
    return getPlayer().getScoreboard();
  }

  @Override
  public void setScoreboard(@NotNull Scoreboard scoreboard) throws IllegalArgumentException, IllegalStateException {
    getPlayer().setScoreboard(scoreboard);
  }

  @Override
  public @Nullable WorldBorder getWorldBorder() {
    return getPlayer().getWorldBorder();
  }

  @Override
  public void setWorldBorder(@Nullable WorldBorder worldBorder) {
    getPlayer().setWorldBorder(worldBorder);
  }

  @Override
  public void sendHealthUpdate(double v, int i, float v1) {

  }

  @Override
  public void sendHealthUpdate() {

  }

  @Override
  public boolean isHealthScaled() {
    return false;
  }

  @Override
  public void setHealthScaled(boolean b) {

  }

  @Override
  public void setHealthScale(double v) throws IllegalArgumentException {

  }

  @Override
  public double getHealthScale() {
    return 0;
  }

  @Override
  public @Nullable Entity getSpectatorTarget() {
    return null;
  }

  @Override
  public void setSpectatorTarget(@Nullable Entity entity) {

  }

  /**
   * @param s
   * @param s1
   * @deprecated
   */
  @Override
  public void sendTitle(@Nullable String s, @Nullable String s1) {
    getPlayer().sendTitle(s, s1);
  }

  /**
   * @param s
   * @param s1
   * @param i
   * @param i1
   * @param i2
   * @deprecated
   */
  @Override
  public void sendTitle(@Nullable String s, @Nullable String s1, int i, int i1, int i2) {

  }

  @Override
  public void resetTitle() {

  }

  @Override
  public void spawnParticle(@NotNull Particle particle, @NotNull Location location, int i) {
    getPlayer().spawnParticle(particle, location, i);
  }

  @Override
  public void spawnParticle(@NotNull Particle particle, double v, double v1, double v2, int i) {
    getPlayer().spawnParticle(particle, v, v1, v2, i);
  }

  @Override
  public <T> void spawnParticle(@NotNull Particle particle, @NotNull Location location, int i, @Nullable T t) {
    getPlayer().spawnParticle(particle, location, i, t);
  }

  @Override
  public <T> void spawnParticle(@NotNull Particle particle, double v, double v1, double v2, int i, @Nullable T t) {
    getPlayer().spawnParticle(particle, v, v1, v2, i, t);
  }

  @Override
  public void spawnParticle(@NotNull Particle particle, @NotNull Location location, int i, double v, double v1, double v2) {
    getPlayer().spawnParticle(particle, location, i, v, v1, v2);
  }

  @Override
  public void spawnParticle(@NotNull Particle particle, double v, double v1, double v2, int i, double v3, double v4, double v5) {
    getPlayer().spawnParticle(particle, v, v1, v2, i, v3, v4, v5);
  }

  @Override
  public <T> void spawnParticle(@NotNull Particle particle, @NotNull Location location, int i, double v, double v1, double v2, @Nullable T t) {
    getPlayer().spawnParticle(particle, location, i, v, v1, v2, t);
  }

  @Override
  public <T> void spawnParticle(@NotNull Particle particle, double v, double v1, double v2, int i, double v3, double v4, double v5, @Nullable T t) {
    getPlayer().spawnParticle(particle, v, v1, v2, i, v3, v4, v5, t);
  }

  @Override
  public void spawnParticle(@NotNull Particle particle, @NotNull Location location, int i, double v, double v1, double v2, double v3) {
    getPlayer().spawnParticle(particle, location, i, v, v1, v2, v3);
  }

  @Override
  public void spawnParticle(@NotNull Particle particle, double v, double v1, double v2, int i, double v3, double v4, double v5, double v6) {
    getPlayer().spawnParticle(particle, v, v1, v2, i, v3, v4, v5, v6);
  }

  @Override
  public <T> void spawnParticle(@NotNull Particle particle, @NotNull Location location, int i, double v, double v1, double v2, double v3, @Nullable T t) {
    getPlayer().spawnParticle(particle, location, i, v, v1, v2, v3, t);
  }

  @Override
  public <T> void spawnParticle(@NotNull Particle particle, double v, double v1, double v2, int i, double v3, double v4, double v5, double v6, @Nullable T t) {
    getPlayer().spawnParticle(particle, v, v1, v2, i, v3, v4, v5, v6, t);
  }

  @Override
  public @NotNull AdvancementProgress getAdvancementProgress(@NotNull Advancement advancement) {
    return getPlayer().getAdvancementProgress(advancement);
  }

  @Override
  public int getClientViewDistance() {
    return getPlayer().getClientViewDistance();
  }

  @Override
  public @NotNull Locale locale() {
    return getPlayer().locale();
  }

  @Override
  public int getPing() {
    return getPlayer().getPing();
  }

  /**
   * @deprecated
   */
  @Override
  public @NotNull String getLocale() {
    return getPlayer().getLocale();
  }

  @Override
  public boolean getAffectsSpawning() {
    return false;
  }

  @Override
  public void setAffectsSpawning(boolean b) {

  }

  @Override
  public int getViewDistance() {
    return 0;
  }

  @Override
  public void setViewDistance(int i) {

  }

  @Override
  public int getSimulationDistance() {
    return 0;
  }

  @Override
  public void setSimulationDistance(int i) {

  }

  @Override
  public int getSendViewDistance() {
    return 0;
  }

  @Override
  public void setSendViewDistance(int i) {

  }

  @Override
  public void updateCommands() {
    getPlayer().updateCommands();
  }

  @Override
  public void openBook(@NotNull ItemStack itemStack) {
    getPlayer().openBook(itemStack);
  }

  /**
   * @param sign
   * @deprecated
   */
  @Override
  public void openSign(@NotNull Sign sign) {
    getPlayer().openSign(sign);
  }

  /**
   * Open a Sign for editing by the Player.
   * <p>
   * The Sign must be placed in the same world as the player.
   *
   * @param sign The sign to edit
   * @param side The side to edit
   */
  @Override
  public void openSign(@NotNull Sign sign, @NotNull Side side) {

  }

  @Override
  public boolean dropItem(boolean b) {
    return getPlayer().dropItem(b);
  }

  @Override
  public float getExhaustion() {
    return 0;
  }

  @Override
  public void setExhaustion(float v) {

  }

  @Override
  public float getSaturation() {
    return 0;
  }

  @Override
  public void setSaturation(float v) {

  }

  @Override
  public int getFoodLevel() {
    return 0;
  }

  @Override
  public void setFoodLevel(int i) {

  }

  @Override
  public int getSaturatedRegenRate() {
    return 0;
  }

  @Override
  public void setSaturatedRegenRate(int i) {

  }

  @Override
  public int getUnsaturatedRegenRate() {
    return 0;
  }

  @Override
  public void setUnsaturatedRegenRate(int i) {

  }

  @Override
  public int getStarvationRate() {
    return 0;
  }

  @Override
  public void setStarvationRate(int i) {

  }

  @Override
  public @Nullable Location getLastDeathLocation() {
    return getPlayer().getLastDeathLocation();
  }

  @Override
  public void setLastDeathLocation(@Nullable Location location) {
    getPlayer().setLastDeathLocation(location);
  }

  @Override
  public @Nullable Firework fireworkBoost(@NotNull ItemStack itemStack) {
    return null;
  }

  @Override
  public void showDemoScreen() {
    getPlayer().showDemoScreen();
  }

  @Override
  public boolean isAllowingServerListings() {
    return false;
  }

  @Override
  public @NotNull PlayerProfile getPlayerProfile() {
    return getPlayer().getPlayerProfile();
  }

  @Override
  public boolean isBanned() {
    return getPlayer().isBanned();
  }

  @Override
  public <E extends BanEntry<? super PlayerProfile>> @Nullable E ban(@Nullable String s, @Nullable Date date, @Nullable String s1) {
    return null;
  }

  @Override
  public <E extends BanEntry<? super PlayerProfile>> @Nullable E ban(@Nullable String s, @Nullable Instant instant, @Nullable String s1) {
    return null;
  }

  @Override
  public <E extends BanEntry<? super PlayerProfile>> @Nullable E ban(@Nullable String s, @Nullable Duration duration, @Nullable String s1) {
    return null;
  }

  @Override
  public boolean isWhitelisted() {
    return false;
  }

  @Override
  public void setWhitelisted(boolean b) {

  }

  @Override
  public void setPlayerProfile(@NotNull PlayerProfile playerProfile) {

  }

  @Override
  public float getCooldownPeriod() {
    return 0;
  }

  @Override
  public float getCooledAttackStrength(float v) {
    return 0;
  }

  @Override
  public void resetCooldown() {

  }

  @Override
  public <T> @NotNull T getClientOption(@NotNull ClientOption<T> clientOption) {
    return null;
  }

  @Override
  public void sendOpLevel(byte b) {

  }

  /**
   * @param collection
   * @deprecated
   */
  @Override
  public void addAdditionalChatCompletions(@NotNull Collection<String> collection) {

  }

  /**
   * @param collection
   * @deprecated
   */
  @Override
  public void removeAdditionalChatCompletions(@NotNull Collection<String> collection) {

  }

  @Override
  public @Nullable String getClientBrandName() {
    return getPlayer().getClientBrandName();
  }

  @Override
  public void setRotation(float v, float v1) {

  }

  /**
   * Teleports this entity to the given location.
   *
   * @param location      New location to teleport this entity to
   * @param cause         The cause of this teleportation
   * @param teleportFlags Flags to be used in this teleportation
   * @return <code>true</code> if the teleport was successful
   */
  @Override
  public boolean teleport(@NotNull Location location, PlayerTeleportEvent.@NotNull TeleportCause cause, @NotNull TeleportFlag @NotNull ... teleportFlags) {
    return false;
  }

  /**
   * Causes the player to look towards the given position.
   *
   * @param x            x coordinate
   * @param y            y coordinate
   * @param z            z coordinate
   * @param playerAnchor What part of the player should face the given position
   */
  @Override
  public void lookAt(double x, double y, double z, @NotNull LookAnchor playerAnchor) {

  }

  /**
   * Causes the player to look towards the given entity.
   *
   * @param entity       Entity to look at
   * @param playerAnchor What part of the player should face the entity
   * @param entityAnchor What part of the entity the player should face
   */
  @Override
  public void lookAt(@NotNull Entity entity, @NotNull LookAnchor playerAnchor, @NotNull LookAnchor entityAnchor) {

  }

  @Override
  public boolean teleport(@NotNull Location location) {
    return getPlayer().teleport(location);
  }

  @Override
  public boolean teleport(@NotNull Location location, PlayerTeleportEvent.@NotNull TeleportCause teleportCause) {
    return getPlayer().teleport(location, teleportCause);
  }

  @Override
  public boolean teleport(@NotNull Entity entity) {
    return getPlayer().teleport(entity);
  }

  @Override
  public boolean teleport(@NotNull Entity entity, PlayerTeleportEvent.@NotNull TeleportCause teleportCause) {
    return getPlayer().teleport(entity, teleportCause);
  }

  @Override
  public @NotNull List<Entity> getNearbyEntities(double v, double v1, double v2) {
    return null;
  }

  @Override
  public int getEntityId() {
    return 0;
  }

  @Override
  public int getFireTicks() {
    return 0;
  }

  @Override
  public int getMaxFireTicks() {
    return 0;
  }

  @Override
  public void setFireTicks(int i) {

  }

  @Override
  public void setVisualFire(boolean b) {

  }

  @Override
  public boolean isVisualFire() {
    return false;
  }

  @Override
  public int getFreezeTicks() {
    return 0;
  }

  @Override
  public int getMaxFreezeTicks() {
    return 0;
  }

  @Override
  public void setFreezeTicks(int i) {

  }

  @Override
  public boolean isFrozen() {
    return false;
  }

  @Override
  public boolean isFreezeTickingLocked() {
    return false;
  }

  @Override
  public void lockFreezeTicks(boolean b) {

  }

  @Override
  public void remove() {

  }

  @Override
  public boolean isDead() {
    return false;
  }

  @Override
  public boolean isValid() {
    return false;
  }

  @Override
  public void sendMessage(@NotNull String s) {
    getPlayer().sendMessage(s);
  }

  @Override
  public void sendMessage(@NotNull String... strings) {
    getPlayer().sendMessage(strings);
  }

  /**
   * @param uuid
   * @param s
   * @deprecated
   */
  @Override
  public void sendMessage(@Nullable UUID uuid, @NotNull String s) {
    getPlayer().sendMessage(uuid, s);
  }

  /**
   * @param uuid
   * @param strings
   * @deprecated
   */
  @Override
  public void sendMessage(@Nullable UUID uuid, @NotNull String... strings) {
    getPlayer().sendMessage(uuid, strings);
  }

  @Override
  public @NotNull Server getServer() {
    return getPlayer().getServer();
  }

  @Override
  public boolean isPersistent() {
    return false;
  }

  @Override
  public void setPersistent(boolean b) {

  }

  /**
   * @deprecated
   */
  @Override
  public @Nullable Entity getPassenger() {
    return null;
  }

  /**
   * @param entity
   * @deprecated
   */
  @Override
  public boolean setPassenger(@NotNull Entity entity) {
    return false;
  }

  @Override
  public @NotNull List<Entity> getPassengers() {
    return null;
  }

  @Override
  public boolean addPassenger(@NotNull Entity entity) {
    return false;
  }

  @Override
  public boolean removePassenger(@NotNull Entity entity) {
    return false;
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public boolean eject() {
    return false;
  }

  @Override
  public float getFallDistance() {
    return 0;
  }

  @Override
  public void setFallDistance(float v) {

  }

  @Override
  public void setLastDamageCause(@Nullable EntityDamageEvent entityDamageEvent) {

  }

  @Override
  public @Nullable EntityDamageEvent getLastDamageCause() {
    return null;
  }

  @Override
  public @NotNull UUID getUniqueId() {
    return getPlayer().getUniqueId();
  }

  @Override
  public int getTicksLived() {
    return 0;
  }

  @Override
  public void setTicksLived(int i) {

  }

  @Override
  public void playEffect(@NotNull EntityEffect entityEffect) {
    getPlayer().playEffect(entityEffect);
  }

  @Override
  public @NotNull EntityType getType() {
    return null;
  }

  @Override
  public @NotNull Sound getSwimSound() {
    return null;
  }

  @Override
  public @NotNull Sound getSwimSplashSound() {
    return null;
  }

  @Override
  public @NotNull Sound getSwimHighSpeedSplashSound() {
    return null;
  }

  @Override
  public boolean isInsideVehicle() {
    return false;
  }

  @Override
  public boolean leaveVehicle() {
    return false;
  }

  @Override
  public @Nullable Entity getVehicle() {
    return null;
  }

  @Override
  public void setCustomNameVisible(boolean b) {

  }

  @Override
  public boolean isCustomNameVisible() {
    return false;
  }

  @Override
  public void setVisibleByDefault(boolean b) {

  }

  @Override
  public boolean isVisibleByDefault() {
    return false;
  }

  @Override
  public @NotNull Set<Player> getTrackedBy() {
    return null;
  }

  @Override
  public void setGlowing(boolean b) {

  }

  @Override
  public boolean isGlowing() {
    return false;
  }

  @Override
  public void setInvulnerable(boolean b) {

  }

  @Override
  public boolean isInvulnerable() {
    return false;
  }

  @Override
  public boolean isSilent() {
    return false;
  }

  @Override
  public void setSilent(boolean b) {

  }

  @Override
  public boolean hasGravity() {
    return false;
  }

  @Override
  public void setGravity(boolean b) {

  }

  @Override
  public int getPortalCooldown() {
    return 0;
  }

  @Override
  public void setPortalCooldown(int i) {

  }

  @Override
  public @NotNull Set<String> getScoreboardTags() {
    return null;
  }

  @Override
  public boolean addScoreboardTag(@NotNull String s) {
    return false;
  }

  @Override
  public boolean removeScoreboardTag(@NotNull String s) {
    return false;
  }

  @Override
  public @NotNull PistonMoveReaction getPistonMoveReaction() {
    return null;
  }

  @Override
  public @NotNull BlockFace getFacing() {
    return null;
  }

  @Override
  public @NotNull Pose getPose() {
    return null;
  }

  @Override
  public void showElderGuardian(boolean b) {

  }

  @Override
  public int getWardenWarningCooldown() {
    return 0;
  }

  @Override
  public void setWardenWarningCooldown(int i) {

  }

  @Override
  public int getWardenTimeSinceLastWarning() {
    return 0;
  }

  @Override
  public void setWardenTimeSinceLastWarning(int i) {

  }

  @Override
  public int getWardenWarningLevel() {
    return 0;
  }

  @Override
  public void setWardenWarningLevel(int i) {

  }

  @Override
  public void increaseWardenWarningLevel() {

  }

  @Override
  public @NotNull Duration getIdleDuration() {
    return null;
  }

  @Override
  public void resetIdleDuration() {

  }

  @Override
  public @NotNull Player.Spigot spigot() {
    return getPlayer().spigot();
  }

  @Override
  public @NotNull Component name() {
    return getPlayer().name();
  }

  @Override
  public @NotNull Component teamDisplayName() {
    return null;
  }

  @Override
  public @Nullable Location getOrigin() {
    return null;
  }

  @Override
  public boolean fromMobSpawner() {
    return false;
  }

  @Override
  public CreatureSpawnEvent.@NotNull SpawnReason getEntitySpawnReason() {
    return null;
  }

  @Override
  public boolean isUnderWater() {
    return false;
  }

  @Override
  public boolean isInRain() {
    return false;
  }

  @Override
  public boolean isInBubbleColumn() {
    return false;
  }

  @Override
  public boolean isInWaterOrRain() {
    return false;
  }

  @Override
  public boolean isInWaterOrBubbleColumn() {
    return false;
  }

  @Override
  public boolean isInWaterOrRainOrBubbleColumn() {
    return false;
  }

  @Override
  public boolean isInLava() {
    return false;
  }

  @Override
  public boolean isTicking() {
    return false;
  }

  /**
   * @deprecated
   */
  @Override
  public @NotNull Set<Player> getTrackedPlayers() {
    return null;
  }

  @Override
  public boolean spawnAt(@NotNull Location location, CreatureSpawnEvent.@NotNull SpawnReason spawnReason) {
    return false;
  }

  @Override
  public boolean isInPowderedSnow() {
    return false;
  }

  @Override
  public double getX() {
    return getPlayer().getX();
  }

  @Override
  public double getY() {
    return getPlayer().getY();
  }

  @Override
  public double getZ() {
    return getPlayer().getZ();
  }

  @Override
  public float getPitch() {
    return getPlayer().getPitch();
  }

  @Override
  public float getYaw() {
    return getPlayer().getYaw();
  }

  @Override
  public boolean collidesAt(@NotNull Location location) {
    return false;
  }

  @Override
  public boolean wouldCollideUsing(@NotNull BoundingBox boundingBox) {
    return false;
  }

  /**
   * Returns the task scheduler for this entity. The entity scheduler can be used to schedule tasks
   * that are guaranteed to always execute on the tick thread that owns the entity.
   * <p><b>If you do not need/want to make your plugin run on Folia, use {@link Server#getScheduler()} instead.</b></p>
   *
   * @return the task scheduler for this entity.
   * @see EntityScheduler
   */
  @Override
  public @NotNull EntityScheduler getScheduler() {
    return null;
  }

  @Override
  public @NotNull String getScoreboardEntryName() {
    return null;
  }

  @Override
  public @NotNull Map<String, Object> serialize() {
    return null;
  }

  @Override
  public @NotNull TriState getFrictionState() {
    return null;
  }

  @Override
  public void setFrictionState(@NotNull TriState triState) {

  }

  @Override
  public @Nullable AttributeInstance getAttribute(@NotNull Attribute attribute) {
    return null;
  }

  @Override
  public void registerAttribute(@NotNull Attribute attribute) {

  }

  @Override
  public void damage(double v) {

  }

  @Override
  public void damage(double v, @Nullable Entity entity) {

  }

  @Override
  public double getHealth() {
    return 0;
  }

  @Override
  public void setHealth(double v) {

  }

  @Override
  public double getAbsorptionAmount() {
    return 0;
  }

  @Override
  public void setAbsorptionAmount(double v) {

  }

  /**
   * @deprecated
   */
  @Override
  public double getMaxHealth() {
    return 0;
  }

  /**
   * @param v
   * @deprecated
   */
  @Override
  public void setMaxHealth(double v) {

  }

  /**
   * @deprecated
   */
  @Override
  public void resetMaxHealth() {

  }

  @Override
  public @Nullable Component customName() {
    return null;
  }

  @Override
  public void customName(@Nullable Component component) {

  }

  /**
   * @deprecated
   */
  @Override
  public @Nullable String getCustomName() {
    return null;
  }

  /**
   * @param s
   * @deprecated
   */
  @Override
  public void setCustomName(@Nullable String s) {

  }

  @Override
  public void setMetadata(@NotNull String s, @NotNull MetadataValue metadataValue) {

  }

  @Override
  public @NotNull List<MetadataValue> getMetadata(@NotNull String s) {
    return null;
  }

  @Override
  public boolean hasMetadata(@NotNull String s) {
    return false;
  }

  @Override
  public void removeMetadata(@NotNull String s, @NotNull Plugin plugin) {

  }

  @Override
  public boolean isPermissionSet(@NotNull String s) {
    return false;
  }

  @Override
  public boolean isPermissionSet(@NotNull Permission permission) {
    return false;
  }

  @Override
  public boolean hasPermission(@NotNull String s) {
    return getPlayer().hasPermission(s);
  }

  @Override
  public boolean hasPermission(@NotNull Permission permission) {
    return getPlayer().hasPermission(permission);
  }

  @Override
  public @NotNull PermissionAttachment addAttachment(@NotNull Plugin plugin, @NotNull String s, boolean b) {
    return null;
  }

  @Override
  public @NotNull PermissionAttachment addAttachment(@NotNull Plugin plugin) {
    return null;
  }

  @Override
  public @Nullable PermissionAttachment addAttachment(@NotNull Plugin plugin, @NotNull String s, boolean b, int i) {
    return null;
  }

  @Override
  public @Nullable PermissionAttachment addAttachment(@NotNull Plugin plugin, int i) {
    return null;
  }

  @Override
  public void removeAttachment(@NotNull PermissionAttachment permissionAttachment) {

  }

  @Override
  public void recalculatePermissions() {

  }

  @Override
  public @NotNull Set<PermissionAttachmentInfo> getEffectivePermissions() {
    return null;
  }

  @Override
  public boolean isOp() {
    return getPlayer().isOp();
  }

  @Override
  public void setOp(boolean b) {
    getPlayer().setOp(b);
  }

  @Override
  public @NotNull PersistentDataContainer getPersistentDataContainer() {
    return null;
  }

  @Override
  public void sendPluginMessage(@NotNull Plugin plugin, @NotNull String s, @NotNull byte[] bytes) {

  }

  @Override
  public @NotNull Set<String> getListeningPluginChannels() {
    return null;
  }

  @Override
  public <T extends Projectile> @NotNull T launchProjectile(@NotNull Class<? extends T> aClass) {
    return null;
  }

  @Override
  public <T extends Projectile> @NotNull T launchProjectile(@NotNull Class<? extends T> aClass, @Nullable Vector vector) {
    return null;
  }

  @Override
  public <T extends Projectile> @NotNull T launchProjectile(@NotNull Class<? extends T> aClass, @Nullable Vector vector, @Nullable Consumer<? super T> consumer) {
    return null;
  }

  //</editor-fold> Player interface
}
