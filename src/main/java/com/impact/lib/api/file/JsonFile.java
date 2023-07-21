package com.impact.lib.api.file;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class JsonFile implements SaveableDataFile {

    private transient final File file;

    public JsonFile(@NotNull final Plugin plugin, @NotNull final String fileName) {
        this(new File(plugin.getDataFolder(), "data" + File.pathSeparatorChar + ((fileName.endsWith(".json")) ? fileName : fileName + ".json")));
    }

    public JsonFile(@NotNull final File file) {
        this.file = file;
    }

    public @NotNull File getFile() {
        return file;
    }

    public @NotNull String getPath() {
        return file.getAbsolutePath();
    }

    @Override
    public void save() {

    }

    @Override
    public void reload() {

    }
}
