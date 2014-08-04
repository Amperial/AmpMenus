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
package ninja.amp.ampmenus.menus;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

/**
 * Allows you to set the {@link ninja.amp.ampmenus.menus.ItemMenu} that created the Inventory as the Inventory's holder.
 */
public class MenuHolder implements InventoryHolder {
    private ItemMenu menu;
    private Inventory inventory;

    public MenuHolder(ItemMenu menu, Inventory inventory) {
        this.menu = menu;
        this.inventory = inventory;
    }

    /**
     * Gets the {@link ninja.amp.ampmenus.menus.ItemMenu} holding the Inventory.
     *
     * @return The {@link ninja.amp.ampmenus.menus.ItemMenu} holding the Inventory.
     */
    public ItemMenu getMenu() {
        return menu;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
