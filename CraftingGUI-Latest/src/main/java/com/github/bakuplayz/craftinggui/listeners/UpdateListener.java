package com.github.bakuplayz.craftinggui.listeners;

import com.github.bakuplayz.craftinggui.CraftingGUI;
import com.github.bakuplayz.craftinggui.utils.UpdateUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.2.6
 */
public final class UpdateListener implements Listener {

    private final CraftingGUI plugin;
    private final UpdateUtil updateUtil;

    public UpdateListener(final @NotNull CraftingGUI plugin) {
        this.plugin = plugin;
        this.updateUtil = plugin.getUpdateUtil();

        sendConsoleUpdate();
    }

    @EventHandler
    public void sendPlayerUpdate(final @NotNull PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!plugin.getConfig().getBoolean("updateMessage.console")) return;
        if (!player.isOp() && !player.hasPermission("craftinggui.update-message")) return;
        if (updateUtil.hasNewUpdate()) updateUtil.sendUpdateAlert(player);
    }

    private void sendConsoleUpdate() {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            if (!plugin.getConfig().getBoolean("updateMessage.console")) return;
            if (updateUtil.hasNewUpdate()) updateUtil.sendUpdateAlert();
        }, 0, 20 * 60 * 31); //ticks:seconds:minutes
    }
}
