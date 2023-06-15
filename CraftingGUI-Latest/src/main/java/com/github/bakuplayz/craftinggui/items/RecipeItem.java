package com.github.bakuplayz.craftinggui.items;

import com.github.bakuplayz.craftinggui.CraftingGUI;
import com.github.bakuplayz.craftinggui.utils.CraftUtil;
import com.github.bakuplayz.craftinggui.utils.ItemUtil;
import com.github.bakuplayz.craftinggui.utils.NameUtil;
import lombok.Getter;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.2.6
 */
public final class RecipeItem {

    private final CraftingGUI plugin;
    private final @Getter Recipe recipe;
    private final @Getter ItemStack result;

    private @Getter String recipeID;
    private @Getter String recipeName;
    private @Getter MenuItem menuItem;

    private @Getter List<ItemStack> ingredients = new ArrayList<>();
    private @Getter Map<Character, RecipeChoice> choices = new HashMap<>();

    public RecipeItem(final @NotNull CraftingGUI plugin, final @NotNull Recipe recipe) {
        this.plugin = plugin;
        this.recipe = recipe;
        this.result = recipe.getResult();

        setChoices();
        setRecipeID();
        setIngredients();
        setRecipeName();
        setMenuItem();
    }

    private void setRecipeID() {
        byte[] resultKey = result.toString().getBytes();
        byte[] ingredientsKey = ingredients.toString().getBytes();
        byte[] totalKey = ArrayUtils.addAll(resultKey, ingredientsKey);

        this.recipeID = UUID.nameUUIDFromBytes(totalKey).toString();
    }

    private void setMenuItem() {
        this.menuItem = new MenuItem(plugin, this);
    }

    private void setRecipeName() {
        this.recipeName = NameUtil.lookup(recipe.getResult());
    }

    private void setIngredients() {
        if (recipe instanceof ShapedRecipe) setShapedIngredients();
        if (recipe instanceof ShapelessRecipe) setShapelessIngredients();
        combineIngredients();
    }

    private void combineIngredients() {
        HashMap<ItemStack, Integer> mapOfIngredients = new HashMap<>();
        for (ItemStack ingredient : ingredients) {
            mapOfIngredients.merge(ingredient, ingredient.getAmount(), Integer::sum);
        }
        mapOfIngredients.forEach(ItemStack::setAmount);
        this.ingredients = new ArrayList<>(mapOfIngredients.keySet());
    }

    private void setShapedIngredients() {
        ShapedRecipe shapedRecipe = (ShapedRecipe) recipe;
        Map<Character, ItemStack> map = shapedRecipe.getIngredientMap();
        for (String row : shapedRecipe.getShape()) {
            for (char i : row.toCharArray()) {
                ItemStack ingredient = map.get(i);
                if (ingredient == null) continue;
                this.ingredients.add(ingredient);
            }
        }
    }

    private void setShapelessIngredients() {
        ShapelessRecipe shapelessRecipe = (ShapelessRecipe) recipe;
        List<ItemStack> ingredients = shapelessRecipe.getIngredientList();
        for (ItemStack ingredient : ingredients) {
            if (ingredient == null) continue;
            this.ingredients.add(ingredient);
        }
    }

    private void setChoices() {
        if (recipe instanceof ShapedRecipe) setShapedChoices();
        if (recipe instanceof ShapelessRecipe) setShapelessChoices();
    }

    private void setShapelessChoices() {
        ShapelessRecipe shapelessRecipe = (ShapelessRecipe) recipe;
        for (RecipeChoice choice : shapelessRecipe.getChoiceList()) {
            this.choices.put('s', choice);
        }
    }

    private void setShapedChoices() {
        ShapedRecipe shapedRecipe = (ShapedRecipe) recipe;
        this.choices = shapedRecipe.getChoiceMap();
    }

    public boolean hasChoice() {
        return !choices.isEmpty();
    }

    public boolean matchesChoice(ItemStack item) {
        if (!hasChoice()) return false;
        for (RecipeChoice choice : choices.values()) {
            if (choice == null && item == null) return true;
            if (choice == null ^ item == null) continue;
            if (CraftUtil.isSimilar(choice, item)) return true;
        }
        return false;
    }

    public boolean isBanned() {
        return plugin.getRecipeConfig().getBannedRecipes().contains(recipeID);
    }

    public ItemStack @NotNull [] getChoiceIngredients(final @NotNull Inventory inventory, final int amount) {
        Inventory tempInventory = Bukkit.createInventory(null, 36);
        tempInventory.setStorageContents(inventory.getStorageContents());

        List<ItemStack> tempIngredients = new ArrayList<>();
        for (ItemStack ingredient : ingredients) {
            int totalAmount = ingredient.getAmount() * amount;

            for (RecipeChoice choice : choices.values()) {
                if (totalAmount == 0) break;
                if (choice == null) continue;

                for (ItemStack item : tempInventory.getStorageContents()) {
                    if (item == null) continue;
                    if (!choice.test(item)) continue;
                    if (item.getAmount() >= totalAmount) {
                        ItemStack i = new ItemUtil(item, totalAmount).toItemStack();
                        tempIngredients.add(i);
                        tempInventory.remove(i);
                        totalAmount = 0;
                    } else {
                        tempIngredients.add(item);
                        tempInventory.remove(item);
                        totalAmount -= item.getAmount();
                    }
                }
            }
        }

        return tempIngredients.toArray(new ItemStack[0]);
    }

    public ItemStack @NotNull [] getIngredientsAsArray(final int amount) {
        List<ItemStack> tempIngredients = new ArrayList<>();
        for (ItemStack ingredient : ingredients) {
            if (matchesChoice(ingredient)) continue;
            tempIngredients.add(new ItemUtil(ingredient)
                    .setAmount(ingredient.getAmount() * amount)
                    .toItemStack());
        }
        return tempIngredients.toArray(new ItemStack[0]);
    }
}