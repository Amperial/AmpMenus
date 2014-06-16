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
import org.bukkit.plugin.Plugin;

/**
 * A utility that allows creating inventories with items that do stuff when clicked.
 */
public class ItemMenu implements Listener, Menu {
    private String name;
    private int size;
    private Plugin plugin;
    private MenuItem[] menuItems;
    private Menu parent;

    /**
     * The ItemStack that appears in empty slots.
     */
    private static final ItemStack EMPTY_SLOT;

    /**
     * Creates a new ItemMenu.
     *
     * @param name   The name of the inventory.
     * @param size   The size of the inventory.
     * @param plugin The Plugin instance.
     * @param parent The ItemMenu's parent.
     */
    public ItemMenu(String name, int size, Plugin plugin, Menu parent) {
        this.name = name;
        this.size = size;
        this.plugin = plugin;
        this.menuItems = new MenuItem[size];
        this.parent = parent;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    /**
     * Creates a new ItemMenu with no parent.
     *
     * @param name   The name of the inventory.
     * @param size   The size of the inventory.
     * @param plugin The Plugin instance.
     */
    public ItemMenu(String name, int size, Plugin plugin) {
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
     * @return The ItemMenu.
     */
    public ItemMenu setItem(int position, MenuItem menuItem) {
        menuItems[position] = menuItem;
        return this;
    }

    /**
     * Opens the ItemMenu for a player.
     *
     * @param player The player.
     */
    public void open(Player player) {
        String playerName = player.getName();
        Inventory inventory = Bukkit.createInventory(player, size, name);
        for (int i = 0; i < menuItems.length; i++) {
            if (menuItems[i] == null) {
                inventory.setItem(i, EMPTY_SLOT);
            } else {
                inventory.setItem(i, menuItems[i].getFinalIcon(playerName));
            }
        }
        player.openInventory(inventory);
    }

    /**
     * Handles InventoryClickEvents for the ItemMenu.
     */
    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equals(name)) {
            event.setCancelled(true);
            if (event.getClick() == ClickType.LEFT) {
                int slot = event.getRawSlot();
                if (slot >= 0 && slot < size && menuItems[slot] != null) {
                    Player player = (Player) event.getWhoClicked();
                    ItemClickEvent itemClickEvent = new ItemClickEvent(player);
                    menuItems[slot].onItemClick(itemClickEvent);
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

    /**
     * Destroys the ItemMenu.
     */
    public void destroy() {
        HandlerList.unregisterAll(this);
        plugin = null;
        menuItems = null;
    }

    static {
        EMPTY_SLOT = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.GRAY.getData());
        ItemMeta meta = EMPTY_SLOT.getItemMeta();
        meta.setDisplayName(" ");
        EMPTY_SLOT.setItemMeta(meta);
    }
}