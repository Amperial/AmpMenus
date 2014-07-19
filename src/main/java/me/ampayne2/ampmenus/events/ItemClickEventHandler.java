/*
 * This file is part of AmpMenus.
 *
 * Copyright (c) 2014 <http://github.com/ampayne2/>
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
package me.ampayne2.ampmenus.events;

/**
 * Handles {@link me.ampayne2.ampmenus.events.ItemClickEvent}s.
 */
public interface ItemClickEventHandler {

    /**
     * Called when an Item in the {@link me.ampayne2.ampmenus.menus.Menu} is clicked.
     *
     * @param event The ItemClickEvent.
     */
    void onItemClick(ItemClickEvent event);
}
