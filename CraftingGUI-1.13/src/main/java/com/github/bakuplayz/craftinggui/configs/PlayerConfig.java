package com.github.bakuplayz.craftinggui.configs;

import com.github.bakuplayz.craftinggui.CraftingGUI;
import com.github.bakuplayz.craftinggui.api.LanguageAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.2.6
 */
public final class PlayerConfig {

    private final CraftingGUI plugin;

    private File file;
    private FileConfiguration config;
    private final String FILE_NAME = "player_settings.yml";

    public PlayerConfig(final CraftingGUI plugin) {
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

    public void setDefaultSettings(Player player) {
        setTableChoice(player, "Table");
        setMultiChoice(player, "Multi");

        short color = getSelectedColor(player);
        if (color > 15 || color <= 0) setSelectedColor(player, (short) 0);
    }

    public boolean isNotRegistered(Player player) {
        String multiChoice = getMultiChoice(player);
        String tableChoice = getTableChoice(player);
        return multiChoice == null || tableChoice == null;
    }

    public short getSelectedColor(@NotNull Player player) {
        return (short) getConfig().getInt("players." + player.getUniqueId() + ".selectedColor");
    }

    public void setSelectedColor(@NotNull Player player, short newColor) {
        setSelectedColor(player.getUniqueId(), newColor);
    }

    public void setSelectedColor(@NotNull UUID uuid, short newColor) {
        config.set("players." + uuid + ".selectedColor", newColor);
        saveConfig();
    }

    public String getTableChoice(@NotNull Player player) {
        return getConfig().getString("players." + player.getUniqueId() + ".table.choice");
    }

    public void setTableChoice(@NotNull Player player, String newChoice) {
        config.set("players." + player.getUniqueId() + ".table.choice", newChoice);
        saveConfig();
    }

    public String getMultiChoice(@NotNull Player player) {
        return getConfig().getString("players." + player.getUniqueId() + ".multi.choice");
    }

    public void setMultiChoice(@NotNull Player player, String newChoice) {
        config.set("players." + player.getUniqueId() + ".multi.choice", newChoice);
        saveConfig();
    }
}
