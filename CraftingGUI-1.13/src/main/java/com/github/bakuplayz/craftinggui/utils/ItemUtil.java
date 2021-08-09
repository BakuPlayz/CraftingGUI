package com.github.bakuplayz.craftinggui.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.2.6
 */
public final class ItemUtil {

    private int amount = 1;
    private short durability;
    private Material material;
    private ItemMeta itemMeta;
    private String displayName = "";
    private List<String> lore = new ArrayList<>();

    public ItemUtil(final Material material) {
        this.material = material;
        this.durability = 0;
    }

    public ItemUtil(final Material material, final int amount) {
        this(material);
        this.amount = amount;
    }

    public ItemUtil(final Material material, final String displayName) {
        this(material);
        this.displayName = displayName;
    }

    public ItemUtil(final Material material, final String displayName, final int amount) {
        this(material, displayName);
        this.amount = amount;
    }

    public ItemUtil(final Material material, final String displayName, final int amount, final List<String> lore) {
        this(material, displayName, amount);
        this.lore = lore;
    }

    public ItemUtil(final @NotNull ItemStack stack) {
        if (stack.hasItemMeta()) {
            itemMeta = stack.getItemMeta();
        }
        this.durability = stack.getDurability();
        this.amount = stack.getAmount();
        this.material = stack.getType();
    }

    public ItemUtil(final ItemStack stack, final int amount) {
        this(stack);
        this.amount = amount;
    }

    public ItemUtil(final ItemStack stack, final String displayName) {
        this(stack);
        this.displayName = displayName;
    }

    public ItemUtil(final ItemStack stack, final String displayName, final int amount) {
        this(stack, amount);
        this.displayName = displayName;
    }

    public ItemUtil(final ItemStack stack, final String displayName, final int amount, final List<String> lore) {
        this(stack, displayName, amount);
        this.lore = lore;
    }

    public ItemUtil setAmount(final int amount) {
        if (amount < -1) return this;
        this.amount = amount;
        return this;
    }

    public ItemUtil setDurability(final short durability) {
        if (durability < -1) return this;
        this.durability = durability;
        return this;
    }

    public ItemUtil setMaterial(final Material material) {
        if (material == null) return this;
        this.material = material;
        return this;
    }

    public ItemUtil setDisplayName(final String displayName) {
        if (displayName == null) return this;
        this.displayName = displayName;
        return this;
    }

    public ItemUtil setLore(final List<String> lore) {
        if (lore == null) return this;
        this.lore = lore;
        return this;
    }

    public ItemUtil setLore(final String... lore) {
        if (lore == null) return this;
        this.lore = Arrays.asList(lore);
        return this;
    }

    private void setEnchantments(ItemMeta meta) {
        for (Map.Entry<Enchantment, Integer> enchants : itemMeta.getEnchants().entrySet()) {
            meta.addEnchant(enchants.getKey(), enchants.getValue(), true);
        }
    }

    public @NotNull ItemStack toItemStack() {
        ItemStack stack = new ItemStack(material, amount);
        ItemMeta meta = itemMeta != null ? itemMeta : stack.getItemMeta();

        if (lore != null) meta.setLore(lore);
        if (durability != 0) stack.setDurability(durability);
        if (displayName != null) meta.setDisplayName(displayName);
        if (itemMeta != null) setEnchantments(meta);
        stack.setItemMeta(meta);

        return stack;
    }
}
