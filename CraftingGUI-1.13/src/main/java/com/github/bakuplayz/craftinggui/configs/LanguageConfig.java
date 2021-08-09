package com.github.bakuplayz.craftinggui.configs;

import com.github.bakuplayz.craftinggui.CraftingGUI;
import com.github.bakuplayz.craftinggui.api.LanguageAPI;
import org.bukkit.ChatColor;
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
public final class LanguageConfig {

    private final CraftingGUI plugin;

    private File file;
    private FileConfiguration config;
    private final String FILE_NAME = "language.yml";

    public LanguageConfig(final CraftingGUI plugin) {
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

    public void reloadConfig() {
        if (config == null || file == null) return;

        config = YamlConfiguration.loadConfiguration(file);
        LanguageAPI.Console.FILE_RELOAD.sendMessage(FILE_NAME);
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public @NotNull String getMessage(String category, String subCategory, String key) {
        String message = getConfig().getString(category + "." + subCategory + "." + key);
        if (message == null) return ChatColor.RED + "Error: Message is null!";

        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public @NotNull List<String> getColors() {
        return getConfig().getStringList("menu.colorPicker.colors");
    }
}
