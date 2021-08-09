package com.github.bakuplayz.craftinggui.commands.subcommands;

import com.github.bakuplayz.craftinggui.CraftingGUI;
import com.github.bakuplayz.craftinggui.api.LanguageAPI;
import com.github.bakuplayz.craftinggui.commands.SubCommand;
import com.github.bakuplayz.craftinggui.menu.menus.ManageRecipesMenu;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.2.6
 */
public final class ManageCommand implements SubCommand {

    private final CraftingGUI plugin;

    public ManageCommand(final CraftingGUI plugin) {
        this.plugin = plugin;
    }

    @Contract(pure = true)
    @Override
    public @NotNull String getName() {
        return "manage";
    }

    @Override
    public @NotNull String getDescription() {
        return LanguageAPI.Command.RECIPES_DESCRIPTION.getMessage(plugin);
    }

    @Contract(pure = true)
    @Override
    public @NotNull String getUsage() {
        return "craftinggui manage";
    }

    @Contract(pure = true)
    @Override
    public @NotNull String getPermission() {
        return "craftinggui.manage";
    }

    @Override
    public boolean hasPermission(@NotNull Player player) {
        return player.hasPermission("craftinggui.*") || player.hasPermission(getPermission());
    }

    @Override
    public void perform(Player player, String[] args) {
        new ManageRecipesMenu(plugin, player).open();
    }
}
