package com.github.bakuplayz.craftinggui.commands;

import com.github.bakuplayz.craftinggui.CraftingGUI;
import com.github.bakuplayz.craftinggui.api.LanguageAPI;
import com.github.bakuplayz.craftinggui.commands.subcommands.*;
import com.github.bakuplayz.craftinggui.menu.menus.CraftingTableMenu;
import com.github.bakuplayz.craftinggui.menu.menus.MultiCraftingMenu;
import com.github.bakuplayz.craftinggui.menu.menus.SettingsMenu;
import com.github.bakuplayz.craftinggui.settings.PlayerSettings;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.2.6
 */
public final class CommandManager implements TabExecutor {

    private final CraftingGUI plugin;
    private final PlayerSettings playerSettings;

    private final @Getter List<SubCommand> subCommands = new ArrayList<>();

    public CommandManager(final @NotNull CraftingGUI plugin) {
        this.playerSettings = plugin.getPlayerSettings();
        this.plugin = plugin;

        registerSubCommands();
    }

    private void registerSubCommands() {
        subCommands.add(new HelpCommand(plugin));
        subCommands.add(new ManageCommand(plugin));
        subCommands.add(new ReloadCommand(plugin));
        subCommands.add(new ResetCommand(plugin));
        subCommands.add(new SettingsCommand(plugin));
    }

    @Override
    public boolean onCommand(final @NotNull CommandSender sender, final @NotNull Command command, final @NotNull String label, final String[] args) {
        if (!(sender instanceof Player)) {
            LanguageAPI.Command.PLAYER_ONLY_COMMAND.sendMessage(plugin, sender);
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            if (playerSettings.isNotRegistered(player)) {
                playerSettings.setDefaultSettings(player);
            }

            switch (playerSettings.getMultiChoice(player)) {
                case "Default":
                case "Multi":
                    if (plugin.isMultiEnabled()) {
                        new MultiCraftingMenu(plugin, player).open();
                        return true;
                    }

                    if (plugin.isTableEnabled()) {
                        new CraftingTableMenu(plugin, player).open();
                        return true;
                    }
                case "Table":
                    if (plugin.isTableEnabled()) {
                        new CraftingTableMenu(plugin, player).open();
                        return true;
                    }

                    if (plugin.isMultiEnabled()) {
                        new MultiCraftingMenu(plugin, player).open();
                        return true;
                    }
                default:
                    new SettingsMenu(plugin, player).open();
                    return true;
            }
        }

        for (SubCommand subCommand : getSubCommands()) {
            if (args[0].equalsIgnoreCase(subCommand.getName())) {
                if (!subCommand.hasPermission(player)) {
                    LanguageAPI.Command.PLAYER_LACK_PERMISSION.sendMessage(plugin, player, subCommand.getPermission());
                    return true;
                }
                subCommand.perform(player, args);
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(final @NotNull CommandSender sender, final @NotNull Command command, final @NotNull String alias, final String @NotNull [] args) {
        if (args.length != 1) return new ArrayList<>();
        return getSubCommands().stream()
                .map(SubCommand::getName)
                .filter(subCommand -> subCommand.startsWith(args[0]))
                .sorted()
                .collect(Collectors.toList());
    }
}
