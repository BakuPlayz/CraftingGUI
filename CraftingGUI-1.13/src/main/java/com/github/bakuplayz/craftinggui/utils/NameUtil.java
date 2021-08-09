package com.github.bakuplayz.craftinggui.utils;

import com.github.bakuplayz.craftinggui.items.RecipeItem;
import com.github.bakuplayz.craftinggui.recipe.IngredientsChoice;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.2.6
 */
public final class NameUtil {

    private static final List<Material> LEATHER_ARMOR = List.of(Material.LEATHER_BOOTS, Material.LEATHER_LEGGINGS, Material.LEATHER_CHESTPLATE, Material.LEATHER_HELMET);

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

        if (recipeItem.hasChoice()) {
            IngredientsChoice choice = recipeItem.getChoice();
            if (choice == null) return lookup(item);
            if (choice.matchesType(item)) return getNameAsPlural(item);
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
        return lookup(item);
    }

    private static @NotNull String capitalizeWords(final @NotNull String str) {
        return WordUtils.capitalizeFully(str.replace("_", " "));
    }
}
