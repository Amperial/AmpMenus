package me.ampayne2.ampmenus.menus;

import org.bukkit.entity.Player;

public interface Menu {

    /**
     * Gets the name of the menu.
     *
     * @return The menu's name.
     */
    String getName();

    /**
     * Gets the size of the menu.
     *
     * @return The menu's size.
     */
    int getSize();

    /**
     * Checks if the menu has a parent.
     *
     * @return True if the menu has a parent, else false.
     */
    boolean hasParent();

    /**
     * Gets the parent of the menu.
     *
     * @return The menu's parent.
     */
    Menu getParent();

    /**
     * Sets the parent of the menu.
     *
     * @param menu The menu's parent.
     */
    void setParent(Menu menu);

    /**
     * Opens a menu for a player.
     *
     * @param player The player.
     */
    void open(Player player);
}
