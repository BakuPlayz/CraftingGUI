package com.github.bakuplayz.craftinggui.recipe;

import com.github.bakuplayz.craftinggui.CraftingGUI;
import com.github.bakuplayz.craftinggui.api.LanguageAPI;
import com.github.bakuplayz.craftinggui.items.MenuItem;
import com.github.bakuplayz.craftinggui.items.RecipeItem;
import com.github.bakuplayz.craftinggui.utils.NameUtil;
import com.github.bakuplayz.craftinggui.utils.VersionUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.2.6
 */
public final class RecipeManager {

    private final CraftingGUI plugin;

    private @Getter List<MenuItem> menuItems = new ArrayList<>();
    private final @Getter List<RecipeItem> recipeItems = new ArrayList<>();

    public RecipeManager(final CraftingGUI plugin) {
        this.plugin = plugin;

        addMissingRecipes();
    }

    public void loadRecipes() {
        LanguageAPI.Console.LOAD_RECIPES.sendMessage();
        try {
            Bukkit.recipeIterator().forEachRemaining(recipe -> {
                if (isBadRecipe(recipe)) return;
                if (recipe.getResult().getAmount() == 0) return;
                if (recipe instanceof ShapedRecipe || recipe instanceof ShapelessRecipe) {
                    RecipeItem recipeItem = new RecipeItem(plugin, recipe);
                    menuItems.add(recipeItem.getMenuItem());
                    recipeItems.add(recipeItem);
                }
            });
            sortMenuItems();
        } catch (Exception e) {
            e.printStackTrace();
            LanguageAPI.Console.ERROR_RECIPES.sendMessage();
        } finally {
            LanguageAPI.Console.LOADED_RECIPES.sendMessage();
        }
    }

    public void reloadRecipes() {
        LanguageAPI.Console.RELOAD_RECIPES.sendMessage();

        recipeItems.clear();
        menuItems.clear();

        loadRecipes();
    }

    private void sortMenuItems() {
        this.menuItems = menuItems.stream()
                .map(MenuItem::getRecipeItem)
                .sorted(Comparator.comparing(RecipeItem::getRecipeName))
                .map(RecipeItem::getMenuItem)
                .collect(Collectors.toList());
    }

    private void addMissingRecipes() {
        if (VersionUtil.isInInterval(11.0, 12.9)) {
            final List<String> COLORS = Arrays.asList("WHITE", "ORANGE", "MAGENTA", "LIGHT_BLUE", "YELLOW", "LIME", "PINK", "GRAY", "SILVER", "CYAN", "PURPLE", "BLUE", "BROWN", "GREEN", "RED", "BLACK");
            for (String color : COLORS) {
                ItemStack result = new ItemStack(Material.getMaterial(color + "_SHULKER_BOX"));
                ShapelessRecipe recipe = new ShapelessRecipe(result);

                recipe.addIngredient(Material.PURPLE_SHULKER_BOX);
                recipe.addIngredient(Material.INK_SACK, (COLORS.size() - 1) - COLORS.indexOf(color));

                Bukkit.addRecipe(recipe);
            }
        }
    }

    private boolean isBadRecipe(@NotNull Recipe recipe) {
        ItemStack result = recipe.getResult();
        String name = NameUtil.lookup(result);
        if (name.equals("")) return true;
        if (name.equals("Tipped Arrow")) return true;

        if (name.equals("Leather Helmet")) {
            return recipe instanceof ShapelessRecipe;
        }

        if (name.equals("Empty Map")) {
            RecipeItem emptyMap = new RecipeItem(plugin, recipe);
            ItemStack compass = new ItemStack(Material.COMPASS);
            return !emptyMap.getIngredients().contains(compass);
        }

        return false;
    }
}
