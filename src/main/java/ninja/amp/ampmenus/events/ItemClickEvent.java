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
package ninja.amp.ampmenus.events;

import org.bukkit.entity.Player;

/**
 * An event called when an Item in the {@link ninja.amp.ampmenus.menus.ItemMenu} is clicked.
 */
public class ItemClickEvent {
    private Player player;
    private boolean goBack = false;
    private boolean close = false;
    private boolean update = false;

    public ItemClickEvent(Player player) {
        this.player = player;
    }

    /**
     * Gets the player who clicked.
     *
     * @return The player who clicked.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Checks if the {@link ninja.amp.ampmenus.menus.ItemMenu} will go back to the parent menu.
     *
     * @return True if the {@link ninja.amp.ampmenus.menus.ItemMenu} will go back to the parent menu, else false.
     */
    public boolean willGoBack() {
        return goBack;
    }

    /**
     * Sets if the {@link ninja.amp.ampmenus.menus.ItemMenu} will go back to the parent menu.
     *
     * @param goBack If the {@link ninja.amp.ampmenus.menus.ItemMenu} will go back to the parent menu.
     */
    public void setWillGoBack(boolean goBack) {
        this.goBack = goBack;
        if (goBack) {
            close = false;
            update = false;
        }
    }

    /**
     * Checks if the {@link ninja.amp.ampmenus.menus.ItemMenu} will close.
     *
     * @return True if the {@link ninja.amp.ampmenus.menus.ItemMenu} will close, else false.
     */
    public boolean willClose() {
        return close;
    }

    /**
     * Sets if the {@link ninja.amp.ampmenus.menus.ItemMenu} will close.
     *
     * @param close If the {@link ninja.amp.ampmenus.menus.ItemMenu} will close.
     */
    public void setWillClose(boolean close) {
        this.close = close;
        if (close) {
            goBack = false;
            update = false;
        }
    }

    /**
     * Checks if the {@link ninja.amp.ampmenus.menus.ItemMenu} will update.
     *
     * @return True if the {@link ninja.amp.ampmenus.menus.ItemMenu} will update, else false.
     */
    public boolean willUpdate() {
        return update;
    }

    /**
     * Sets if the {@link ninja.amp.ampmenus.menus.ItemMenu} will update.
     *
     * @param update If the {@link ninja.amp.ampmenus.menus.ItemMenu} will update.
     */
    public void setWillUpdate(boolean update) {
        this.update = update;
        if (update) {
            goBack = false;
            close = false;
        }
    }
}
