package com.impact.lib.api.block;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DzialkaBlock extends CustomBlock implements ClickableCustomBlock, TickableCustomBlock {

    public DzialkaBlock(@NotNull Material material) {
        super(material, "dzialka");
    }

    @Override
    public boolean canPlace() {
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

    @Override
    public boolean canDestroy() {
        return false;
    }

    @Override
    public void onRightClick(Player player) {

    }

    @Override
    public void tick() {

    }
}
