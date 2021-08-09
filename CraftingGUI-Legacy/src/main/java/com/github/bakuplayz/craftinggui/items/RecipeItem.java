package com.github.bakuplayz.craftinggui.items;

import com.github.bakuplayz.craftinggui.CraftingGUI;
import com.github.bakuplayz.craftinggui.recipe.IngredientsChoice;
import com.github.bakuplayz.craftinggui.recipe.IngredientsChoices;
import com.github.bakuplayz.craftinggui.utils.ItemUtil;
import com.github.bakuplayz.craftinggui.utils.NameUtil;
import lombok.Getter;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
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
    private @Getter MenuItem menuItem;
    private @Getter String recipeName;

    private @Getter IngredientsChoice choice;
    private @Getter List<ItemStack> ingredients = new ArrayList<>();

    public RecipeItem(final @NotNull CraftingGUI plugin, final @NotNull Recipe recipe) {
        this.plugin = plugin;
        this.recipe = recipe;
        this.result = recipe.getResult();

        setRecipeName();
        setIngredientsChoice();
        setIngredients();
        setMenuItem();
        setRecipeID();
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
                if (ingredient == null || ingredient.getType() == Material.AIR) continue;
                if (ingredient.getDurability() > 32766) {
                    ingredient.setDurability((short) 0);
                }
                this.ingredients.add(ingredient);
            }
        }
    }

    private void setShapelessIngredients() {
        ShapelessRecipe shapelessRecipe = (ShapelessRecipe) recipe;
        List<ItemStack> ingredients = shapelessRecipe.getIngredientList();
        for (ItemStack ingredient : ingredients) {
            if (ingredient == null) continue;
            if (ingredient.getDurability() > 32766) {
                ingredient.setDurability((short) 0);
            }
            this.ingredients.add(ingredient);
        }
    }

    private void setIngredientsChoice() {
        String enumName = recipeName.toUpperCase().replace(" ", "_");
        for (IngredientsChoices choice : IngredientsChoices.values()) {
            IngredientsChoice[] choices = choice.getChoices();
            if (choice.name().equals(enumName)) {
                this.choice = choices[0];
                return;
            }
        }
        this.choice = null;
    }

    public boolean hasChoice() {
        return choice != null;
    }

    public boolean isBanned() {
        return plugin.getRecipeConfig().getBannedRecipes().contains(recipeID);
    }

    public ItemStack @NotNull [] getIngredientsAsArray(int amount) {
        List<ItemStack> tempIngredients = new ArrayList<>();
        for (ItemStack ingredient : ingredients) {
            if (hasChoice() && (choice.matchesFirst(ingredient) ^ choice.matchesSecond(ingredient))) continue;
            tempIngredients.add(new ItemUtil(ingredient)
                    .setAmount(ingredient.getAmount() * amount)
                    .toItemStack());
        }
        return tempIngredients.toArray(new ItemStack[0]);
    }
}
