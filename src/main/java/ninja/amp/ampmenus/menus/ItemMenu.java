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

import ninja.amp.ampmenus.MenuListener;
import ninja.amp.ampmenus.events.ItemClickEvent;
import ninja.amp.ampmenus.items.MenuItem;
import ninja.amp.ampmenus.items.StaticMenuItem;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * A Menu controlled by ItemStacks in an Inventory.
 */
public class ItemMenu implements Menu {
    private JavaPlugin plugin;
    private String name;
    private int size;
    private MenuItem[] items;
    private Menu parent;

    /**
     * The {@link ninja.amp.ampmenus.items.StaticMenuItem} that appears in empty slots if {@link ninja.amp.ampmenus.menus.ItemMenu#fillEmptySlots()} is called.
     */
    @SuppressWarnings("deprecation")
    private static final MenuItem EMPTY_SLOT_ITEM = new StaticMenuItem(" ", new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.GRAY.getData()));

    /**
     * Creates an {@link ninja.amp.ampmenus.menus.ItemMenu}.
     *
     * @param name   The name of the inventory.
     * @param size   The size of the inventory.
     * @param plugin The {@link org.bukkit.plugin.java.JavaPlugin} instance.
     * @param parent The ItemMenu's parent.
     */
    public ItemMenu(String name, int size, JavaPlugin plugin, Menu parent) {
        this.plugin = plugin;
        this.name = name;
        this.size = size;
        this.items = new MenuItem[size];
        this.parent = parent;
        MenuListener.getInstance().register(plugin);
    }

    /**
     * Creates an {@link ninja.amp.ampmenus.menus.ItemMenu} with no parent.
     *
     * @param name   The name of the inventory.
     * @param size   The size of the inventory.
     * @param plugin The Plugin instance.
     */
    public ItemMenu(String name, int size, JavaPlugin plugin) {
        this(name, size, plugin, null);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean hasParent() {
        return parent != null;
    }

    @Override
    public Menu getParent() {
        return parent;
    }

    @Override
    public void setParent(Menu parent) {
        this.parent = parent;
    }

    /**
     * Sets the {@link ninja.amp.ampmenus.items.MenuItem} of a slot.
     *
     * @param position The slot position.
     * @param menuItem The {@link ninja.amp.ampmenus.items.MenuItem}.
     * @return The {@link ninja.amp.ampmenus.menus.ItemMenu}.
     */
    public ItemMenu setItem(int position, MenuItem menuItem) {
        items[position] = menuItem;
        return this;
    }

    /**
     * Fills all empty slots in the {@link ninja.amp.ampmenus.menus.ItemMenu} with a certain {@link ninja.amp.ampmenus.items.MenuItem}.
     *
     * @param menuItem The {@link ninja.amp.ampmenus.items.MenuItem}.
     * @return The {@link ninja.amp.ampmenus.menus.ItemMenu}.
     */
    public ItemMenu fillEmptySlots(MenuItem menuItem) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                items[i] = menuItem;
            }
        }
        return this;
    }

    /**
     * Fills all empty slots in the {@link ninja.amp.ampmenus.menus.ItemMenu} with the default empty slot item.
     *
     * @return The {@link ninja.amp.ampmenus.menus.ItemMenu}.
     */
    public ItemMenu fillEmptySlots() {
        return fillEmptySlots(EMPTY_SLOT_ITEM);
    }

    /**
     * Opens the {@link ninja.amp.ampmenus.menus.ItemMenu} for a player.
     *
     * @param player The player.
     */
    @Override
    public void open(Player player) {
        if (MenuListener.getInstance().isRegistered(plugin)) {
            Inventory inventory = Bukkit.createInventory(new MenuHolder(this, Bukkit.createInventory(player, size)), size, name);
            apply(inventory, player);
            player.openInventory(inventory);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public void update(Player player) {
        if (player.getOpenInventory() != null) {
            Inventory inventory = player.getOpenInventory().getTopInventory();
            if (inventory.getHolder() instanceof MenuHolder && ((MenuHolder) inventory.getHolder()).getMenu().equals(this)) {
                apply(inventory, player);
                player.updateInventory();
            }
        }
    }

    /**
     * Applies a Player's ItemMenu to an Inventory.
     *
     * @param inventory The Inventory.
     * @param player    The Player.
     */
    private void apply(Inventory inventory, Player player) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) {
                inventory.setItem(i, items[i].getFinalIcon(player));
            }
        }
    }

    /**
     * Handles InventoryClickEvents for the ItemMenu.
     */
    @SuppressWarnings("deprecation")
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClick() == ClickType.LEFT) {
            int slot = event.getRawSlot();
            if (slot >= 0 && slot < size && items[slot] != null) {
                Player player = (Player) event.getWhoClicked();
                ItemClickEvent itemClickEvent = new ItemClickEvent(player);
                items[slot].onItemClick(itemClickEvent);
                if (itemClickEvent.willUpdate()) {
                    update(player);
                } else {
                    player.updateInventory();
                    if (itemClickEvent.willClose() || itemClickEvent.willGoBack()) {
                        final String playerName = player.getName();
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                            public void run() {
                                Player p = Bukkit.getPlayerExact(playerName);
                                if (p != null) {
                                    p.closeInventory();
                                }
                            }
                        }, 1);
                    }
                    if (itemClickEvent.willGoBack() && hasParent()) {
                        final String playerName = player.getName();
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                            public void run() {
                                Player p = Bukkit.getPlayerExact(playerName);
                                if (p != null) {
                                    parent.open(p);
                                }
                            }
                        }, 3);
                    }
                }
            }
        }
    }

    @Override
    public void destroy() {
        plugin = null;
        items = null;
    }
}
