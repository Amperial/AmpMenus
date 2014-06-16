package me.ampayne2.ampmenus.items;

import me.ampayne2.ampmenus.events.ItemClickEvent;
import me.ampayne2.ampmenus.events.ItemClickEventHandler;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * An Item inside an {@link me.ampayne2.ampmenus.menus.ItemMenu}.
 */
public class MenuItem implements ItemClickEventHandler {
    private final String displayName;
    private final ItemStack icon;
    private final String[] lore;

    public MenuItem(String displayName, ItemStack icon, String... lore) {
        this.displayName = displayName;
        this.icon = icon;
        for (int i = 0; i < lore.length; i++) {
            lore[i] = ChatColor.GRAY + lore[i];
        }
        this.lore = lore;
    }

    /**
     * Gets the display name of the MenuItem.
     *
     * @return The display name.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Gets the icon of the MenuItem.
     *
     * @return The icon.
     */
    public ItemStack getIcon() {
        return icon;
    }

    /**
     * Gets the lore of the MenuItem.
     *
     * @return The lore.
     */
    public String[] getLore() {
        return lore;
    }

    /**
     * Gets the ItemStack to be shown to the player.
     *
     * @param playerName The player's name.
     * @return The final icon.
     */
    public ItemStack getFinalIcon(String playerName) {
        ItemStack finalIcon = getIcon().clone();
        ItemMeta meta = finalIcon.getItemMeta();
        meta.setDisplayName(getDisplayName());
        meta.setLore(Arrays.asList(getLore()));
        finalIcon.setItemMeta(meta);
        return finalIcon;
    }

    @Override
    public void onItemClick(ItemClickEvent event) {
    }
}
