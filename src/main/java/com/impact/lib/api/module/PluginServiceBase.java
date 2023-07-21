package com.impact.lib.api.service;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public interface PluginServiceBase<T extends Plugin> {

    void enable(@NotNull final T plugin);

    void disable(@NotNull final T plugin);

}
