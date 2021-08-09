package com.github.bakuplayz.craftinggui.commands.subcommands;

import com.github.bakuplayz.craftinggui.CraftingGUI;
import com.github.bakuplayz.craftinggui.api.LanguageAPI;
import com.github.bakuplayz.craftinggui.commands.SubCommand;
import com.github.bakuplayz.craftinggui.menu.menus.SettingsMenu;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.2.6
 */
public final class SettingsCommand implements SubCommand {

    private final CraftingGUI plugin;

    public SettingsCommand(final CraftingGUI plugin) {
        this.plugin = plugin;
    }

    @Contract(pure = true)
    @Override
    public @NotNull String getName() {
        return "settings";
    }

    @Override
    public @NotNull String getDescription() {
        return LanguageAPI.Command.SETTINGS_DESCRIPTION.getMessage(plugin);
    }

    @Contract(pure = true)
    @Override
    public @NotNull String getUsage() {
        return "craftinggui settings";
    }

    @Contract(pure = true)
    @Override
    public @NotNull String getPermission() {
        return "None";
    }

    @Override
    public boolean hasPermission(Player player) {
        return true;
    }

    @Override
    public void perform(Player player, String[] args) {
        new SettingsMenu(plugin, player).open();
    }
}
