package com.github.bakuplayz.craftinggui.settings;

import com.github.bakuplayz.craftinggui.CraftingGUI;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public final class PlayerSettings {

    private final NamespacedKey selectedColor;
    private final NamespacedKey multiChoice;
    private final NamespacedKey tableChoice;

    public PlayerSettings(final CraftingGUI plugin) {
        this.selectedColor = new NamespacedKey(plugin, "selectedColor");
        this.multiChoice = new NamespacedKey(plugin, "multiChoice");
        this.tableChoice = new NamespacedKey(plugin, "tableChoice");
    }

    public @NotNull PersistentDataContainer getContainer(@NotNull Player player) {
        return player.getPersistentDataContainer();
    }

    public void resetSettings() {
        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            setDefaultSettings(player.getPlayer());
        }
    }

    public void setDefaultSettings(Player player) {
        setTableChoice(player, "Table");
        setMultiChoice(player, "Multi");

        short color = getSelectedColor(player);
        if (color > 15 || color <= 0) setSelectedColor(player, (short) 0);
    }

    public boolean isNotRegistered(Player player) {
        String multiChoice = getMultiChoice(player);
        String tableChoice = getTableChoice(player);
        return multiChoice == null || tableChoice == null;
    }

    public short getSelectedColor(Player player) {
        return getContainer(player).getOrDefault(selectedColor, PersistentDataType.SHORT, (short) 0);
    }

    public String getMultiChoice(Player player) {
        return getContainer(player).get(multiChoice, PersistentDataType.STRING);
    }

    public String getTableChoice(Player player) {
        return getContainer(player).get(tableChoice, PersistentDataType.STRING);
    }

    public void setSelectedColor(Player player, short color) {
        getContainer(player).set(selectedColor, PersistentDataType.SHORT, color);
    }

    public void setMultiChoice(Player player, String choice) {
        getContainer(player).set(multiChoice, PersistentDataType.STRING, choice);
    }

    public void setTableChoice(Player player, String choice) {
        getContainer(player).set(tableChoice, PersistentDataType.STRING, choice);
    }
}
