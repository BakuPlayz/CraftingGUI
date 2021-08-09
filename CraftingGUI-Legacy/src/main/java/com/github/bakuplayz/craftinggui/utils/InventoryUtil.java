package com.github.bakuplayz.craftinggui.utils;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.2.6
 */
public final class InventoryUtil {

    public static Inventory createInventory() {
        if (VersionUtil.isUnderTen()) {
            return Bukkit.createInventory(null, InventoryType.PLAYER);
        }
        return Bukkit.createInventory(null, 36);
    }

    public static ItemStack[] getStorage(Inventory inventory) {
        if (VersionUtil.isUnderTen()) {
            return getContents(inventory);
        }
        return inventory.getStorageContents();
    }

    private static ItemStack @NotNull [] getContents(Inventory inventory) {
        ItemStack[] storage = new ItemStack[36];
        for (int i = 0; i < 36; i++) {
            storage[i] = inventory.getContents()[i];
        }
        return storage;
    }

    public static void setStorage(Inventory first, Inventory second) {
        if (VersionUtil.isUnderTen()) {
            first.setContents(getStorage(second));
            return;
        }
        first.setStorageContents(getStorage(second));
    }
}