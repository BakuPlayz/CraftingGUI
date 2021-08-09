package com.github.bakuplayz.craftinggui.menu.menus;

import com.github.bakuplayz.craftinggui.CraftingGUI;
import com.github.bakuplayz.craftinggui.api.LanguageAPI;
import com.github.bakuplayz.craftinggui.configs.PlayerConfig;
import com.github.bakuplayz.craftinggui.menu.DefaultMenu;
import com.github.bakuplayz.craftinggui.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.2.6
 */
public final class ColorPickerMenu extends DefaultMenu {

    private final PlayerConfig playerConfig;

    private final List<String> colors;
    private final List<Integer> COLOR_SLOTS = Arrays.asList(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30);

    public ColorPickerMenu(final CraftingGUI plugin, final Player player) {
        setPlayer(player);
        setPlugin(plugin);
        setDefaultSettings();
        this.playerConfig = plugin.getPlayerConfig();
        this.colors = plugin.getLanguageConfig().getColors();
    }

    @Override
    public @NotNull String getTitle() {
        return LanguageAPI.Menu.COLOR_PICKER_TITLE.getTitle(plugin);
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void setMenuItems() {
        for (int i = 0; i < colors.size(); i++) {
            inventory.setItem(COLOR_SLOTS.get(i), getColorItem(i));
        }

        inventory.setItem(49, getBackItem());
    }

    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        int slot = event.getSlot();
        if (COLOR_SLOTS.contains(slot)) {
            playerConfig.setSelectedColor(player, (short) COLOR_SLOTS.indexOf(slot));
            updateMenu();
        }

        if (clicked.equals(getBackItem())) new SettingsMenu(plugin, player).open();
    }

    @Override
    public void handleDrag(@NotNull InventoryDragEvent event) {
        event.setCancelled(true);
    }

    private @NotNull ItemStack getColorItem(int colorIndex) {
        String color = colors.get(colorIndex);
        return new ItemUtil(Material.STAINED_GLASS)
                .setLore(isSelectedColor(color) ? LanguageAPI.Menu.SELECTED_CHOICE.getMessage(plugin) : "")
                .setDisplayName(LanguageAPI.Menu.COLOR_ITEM_NAME.getMessage(plugin, color))
                .setDamage((short) colorIndex)
                .toItemStack();
    }

    private boolean isSelectedColor(String color) {
        return playerConfig.getSelectedColor(player) == colors.indexOf(color);
    }
}
