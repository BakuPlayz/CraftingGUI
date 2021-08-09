package com.github.bakuplayz.craftinggui.menu.menus;

import com.github.bakuplayz.craftinggui.CraftingGUI;
import com.github.bakuplayz.craftinggui.api.LanguageAPI;
import com.github.bakuplayz.craftinggui.configs.PlayerConfig;
import com.github.bakuplayz.craftinggui.items.RecipeItem;
import com.github.bakuplayz.craftinggui.menu.DefaultMenu;
import com.github.bakuplayz.craftinggui.recipe.IngredientsChoice;
import com.github.bakuplayz.craftinggui.recipe.RecipeManager;
import com.github.bakuplayz.craftinggui.utils.CraftUtil;
import com.github.bakuplayz.craftinggui.utils.InventoryUtil;
import com.github.bakuplayz.craftinggui.utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.2.6
 */
public final class CraftingTableMenu extends DefaultMenu implements Listener {

    private final PlayerConfig playerConfig;
    private final RecipeManager recipeManager;

    private Recipe recipe;
    private String[] shape;
    private RecipeItem recipeItem;
    private Map<Character, ItemStack> ingredientMap;

    private final ItemStack[] playerInput;
    private final Integer[] INPUT_SLOTS = {11, 12, 13, 20, 21, 22, 29, 30, 31};
    private final Character[] CRAFT_CHARS = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i'};

    public CraftingTableMenu(final CraftingGUI plugin, final Player player) {
        setPlayer(player);
        setPlugin(plugin);
        setDefaultSettings();
        this.playerInput = new ItemStack[9];
        this.playerConfig = plugin.getPlayerConfig();
        this.recipeManager = plugin.getRecipeManager();
    }

    @Override
    public @NotNull String getTitle() {
        return LanguageAPI.Menu.CRAFTING_TABLE_TITLE.getTitle(plugin);
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void setMenuItems() {
        ItemStack glass = getGlassChoice();
        for (int slot = 0; slot < getSlots(); slot++) {
            inventory.setItem(slot, glass);
        }

        for (int slot : INPUT_SLOTS) {
            inventory.setItem(slot, null);
        }

        inventory.setItem(24, getResultPlaceholder());
        inventory.setItem(45, getSettingsItem());

        ItemStack multiItem = getMultiItem();
        if (multiItem != null) {
            inventory.setItem(53, getMultiItem());
        }
    }

    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();
        if (event.getRawSlot() > 54 && !event.isShiftClick()) return;

        if (clicked.equals(getGlassChoice())) {
            event.setCancelled(true);
            return;
        }

        if (clicked.equals(getResultPlaceholder())) {
            event.setCancelled(true);
            return;
        }

        Bukkit.getScheduler().runTaskLater(plugin, this::updateResult, 0);

        if (recipe == null) return;
        if (clicked.equals(recipe.getResult()) && event.getRawSlot() == 24) {
            event.setCancelled(true);

            if (event.getClick().isShiftClick()) {
                shiftCraft();
            } else {
                craft();
            }
        }
    }

    @Override
    public void handleDrag(@NotNull InventoryDragEvent event) {
        Bukkit.getScheduler().runTaskLater(plugin, this::updateResult, 0);
    }

    @EventHandler
    public void handleClose(@NotNull InventoryCloseEvent event) {
        updatePlayerInput();
        for (ItemStack input : playerInput) {
            if (input == null) continue;
            for (ItemStack item : player.getInventory().addItem(input).values()) {
                player.getWorld().dropItem(player.getLocation(), item);
            }
        }
    }

    public void updateResult() {
        inventory.setItem(24, getResultPlaceholder());

        updatePlayerInput();

        this.recipe = searchRecipes();
        if (recipe != null) inventory.setItem(24, recipe.getResult());
    }

    private void updatePlayerInput() {
        ItemStack[] contents = InventoryUtil.getStorage(inventory);
        for (int i = 0; i < 9; i++) {
            this.playerInput[i] = contents[INPUT_SLOTS[i]];
        }
    }

    private void craft() {
        if (canContainResult(1)) {
            removeIngredients();
            addResult(1);
        }
        updateResult();
    }

    private void shiftCraft() {
        int craftTimes = 0;
        for (int i = -1; i < craftTimes; i++, craftTimes++) {
            if (!matchesRecipe(recipe)) break;
            if (!canContainResult((i + 2))) break;
            removeIngredients();
        }
        addResult(craftTimes);
        updateResult();
    }

    private void removeIngredients() {
        for (int i = 0; i < 9; i++) {
            ItemStack item = playerInput[i];
            if (item == null) continue;
            if (item.getType() == Material.MILK_BUCKET) {
                if (recipe.getResult().getType() == Material.CAKE) {
                    item.setType(Material.BUCKET);
                }
            } else {
                item.setAmount(item.getAmount() - 1);
                inventory.setItem(INPUT_SLOTS[i], item);
            }
        }
    }

    private void addResult(final int amount) {
        ItemStack result = recipe.getResult();
        if (result.getMaxStackSize() > 1) {
            result.setAmount(result.getAmount() * amount);
            player.getInventory().addItem(result);
        } else {
            for (int i = 0; i < amount; i++) {
                player.getInventory().addItem(result);
            }
        }
    }

    private boolean matchesRecipe(Recipe recipe) {
        if (recipe instanceof ShapedRecipe) return matchesShaped((ShapedRecipe) recipe);
        if (recipe instanceof ShapelessRecipe) return matchesShapeless((ShapelessRecipe) recipe);
        return false;
    }

