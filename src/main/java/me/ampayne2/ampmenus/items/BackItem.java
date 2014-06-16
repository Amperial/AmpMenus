package me.ampayne2.ampmenus.items;

import me.ampayne2.ampmenus.events.ItemClickEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * A {@link me.ampayne2.ampmenus.items.MenuItem} that opens the {@link me.ampayne2.ampmenus.menus.Menu}'s parent menu if it exists.
 */
public class BackItem extends MenuItem {
    private static final String DISPLAY_NAME = ChatColor.RED + "Back";
    private static final ItemStack ICON = new ItemStack(Material.FENCE_GATE);

    public BackItem() {
        super(DISPLAY_NAME, ICON);
    }

    @Override
    public void onItemClick(ItemClickEvent event) {
        event.setWillGoBack(true);
    }
}
