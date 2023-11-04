package com.impact.lib.api.registry;

import org.bukkit.plugin.Plugin;

public record RegistryObject<T>(Plugin owner, T value) {
}