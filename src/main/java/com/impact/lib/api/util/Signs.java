package pl.impact.lib.api.block;

import net.kyori.adventure.text.Component;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public final class Signs {

    private Signs() {
    }

    public static boolean isSignBlock(@NotNull final Location blockLocation) {
        return isSignBlock(blockLocation.getBlock());
    }

    public static boolean isSignBlock(@NotNull final Block block) {
        return block.getState() instanceof Sign;
    }

    public static @NotNull List<Component> getLines(@NotNull final Block block) {
        return getLines(getSign(block));
    }

    public static @NotNull List<Component> getLines(@NotNull final Sign sign) {
        return sign.lines();
    }

    public static @NotNull Sign getSign(@NotNull final Block block) {
        if (!isSignBlock(block)) throw new RuntimeException("Block is not a Sign!");
        return (Sign) block.getState();
    }

    public static @NotNull Component getLine(@NotNull final Block block, final int lineIndex) {
        return getLine(getSign(block), lineIndex);
    }

    public static @NotNull Component getLine(@NotNull final Sign sign, final int lineIndex) {
        validateIndex(lineIndex);
        return sign.line(lineIndex);
    }

    private static void validateIndex(final int lineIndex) {
        if (lineIndex < 0 || lineIndex > 3) throw new IllegalArgumentException("Invalid line index. 0 - 3!");
    }

    public static @NotNull Optional<DyeColor> getDyeColor(@NotNull final Block block) {
        return getDyeColor(getSign(block));
    }

    public static @NotNull Optional<DyeColor> getDyeColor(@NotNull final Sign sign) {
        return Optional.ofNullable(sign.getColor());
    }

    public static void setLine(@NotNull final Block block, final int lineIndex, @NotNull final Component content) {
        setLine(getSign(block), lineIndex, content);
    }

    public static void setLine(@NotNull final Sign sign, final int lineIndex, @NotNull final Component content) {
        validateIndex(lineIndex);
        sign.line(lineIndex, content);
        sign.update();
    }

    public static boolean isGlowingText(@NotNull final Block signBlock) {
        return isGlowingText(getSign(signBlock));
    }

    public static boolean isGlowingText(@NotNull final Sign sign) {
        return sign.isGlowingText();
    }

}
