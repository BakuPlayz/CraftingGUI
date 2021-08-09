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
import java.util.Objects;
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
        final List<String> WOOD_TYPES = List.of("ACACIA", "BIRCH", "DARK_OAK", "JUNGLE", "OAK", "SPRUCE");
        final List<String> DIFFERENT_TYPES = List.of("WOOD", "LOG");
        final List<String> VARIANTS = List.of("STRIPPED_", "");
        for (String variant : VARIANTS) {
            for (String type : WOOD_TYPES) {
                for (String different : DIFFERENT_TYPES) {
                    if (variant.equals("") && Objects.equals(different, "WOOD")) continue;
                    NamespacedKey key = new NamespacedKey(plugin, variant.toLowerCase() + type.toLowerCase() + different.toLowerCase());
                    ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.getMaterial(type + "_PLANKS"), 4));
                    recipe.addIngredient(Material.getMaterial(variant + type + "_" + different));
                    Bukkit.addRecipe(recipe);
                }
            }
        }

        final List<String> COLORS = List.of("BLACK", "BLUE", "BROWN", "CYAN", "GRAY", "GREEN", "LIGHT_BLUE", "LIGHT_GRAY", "LIME", "MAGENTA", "ORANGE", "PINK", "PURPLE", "RED", "WHITE", "YELLOW");
        final List<String> DYES = List.of("INK_SAC", "LAPIS_LAZULI", "COCOA_BEANS", "CYAN_DYE", "GRAY_DYE", "CACTUS_GREEN", "LIGHT_BLUE_DYE", "LIGHT_GRAY_DYE", "LIME_DYE", "MAGENTA_DYE", "ORANGE_DYE", "PINK_DYE", "PURPLE_DYE", "ROSE_RED", "BONE_MEAL", "DANDELION_YELLOW");
        for (int i = 0; i < COLORS.size(); i++) {
            NamespacedKey key = new NamespacedKey(plugin, "SHULKER_" + COLORS.get(i).toLowerCase());
            ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.getMaterial(COLORS.get(i) + "_SHULKER_BOX")));
            recipe.addIngredient(Material.SHULKER_BOX);
            recipe.addIngredient(Material.getMaterial(DYES.get(i)));
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
