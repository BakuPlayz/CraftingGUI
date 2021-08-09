package com.github.bakuplayz.craftinggui;

import com.github.bakuplayz.craftinggui.api.LanguageAPI;
import com.github.bakuplayz.craftinggui.commands.CommandManager;
import com.github.bakuplayz.craftinggui.configs.LanguageConfig;
import com.github.bakuplayz.craftinggui.configs.OldToNewConfigs;
import com.github.bakuplayz.craftinggui.configs.PlayerConfig;
import com.github.bakuplayz.craftinggui.configs.RecipeConfig;
import com.github.bakuplayz.craftinggui.listeners.CraftingTableListener;
import com.github.bakuplayz.craftinggui.listeners.MenuListener;
import com.github.bakuplayz.craftinggui.listeners.UpdateListener;
import com.github.bakuplayz.craftinggui.metric.Metrics;
import com.github.bakuplayz.craftinggui.recipe.RecipeManager;
import com.github.bakuplayz.craftinggui.utils.UpdateUtil;
import com.github.bakuplayz.craftinggui.utils.VersionUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.2.6
 */
public final class CraftingGUI extends JavaPlugin {

    private @Getter RecipeConfig recipeConfig;
    private @Getter PlayerConfig playerConfig;
    private @Getter LanguageConfig languageConfig;
    private @Getter OldToNewConfigs oldToNewConfigs;

    private @Getter UpdateUtil updateUtil;
    private @Getter RecipeManager recipeManager;
    private @Getter CommandManager commandManager;

    private @Getter Metrics metrics;

    @Override
    public void onEnable() {
        if (VersionUtil.isInInterval(12.9, 13.9)) {
            registerConfigs();
            registerManagers();
            registerUtils();

            registerCommands();
            registerEvents();
            setupConfigs();
            loadRecipes();

            updateUtil.startUpdateInterval();
        } else {
            LanguageAPI.Console.NOT_SUPPORTED_VERSION.sendMessage();
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
    }

    private void registerUtils() {
        this.updateUtil = new UpdateUtil(this);
        this.metrics = new Metrics(this, 8457);
    }

    private void registerCommands() {
        PluginCommand command = getCommand("craftinggui");
        if (command == null) {
            LanguageAPI.Console.FAILED_COMMAND_REGISTERING.sendMessage();
            return;
        }

        command.setExecutor(commandManager);
        command.setTabCompleter(commandManager);
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new MenuListener(this), this);
        Bukkit.getPluginManager().registerEvents(new UpdateListener(this), this);
        Bukkit.getPluginManager().registerEvents(new CraftingTableListener(this), this);
    }

    private void registerManagers() {
        this.recipeManager = new RecipeManager(this);
        this.commandManager =  new CommandManager(this);
    }

    private void registerConfigs() {
        this.recipeConfig = new RecipeConfig(this);
        this.playerConfig = new PlayerConfig(this);
        this.languageConfig = new LanguageConfig(this);
        this.oldToNewConfigs = new OldToNewConfigs(this);
    }

    public void setupConfigs() {
        LanguageAPI.Console.FILE_SETUP_LOAD.sendMessage("config.yml");

        getConfig().options().copyDefaults(true);
        saveConfig();

        playerConfig.setup();
        recipeConfig.setup();
        languageConfig.setup();
        oldToNewConfigs.setNewSettings(playerConfig);
    }

    private void loadRecipes() {
        Bukkit.getScheduler().runTaskLater(this, recipeManager::loadRecipes, 1);
    }

    public boolean isMultiEnabled() {
        return getConfig().getBoolean("isEnabled.multi");
    }

    public boolean isTableEnabled() {
        return getConfig().getBoolean("isEnabled.table");
    }
}
