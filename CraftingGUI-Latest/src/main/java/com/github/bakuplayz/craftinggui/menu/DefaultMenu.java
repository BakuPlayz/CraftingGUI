package com.github.bakuplayz.craftinggui.menu;

import com.github.bakuplayz.craftinggui.CraftingGUI;
import com.github.bakuplayz.craftinggui.api.LanguageAPI;
import com.github.bakuplayz.craftinggui.settings.PlayerSettings;
import com.github.bakuplayz.craftinggui.utils.ItemUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.2.6
 */
public abstract class DefaultMenu implements InventoryHolder {

    protected @Setter Player player;
    protected @Setter CraftingGUI plugin;
    protected @Getter Inventory inventory;

    public abstract String getTitle();

    public abstract int getSlots();

    public abstract void setMenuItems();

    public abstract void handleMenu(final InventoryClickEvent event);

    public abstract void handleDrag(final InventoryDragEvent event);

    private void setInventory() {
        this.inventory = Bukkit.createInventory(this, getSlots(), getTitle());
    }

    public void open() {
        setInventory();
        setMenuItems();
        player.openInventory(inventory);
    }

    public void updateMenu() {
        inventory.clear();
        setMenuItems();
    }

    protected void setDefaultSettings() {
        PlayerSettings playerConfig = plugin.getPlayerSettings();
        if (playerConfig.isNotRegistered(player)) {
            playerConfig.setDefaultSettings(player);
        }
    }

    protected final @NotNull Material getColorMaterial(short color, boolean pane) {
        String colorName;
        switch (color) {
            case 0:
                colorName = "WHITE";
                break;
            case 1:
                colorName = "ORANGE";
                break;
            case 2:
                colorName = "MAGENTA";
                break;
            case 3:
                colorName = "LIGHT_BLUE";
                break;
            case 4:
                colorName = "YELLOW";
                break;
            case 5:
                colorName = "LIME";
                break;
            case 6:
                colorName = "PINK";
                break;
            case 7:
                colorName = "GRAY";
                break;
            case 8:
                colorName = "LIGHT_GRAY";
                break;
            case 9:
                colorName = "CYAN";
                break;
            case 10:
                colorName = "PURPLE";
                break;
            case 11:
                colorName = "BLUE";
                break;
            case 12:
                colorName = "BROWN";
                break;
            case 13:
                colorName = "GREEN";
                break;
            case 14:
                colorName = "RED";
                break;
            default:
                colorName = "BLACK";
                break;
        }
        Material stainedGlass = pane ?
                Material.getMaterial(colorName + "_STAINED_GLASS_PANE") :
                Material.getMaterial(colorName + "_STAINED_GLASS");
        return stainedGlass != null ? stainedGlass : pane ? Material.WHITE_STAINED_GLASS_PANE : Material.WHITE_STAINED_GLASS;
    }

    protected final @NotNull ItemStack getBackItem() {
        return new ItemUtil(Material.BARRIER)
                .setDisplayName(LanguageAPI.Menu.BACK_ITEM_NAME.getMessage(plugin))
                .toItemStack();
    }

    public final @NotNull ItemStack getSettingsItem() {
        return new ItemUtil(Material.CHEST)
                .setDisplayName(LanguageAPI.Menu.SETTINGS_ITEM_NAME.getMessage(plugin))
                .toItemStack();
    }

    public final @Nullable ItemStack getMultiItem() {
        if (!plugin.isMultiEnabled()) return null;

        return new ItemUtil(Material.PAINTING)
                .setDisplayName(LanguageAPI.Menu.MULTI_ITEM_NAME.getMessage(plugin))
                .toItemStack();
    }

    public final @Nullable ItemStack getTableItem() {
        if (!plugin.isTableEnabled()) return null;

        return new ItemUtil(Material.CRAFTING_TABLE)
                .setDisplayName(LanguageAPI.Menu.TABLE_ITEM_NAME.getMessage(plugin))
                .toItemStack();
    }
}
