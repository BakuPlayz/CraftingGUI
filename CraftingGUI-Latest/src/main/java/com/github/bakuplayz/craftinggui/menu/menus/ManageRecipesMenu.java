package com.github.bakuplayz.craftinggui.menu.menus;

import com.github.bakuplayz.craftinggui.CraftingGUI;
import com.github.bakuplayz.craftinggui.api.LanguageAPI;
import com.github.bakuplayz.craftinggui.items.MenuItem;
import com.github.bakuplayz.craftinggui.menu.PaginatedMenu;
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
public final class ManageRecipesMenu extends PaginatedMenu {

    private final List<MenuItem> menuItems;

    public ManageRecipesMenu(final CraftingGUI plugin, final Player player) {
        setPlayer(player);
        setPlugin(plugin);
        setDefaultSettings();
        this.menuItems = plugin.getRecipeManager().getMenuItems();
    }

    @Override
    public @NotNull String getTitle() {
        return LanguageAPI.Menu.MANAGE_RECIPES_TITLE.getTitle(plugin);
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void setMenuItems() {
        for (int i = 0; i < MAX_ITEMS_PER_PAGE; i++) {
            itemIndex = MAX_ITEMS_PER_PAGE * page + i;
            if (itemIndex >= menuItems.size()) break;
            inventory.addItem(menuItems.get(itemIndex).getItem());
        }

        if (page != 0) {
            inventory.setItem(48, getPreviousPageItem());
        }

        inventory.setItem(49, getCurrentPageItem());

        if (menuItems.size() > itemIndex) {
            inventory.setItem(50, getNextPageItem());
        }

        inventory.setItem(45, getBackItem());
        inventory.setItem(53, getBackItem());
    }

    @Override
    public void handleMenu(final @NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        assert clicked != null;
        if (clicked.equals(getPreviousPageItem())) {
            if (page > 0) page -= 1;
            updateMenu();
        }

        if (clicked.equals(getNextPageItem())) {
            if (menuItems.size() > itemIndex) page += 1;
            updateMenu();
        }

        for (MenuItem menuItem : menuItems) {
            if (clicked.equals(menuItem.getItem())) {
                new ManageRecipeMenu(plugin, player, menuItem).open();
            }
        }

        if (clicked.equals(getBackItem())) {
            new AdminSettingsMenu(plugin, player).open();
        }
    }

    @Override
    public void handleDrag(final @NotNull InventoryDragEvent event) {
        event.setCancelled(true);
    }
}
