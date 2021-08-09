package com.github.bakuplayz.craftinggui.items;

import be.seeseemelk.mockbukkit.MockBukkit;
import com.github.bakuplayz.craftinggui.CraftingGUI;
import com.github.bakuplayz.craftinggui.items.MenuItem;
import com.github.bakuplayz.craftinggui.items.RecipeItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.2.6
 */
public final class MenuItemTests {

    private CraftingGUI plugin;

    private Recipe testRecipe;
    private MenuItem testItem;

    @Before
    public void setUp() {
        MockBukkit.mock();

        plugin = MockBukkit.load(CraftingGUI.class);

        setupTestRecipe();
        setupTestMenuItem();
    }

    private void setupTestRecipe() {
        NamespacedKey testKey = new NamespacedKey(plugin, "testRecipe");

        ItemStack testResult = new ItemStack(Material.CRAFTING_TABLE);
        ItemMeta testResultMeta = testResult.getItemMeta();

        assert testResultMeta != null;
        testResultMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, false);
        testResultMeta.addEnchant(Enchantment.ARROW_FIRE, 1, true);

        testResultMeta.setDisplayName(ChatColor.GREEN + "Test Result");

        testResult.setItemMeta(testResultMeta);

        ShapelessRecipe recipe = new ShapelessRecipe(testKey, testResult);
        recipe.addIngredient(Material.ACACIA_PLANKS);
        recipe.addIngredient(Material.BIRCH_PLANKS);
        recipe.addIngredient(Material.DARK_OAK_PLANKS);
        recipe.addIngredient(Material.JUNGLE_PLANKS);

        testRecipe = recipe;
    }

    private void setupTestMenuItem() {
        RecipeItem recipeItem = new RecipeItem(plugin, testRecipe);
        this.testItem = new MenuItem(plugin, recipeItem);
    }

    @After
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    public void testMenuItem() {
        assertNotNull(testItem);
    }

    @Test
    public void testItem() {
        assertNotNull(testItem.getItem());
    }

    @Test
    public void testItemMeta() {
        assertNotNull(testItem.getItem().getItemMeta());
    }

    @Test
    public void testItemLore() {
        ItemStack item = testItem.getItem();
        ItemMeta meta = item.getItemMeta();

        List<String> lore = null;
        if (meta != null) {
            lore = meta.getLore();
        }

        assertNotNull(lore);
        assertFalse(lore.isEmpty());
        assertFalse(lore.contains(null));
        assertTrue(lore.contains(""));
    }

    @Test
    public void testItemEnchantments() {
        ItemStack item = testItem.getItem();
        ItemMeta meta = item.getItemMeta();

        Map<Enchantment, Integer> enchantments = null;
        if (meta != null) {
            enchantments = meta.getEnchants();
        }

        assertNotNull(enchantments);
        assertFalse(enchantments.isEmpty());
    }

    @Test
    public void testItemDisplayName() {
        ItemStack item = testItem.getItem();
        ItemMeta meta = item.getItemMeta();

        String displayName = null;
        if (meta != null) {
            displayName = meta.getDisplayName();
        }

        assertNotNull(displayName);
    }
}