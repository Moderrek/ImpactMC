package com.impact.lib.api.block;

import org.bukkit.block.Block;

public record ImpactBlock(Block block) {

    public boolean isCustom() {
        return block.getMetadata("custom").contains(true);
    }

}
