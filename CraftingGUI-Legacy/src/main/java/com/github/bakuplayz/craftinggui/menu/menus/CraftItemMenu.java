package com.github.bakuplayz.craftinggui.menu.menus;

import com.github.bakuplayz.craftinggui.CraftingGUI;
import com.github.bakuplayz.craftinggui.api.LanguageAPI;
import com.github.bakuplayz.craftinggui.items.MenuItem;
import com.github.bakuplayz.craftinggui.items.RecipeItem;
import com.github.bakuplayz.craftinggui.menu.DefaultMenu;
import com.github.bakuplayz.craftinggui.recipe.IngredientsChoice;
import com.github.bakuplayz.craftinggui.utils.CraftUtil;
import com.github.bakuplayz.craftinggui.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.2.6
 */
public final class CraftItemMenu extends DefaultMenu {

    private final RecipeItem recipeItem;
    private final ItemStack selectedItem;

    private final boolean hasChoice;
    private final IngredientsChoice choice;

    private final ItemStack result;
    private final int resultAmount;

    private final CraftUtil craftUtil;
    private final Inventory playerInventory;

    private boolean canCraft1;
    private boolean canCraft4;
    private boolean canCraft16;
    private boolean canCraft32;
    private boolean canCraft64;
    private boolean canCraftMax;

    public CraftItemMenu(final CraftingGUI plugin, final Player player, final @NotNull MenuItem selected) {
        setPlayer(player);
        setPlugin(plugin);
        setDefaultSettings();
        this.selectedItem = selected.getItem();
        this.recipeItem = selected.getRecipeItem();
        this.choice = recipeItem.getChoice();
        this.hasChoice = recipeItem.hasChoice();
        this.result = recipeItem.getResult();
        this.resultAmount = result.getAmount();
        this.playerInventory = player.getInventory();
        this.craftUtil = new CraftUtil(playerInventory, recipeItem);
    }

    @Override
    public @NotNull String getTitle() {
        return LanguageAPI.Menu.CRAFT_ITEM_TITLE.getTitle(plugin);
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void setMenuItems() {
        updateCanCraftCache(0);

        inventory.setItem(13, selectedItem);
        inventory.setItem(28, getXItem(1));
        inventory.setItem(29, getXItem(4));
        inventory.setItem(30, getXItem(16));
        inventory.setItem(31, getXItem(32));
        inventory.setItem(32, getXItem(64));
        inventory.setItem(34, getMaxItem());
        inventory.setItem(49, getBackItem());
    }

    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        if (canCraftMax && clicked.equals(getMaxItem())) craftMaxItems();
        if (canCraft1 && clicked.equals(getXItem(1))) craftXItem(1);
        if (canCraft4 && clicked.equals(getXItem(4))) craftXItem(4);
        if (canCraft16 && clicked.equals(getXItem(16))) craftXItem(16);
        if (canCraft32 && clicked.equals(getXItem(32))) craftXItem(32);
        if (canCraft64 && clicked.equals(getXItem(64))) craftXItem(64);
        if (clicked.equals(getBackItem())) new MultiCraftingMenu(plugin, player).open();
    }

    @Override
    public void handleDrag(@NotNull InventoryDragEvent event) {
        event.setCancelled(true);
    }

    private @NotNull ItemStack getXItem(int amount) {
        return new ItemUtil(Material.IRON_INGOT)
                .setDisplayName(LanguageAPI.Menu.CRAFT_X_ITEM_NAME.getMessage(plugin, amount))
                .setLore(hasCachedCanCraft(amount) ?
                        LanguageAPI.Menu.CAN_CRAFT_X.getMessage(plugin, amount) :
                        LanguageAPI.Menu.CANNOT_CRAFT_X.getMessage(plugin, amount))
                .toItemStack();
    }

    private @NotNull ItemStack getMaxItem() {
        return new ItemUtil(Material.GOLD_INGOT)
                .setDisplayName(LanguageAPI.Menu.CRAFT_MAX_ITEM_NAME.getMessage(plugin))
                .setLore(hasCachedCanCraft(0) ?
                        LanguageAPI.Menu.CAN_CRAFT_MAX.getMessage(plugin) :
                        LanguageAPI.Menu.CANNOT_CRAFT_MAX.getMessage(plugin))
                .toItemStack();
    }

    private void craftXItem(int amount) {
        if (hasCachedCanCraft(amount)) {
            craftItem(amount);
        }
        updateMenu();
    }

    private void craftMaxItems() {
        while (canCraftMax) {
            if (canCraft64) {
                craftItem(64);
                continue;
            }

            updateCanCraftCache(32);
            if (canCraft32) {
                craftItem(32);
                continue;
            }

            updateCanCraftCache(16);
            if (canCraft16) {
                craftItem(16);
                continue;
            }

            updateCanCraftCache(4);
            if (canCraft4) {
                craftItem(4);
                continue;
            }

            updateCanCraftCache(1);
            if (canCraft1) {
                craftItem(1);
                continue;
            }
            break;
        }
        updateMenu();
    }

    private void craftItem(int amount) {
        craftUtil.removeIngredients(playerInventory, amount);
        updateCanCraftCache(amount);
        addResults(amount);
    }

    private void addResults(int amount) {
        for (int i = 0; i < amount; i++) {
            if (result.getType() == Material.CAKE) {
                playerInventory.addItem(new ItemStack(Material.BUCKET, 3));
            }
            playerInventory.addItem(result);
            result.setAmount(resultAmount); //Needed since something weird with result#amount changing randomly after inventory#addItem()
        }
    }

    private void updateCanCraftCache(int amount) {
        if (amount == 0 || amount == 1) this.canCraft1 = canCraftItem(1);
        if (amount == 0 || amount == 4) this.canCraft4 = canCraftItem(4);
        if (amount == 0 || amount == 16) this.canCraft16 = canCraftItem(16);
        if (amount == 0 || amount == 32) this.canCraft32 = canCraftItem(32);
        if (amount == 0 || amount == 64) this.canCraft64 = canCraftItem(64);
        if (amount == 0) this.canCraftMax = hasCachedCanCraft(0);
    }

    private boolean hasCachedCanCraft(int amount) {
        if (amount == 1) return canCraft1;
        if (amount == 4) return canCraft4;
        if (amount == 16) return canCraft16;
        if (amount == 32) return canCraft32;
        if (amount == 64) return canCraft64;
        if (amount == 0) return canCraft64 || canCraft32 || canCraft16 || canCraft4 || canCraft1;
        return false;
    }

    private boolean canCraftItem(int amount) {
        return !recipeItem.isBanned()
                && containsIngredients(amount)
                && craftUtil.canContainResult(amount);
    }

    private boolean containsIngredients(int amount) {
        for (ItemStack ingredient : recipeItem.getIngredients()) {
            if (hasChoice && (choice.matchesFirst(ingredient) ^ choice.matchesSecond(ingredient))) continue;
            if (!playerInventory.containsAtLeast(ingredient, ingredient.getAmount() * amount)) return false;
        }
        return !hasChoice || choice.containsIngredients(playerInventory, amount);
    }
}