package com.github.bakuplayz.craftinggui.recipe;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.2.6
 */
public enum IngredientsChoices {
    BED(new IngredientsChoice(new ItemStack(Material.WOOD, 3), new ItemStack(Material.WOOL, 3))),
    BLACK_BED(new IngredientsChoice(new ItemStack(Material.WOOD, 3))),
    BLUE_BED(new IngredientsChoice(new ItemStack(Material.WOOD, 3))),
    BOAT(new IngredientsChoice(new ItemStack(Material.WOOD,5))),
    BOOKSHELF(new IngredientsChoice(new ItemStack(Material.WOOD, 6))),
    BOWL(new IngredientsChoice(new ItemStack(Material.WOOD, 3))),
    BROWN_BED(new IngredientsChoice(new ItemStack(Material.WOOD, 3))),
    CHEST(new IngredientsChoice(new ItemStack(Material.WOOD, 8))),
    CRAFTING_TABLE(new IngredientsChoice(new ItemStack(Material.WOOD, 4))),
    CYAN_BED(new IngredientsChoice(new ItemStack(Material.WOOD, 3))),
    DAYLIGHT_SENSOR(new IngredientsChoice(new ItemStack(Material.WOOD_STEP, 3))),
    GRAY_BED(new IngredientsChoice(new ItemStack(Material.WOOD, 3))),
    GREEN_BED(new IngredientsChoice(new ItemStack(Material.WOOD, 3))),
    JUKEBOX(new IngredientsChoice(new ItemStack(Material.WOOD, 8))),
    LIGHT_BLUE_BED(new IngredientsChoice(new ItemStack(Material.WOOD, 3))),
    LIGHT_GRAY_BED(new IngredientsChoice(new ItemStack(Material.WOOD, 3))),
    LIME_BED(new IngredientsChoice(new ItemStack(Material.WOOD, 3))),
    MAGENTA_BED(new IngredientsChoice(new ItemStack(Material.WOOD, 3))),
    NOTE_BLOCK(new IngredientsChoice(new ItemStack(Material.WOOD, 8))),
    ORANGE_BED(new IngredientsChoice(new ItemStack(Material.WOOD, 3))),
    PAINTING(new IngredientsChoice(new ItemStack(Material.WOOL))),
    PINK_BED(new IngredientsChoice(new ItemStack(Material.WOOD, 3))),
    PISTON(new IngredientsChoice(new ItemStack(Material.WOOD, 3))),
    PURPLE_BED(new IngredientsChoice(new ItemStack(Material.WOOD, 3))),
    RED_BED(new IngredientsChoice(new ItemStack(Material.WOOD, 3))),
    SHIELD(new IngredientsChoice(new ItemStack(Material.WOOD, 6))),
    SIGN(new IngredientsChoice(new ItemStack(Material.WOOD, 6))),
    STICK(new IngredientsChoice(new ItemStack(Material.WOOD, 2))),
    TORCH(new IngredientsChoice(new ItemStack(Material.COAL, 1))),
    TRIPWIRE_HOOK(new IngredientsChoice(new ItemStack(Material.WOOD))),
    WHITE_BED(new IngredientsChoice(new ItemStack(Material.WOOD, 3))),
    WOODEN_AXE(new IngredientsChoice(new ItemStack(Material.WOOD, 3))),
    WOODEN_BUTTON(new IngredientsChoice(new ItemStack(Material.WOOD))),
    WOODEN_HOE(new IngredientsChoice(new ItemStack(Material.WOOD, 2))),
    WOODEN_PICKAXE(new IngredientsChoice(new ItemStack(Material.WOOD, 3))),
    WOODEN_SHOVEL(new IngredientsChoice(new ItemStack(Material.WOOD, 1))),
    WOODEN_SWORD(new IngredientsChoice(new ItemStack(Material.WOOD, 2))),
    WOODEN_TRAPDOOR(new IngredientsChoice(new ItemStack(Material.WOOD, 6))),
    WOOD_PRESSURE_PLATE(new IngredientsChoice(new ItemStack(Material.WOOD, 2))),
    YELLOW_BED(new IngredientsChoice(new ItemStack(Material.WOOD, 3)));

    public final @Getter IngredientsChoice[] choices;

    IngredientsChoices(final IngredientsChoice... choices) {
        this.choices = choices;
    }
}