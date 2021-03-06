package com.github.bakuplayz.craftinggui.commands;

import org.bukkit.entity.Player;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.2.6
 */
public interface SubCommand {

    String getName();

    String getDescription();

    String getUsage();

    String getPermission();

    boolean hasPermission(Player player);

    void perform(Player player, String[] args);
}