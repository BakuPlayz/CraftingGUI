package com.github.bakuplayz.craftinggui.menu.menus;

import com.github.bakuplayz.craftinggui.CraftingGUI;
import com.github.bakuplayz.craftinggui.api.LanguageAPI;
import com.github.bakuplayz.craftinggui.menu.DefaultMenu;
import com.github.bakuplayz.craftinggui.utils.ItemUtil;
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
public final class AdminSettingsMenu extends DefaultMenu {

    public AdminSettingsMenu(final CraftingGUI plugin, final Player player) {
        setPlayer(player);
        setPlugin(plugin);
        setDefaultSettings();
    }

    @Override
    public @NotNull String getTitle() {
        return LanguageAPI.Menu.ADMIN_SETTINGS_TITLE.getTitle(plugin);
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(20, getUpdateSettingsItem());
        inventory.setItem(22, getReloadRecipesItem());
        inventory.setItem(24, getManageRecipesItem());
        inventory.setItem(49, getBackItem());
    }

    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        if (clicked.equals(getBackItem())) new SettingsMenu(plugin, player).open();
        if (clicked.equals(getReloadRecipesItem())) player.performCommand("cg reload");
        if (clicked.equals(getManageRecipesItem())) new ManageRecipesMenu(plugin, player).open();
        if (clicked.equals(getUpdateSettingsItem())) new UpdateSettingsMenu(plugin, player).open();
    }

    @Override
    public void handleDrag(@NotNull InventoryDragEvent event) {
        event.setCancelled(true);
    }

    private @NotNull ItemStack getUpdateSettingsItem() {
        return new ItemUtil(Material.ITEM_FRAME)
                .setDisplayName(LanguageAPI.Menu.UPDATE_SETTINGS_ITEM_NAME.getMessage(plugin))
                .toItemStack();
    }

    private @NotNull ItemStack getManageRecipesItem() {
        return new ItemUtil(Material.ENCHANTMENT_TABLE)
                .setDisplayName(LanguageAPI.Menu.MANAGE_RECIPES_ITEM_NAME.getMessage(plugin))
                .toItemStack();
    }

    private @NotNull ItemStack getReloadRecipesItem() {
        return new ItemUtil(Material.BOOKSHELF)
                .setDisplayName(LanguageAPI.Menu.RELOAD_RECIPES_ITEM_NAME.getMessage(plugin))
                .toItemStack();
    }
}
