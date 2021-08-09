package com.github.bakuplayz.craftinggui.configs;

import com.github.bakuplayz.craftinggui.CraftingGUI;
import com.github.bakuplayz.craftinggui.api.LanguageAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.2.6
 */
public final class OldToNewConfigs {

    private final CraftingGUI plugin;

    private final File langFile;
    private final File pluginFile;
    private final File playerFile;
    private final File recipesFile;

    private boolean updatePlayer;
    private boolean updateConsole;
    private boolean isMultiEnabled;
    private boolean isTableEnabled;

    public OldToNewConfigs(final @NotNull CraftingGUI plugin) {
        this.plugin = plugin;
        this.langFile = new File(plugin.getDataFolder(), "lang.yml");
        this.pluginFile = new File(plugin.getDataFolder(), "config.yml");
        this.recipesFile = new File(plugin.getDataFolder(), "recipes.yml");
        this.playerFile = new File(plugin.getDataFolder(), "playersettings.yml");

        if (hasNewSettings()) return;

        saveOldSettings();
    }

    private boolean hasNewSettings() {
        if (!langFile.exists()) return true;
        if (!playerFile.exists()) return true;
        return !recipesFile.exists();
    }

    private void saveOldSettings() {
        setPluginSettings();
    }

    private void setPluginSettings() {
        FileConfiguration pluginConfig = YamlConfiguration.loadConfiguration(pluginFile);
        this.updatePlayer = pluginConfig.getBoolean("Update-Message.Player");
        this.updateConsole = pluginConfig.getBoolean("Update-Message.Console");
        this.isMultiEnabled = pluginConfig.getBoolean("Admin.Command.Enabled");
        this.isTableEnabled = pluginConfig.getBoolean("Admin.Crafting-Table.Enabled");
    }

    private void deleteOldSettings() {
        try {
            Files.deleteIfExists(langFile.toPath());
            Files.deleteIfExists(playerFile.toPath());
            Files.deleteIfExists(recipesFile.toPath());
            Files.deleteIfExists(pluginFile.toPath());
            LanguageAPI.Console.OLD_SETTINGS_REMOVED.sendMessage();
        } catch (IOException exception) {
            exception.printStackTrace();
            LanguageAPI.Console.OLD_SETTINGS_FAILED.sendMessage();
        } finally {
            LanguageAPI.Console.OLD_SETTINGS_SUCCESS.sendMessage();
        }
    }

    public void setNewSettings() {
        if (hasNewSettings()) return;
        deleteOldSettings();

        plugin.getConfig().set("Admin", null);
        plugin.getConfig().set("Update-Message", null);
        plugin.getConfig().set("Crafting-Table", null);
        plugin.getConfig().set("updateMessage.player", updatePlayer);
        plugin.getConfig().set("updateMessage.console", updateConsole);
        plugin.getConfig().set("isEnabled.multi", isMultiEnabled);
        plugin.getConfig().set("isEnabled.table", isTableEnabled);
        plugin.saveConfig();
    }
}
