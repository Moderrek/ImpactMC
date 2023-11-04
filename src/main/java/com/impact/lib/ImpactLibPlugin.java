package com.impact.lib;

import com.impact.lib._test.CustomGrass;
import com.impact.lib._test.ExampleBlock;
import com.impact.lib._test.ExampleItem;
import com.impact.lib._test.PrzenosnyEnderchest;
import com.impact.lib.api.block.CustomBlock;
import com.impact.lib.api.gui.GuiModule;
import com.impact.lib.api.input.InputModule;
import com.impact.lib.api.item.CustomBlockItem;
import com.impact.lib.api.module.PluginModule;
import com.impact.lib.api.registry.ImpactRegistries;
import com.impact.lib.api.registry.ImpactRegistry;
import com.impact.lib.plugin.listener.PluginDisableListener;
import com.impact.lib.plugin.module.CustomBlockModule;
import com.impact.lib.plugin.module.CustomItemModule;
import org.bukkit.Server;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class ImpactLibPlugin extends JavaPlugin {

  private static volatile ImpactLibPlugin instance;
  private final transient Logger logger = getSLF4JLogger();
  Set<Listener> impactRegisteredListeners = ConcurrentHashMap.newKeySet();
  private transient CustomItemModule itemModule;
  private transient GuiModule guiModule;
  private transient InputModule inputModule;
  private transient CustomBlockModule customBlockModule;

  public ImpactLibPlugin() {
  }

  public ImpactLibPlugin(Server server) {
    super(new JavaPluginLoader(server), new PluginDescriptionFile("ImpactLib", "", "com.impact.lib.ImpactLibPlugin"), null, null);
  }

  /**
   * Gets ImpactLib instance.
   *
   * @return The singleton Impact Lib Plugin instance.
   */
  @Contract(pure = true)
  public static @NotNull ImpactLibPlugin getInstance() {
    return instance;
  }

  @Contract(pure = true)
  public static @NotNull String getVersion() {
    return "1.0";
  }

  /**
   * Gets Impact Gui Service instance.
   *
   * @return The Impact Gui Service instance.
   */
  public @NotNull GuiModule getGuiModule() {
    return guiModule;
  }

  public @NotNull InputModule getInputModule() {
    return inputModule;
  }

  public void unregisterCommands(@NotNull Plugin plugin) {
    logger.info("Unregistering commands for {}..", plugin.getName());
    ImpactRegistries.COMMAND.getAll()
        .stream()
        .filter(command -> Objects.equals(command.getOwner(), plugin))
        .forEach(ImpactRegistries.COMMAND::remove);
  }

  @Override
  public void onDisable() {
    long start = System.currentTimeMillis();

    ImpactRegistries.COMMAND.getAll()
        .forEach(ImpactRegistries.COMMAND::remove);
    tryDisableModule(guiModule);
    tryDisableModule(inputModule);
    tryDisableModule(itemModule);
    tryDisableModule(customBlockModule);
    checkListeners();

    logger.info("Disabled in {} ms", System.currentTimeMillis() - start);
  }

  private void tryDisableModule(@NotNull PluginModule<ImpactLibPlugin> module) {
    long start = System.currentTimeMillis();
    logger.info("Disabling {}..", module.getClass().getSimpleName());
    try {
      module.disable(this);
      logger.info("Disabled {} in {} ms", module.getClass().getSimpleName(), System.currentTimeMillis() - start);
    } catch (Exception ignored) {
      ignored.printStackTrace();
    }
  }

  private void checkListeners() {
    logger.info("Checking listeners..");
    impactRegisteredListeners.forEach(Impact::unregisterListener);
  }

  @Override
  public void onEnable() {
    long start = System.currentTimeMillis();

    // init
    ImpactLibPlugin.instance = this;
    Impact.setServer(getServer(), logger);
    logger.info("Started loading");
    // Custom Item Module
    itemModule = new CustomItemModule();
    itemModule.enable(this);
    // Gui Module
    guiModule = new GuiModule();
    guiModule.enable(this);
    // Input Module
    inputModule = new InputModule();
    inputModule.enable(this);
    // CustomBlock Module
    customBlockModule = new CustomBlockModule();
    customBlockModule.enable(this);
    // Listeners
    Impact.addListener(new PluginDisableListener(this), this);

    ImpactRegistry.register(ImpactRegistries.CUSTOM_ITEM, ExampleItem.IDENTIFIER, new ExampleItem());
    CustomBlock exampleBlock = new ExampleBlock();
    ImpactRegistry.register(ImpactRegistries.CUSTOM_BLOCK, ExampleBlock.IDENTIFIER, exampleBlock);
    CustomGrass customGrass = new CustomGrass();
    ImpactRegistry.register(ImpactRegistries.CUSTOM_BLOCK, Impact.createKey("grass"), customGrass);
    ImpactRegistry.register(ImpactRegistries.CUSTOM_ITEM, Impact.createKey("grass"), new CustomBlockItem(customGrass));
    ImpactRegistry.register(ImpactRegistries.CUSTOM_ITEM, ExampleBlock.IDENTIFIER, new CustomBlockItem(exampleBlock));
    ImpactRegistry.register(ImpactRegistries.CUSTOM_ITEM, PrzenosnyEnderchest.IDENTIFIER, new PrzenosnyEnderchest());

    Impact.getPlayers().forEach(impactPlayer -> {
      ImpactRegistries.CUSTOM_ITEM.getAll().forEach(customItem -> {
        impactPlayer.giveItem(customItem.getItemStack(1));
      });
    });

    logger.info("Enabled in {} ms", System.currentTimeMillis() - start);
  }

  public void onEnableTest(Server server) {
    ImpactLibPlugin.instance = this;
    Impact.setServer(server, logger);
  }

}
