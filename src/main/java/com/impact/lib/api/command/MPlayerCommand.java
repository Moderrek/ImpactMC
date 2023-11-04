package com.impact.lib.api.command;

import com.impact.lib.ImpactLibPlugin;
import com.impact.lib.api.gui.Gui;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public abstract class MPlayerCommand extends MCommand<Player> {

  private transient Player player;

  public MPlayerCommand(@NotNull String label) {
    super(label);
  }

  @Override
  public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
    return null;
  }

  @Override
  public final boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    if (!(sender instanceof Player player)) {
      // not player
      cantUseCommandMessage().ifPresent(sender::sendMessage);
      return true;
    }
    // player
    this.player = player;
    perform(player, command, args.length, args);
    this.player = null;
    return true;
  }

  public @NotNull Optional<Component> cantUseCommandMessage() {
    return Optional.of(Component
        .text()
        .content("You cannot execute this command!")
        .color(NamedTextColor.RED)
        .build()
    );
  }

  private void checkPlayerScope() {
    if (player == null) throw new RuntimeException("Out of scope!");
  }

  protected final Player player() {
    checkScope();
    return player;
  }

  protected final Optional<Player> player(@Nullable final UUID mcid) {
    if (mcid == null) return Optional.empty();
    return Optional.ofNullable(Bukkit.getPlayer(mcid));
  }

  protected final Optional<Player> player(@Nullable final String mcname) {
    if (mcname == null) return Optional.empty();
    return Optional.ofNullable(Bukkit.getPlayer(mcname));
  }

  protected final boolean isOnline(@Nullable final Player player) {
    if (player == null) return false;
    return player.isOnline();
  }

  protected final boolean isOnline(@Nullable UUID mcid) {
    if (mcid == null) return false;
    return Bukkit.getPlayer(mcid) != null;
  }

  protected final boolean isOnline(@Nullable String mcname) {
    if (mcname == null) return false;
    return Bukkit.getPlayer(mcname) != null;
  }

  protected final @NotNull World world() {
    checkScope();
    return player.getWorld();
  }

  protected final @NotNull Optional<World> world(@Nullable final String name) {
    checkScope();
    if (name == null) return Optional.empty();
    return Optional.ofNullable(Bukkit.getWorld(name));
  }

  protected final void responseActionBar(Component content) {
    checkScope();
    player.sendActionBar(content);
  }

  protected final void responseActionBar(String strContent, TextColor color) {
    response(Component.text().content(strContent).color(color).build());
  }

  protected final void responseActionBar(String strContent) {
    response(strContent, NamedTextColor.WHITE);
  }

  protected final void responseActionBar(Object object) {
    response(String.valueOf(object));
  }

  protected final void responseSound(@Nullable final Sound sound, final float volume) {
    responseSound(sound, volume, 1.0F);
  }

  protected final void responseSound(@Nullable final Sound sound, final float volume, final float pitch) {
    checkScope();
    if (sound == null) return;
    player.playSound(location(), sound, volume, pitch);
  }

  protected final @NotNull Location location() {
    checkScope();
    return player.getLocation();
  }

  protected final void responseSound(@Nullable final Sound sound) {
    responseSound(sound, 1.0F, 1.0F);
  }

  protected final boolean open(@Nullable final Gui gui) {
    return openGui(gui);
  }

  protected final boolean openGui(@Nullable final Gui gui) {
    checkScope();
    if (gui == null) return false;
    gui.open(player);
    return true;
  }

  protected final boolean open(@Nullable final Inventory inventory) {
    return openInventory(inventory);
  }

  protected final boolean openInventory(@Nullable final Inventory inventory) {
    checkScope();
    if (inventory == null) return false;
    player.openInventory(inventory);
    return true;
  }

  protected final boolean open(@Nullable final InventoryView inventory) {
    return openInventory(inventory);
  }

  protected final boolean openInventory(@Nullable final InventoryView inventory) {
    checkScope();
    if (inventory == null) return false;
    player.openInventory(inventory);
    return true;
  }

  protected final boolean teleport(@Nullable final Location to) {
    checkScope();
    if (to == null) return false;
    player.teleport(to);
    return true;
  }

  protected final boolean move(@Nullable final Location to) {
    checkScope();
    if (to == null) return false;
    player.teleport(to);
    return true;
  }

  protected final void give(@Nullable ItemStack item) {
    giveItem(item);
  }

  protected final void giveItem(@Nullable ItemStack item) {
    checkScope();
    if (item == null) return;
    player.getInventory().addItem(item);
  }

  protected final @NotNull CompletableFuture<Optional<String>> inputChat() {
    checkScope();
    return ImpactLibPlugin.getInstance().getInputModule().requestChatInput(player);
  }

  protected final Optional<Block> targetBlock(int distance) {
    checkScope();
    Block target = player.getTargetBlockExact(distance);
    return Optional.ofNullable(target);
  }

  protected final Optional<BlockFace> targetFace(int distance) {
    checkScope();
    BlockFace target = player.getTargetBlockFace(distance);
    return Optional.ofNullable(target);
  }

  protected final Optional<ItemStack> itemInHand() {
    checkScope();
    return Optional.ofNullable(player.getActiveItem());
  }

  protected final @NotNull Chunk chunk() {
    checkScope();
    return player.getChunk();
  }

}
