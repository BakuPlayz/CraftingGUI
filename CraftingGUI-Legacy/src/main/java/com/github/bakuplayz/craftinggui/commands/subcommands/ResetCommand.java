package com.github.bakuplayz.craftinggui.commands.subcommands;

import com.github.bakuplayz.craftinggui.CraftingGUI;
import com.github.bakuplayz.craftinggui.api.LanguageAPI;
import com.github.bakuplayz.craftinggui.commands.SubCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
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
public final class ResetCommand implements SubCommand {

    private final CraftingGUI plugin;

    public ResetCommand(final CraftingGUI plugin) {
        this.plugin = plugin;
    }

    @Contract(pure = true)
    @Override
    public @NotNull String getName() {
        return "reset";
    }

    @Override
    public @NotNull String getDescription() {
        return LanguageAPI.Command.RESET_DESCRIPTION.getMessage(plugin);
    }

    @Contract(pure = true)
    @Override
    public @NotNull String getUsage() {
        return "craftinggui reset";
    }

    @Contract(pure = true)
    @Override
    public @NotNull String getPermission() {
        return "craftinggui.reset";
    }

    @Override
    public boolean hasPermission(@NotNull Player player) {
        return player.hasPermission("craftinggui.*") || player.hasPermission(getPermission());
    }

    @Override
    public void perform(Player player, String[] args) {
        try {
            deleteConfigs();
            LanguageAPI.Command.RESET_DELETE.sendMessage(plugin, player);
        } catch (IOException e) {
            e.printStackTrace();
            LanguageAPI.Command.RESET_FAILED.sendMessage(plugin, player);
        } finally {
            plugin.setupConfigs();
            LanguageAPI.Command.RESET_SUCCESS.sendMessage(plugin, player);
        }
    }

    private void deleteConfigs() throws IOException {
        Files.deleteIfExists(new File(plugin.getDataFolder(), "config.yml").toPath());
        Files.deleteIfExists(new File(plugin.getDataFolder(), "language.yml").toPath());
        Files.deleteIfExists(new File(plugin.getDataFolder(), "banned_recipes.yml").toPath());
        Files.deleteIfExists(new File(plugin.getDataFolder(), "player_settings.yml").toPath());
    }
}
