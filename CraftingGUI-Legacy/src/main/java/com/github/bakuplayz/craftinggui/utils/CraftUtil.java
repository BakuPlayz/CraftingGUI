package com.github.bakuplayz.craftinggui.utils;

import com.github.bakuplayz.craftinggui.items.RecipeItem;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.2.6
 */
public final class CraftUtil {

    private final ItemStack result;
    private final ItemStack maxedResult;

    private final int resultAmount;
    private final int resultMaxStackSize;

    private final RecipeItem recipeItem;
    private final Inventory playerInventory;

    public CraftUtil(Inventory playerInventory, @NotNull RecipeItem recipeItem) {
        this.playerInventory = playerInventory;
        this.recipeItem = recipeItem;
        this.result = recipeItem.getResult();
        this.resultAmount = result.getAmount();
        this.resultMaxStackSize = result.getMaxStackSize();
        this.maxedResult = new ItemUtil(result, resultMaxStackSize).toItemStack();
    }

    public boolean canContainResult(int amount) {
        Inventory cInventory = InventoryUtil.createInventory();
        InventoryUtil.setStorage(cInventory, playerInventory);
        removeIngredients(cInventory, amount);

        int totalAmount = resultAmount * amount;

        ItemStack[] contents = cInventory.getContents();
        for (int i = 0; i < contents.length; i++) {
            if (0 >= totalAmount) return true;
            if (i > 36) break;

            ItemStack item = contents[i];
            if (item == null) {
                if (resultMaxStackSize == 1 && totalAmount == 1) {
                    return true;
                }
                totalAmount -= resultMaxStackSize;
                cInventory.setItem(i, maxedResult);
                continue;
            }

            if (!item.isSimilar(result)) continue;

            int itemAmount = item.getAmount();
            if (resultMaxStackSize > itemAmount) {
                cInventory.setItem(i, maxedResult);
                totalAmount -= (resultMaxStackSize - itemAmount);
            }
        }
        return false;
    }

    public void removeIngredients(@NotNull Inventory inventory, int amount) {
        inventory.removeItem(getIngredients(inventory, amount));
    }

    private ItemStack[] getIngredients(Inventory inventory, int amount) {
        ItemStack[] ingredients = recipeItem.getIngredientsAsArray(amount);
        ItemStack[] choiceIngredients = recipeItem.hasChoice() ? recipeItem.getChoice().getIngredients(inventory, amount) : new ItemStack[0];
        return (ItemStack[]) ArrayUtils.addAll(ingredients, choiceIngredients);
    }
}