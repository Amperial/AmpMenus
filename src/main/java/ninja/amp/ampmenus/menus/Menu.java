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

import org.bukkit.entity.Player;

public interface Menu {

    /**
     * Gets the name of the menu.
     *
     * @return The menu's name.
     */
    String getName();

    /**
     * Gets the size of the menu.
     *
     * @return The menu's size.
     */
    int getSize();

    /**
     * Checks if the menu has a parent.
     *
     * @return True if the menu has a parent, else false.
     */
    boolean hasParent();

    /**
     * Gets the parent of the menu.
     *
     * @return The menu's parent.
     */
    Menu getParent();

    /**
     * Sets the parent of the menu.
     *
     * @param menu The menu's parent.
     */
    void setParent(Menu menu);

    /**
     * Opens a menu for a player.
     *
     * @param player The player.
     */
    void open(Player player);

    /**
     * Updates the menu for a player.
     *
     * @param player The player to update the menu for.
     */
    void update(Player player);

    /**
     * Destroys the menu.
     */
    void destroy();
}
