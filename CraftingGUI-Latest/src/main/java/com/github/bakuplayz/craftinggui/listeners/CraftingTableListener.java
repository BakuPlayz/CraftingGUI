package com.github.bakuplayz.craftinggui.listeners;

import com.github.bakuplayz.craftinggui.CraftingGUI;
import com.github.bakuplayz.craftinggui.menu.menus.CraftingTableMenu;
import com.github.bakuplayz.craftinggui.menu.menus.MultiCraftingMenu;
import com.github.bakuplayz.craftinggui.settings.PlayerSettings;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
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

    public CraftingTableListener(@NotNull CraftingGUI plugin) {
        this.plugin = plugin;
        this.playerSettings = plugin.getPlayerSettings();
    }

    @EventHandler
    public void onInteractWithTable(@NotNull PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getClickedBlock().getType() != Material.CRAFTING_TABLE) return;
        event.setCancelled(true);

        if (isSlimeFunTable(event.getClickedBlock())) {
            return;
        }

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


    public boolean isSlimeFunTable(@NotNull Block table) {
        Block below = table.getRelative(BlockFace.DOWN);
        Block north = table.getRelative(BlockFace.NORTH);
        Block west = table.getRelative(BlockFace.WEST);
        Block east = table.getRelative(BlockFace.EAST);
        Block south = table.getRelative(BlockFace.SOUTH);

        if (below.getType() == Material.DISPENSER) {
            return true;
        }

        if (north.getType() == Material.DISPENSER) {
            return true;
        }

        if (west.getType() == Material.DISPENSER) {
            return true;
        }

        if (east.getType() == Material.DISPENSER) {
            return true;
        }

        return south.getType() == Material.DISPENSER;
    }
}