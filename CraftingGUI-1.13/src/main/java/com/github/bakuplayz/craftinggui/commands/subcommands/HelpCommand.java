package com.github.bakuplayz.craftinggui.commands.subcommands;

import com.github.bakuplayz.craftinggui.CraftingGUI;
import com.github.bakuplayz.craftinggui.api.LanguageAPI;
import com.github.bakuplayz.craftinggui.commands.SubCommand;
import com.github.bakuplayz.craftinggui.menu.menus.HelpMenu;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.2.6
 */
public final class HelpCommand implements SubCommand {

    private final CraftingGUI plugin;

    public HelpCommand(final CraftingGUI plugin) {
        this.plugin = plugin;
    }

    @Contract(pure = true)
    @Override
    public @NotNull String getName() {
        return "help";
    }

    @Override
    public @NotNull String getDescription() {
        return LanguageAPI.Command.HELP_DESCRIPTION.getMessage(plugin);
    }

    @Contract(pure = true)
    @Override
    public @NotNull String getUsage() {
        return "craftinggui help";
    }

    @Contract(pure = true)
    @Override
    public @NotNull String getPermission() {
        return "craftinggui.help";
    }

    @Override
    public boolean hasPermission(@NotNull Player player) {
        return player.hasPermission("craftinggui.*") || player.hasPermission(getPermission());
    }

    @Override
    public void perform(Player player, String[] args) {
        new HelpMenu(plugin, player).open();
    }
}
