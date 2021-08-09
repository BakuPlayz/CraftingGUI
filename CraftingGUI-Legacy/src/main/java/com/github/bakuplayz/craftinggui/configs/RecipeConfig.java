package com.github.bakuplayz.craftinggui.configs;

import com.github.bakuplayz.craftinggui.CraftingGUI;
import com.github.bakuplayz.craftinggui.api.LanguageAPI;
import com.github.bakuplayz.craftinggui.items.RecipeItem;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.2.6
 */
public final class RecipeConfig {

    private final CraftingGUI plugin;

    private File file;
    private FileConfiguration config;
    private final String FILE_NAME = "banned_recipes.yml";

    public RecipeConfig(final CraftingGUI plugin) {
        this.plugin = plugin;
    }

    public void setup() {
        file = new File(plugin.getDataFolder(), FILE_NAME);

        try {
            if (file.createNewFile()) {
                plugin.saveResource(FILE_NAME, true);
            }
        } catch (IOException e) {
            e.printStackTrace();
            LanguageAPI.Console.FILE_SETUP_FAILED.sendMessage(FILE_NAME);
        } finally {
            config = YamlConfiguration.loadConfiguration(file);
            LanguageAPI.Console.FILE_SETUP_LOAD.sendMessage(FILE_NAME);
        }
    }

    public void saveConfig() {
        if (config == null || file == null) return;

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
            LanguageAPI.Console.FILE_SAVE_FAILED.sendMessage(FILE_NAME);
        }
    }

    public void reloadConfig() {
        if (config == null || file == null) return;

        config = YamlConfiguration.loadConfiguration(file);
        LanguageAPI.Console.FILE_RELOAD.sendMessage(FILE_NAME);
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public List<String> getBannedRecipes() {
        return getConfig().getStringList("recipes");
    }

    public void banRecipe(@NotNull RecipeItem item) {
        List<String> bannedRecipes = getBannedRecipes();
        bannedRecipes.add(item.getRecipeID());
        updateBannedRecipes(bannedRecipes);
    }

    public void unbanRecipe(@NotNull RecipeItem item) {
        List<String> bannedRecipes = getBannedRecipes();
        bannedRecipes.remove(item.getRecipeID());
        updateBannedRecipes(bannedRecipes);
    }

    private void updateBannedRecipes(List<String> bannedRecipes) {
        config.set("recipes", bannedRecipes);
        saveConfig();
    }
}
