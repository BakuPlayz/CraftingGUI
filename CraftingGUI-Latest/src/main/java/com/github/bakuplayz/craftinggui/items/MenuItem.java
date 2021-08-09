package com.github.bakuplayz.craftinggui.items;


import com.github.bakuplayz.craftinggui.CraftingGUI;
import com.github.bakuplayz.craftinggui.api.LanguageAPI;
import com.github.bakuplayz.craftinggui.utils.CraftUtil;
import com.github.bakuplayz.craftinggui.utils.ItemUtil;
import com.github.bakuplayz.craftinggui.utils.NameUtil;
import lombok.Getter;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.2.6
 */
public final class MenuItem {

    private final CraftingGUI plugin;

    private @Getter ItemStack item;
    private final @Getter RecipeItem recipeItem;

    public MenuItem(final @NotNull CraftingGUI plugin, final @NotNull RecipeItem recipeItem) {
        this.plugin = plugin;
        this.recipeItem = recipeItem;

        setItem(recipeItem);
    }

    private void setItem(final @NotNull RecipeItem item) {
        ItemStack result = item.getResult();
        this.item = new ItemUtil(result)
                .setDisplayName(LanguageAPI.Menu.RESULT_NAME.getMessage(plugin, NameUtil.lookup(result)))
                .setLore(getItemLore())
                .setAmount(1)
                .toItemStack();
    }

    private @NotNull List<String> getItemLore() {
        List<String> lore = new ArrayList<>();

        lore.add(LanguageAPI.Menu.INGREDIENTS_HEADER.getMessage(plugin));
        for (ItemStack ingredient : recipeItem.getIngredients()) {
            lore.add(LanguageAPI.Menu.INGREDIENT_NAME.getMessage(plugin, NameUtil.lookupWithAmount(ingredient, recipeItem)));
        }

        lore.add(""); //INTENTIONALLY EMPTY

        lore.add(LanguageAPI.Menu.RESULT_HEADER.getMessage(plugin));
        lore.add(LanguageAPI.Menu.RESULT_NAME.getMessage(plugin, NameUtil.lookupWithAmount(recipeItem.getResult())));

        return lore;
    }

    public boolean canShowItem(Inventory inventory) {
        if (recipeItem.isBanned()) return false;
        return new CraftUtil(inventory, recipeItem).containsIngredients(1);
    }
}
