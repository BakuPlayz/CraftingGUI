package com.github.bakuplayz.craftinggui.listeners;

import com.github.bakuplayz.craftinggui.CraftingGUI;
import com.github.bakuplayz.craftinggui.menu.menus.CraftingTableMenu;
import com.github.bakuplayz.craftinggui.menu.menus.MultiCraftingMenu;
import com.github.bakuplayz.craftinggui.settings.PlayerSettings;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.2.6
 */
public final class CraftingTableListener implements Listener {

    private final CraftingGUI plugin;
    private final PlayerSettings playerSettings;

    public CraftingTableListener(final @NotNull CraftingGUI plugin) {
        this.plugin = plugin;
        this.playerSettings = plugin.getPlayerSettings();
    }

    @EventHandler
    public void onInteractWithTable(final @NotNull PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getClickedBlock() == null || event.getClickedBlock().getType() != Material.CRAFTING_TABLE) return;
        event.setCancelled(true);

        Player player = event.getPlayer();
        if (playerSettings.isNotRegistered(player)) {
            playerSettings.setDefaultSettings(player);
        }

        switch (playerSettings.getTableChoice(player)) {
            case "Multi":
                if (plugin.isMultiEnabled()) {
                    new MultiCraftingMenu(plugin, player).open();
                    return;
                }

                if (plugin.isTableEnabled()) {
                    new CraftingTableMenu(plugin, player).open();
                    return;
                }
            case "Table":
                if (plugin.isTableEnabled()) {
                    new CraftingTableMenu(plugin, player).open();
                    return;
                }

                if (plugin.isMultiEnabled()) {
                    new MultiCraftingMenu(plugin, player).open();
                    return;
                }
            default:
                player.openWorkbench(player.getLocation(), true);
                break;
        }
    }
}
