/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2023 BakuPlayz
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.bakuplayz.craftinggui.addons;

import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;


/**
 *
 * @author BakuPlayz
 * @version 1.2.7
 * @since 1.2.7
 */
public final class SlimefunAddon {

    public SlimefunAddon() {}

    public static boolean isSlimefunBlock(@NotNull Block block) {
        BlockStorage storage = BlockStorage.getStorage(block.getWorld());
        return storage != null && storage.hasInventory(block.getLocation());
    }

}