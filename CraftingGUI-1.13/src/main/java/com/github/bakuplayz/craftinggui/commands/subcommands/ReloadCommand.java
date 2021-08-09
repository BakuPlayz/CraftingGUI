package com.github.bakuplayz.craftinggui.commands.subcommands;

import com.github.bakuplayz.craftinggui.CraftingGUI;
import com.github.bakuplayz.craftinggui.api.LanguageAPI;
import com.github.bakuplayz.craftinggui.commands.SubCommand;
import com.github.bakuplayz.craftinggui.configs.LanguageConfig;
import com.github.bakuplayz.craftinggui.configs.PlayerConfig;
import com.github.bakuplayz.craftinggui.configs.RecipeConfig;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.2.6
 */
public final class ReloadCommand implements SubCommand {

    private final CraftingGUI plugin;

    private final PlayerConfig playerConfig;
    private final RecipeConfig recipeConfig;
    private final LanguageConfig languageConfig;

    public ReloadCommand(final @NotNull CraftingGUI plugin) {
        this.plugin = plugin;
        this.languageConfig = plugin.getLanguageConfig();
        this.playerConfig = plugin.getPlayerConfig();
        this.recipeConfig = plugin.getRecipeConfig();
    }

    @Contract(pure = true)
    @Override
    public @NotNull String getName() {
        return "reload";
    }

    @Override
    public @NotNull String getDescription() {
        return LanguageAPI.Command.RELOAD_DESCRIPTION.getMessage(plugin);
    }

    @Contract(pure = true)
    @Override
    public @NotNull String getUsage() {
        return "craftinggui reload";
    }

    @Contract(pure = true)
    @Override
    public @NotNull String getPermission() {
        return "craftinggui.reload";
    }

    @Override
    public boolean hasPermission(@NotNull Player player) {
        return player.hasPermission("craftinggui.*") || player.hasPermission(getPermission());
    }

    @Override
    public void perform(Player player, String[] args) {
        plugin.reloadConfig();
        LanguageAPI.Console.FILE_RELOAD.sendMessage("config.yml");

        playerConfig.reloadConfig();
        recipeConfig.reloadConfig();
        languageConfig.reloadConfig();
        plugin.getRecipeManager().reloadRecipes();

        LanguageAPI.Command.RELOAD_SUCCESS.sendMessage(plugin, player);
    }
}
