package com.github.bakuplayz.craftinggui.items;

import be.seeseemelk.mockbukkit.MockBukkit;
import com.github.bakuplayz.craftinggui.CraftingGUI;
import com.github.bakuplayz.craftinggui.items.RecipeItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
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
public final class RecipeItemTests {

    private CraftingGUI plugin;

    private Recipe testRecipe;
    private RecipeItem testItem;

    @Before
    public void setUp() {
        MockBukkit.mock();

        plugin = MockBukkit.load(CraftingGUI.class);

        setupTestRecipe();
        setupTestRecipeItem();
    }

    private void setupTestRecipe() {
        NamespacedKey testKey = new NamespacedKey(plugin, "testRecipe");

        ItemStack testResult = new ItemStack(Material.CRAFTING_TABLE);
        ItemMeta testResultMeta = testResult.getItemMeta();

        assert testResultMeta != null;
        testResultMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, false);
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

    private void setupTestRecipeItem() {
        this.testItem = new RecipeItem(plugin, testRecipe);
    }

    @After
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    public void testRecipe() {
        assertNotNull(testRecipe);
    }

    @Test
    public void testRecipeItem() {
        assertNotNull(plugin);
        assertNotNull(testItem);
    }

    @Test
    public void testMenuItem() {
        assertNotNull(testItem.getMenuItem());
    }

    @Test
    public void testRecipeID() {
        assertNotEquals("", testItem.getRecipeID());
    }

    @Test
    public void testRecipeName() {
        String recipeName = testItem.getRecipeName();
        assertNotEquals("", recipeName);
        assertEquals(ChatColor.GREEN + "Test Result", recipeName);
    }

    @Test
    public void testRecipeIngredients() {
        List<ItemStack> ingredients = testItem.getIngredients();

        assertNotNull(ingredients);
        assertFalse(ingredients.isEmpty());
        assertFalse(ingredients.contains(null));

        assertTrue(ingredients.contains(new ItemStack(Material.ACACIA_PLANKS)));
        assertTrue(ingredients.contains(new ItemStack(Material.BIRCH_PLANKS)));
        assertTrue(ingredients.contains(new ItemStack(Material.DARK_OAK_PLANKS)));
        assertTrue(ingredients.contains(new ItemStack(Material.JUNGLE_PLANKS)));
    }

    @Test
    public void testRecipeChoices() {
        Map<Character, RecipeChoice> choices = testItem.getChoices();

        if (testItem.hasChoice()) {
            assertFalse(choices.isEmpty());
        } else {
            assertTrue(choices.isEmpty());
        }
    }
}
