package pl.impact.lib;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import pl.impact.lib.api.gui.GuiModule;
import pl.impact.lib.api.input.InputModule;
import pl.impact.lib.plugin.command.TestCommand;

public final class ImpactLibPlugin extends JavaPlugin {

    private static volatile ImpactLibPlugin instance;
    private final transient Logger logger = getSLF4JLogger();
    private transient GuiModule guiService;
    private transient InputModule inputModule;

//    private Map<Class<? extends PluginService<ImpactLibPlugin>>, PluginService<ImpactLibPlugin>> services = new ConcurrentHashMap<>();

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
        return "v1.0";
    }

    /**
     * Gets Impact Gui Service instance.
     *
     * @return The Impact Gui Service instance.
     */
    public @NotNull GuiModule getGuiService() {
        return guiService;
    }

    public @NotNull InputModule getInputModule() {
        return inputModule;
    }

    @Override
    public void onDisable() {
        guiService.disable(this);
        inputModule.enable(this);
    }

    @Override
    public void onEnable() {
        long start = System.currentTimeMillis();
        ImpactLibPlugin.instance = this;
        Impact.setServer(getServer(), logger);
        logger.info("Started loading");
        guiService = new GuiModule();
        guiService.enable(this);
        inputModule = new InputModule();
        inputModule.enable(this);
        Impact.registerCommand(new TestCommand(this));
        logger.info("Loaded in {} ms", System.currentTimeMillis() - start);
    }

}
