package com.github.bakuplayz.craftinggui.utils;

import com.github.bakuplayz.craftinggui.items.RecipeItem;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public CraftUtil(final Inventory playerInventory, final @NotNull RecipeItem recipeItem) {
        this.playerInventory = playerInventory;
        this.recipeItem = recipeItem;
        this.result = recipeItem.getResult();
        this.resultAmount = result.getAmount();
        this.resultMaxStackSize = result.getMaxStackSize();
        this.maxedResult = new ItemUtil(result, resultMaxStackSize).toItemStack();
    }

    public boolean containsChoiceIngredients(final RecipeChoice choice, final ItemStack ingredient, final int amount) {
        int ingredientAmount = 0;
        for (ItemStack item : playerInventory.getStorageContents()) {
            if (item == null || item.getType() == Material.AIR) continue;
            if (choice.test(item)) ingredientAmount += item.getAmount();
        }
        return ingredientAmount >= ingredient.getAmount() * amount;
    }

    public boolean containsIngredients(final int amount) {
        List<RecipeChoice> choices = new ArrayList<>();
        List<ItemStack> ingredients = new ArrayList<>();

        for (ItemStack ingredient : recipeItem.getIngredients()) {
            for (RecipeChoice choice : recipeItem.getChoices().values()) {
                if (choice == null) continue;
                if (choices.contains(choice)) continue;
                if (choice.test(ingredient)) {
                    choices.add(choice);
                    ingredients.add(ingredient);
                }
                if (!containsChoiceIngredients(choice, ingredient, amount) &&
                        !playerInventory.containsAtLeast(ingredient, ingredient.getAmount() * amount)) return false;
            }
        }

        if (ingredients.size() != recipeItem.getIngredients().size()) {
            for (ItemStack foundIngredient : ingredients) {
                for (ItemStack ingredient : recipeItem.getIngredients()) {
                    if (foundIngredient == ingredient) continue;
                    if (!playerInventory.containsAtLeast(ingredient, ingredient.getAmount() * amount)) return false;
                }
            }
        }
        return true;
    }

    public boolean canContainResult(final int amount) {
        Inventory cInventory = Bukkit.createInventory(null, 36);
        cInventory.setStorageContents(playerInventory.getStorageContents());
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

    public void removeIngredients(final @NotNull Inventory inventory, final int amount) {
        inventory.removeItem(getIngredients(inventory, amount));
    }

    private ItemStack @NotNull [] getIngredients(final Inventory inventory, final int amount) {
        ItemStack[] ingredients = recipeItem.getIngredientsAsArray(amount);
        ItemStack[] choiceIngredients = recipeItem.getChoiceIngredients(inventory, amount);
        return (ItemStack[]) ArrayUtils.addAll(ingredients, choiceIngredients);
    }
}
