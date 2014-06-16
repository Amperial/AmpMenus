package me.ampayne2.ampmenus.items;

import me.ampayne2.ampmenus.events.ItemClickEvent;
import me.ampayne2.ampmenus.menus.Menu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * A {@link me.ampayne2.ampmenus.items.MenuItem} that opens a sub {@link me.ampayne2.ampmenus.menus.Menu}.
 */
public class SubMenuItem extends MenuItem {
    private final JavaPlugin plugin;
    private final Menu menu;

    public SubMenuItem(JavaPlugin plugin, String displayName, ItemStack icon, Menu menu, String... lore) {
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
