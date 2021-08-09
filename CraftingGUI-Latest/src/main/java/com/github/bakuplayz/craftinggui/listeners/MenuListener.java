package com.github.bakuplayz.craftinggui.listeners;

import com.github.bakuplayz.craftinggui.CraftingGUI;
import com.github.bakuplayz.craftinggui.menu.DefaultMenu;
import com.github.bakuplayz.craftinggui.menu.menus.CraftingTableMenu;
import com.github.bakuplayz.craftinggui.menu.menus.MultiCraftingMenu;
import com.github.bakuplayz.craftinggui.menu.menus.SettingsMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.2.6
 */
public final class MenuListener implements Listener {

    private final CraftingGUI plugin;

    public MenuListener(final @NotNull CraftingGUI plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMenuClick(final @NotNull InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (!(holder instanceof DefaultMenu)) return;
        if (!(holder instanceof CraftingTableMenu)) event.setCancelled(true);

        DefaultMenu menu = (DefaultMenu) holder;
        ItemStack clicked = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();

        if (event.getRawSlot() == -999) {
            player.closeInventory();
            event.setCancelled(true);
            return;
        }

        if (clicked == null) return;

        if (clicked.equals(menu.getMultiItem())) {
            new MultiCraftingMenu(plugin, player).open();
            event.setCancelled(true);
            return;
        }

        if (clicked.equals(menu.getTableItem())) {
            new CraftingTableMenu(plugin, player).open();
            event.setCancelled(true);
            return;
        }

        if (clicked.equals(menu.getSettingsItem())) {
            new SettingsMenu(plugin, player).open();
            event.setCancelled(true);
            return;
        }

        menu.handleMenu(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMenuDrag(final @NotNull InventoryDragEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof DefaultMenu) {
            ((DefaultMenu) holder).handleDrag(event);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMenuClose(final @NotNull InventoryCloseEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof CraftingTableMenu) {
            ((CraftingTableMenu) holder).handleClose(event);
        }
    }
}
