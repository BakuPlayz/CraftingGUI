package com.github.bakuplayz.craftinggui.recipe;

import be.seeseemelk.mockbukkit.MockBukkit;
import com.github.bakuplayz.craftinggui.CraftingGUI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.2.6
 */
public final class RecipeManagerTests {

    private CraftingGUI plugin;
    private RecipeManager recipeManager;

    @Before
    public void setUp() {
        MockBukkit.mock();

        plugin = MockBukkit.load(CraftingGUI.class);

        setupTestRecipeManager();
    }

    private void setupTestRecipeManager() {
        this.recipeManager = plugin.getRecipeManager();
    }

    @After
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    public void testLoadingRecipes() {
        assertDoesNotThrow(() -> recipeManager.loadRecipes());
    }
}
