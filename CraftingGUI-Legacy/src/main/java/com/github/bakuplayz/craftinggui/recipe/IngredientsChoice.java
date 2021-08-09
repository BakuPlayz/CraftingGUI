package com.github.bakuplayz.craftinggui.recipe;

import com.github.bakuplayz.craftinggui.utils.InventoryUtil;
import com.github.bakuplayz.craftinggui.utils.ItemUtil;
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

    private final ItemStack[] ingredients;

    private ArrayList<ItemStack> tempIngredients = new ArrayList<>();
    private final Inventory tempInventory = InventoryUtil.createInventory();

    public IngredientsChoice(final ItemStack... ingredients) {
        this.ingredients = ingredients;
    }

    public boolean matchesFirst(final ItemStack item) {
        if (0 >= ingredients.length) return false;
        return item.getType() == ingredients[0].getType();
    }

    public boolean matchesSecond(final ItemStack item) {
        if (1 >= ingredients.length) return false;
        return item.getType() == ingredients[1].getType();
    }

    public boolean containsIngredients(final Inventory inventory, final int amount) {
        if (ingredients.length >= 1) {
            ItemStack item = ingredients[0];
            if (!inventory.contains(item.getType(), item.getAmount() * amount)) return false;
        }
        if (ingredients.length == 2) {
            ItemStack item = ingredients[1];
            if (!inventory.contains(item.getType(), item.getAmount() * amount)) return false;
        }
        return ingredients.length != 0;
    }

    public ItemStack @NotNull [] getIngredients(final @NotNull Inventory inventory, final int amount) {
        int first = ingredients.length >= 1 ? ingredients[0].getAmount() * amount : -1;
        int second = ingredients.length == 2 ? ingredients[1].getAmount() * amount : -1;

        tempIngredients = new ArrayList<>();
        InventoryUtil.setStorage(tempInventory, inventory);

        for (ItemStack item : InventoryUtil.getStorage(tempInventory)) {
            if (first == 0 && second == -1) break;
            if (first == 0 && second == 0) break;
            if (item == null || item.getType() == Material.AIR) continue;
            if (first > 0 && item.getType() == ingredients[0].getType()) {
                first -= addIngredient(item, first);
            } else if (second > 0 && item.getType() == ingredients[1].getType()) {
                second -= addIngredient(item, second);
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
