package com.github.bakuplayz.craftinggui.menu;

import com.github.bakuplayz.craftinggui.CraftingGUI;
import com.github.bakuplayz.craftinggui.api.LanguageAPI;
import com.github.bakuplayz.craftinggui.configs.PlayerConfig;
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
        PlayerConfig playerConfig = plugin.getPlayerConfig();
        if (playerConfig.isNotRegistered(player)) {
            playerConfig.setDefaultSettings(player);
        }
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

        return new ItemUtil(58)
                .setDisplayName(LanguageAPI.Menu.TABLE_ITEM_NAME.getMessage(plugin))
                .toItemStack();
    }
}
