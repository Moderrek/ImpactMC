package com.impact.lib;

import com.impact.lib.api.block.CustomBlockModule;
import com.impact.lib.api.block.DzialkaBlock;
import com.impact.lib.api.command.MCommand;
import com.impact.lib.api.gui.GuiModule;
import com.impact.lib.api.input.InputModule;
import com.impact.lib.api.item.ExampleBlock;
import com.impact.lib.api.module.PluginModule;
import com.impact.lib.api.registry.ImpactRegistries;
import com.impact.lib.plugin.listener.PluginDisableListener;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class ImpactLibPlugin extends JavaPlugin {

    private static volatile ImpactLibPlugin instance;
    private final transient Logger logger = getSLF4JLogger();
    private transient GuiModule guiModule;
    private transient InputModule inputModule;
    private transient CustomBlockModule customBlockModule;

//    private final Map<Class<? extends PluginModule<ImpactLibPlugin>>, PluginModule<ImpactLibPlugin>> services = new ConcurrentHashMap<>();

    Set<Listener> impactRegisteredListeners = ConcurrentHashMap.newKeySet();
    Set<MCommand<?>> impactRegisteredCommands = ConcurrentHashMap.newKeySet();

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

    private void checkCommands() {
        logger.info("Checking commands..");
        for(MCommand<?> registeredCommand : impactRegisteredCommands) {
            Impact.unregisterCommand(registeredCommand);
        }
    }

    public void unregisterCommands(@NotNull Plugin plugin) {
        logger.info("Unregistering commands for {}..", plugin.getName());
        impactRegisteredCommands.stream().filter(command -> command.getOwner() == plugin).forEach(Impact::unregisterCommand);
    }

    private void checkListeners() {
        logger.info("Checking listeners..");
        for(Listener registeredListener : impactRegisteredListeners) {
            Impact.unregisterListener(registeredListener);
        }
    }

    @Override
    public void onEnable() {
        long start = System.currentTimeMillis();

        // init
        ImpactLibPlugin.instance = this;
        Impact.setServer(getServer(), logger);
        logger.info("Started loading");
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

        ImpactRegistries.register(ImpactRegistries.CUSTOM_BLOCK, new NamespacedKey(this, "dzialka"), new DzialkaBlock(Material.LODESTONE));

        Impact.getMainWorld().setBlock(ExampleBlock.IDENTIFIER, "world", 1,2,4);

        logger.info("Enabled in {} ms", System.currentTimeMillis() - start);
    }

    @Override
    public void onDisable() {
        long start = System.currentTimeMillis();

        tryDisableModule(inputModule);
        tryDisableModule(guiModule);
        tryDisableModule(customBlockModule);
        checkCommands();
        checkListeners();

        logger.info("Disabled in {} ms", System.currentTimeMillis() - start);
    }

}
