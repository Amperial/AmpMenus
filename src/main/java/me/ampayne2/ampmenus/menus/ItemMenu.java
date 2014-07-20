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
package me.ampayne2.ampmenus.menus;

import me.ampayne2.ampmenus.events.ItemClickEvent;
import me.ampayne2.ampmenus.items.MenuItem;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * A Menu controlled by ItemStacks in an Inventory.
 */
public class ItemMenu implements Menu, Listener {
    private JavaPlugin plugin;
    private String name;
    private int size;
    private MenuItem[] items;
    private Menu parent;

    /**
     * The ItemStack that appears in empty slots.
     */
    @SuppressWarnings("deprecation")
    private static final ItemStack EMPTY_SLOT = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.GRAY.getData());

    /**
     * Creates an {@link me.ampayne2.ampmenus.menus.ItemMenu}.
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
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    /**
     * Creates an {@link me.ampayne2.ampmenus.menus.ItemMenu} with no parent.
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
     * Sets the {@link me.ampayne2.ampmenus.items.MenuItem} of a slot.
     *
     * @param position The slot position.
     * @param menuItem The {@link me.ampayne2.ampmenus.items.MenuItem}.
     * @return The {@link me.ampayne2.ampmenus.menus.ItemMenu}.
     */
    public ItemMenu setItem(int position, MenuItem menuItem) {
        items[position] = menuItem;
        return this;
    }

    /**
     * Opens the {@link me.ampayne2.ampmenus.menus.ItemMenu} for a player.
     *
     * @param player The player.
     */
    @Override
    public void open(Player player) {
        Inventory inventory = Bukkit.createInventory(new MenuHolder(this, Bukkit.createInventory(player, size)), size, name);
        apply(inventory, player);
        player.openInventory(inventory);
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
            if (items[i] == null) {
                inventory.setItem(i, EMPTY_SLOT);
            } else {
                inventory.setItem(i, items[i].getFinalIcon(player));
            }
        }
    }

    /**
     * Handles InventoryClickEvents for the ItemMenu.
     */
    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player) || !(event.getInventory().getHolder() instanceof MenuHolder) || !((MenuHolder) event.getInventory().getHolder()).getMenu().equals(this)) {
            return;
        }

        event.setCancelled(true);
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
        HandlerList.unregisterAll(this);
        plugin = null;
        items = null;
    }

    static {
        ItemMeta meta = EMPTY_SLOT.getItemMeta();
        meta.setDisplayName(" ");
        EMPTY_SLOT.setItemMeta(meta);
    }
}
