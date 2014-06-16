package me.ampayne2.ampmenus.items;

import me.ampayne2.ampmenus.events.ItemClickEvent;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

/**
 * A {@link me.ampayne2.ampmenus.items.MenuItem} that closes the {@link me.ampayne2.ampmenus.menus.Menu}.
 */
public class CloseItem extends MenuItem {
    private static final String DISPLAY_NAME = ChatColor.RED + "Close";
    @SuppressWarnings("deprecation")
    private static final ItemStack ICON = new ItemStack(351, 1, (short) 1);

    public CloseItem() {
        super(DISPLAY_NAME, ICON);
    }

    @Override
    public void onItemClick(ItemClickEvent event) {
        event.setWillClose(true);
    }
}
