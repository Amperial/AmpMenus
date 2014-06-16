package me.ampayne2.ampmenus.events;

import org.bukkit.entity.Player;

/**
 * An event fired when an Icon in the IconMenu is clicked.
 */
public class ItemClickEvent {
    private Player player;
    private boolean goBack = false;
    private boolean close = true;

    public ItemClickEvent(Player player) {
        this.player = player;
    }

    /**
     * Gets the player who clicked.
     *
     * @return The player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Checks if the menu will go back to the parent menu.
     *
     * @return True if the menu will go back to the parent menu, else false.
     */
    public boolean willGoBack() {
        return goBack;
    }

    /**
     * Sets if the menu will go back to the parent menu.
     *
     * @param goBack If the menu will go back to the parent menu.
     */
    public void setWillGoBack(boolean goBack) {
        this.goBack = goBack;
        if (goBack) {
            close = false;
        }
    }

    /**
     * Checks if the menu will close.
     *
     * @return True if the menu will close, else false.
     */
    public boolean willClose() {
        return close;
    }

    /**
     * Sets if the menu will close.
     *
     * @param close If the menu will close.
     */
    public void setWillClose(boolean close) {
        this.close = close;
        if (close) {
            goBack = false;
        }
    }
}
