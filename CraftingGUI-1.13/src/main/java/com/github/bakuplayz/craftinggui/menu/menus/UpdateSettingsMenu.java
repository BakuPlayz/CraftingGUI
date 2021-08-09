package com.github.bakuplayz.craftinggui.menu.menus;

import com.github.bakuplayz.craftinggui.CraftingGUI;
import com.github.bakuplayz.craftinggui.api.LanguageAPI;
import com.github.bakuplayz.craftinggui.menu.DefaultMenu;
import com.github.bakuplayz.craftinggui.utils.ItemUtil;
import com.github.bakuplayz.craftinggui.utils.UpdateUtil;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.2.6
 */
public final class UpdateSettingsMenu extends DefaultMenu {

    private @Setter UpdateUtil updateUtil;

    public UpdateSettingsMenu(final CraftingGUI plugin, final Player player) {
        setPlayer(player);
        setPlugin(plugin);
        setDefaultSettings();
        setUpdateUtil(plugin.getUpdateUtil());
    }

    @Override
    public @NotNull String getTitle() {
        return LanguageAPI.Menu.UPDATE_SETTINGS_TITLE.getTitle(plugin);
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(20, getConsoleMessageItem());
        inventory.setItem(22, getUpdateStatusItem());
        inventory.setItem(24, getPlayerMessageItem());
        inventory.setItem(49, getBackItem());
    }

    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        assert clicked != null;
        if (clicked.equals(getPlayerMessageItem())) {
            togglePlayerMessage();
            updateMenu();
        }

        if (clicked.equals(getUpdateStatusItem())) {
            if (updateUtil.hasNewUpdate()) updateUtil.sendUpdateAlert(player);
        }

        if (clicked.equals(getConsoleMessageItem())) {
            toggleConsoleMessage();
            updateMenu();
        }

        if (clicked.equals(getBackItem())) {
            new AdminSettingsMenu(plugin, player).open();
        }
    }

    @Override
    public void handleDrag(@NotNull InventoryDragEvent event) {
        event.setCancelled(true);
    }

    private @NotNull ItemStack getUpdateStatusItem() {
        String status = updateUtil.hasNewUpdate() ?
                LanguageAPI.Menu.NEW_UPDATE.getMessage(plugin) :
                LanguageAPI.Menu.NO_NEW_UPDATE.getMessage(plugin);
        return new ItemUtil(Material.GREEN_STAINED_GLASS)
                .setDisplayName(LanguageAPI.Menu.UPDATE_STATUS_ITEM_NAME.getMessage(plugin))
                .setLore(LanguageAPI.Menu.UPDATE_STATUS.getMessage(plugin, status))
                .setMaterial(updateUtil.hasNewUpdate() ?
                        Material.GREEN_STAINED_GLASS :
                        Material.ORANGE_STAINED_GLASS)
                .toItemStack();
    }

    private @NotNull ItemStack getConsoleMessageItem() {
        String status = plugin.getConfig().getBoolean("updateMessage.console") ?
                LanguageAPI.Menu.MESSAGE_ENABLED.getMessage(plugin) :
                LanguageAPI.Menu.MESSAGE_DISABLED.getMessage(plugin);
        return new ItemUtil(Material.PAINTING)
                .setDisplayName(LanguageAPI.Menu.CONSOLE_MESSAGE_ITEM_NAME.getMessage(plugin))
                .setLore(LanguageAPI.Menu.CONSOLE_MESSAGE_STATUS.getMessage(plugin, status))
                .toItemStack();
    }

    private @NotNull ItemStack getPlayerMessageItem() {
        String status = plugin.getConfig().getBoolean("updateMessage.player") ?
                LanguageAPI.Menu.MESSAGE_ENABLED.getMessage(plugin) :
                LanguageAPI.Menu.MESSAGE_DISABLED.getMessage(plugin);
        return new ItemUtil(Material.PAINTING)
                .setDisplayName(LanguageAPI.Menu.PLAYER_MESSAGE_ITEM_NAME.getMessage(plugin))
                .setLore(LanguageAPI.Menu.PLAYER_MESSAGE_STATUS.getMessage(plugin, status))
                .toItemStack();
    }

    private void togglePlayerMessage() {
        boolean updateOptionPlayer = plugin.getConfig().getBoolean("updateMessage.player");
        plugin.getConfig().set("updateMessage.player", !updateOptionPlayer);
        plugin.saveConfig();
    }

    private void toggleConsoleMessage() {
        boolean updateOptionConsole = plugin.getConfig().getBoolean("updateMessage.console");
        plugin.getConfig().set("updateMessage.console", !updateOptionConsole);
        plugin.saveConfig();
    }
}