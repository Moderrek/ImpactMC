package com.impact.lib.api.item;

import com.impact.lib.Impact;
import com.impact.lib.ImpactLibPlugin;
import com.impact.lib.api.block.CustomBlock;
import com.impact.lib.api.registry.ImpactRegistries;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

public class ExampleBlock extends CustomBlock {

    public static final NamespacedKey IDENTIFIER = new NamespacedKey(ImpactLibPlugin.getInstance(), "example_block");

    public ExampleBlock() {
        super(Material.STONE);
    }

    static {
        ImpactRegistries.register(ImpactRegistries.CUSTOM_BLOCK, IDENTIFIER, new ExampleBlock());
        Impact.getMainWorld().setBlock(ExampleBlock.IDENTIFIER, new Location(Impact.getMainWorld().world(), 0,0,0));
    }

    @Override
    public boolean canPlace() {
        return false;
    }

    @Override
    public boolean canDestroy() {
        return false;
    }

    @Override
    public boolean canExplode() {
        return false;
    }

    @Override
    public boolean canPistonMove() {
        return false;
    }
}
