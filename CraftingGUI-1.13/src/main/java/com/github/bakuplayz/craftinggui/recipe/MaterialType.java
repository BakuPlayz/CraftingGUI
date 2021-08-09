package com.github.bakuplayz.craftinggui.recipe;

import lombok.Getter;
import org.bukkit.Material;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.2.6
 */
public enum MaterialType {

    COAL(Material.COAL, Material.CHARCOAL),
    PLANKS(Material.ACACIA_PLANKS, Material.BIRCH_PLANKS, Material.DARK_OAK_PLANKS, Material.JUNGLE_PLANKS, Material.OAK_PLANKS, Material.SPRUCE_PLANKS),
    WOOD_SLAB(Material.ACACIA_SLAB, Material.BIRCH_SLAB, Material.DARK_OAK_SLAB, Material.JUNGLE_SLAB, Material.OAK_SLAB, Material.SPRUCE_SLAB),
    WOOL(Material.BLACK_WOOL);

    private final @Getter Material[] materials;

    MaterialType(Material... materials) {
        this.materials = materials;
    }
}
