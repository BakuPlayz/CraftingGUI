package com.github.bakuplayz.craftinggui.menu.menus;

import com.github.bakuplayz.craftinggui.CraftingGUI;
import com.github.bakuplayz.craftinggui.api.LanguageAPI;
import com.github.bakuplayz.craftinggui.configs.PlayerConfig;
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
public final class TableChoiceMenu extends DefaultMenu {

    private final PlayerConfig playerConfig;

    public TableChoiceMenu(final CraftingGUI plugin, final Player player) {
        setPlayer(player);
        setPlugin(plugin);
        setDefaultSettings();
        this.playerConfig = plugin.getPlayerConfig();
    }

    @Override
    public @NotNull String getTitle() {
        return LanguageAPI.Menu.TABLE_CHOICE_TITLE.getTitle(plugin);
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void setMenuItems() {
        if (player.isOp() || player.hasPermission("craftinggui.*")) {
            inventory.setItem(13, getToggleItem());
            inventory.setItem(29, getMultiChoiceItem());
            inventory.setItem(31, getDefaultChoiceItem());
            inventory.setItem(33, getTableChoiceItem());
        } else {
            inventory.setItem(20, getMultiChoiceItem());
            inventory.setItem(22, getDefaultChoiceItem());
            inventory.setItem(24, getTableChoiceItem());
        }

        inventory.setItem(49, getBackItem());
    }

    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        assert clicked != null;
        if (clicked.equals(getToggleItem())) {
            toggleTable();
            updateMenu();
        }

        if (clicked.equals(getMultiChoiceItem())) {
            playerConfig.setTableChoice(player, "Multi");
            updateMenu();
        }

        if (clicked.equals(getTableChoiceItem())) {
            playerConfig.setTableChoice(player, "Table");
            updateMenu();
        }

        if (clicked.equals(getDefaultChoiceItem())) {
            playerConfig.setTableChoice(player, "Default");
            updateMenu();
        }

        if (clicked.equals(getBackItem())) {
            new SettingsMenu(plugin, player).open();
        }
    }

    @Override
    public void handleDrag(@NotNull InventoryDragEvent event) {
        event.setCancelled(true);
    }

    private @NotNull ItemStack getToggleItem() {
        boolean isEnabled = plugin.isTableEnabled();
        return new ItemUtil(Material.GREEN_TERRACOTTA)
                .setDisplayName(LanguageAPI.Menu.TOGGLE_ITEM_NAME.getMessage(plugin))
                .setLore(isEnabled ?
                        LanguageAPI.Menu.TOGGLE_ENABLED.getMessage(plugin) :
                        LanguageAPI.Menu.TOGGLE_DISABLED.getMessage(plugin))
                .setMaterial(isEnabled ?
                        Material.GREEN_TERRACOTTA :
                        Material.RED_TERRACOTTA)
                .toItemStack();
    }

    private @NotNull ItemStack getMultiChoiceItem() {
        boolean hasMultiSelected = playerConfig.getTableChoice(player).equals("Multi");
        return new ItemUtil(Material.ITEM_FRAME)
                .setLore(hasMultiSelected ? LanguageAPI.Menu.SELECTED_CHOICE.getMessage(plugin) : "")
                .setDisplayName(LanguageAPI.Menu.MULTI_CHOICE_ITEM_NAME.getMessage(plugin))
                .toItemStack();
    }

    private @NotNull ItemStack getTableChoiceItem() {
        boolean hasTableSelected = playerConfig.getTableChoice(player).equals("Table");
        return new ItemUtil(Material.CRAFTING_TABLE)
                .setLore(hasTableSelected ? LanguageAPI.Menu.SELECTED_CHOICE.getMessage(plugin) : "")
                .setDisplayName(LanguageAPI.Menu.TABLE_CHOICE_ITEM_NAME.getMessage(plugin))
                .toItemStack();
    }

    private @NotNull ItemStack getDefaultChoiceItem() {
        boolean hasDefaultSelected = playerConfig.getTableChoice(player).equals("Default");
        return new ItemUtil(Material.WHITE_STAINED_GLASS_PANE)
                .setLore(hasDefaultSelected ? LanguageAPI.Menu.SELECTED_CHOICE.getMessage(plugin) : "")
                .setDisplayName(LanguageAPI.Menu.DEFAULT_CHOICE_ITEM_NAME.getMessage(plugin))
                .toItemStack();
    }

    private void toggleTable() {
        plugin.getConfig().set("isEnabled.table", !plugin.isTableEnabled());
        plugin.saveConfig();
    }
}
