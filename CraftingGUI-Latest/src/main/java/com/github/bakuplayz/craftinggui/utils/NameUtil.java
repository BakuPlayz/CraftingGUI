package com.github.bakuplayz.craftinggui.utils;

import com.github.bakuplayz.craftinggui.items.RecipeItem;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.2.6
 */
public final class NameUtil {

    private static final List<Material> LEATHER_ARMOR = List.of(Material.LEATHER_BOOTS, Material.LEATHER_LEGGINGS, Material.LEATHER_CHESTPLATE, Material.LEATHER_HELMET);
    private static final List<Material> PLURAL_RESULTS = new ArrayList<>(Arrays.asList(Material.ACACIA_PLANKS, Material.BIRCH_PLANKS, Material.BOOKSHELF, Material.BOWL, Material.CHEST, Material.CRAFTING_TABLE, Material.DARK_OAK_PLANKS, Material.DAYLIGHT_DETECTOR, Material.JUKEBOX, Material.JUNGLE_PLANKS, Material.NOTE_BLOCK, Material.OAK_PLANKS, Material.PAINTING, Material.PISTON, Material.SHIELD, Material.SPRUCE_PLANKS, Material.STICK, Material.TORCH, Material.TRIPWIRE_HOOK, Material.WOODEN_AXE, Material.WOODEN_HOE, Material.WOODEN_PICKAXE, Material.WOODEN_SHOVEL, Material.WOODEN_SWORD));

    public static @NotNull String lookup(final ItemStack item) {
        if (item == null || item.getType() == Material.AIR) {
            return "";
        }

        String name = item.getType().name();
        String readableName = capitalizeWords(name);

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return readableName;
        if (meta.hasDisplayName()) return meta.getDisplayName();

        if (LEATHER_ARMOR.contains(item.getType())) {
            Color color = ((LeatherArmorMeta) meta).getColor();
            DyeColor dyeColor = DyeColor.getByColor(color);
            return dyeColor == null ? readableName : capitalizeWords(dyeColor + " " + readableName);
        }

        return readableName;
    }

    public static @NotNull String lookup(final ItemStack item, final RecipeItem recipeItem) {
        if (item == null || recipeItem == null) {
            return "";
        }

        if (VersionUtil.isInInterval(15.9, 100)) {
            PLURAL_RESULTS.add(Material.CRIMSON_PLANKS);
            PLURAL_RESULTS.add(Material.WARPED_PLANKS);
        }

        if (PLURAL_RESULTS.contains(recipeItem.getResult().getType())) {
            return getNameAsPlural(item);
        }

        return lookup(item);
    }

    public static @NotNull String lookupWithAmount(final @NotNull ItemStack item) {
        return item.getAmount() + " x " + lookup(item);
    }

    public static @NotNull String lookupWithAmount(final @NotNull ItemStack item, final @NotNull RecipeItem recipeItem) {
        return item.getAmount() + " x " + lookup(item, recipeItem);
    }

    private static @NotNull String getNameAsPlural(final @NotNull ItemStack item) {
        String name = item.getType().name();
        if (name.contains("COAL")) return "Coal";
        if (name.contains("WOOL")) return "Wool";
        if (name.contains("PLANKS")) return "Planks";
        if (name.contains("SLAB")) return "Wood Slabs";

        if (name.contains("LOG") ^ name.contains("WOOD")) {
            if (name.contains("OAK")) return "Oak Log / Oak Wood";
            if (name.contains("BIRCH")) return "Birch Log / Birch Wood";
            if (name.contains("ACACIA")) return "Acacia Log / Acacia Wood";
            if (name.contains("JUNGLE")) return "Jungle Log / Jungle Wood";
            if (name.contains("SPRUCE")) return "Spruce Log / Spruce Wood";
            if (name.contains("WARPED")) return "Warped Log / Warped Wood";
            if (name.contains("CRIMSON")) return "Crimson Log / Crimson Wood";
            if (name.contains("DARK_OAK")) return "Dark Oak Log / Dark Oak Wood";
        }
        return lookup(item);
    }

    private static @NotNull String capitalizeWords(final @NotNull String str) {
        return WordUtils.capitalizeFully(str.replace("_", " "));
    }
}
