package com.github.bakuplayz.craftinggui.utils;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.2.6
 */
public final class VersionUtil {

    public static @NotNull String getServerVersion() {
        return Bukkit.getServer().getBukkitVersion().split("-")[0].substring(2);
    }

    public static boolean isInInterval(final double start, final double end) {
        double serverVersion = Double.parseDouble(getServerVersion());
        return (serverVersion >= start) && (end >= serverVersion);
    }

    public static boolean isUnderTen() {
        return isInInterval(0.0, 9.9);
    }
}
