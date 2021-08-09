package com.github.bakuplayz.craftinggui.menu.menus;

import com.github.bakuplayz.craftinggui.CraftingGUI;
import com.github.bakuplayz.craftinggui.api.LanguageAPI;
import com.github.bakuplayz.craftinggui.configs.RecipeConfig;
import com.github.bakuplayz.craftinggui.items.MenuItem;
import com.github.bakuplayz.craftinggui.items.RecipeItem;
import com.github.bakuplayz.craftinggui.menu.DefaultMenu;
import com.github.bakuplayz.craftinggui.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.2.6
 */
public final class ManageRecipeMenu extends DefaultMenu {

    private final RecipeItem recipeItem;
    private final ItemStack selectedItem;

    private final RecipeConfig recipeConfig;

    public ManageRecipeMenu(final CraftingGUI plugin, final Player player, final @NotNull MenuItem selected) {
        setPlayer(player);
        setPlugin(plugin);
        setDefaultSettings();
        this.selectedItem = selected.getItem();
        this.recipeItem = selected.getRecipeItem();
        this.recipeConfig = plugin.getRecipeConfig();
    }

    @Override
    public @NotNull String getTitle() {
        return LanguageAPI.Menu.MANAGE_RECIPE_TITLE.getTitle(plugin);
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(13, selectedItem);
        inventory.setItem(31, getBanItem());
        inventory.setItem(49, getBackItem());
    }

    @Override
    public void handleMenu(final @NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        assert clicked != null;
        if (clicked.equals(getBanItem())) {
            if (recipeItem.isBanned()) {
                recipeConfig.unbanRecipe(recipeItem);
            } else recipeConfig.banRecipe(recipeItem);
            updateMenu();
        }

        if (clicked.equals(getBackItem())) {
            new ManageRecipesMenu(plugin, player).open();
        }
    }

    @Override
    public void handleDrag(final @NotNull InventoryDragEvent event) {
        event.setCancelled(true);
    }

    private @NotNull ItemStack getBanItem() {
        return new ItemUtil(Material.GREEN_TERRACOTTA)
                .setDisplayName(LanguageAPI.Menu.BAN_ITEM_NAME.getMessage(plugin))
                .setMaterial(recipeItem.isBanned() ?
                        Material.RED_TERRACOTTA :
                        Material.GREEN_TERRACOTTA)
                .setLore(recipeItem.isBanned() ?
                        LanguageAPI.Menu.RECIPE_BANNED.getMessage(plugin) :
                        LanguageAPI.Menu.RECIPE_NOT_BANNED.getMessage(plugin))
                .toItemStack();
    }
}
