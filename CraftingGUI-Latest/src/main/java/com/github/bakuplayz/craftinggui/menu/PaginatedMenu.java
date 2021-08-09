package com.github.bakuplayz.craftinggui.menu;

import com.github.bakuplayz.craftinggui.api.LanguageAPI;
import com.github.bakuplayz.craftinggui.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.2.6
 */
public abstract class PaginatedMenu extends DefaultMenu {

    protected int page = 0;
    protected int itemIndex = 0;
    protected final int MAX_ITEMS_PER_PAGE = 45;

    protected final @NotNull ItemStack getPreviousPageItem() {
        return new ItemUtil(Material.ARROW)
                .setDisplayName(LanguageAPI.Menu.PREVIOUS_PAGE_ITEM_NAME.getMessage(plugin))
                .toItemStack();
    }

    protected final @NotNull ItemStack getNextPageItem() {
        return new ItemUtil(Material.ARROW)
                .setDisplayName(LanguageAPI.Menu.NEXT_PAGE_ITEM_NAME.getMessage(plugin))
                .toItemStack();
    }

    protected final @NotNull ItemStack getCurrentPageItem() {
        return new ItemUtil(Material.BOOK)
                .setDisplayName(LanguageAPI.Menu.CURRENT_PAGE_ITEM_NAME.getMessage(plugin, page + 1))
                .toItemStack();
    }
}
