package com.github.bakuplayz.craftinggui.menu.menus;

import com.github.bakuplayz.craftinggui.CraftingGUI;
import com.github.bakuplayz.craftinggui.api.LanguageAPI;
import com.github.bakuplayz.craftinggui.menu.DefaultMenu;
import com.github.bakuplayz.craftinggui.settings.PlayerSettings;
import com.github.bakuplayz.craftinggui.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.2.6
 */
public final class SettingsMenu extends DefaultMenu {

    private final PlayerSettings playerSettings;

    public SettingsMenu(final CraftingGUI plugin, final Player player) {
        setPlayer(player);
        setPlugin(plugin);
        setDefaultSettings();
        this.playerSettings = plugin.getPlayerSettings();
    }

    @Override
    public @NotNull String getTitle() {
        return LanguageAPI.Menu.SETTINGS_TITLE.getTitle(plugin);
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(20, getMultiChoiceItem());
        inventory.setItem(22, getColorPickerItem());
        inventory.setItem(24, getTableChoiceItem());
        inventory.setItem(45, getTableItem());

        if (player.isOp() || player.hasPermission("craftinggui.*")) {
            inventory.setItem(49, getAdminSettingsItem());
        }

        inventory.setItem(53, getMultiItem());
    }

    @Override
    public void handleMenu(final @NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        assert clicked != null;
        if (clicked.equals(getTableChoiceItem())) {
            if (player.isOp() || player.hasPermission("craftinggui.*")) {
                new TableChoiceMenu(plugin, player).open();
                return;
            }

            if (plugin.isTableEnabled()) {
                new TableChoiceMenu(plugin, player).open();
            }
        }

        if (clicked.equals(getMultiChoiceItem())) {
            if (player.isOp() || player.hasPermission("craftinggui.*")) {
                new MultiChoiceMenu(plugin, player).open();
                return;
            }

            if (plugin.isMultiEnabled()) {
                new MultiChoiceMenu(plugin, player).open();
            }
        }

        if (clicked.equals(getColorPickerItem())) {
            new ColorPickerMenu(plugin, player).open();
        }

        if (clicked.equals(getAdminSettingsItem())) {
            new AdminSettingsMenu(plugin, player).open();
        }
    }

    @Override
    public void handleDrag(final @NotNull InventoryDragEvent event) {
        event.setCancelled(true);
    }

    private @NotNull ItemStack getColorPickerItem() {
        short selectedColor = playerSettings.getSelectedColor(player);
        return new ItemUtil(getColorMaterial(selectedColor, false))
                .setDisplayName(LanguageAPI.Menu.COLOR_PICKER_ITEM_NAME.getMessage(plugin))
                .setLore(getColorName(selectedColor))
                .toItemStack();
    }

    private @NotNull ItemStack getMultiChoiceItem() {
        return new ItemUtil(Material.PAINTING)
                .setLore(LanguageAPI.Menu.MULTI_CHOICE_STATUS.getMessage(plugin, getMultiChoice()))
                .setDisplayName(LanguageAPI.Menu.MULTI_CHOICE_ITEM_NAME.getMessage(plugin))
                .toItemStack();
    }

    private @NotNull ItemStack getTableChoiceItem() {
        return new ItemUtil(Material.PAINTING)
                .setLore(LanguageAPI.Menu.TABLE_CHOICE_STATUS.getMessage(plugin, getTableChoice()))
                .setDisplayName(LanguageAPI.Menu.TABLE_CHOICE_ITEM_NAME.getMessage(plugin))
                .toItemStack();
    }

    private @NotNull ItemStack getAdminSettingsItem() {
        return new ItemUtil(Material.ENDER_CHEST)
                .setDisplayName(LanguageAPI.Menu.ADMIN_SETTINGS_ITEM_NAME.getMessage(plugin))
                .toItemStack();
    }

    private @NotNull String getMultiChoice() {
        if (!plugin.isMultiEnabled()) {
            return LanguageAPI.Menu.TOGGLE_DISABLED.getMessage(plugin);
        }

        String choice = playerSettings.getTableChoice(player);
        if (choice.equals("Multi")) return LanguageAPI.Menu.MULTI_CHOICE.getMessage(plugin);
        if (choice.equals("Table")) return LanguageAPI.Menu.TABLE_CHOICE.getMessage(plugin);
        return LanguageAPI.Menu.DEFAULT_CHOICE.getMessage(plugin);
    }

    private @NotNull String getTableChoice() {
        if (!plugin.isTableEnabled()) {
            return LanguageAPI.Menu.TOGGLE_DISABLED.getMessage(plugin);
        }

        String choice = playerSettings.getTableChoice(player);
        if (choice.equals("Multi")) return LanguageAPI.Menu.MULTI_CHOICE.getMessage(plugin);
        if (choice.equals("Table")) return LanguageAPI.Menu.TABLE_CHOICE.getMessage(plugin);
        return LanguageAPI.Menu.DEFAULT_CHOICE.getMessage(plugin);
    }

    private @NotNull String getColorName(int selectedColor) {
        List<String> colors = plugin.getLanguageConfig().getColors();
        return LanguageAPI.Menu.COLOR_PICKER_STATUS.getMessage(plugin, colors.get(selectedColor));
    }
}
