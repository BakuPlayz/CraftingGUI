package com.github.bakuplayz.craftinggui.recipe;

import lombok.Getter;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.2.6
 */
public enum IngredientsChoices {
    BLACK_BED(new IngredientsChoice(MaterialType.PLANKS,3)),
    BLUE_BED(new IngredientsChoice(MaterialType.PLANKS,3)),
    BOOKSHELF(new IngredientsChoice(MaterialType.PLANKS,6)),
    BOWL(new IngredientsChoice(MaterialType.PLANKS,3)),
    BROWN_BED(new IngredientsChoice(MaterialType.PLANKS,3)),
    CHEST(new IngredientsChoice(MaterialType.PLANKS,8)),
    CRAFTING_TABLE(new IngredientsChoice(MaterialType.PLANKS,4)),
    CYAN_BED(new IngredientsChoice(MaterialType.PLANKS,3)),
    DAYLIGHT_SENSOR(new IngredientsChoice(MaterialType.WOOD_SLAB,3)),
    GRAY_BED(new IngredientsChoice(MaterialType.PLANKS,3)),
    GREEN_BED(new IngredientsChoice(MaterialType.PLANKS,3)),
    JUKEBOX(new IngredientsChoice(MaterialType.PLANKS,8)),
    LIGHT_BLUE_BED(new IngredientsChoice(MaterialType.PLANKS,3)),
    LIGHT_GRAY_BED(new IngredientsChoice(MaterialType.PLANKS,3)),
    LIME_BED(new IngredientsChoice(MaterialType.PLANKS,3)),
    MAGENTA_BED(new IngredientsChoice(MaterialType.PLANKS,3)),
    NOTE_BLOCK(new IngredientsChoice(MaterialType.PLANKS,8)),
    ORANGE_BED(new IngredientsChoice(MaterialType.PLANKS,3)),
    PAINTING(new IngredientsChoice(MaterialType.WOOL)),
    PINK_BED(new IngredientsChoice(MaterialType.PLANKS,3)),
    PISTON(new IngredientsChoice(MaterialType.PLANKS,3)),
    PURPLE_BED(new IngredientsChoice(MaterialType.PLANKS,3)),
    RED_BED(new IngredientsChoice(MaterialType.PLANKS,3)),
    SHIELD(new IngredientsChoice(MaterialType.PLANKS,6)),
    STICK(new IngredientsChoice(MaterialType.PLANKS,2)),
    TORCH(new IngredientsChoice(MaterialType.COAL)),
    TRIPWIRE_HOOK(new IngredientsChoice(MaterialType.PLANKS)),
    WHITE_BED(new IngredientsChoice(MaterialType.PLANKS,3)),
    WOODEN_AXE(new IngredientsChoice(MaterialType.PLANKS,3)),
    WOODEN_HOE(new IngredientsChoice(MaterialType.PLANKS,2)),
    WOODEN_PICKAXE(new IngredientsChoice(MaterialType.PLANKS,3)),
    WOODEN_SHOVEL(new IngredientsChoice(MaterialType.PLANKS)),
    WOODEN_SWORD(new IngredientsChoice(MaterialType.PLANKS,2)),
    YELLOW_BED(new IngredientsChoice(MaterialType.PLANKS,3));

    public final @Getter IngredientsChoice[] choices;

    IngredientsChoices(final IngredientsChoice... choices) {
        this.choices = choices;
    }
}