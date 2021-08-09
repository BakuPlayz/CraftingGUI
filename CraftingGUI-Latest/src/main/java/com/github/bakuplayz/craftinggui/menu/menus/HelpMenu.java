package com.github.bakuplayz.craftinggui.menu.menus;

import com.github.bakuplayz.craftinggui.CraftingGUI;
import com.github.bakuplayz.craftinggui.api.LanguageAPI;
import com.github.bakuplayz.craftinggui.commands.SubCommand;
import com.github.bakuplayz.craftinggui.menu.DefaultMenu;
import com.github.bakuplayz.craftinggui.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.2.6
 */
public final class HelpMenu extends DefaultMenu {

    private final List<SubCommand> subCommands;

    public HelpMenu(final CraftingGUI plugin, final Player player) {
        setPlayer(player);
        setPlugin(plugin);
        setDefaultSettings();
        this.subCommands = plugin.getCommandManager().getSubCommands();
    }

    @Override
    public @NotNull String getTitle() {
        return LanguageAPI.Menu.HELP_TITLE.getTitle(plugin);
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void setMenuItems() {
        inventory.addItem(getDefaultCommandItem());

        for (SubCommand subCommand : subCommands) {
            inventory.addItem(getSubCommandItem(subCommand));
        }
    }

    @Override
    public void handleMenu(final @NotNull InventoryClickEvent event) {
        if (event.getSlot() > 0) {
            player.closeInventory();
            player.performCommand(subCommands.get(event.getSlot() - 1).getUsage());
            return;
        }

        player.closeInventory();
        player.performCommand("craftinggui");
    }

    @Override
    public void handleDrag(final @NotNull InventoryDragEvent event) {
        event.setCancelled(true);
    }

    private @NotNull ItemStack getSubCommandItem(@NotNull SubCommand cmd) {
        return new ItemUtil(Material.BOOK)
                .setDisplayName(LanguageAPI.Menu.HELP_ITEM_NAME.getMessage(plugin, cmd.getName()))
                .setLore(LanguageAPI.Menu.HELP_ITEM_DESCRIPTION.getMessage(plugin, cmd.getDescription()),
                        LanguageAPI.Menu.HELP_ITEM_PERMISSION.getMessage(plugin, cmd.getPermission()),
                        LanguageAPI.Menu.HELP_ITEM_USAGE.getMessage(plugin, cmd.getUsage()))
                .toItemStack();
    }

    private @NotNull ItemStack getDefaultCommandItem() {
        String description = LanguageAPI.Command.DEFAULT_DESCRIPTION.getMessage(plugin);
        return new ItemUtil(Material.BOOK)
                .setDisplayName(LanguageAPI.Menu.HELP_ITEM_NAME.getMessage(plugin, ""))
                .setLore(LanguageAPI.Menu.HELP_ITEM_DESCRIPTION.getMessage(plugin, description),
                        LanguageAPI.Menu.HELP_ITEM_PERMISSION.getMessage(plugin, "None"),
                        LanguageAPI.Menu.HELP_ITEM_USAGE.getMessage(plugin, "craftinggui"))
                .toItemStack();
    }
}