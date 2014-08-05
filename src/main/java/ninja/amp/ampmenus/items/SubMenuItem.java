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
import ninja.amp.ampmenus.menus.ItemMenu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * A {@link ninja.amp.ampmenus.items.MenuItem} that opens a sub {@link ninja.amp.ampmenus.menus.ItemMenu}.
 */
public class SubMenuItem extends MenuItem {
    private final JavaPlugin plugin;
    private final ItemMenu menu;

    public SubMenuItem(JavaPlugin plugin, String displayName, ItemStack icon, ItemMenu menu, String... lore) {
        super(displayName, icon, lore);
        this.plugin = plugin;
        this.menu = menu;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onItemClick(ItemClickEvent event) {
        event.setWillClose(true);
        final String playerName = event.getPlayer().getName();
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            public void run() {
                Player p = Bukkit.getPlayerExact(playerName);
                if (p != null) {
                    menu.open(p);
                }
            }
        }, 3);
    }
}
