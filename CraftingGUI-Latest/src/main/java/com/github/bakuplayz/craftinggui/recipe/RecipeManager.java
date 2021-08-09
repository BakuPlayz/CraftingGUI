package com.github.bakuplayz.craftinggui.recipe;

import com.github.bakuplayz.craftinggui.CraftingGUI;
import com.github.bakuplayz.craftinggui.api.LanguageAPI;
import com.github.bakuplayz.craftinggui.items.MenuItem;
import com.github.bakuplayz.craftinggui.items.RecipeItem;
import com.github.bakuplayz.craftinggui.utils.NameUtil;
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
    private final @Getter List<RecipeItem> recipeItems = new ArrayList<>();
    private @Getter List<MenuItem> menuItems = new ArrayList<>();

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
        } catch (Exception exception) {
            exception.printStackTrace();
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

    @SuppressWarnings("ConstantConditions")
    private void addMissingRecipes() {
        final List<String> COLORS = List.of("BLACK", "BLUE", "BROWN", "CYAN", "GRAY", "GREEN", "LIGHT_BLUE", "LIGHT_GRAY", "LIME", "MAGENTA", "ORANGE", "PINK", "PURPLE", "RED", "WHITE", "YELLOW");
        final List<Material> DYES = List.of(Material.BLACK_DYE, Material.BLUE_DYE, Material.BROWN_DYE, Material.CYAN_DYE, Material.GRAY_DYE, Material.GREEN_DYE, Material.LIGHT_BLUE_DYE, Material.LIGHT_GRAY_DYE, Material.LIME_DYE, Material.MAGENTA_DYE, Material.ORANGE_DYE, Material.PINK_DYE, Material.PURPLE_DYE, Material.RED_DYE, Material.WHITE_DYE, Material.YELLOW_DYE);
        for (int i = 0; i < COLORS.size(); i++) {
            NamespacedKey key = new NamespacedKey(plugin, "SHULKER_" + COLORS.get(i).toLowerCase());
            ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.getMaterial(COLORS.get(i) + "_SHULKER_BOX")));
            recipe.addIngredient(Material.SHULKER_BOX);
            recipe.addIngredient(DYES.get(i));
            Bukkit.addRecipe(recipe);
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

        if (name.equals("Map")) {
            RecipeItem map = new RecipeItem(plugin, recipe);
            ItemStack compass = new ItemStack(Material.COMPASS);
            return !map.getIngredients().contains(compass);
        }

        return false;
    }
}