    private boolean canContainResult(int amount) {
        if (recipe == null) return false;
        return new CraftUtil(player.getInventory(), recipeItem).canContainResult(amount);
    }

    private @Nullable Recipe searchRecipes() {
        for (RecipeItem item : recipeManager.getRecipeItems()) {
            if (item.isBanned()) continue;
            this.recipeItem = item;

            Recipe recipe = item.getRecipe();
            if (matchesRecipe(recipe)) return recipe;
        }
        return null;
    }

    private void setShapeAndIngredientMap() {
        int firstRow = -1;
        int lastRow = 0;
        int firstCol = -1;
        int lastCol = 0;

        for (int row = 0; row < 3; row++) {
            boolean removeRow = true;
            for (int col = 0; col < 3; col++) {
                if (playerInput[(row * 3) + col] != null) {
                    removeRow = false;
                    break;
                }
            }
            if (!removeRow) {
                if (firstRow == -1) firstRow = row;
                if (row > lastRow) lastRow = row;
            }
        }

        for (int col = 0; col < 3; col++) {
            boolean removeCol = true;
            for (int row = 0; row < 3; row++) {
                if (playerInput[(row * 3) + col] != null) {
                    removeCol = false;
                    break;
                }
            }
            if (!removeCol) {
                if (firstCol == -1) firstCol = col;
                if (col > lastCol) lastCol = col;
            }
        }

        if (firstRow == -1) firstRow = 0;
        if (firstCol == -1) firstCol = 0;

        Map<Character, ItemStack> itemMap = new HashMap<>();
        String[] shape = new String[(lastRow - firstRow + 1)];

        int index = 0;
        int rowIndex = 0;
        for (int row = firstRow; row <= lastRow; row++) {
            StringBuilder shapeRow = new StringBuilder();
            for (int col = firstCol; col <= lastCol; col++) {
                shapeRow.append(CRAFT_CHARS[index]);
                itemMap.put(CRAFT_CHARS[index], playerInput[(row * 3) + col]);
                index++;
            }
            shape[rowIndex] = shapeRow.toString();
            rowIndex++;
        }

        this.shape = shape;
        this.ingredientMap = itemMap;
    }

    private boolean matchesShaped(@NotNull ShapedRecipe recipe) {
        setShapeAndIngredientMap();
        if (!Arrays.equals(shape, recipe.getShape())) return false;
        if (!matchesChoiceMap(recipe.getIngredientMap(), false)) {
            return matchesChoiceMap(recipe.getIngredientMap(), true);
        }
        return true;
    }

    private boolean matchesChoiceMap(@NotNull Map<Character, ItemStack> choiceMap, boolean mirror) {
        Map<Character, Character> charMap = mirror ? flipShape() : null;
        for (Map.Entry<Character, ItemStack> entry : choiceMap.entrySet()) {
            Character character = mirror ? charMap.get(entry.getKey()) : entry.getKey();
            ItemStack item = ingredientMap.get(character);
            ItemStack choice = entry.getValue();

            if (item == null && choice != null) {
                if (choice.getType() == Material.AIR) continue;
            }
            if (choice == null ^ item == null) return false;
            if (item == null) continue;

            if (!isSimilar(choice, item)) return false;
        }
        return true;
    }

    private boolean matchesShapeless(@NotNull ShapelessRecipe recipe) {
        ArrayList<ItemStack> input = new ArrayList<>(Arrays.asList(playerInput));
        for (ItemStack ingredient : recipe.getIngredientList()) {
            boolean matches = false;
            for (ItemStack item : input) {
                if (item == null) continue;
                if (isSimilar(ingredient, item)) {
                    matches = true;
                    input.remove(item);
                    break;
                }
            }
            if (!matches) return false;
        }
        return input.stream().noneMatch(Objects::nonNull);
    }

    private boolean isSimilar(@NotNull ItemStack first, @NotNull ItemStack second) {
        if (first.getType() != second.getType()) return false;

        if (!recipeItem.hasChoice()) {
            Recipe recipe = recipeItem.getRecipe();
            if (recipe instanceof ShapelessRecipe) return first.isSimilar(second);
            if (recipe instanceof ShapedRecipe) return first.getType() == second.getType();
            return false;
        }

        IngredientsChoice choice = recipeItem.getChoice();
        if (!choice.matchesFirst(second) && !choice.matchesSecond(second)) {
            return first.isSimilar(second);
        }
        return choice.matchesFirst(second) ^ choice.matchesSecond(second);
    }

    private @NotNull Map<Character, Character> flipShape() {
        Map<Character, Character> map = new HashMap<>();
        for (String s : shape) {
            StringBuilder input = new StringBuilder();
            String reverse = input.append(s).reverse().toString();
            char[] originalChars = s.toCharArray();
            char[] reverseChars = reverse.toCharArray();
            for (int c = 0; c < s.length(); c++) {
                map.put(originalChars[c], reverseChars[c]);
            }
        }
        return map;
    }

    private @NotNull ItemStack getResultPlaceholder() {
        return new ItemUtil(Material.BARRIER)
                .setDisplayName(LanguageAPI.Menu.RESULTS_ITEM_NAME.getMessage(plugin))
                .toItemStack();
    }

    private @NotNull ItemStack getGlassChoice() {
        return new ItemUtil(Material.STAINED_GLASS_PANE)
                .setDisplayName(LanguageAPI.Menu.GLASS_ITEM_NAME.getMessage(plugin))
                .setDamage(playerConfig.getSelectedColor(player))
                .toItemStack();
    }
}