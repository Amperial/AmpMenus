/*
 * This file is part of AmpMenus.
 *
 * Copyright (c) 2014 <http://github.com/ampayne2/AmpMenus/>
 *
 * AmpMenus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AmpMenus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with AmpMenus.  If not, see <http://www.gnu.org/licenses/>.
 */
package ninja.amp.ampmenus.items;

import ninja.amp.ampmenus.events.ItemClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

/**
 * An Item inside an {@link ninja.amp.ampmenus.menus.ItemMenu}.
 */
public class MenuItem implements IMenuItem {
    private final String displayName;
    private final ItemStack icon;
    private final List<String> lore;

    public MenuItem(String displayName, ItemStack icon, String... lore) {
        this.displayName = displayName;
        this.icon = icon;
        this.lore = Arrays.asList(lore);
    }

    /**
     * Gets the display name of the MenuItem.
     *
     * @return The display name.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Gets the icon of the MenuItem.
     *
     * @return The icon.
     */
    public ItemStack getIcon() {
        return icon;
    }

    /**
     * Gets the lore of the MenuItem.
     *
     * @return The lore.
     */
    public List<String> getLore() {
        return lore;
    }

    /**
     * Gets the ItemStack to be shown to the player.
     *
     * @param player The player.
     * @return The final icon.
     */
    @Override public ItemStack getFinalIcon(Player player) {
        return setNameAndLore(getIcon().clone(), getDisplayName(), getLore());
    }

    /**
     * Called when the MenuItem is clicked.
     *
     * @param event The {@link ninja.amp.ampmenus.events.ItemClickEvent}.
     */
    @Override public void onItemClick(ItemClickEvent event) {
        // Do nothing by default
    }

    /**
     * Sets the display name and lore of an ItemStack.
     *
     * @param itemStack   The ItemStack.
     * @param displayName The display name.
     * @param lore        The lore.
     * @return The ItemStack.
     */
    public static ItemStack setNameAndLore(ItemStack itemStack, String displayName, List<String> lore) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
