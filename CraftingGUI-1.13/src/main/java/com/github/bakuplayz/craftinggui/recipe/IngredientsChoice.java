package com.github.bakuplayz.craftinggui.recipe;

import com.github.bakuplayz.craftinggui.utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.2.6
 */
public final class IngredientsChoice {

    private final int amountOfIngredient;
    private final MaterialType materialType;

    private Inventory tempInventory;
    private ArrayList<ItemStack> tempIngredients;

    public IngredientsChoice(MaterialType materialType) {
        this.amountOfIngredient = 1;
        this.materialType = materialType;
    }

    public IngredientsChoice(MaterialType materialType, int amountOfIngredient) {
        this.materialType = materialType;
        this.amountOfIngredient = amountOfIngredient;
    }

    public boolean matchesType(final ItemStack item) {
        for (Material material : materialType.getMaterials()) {
            if (material == item.getType()) return true;
        }
        return false;
    }

    public boolean containsIngredients(final @NotNull Inventory inventory, final int amount) {
        int containsAmount = 0;
        for (ItemStack item : inventory.getStorageContents()) {
            if (item == null || item.getType() == Material.AIR) continue;
            if (matchesType(item)) containsAmount += item.getAmount();
        }
        return containsAmount >= (amount * amountOfIngredient);
    }

    public ItemStack @NotNull [] getIngredients(final @NotNull Inventory inventory, final int amount) {
        tempInventory = Bukkit.createInventory(null, 36);
        tempInventory.setStorageContents(inventory.getStorageContents());
        tempIngredients = new ArrayList<>();

        int ingredientsAmount = amountOfIngredient * amount;
        for (ItemStack item : tempInventory.getStorageContents()) {
            if (item == null || item.getType() == Material.AIR) continue;
            if (matchesType(item)) {
                ingredientsAmount -= addIngredient(item, ingredientsAmount);
            }
        }
        return tempIngredients.toArray(new ItemStack[0]);
    }

    private int addIngredient(final @NotNull ItemStack item, final int num) {
        if (item.getAmount() >= num) {
            ItemStack stack = new ItemUtil(item)
                    .setAmount(num)
                    .toItemStack();
            tempInventory.removeItem(stack);
            tempIngredients.add(stack);
            return num;
        } else {
            tempInventory.removeItem(item);
            tempIngredients.add(item);
            return item.getAmount();
        }
    }
}