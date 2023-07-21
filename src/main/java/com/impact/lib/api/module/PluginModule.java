package com.impact.lib.api.service;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public abstract class PluginModule<T extends Plugin> implements PluginServiceBase<T> {

    @Override
    public abstract void enable(@NotNull final T plugin);

    @Override
    public abstract void disable(@NotNull final T plugin);
}
