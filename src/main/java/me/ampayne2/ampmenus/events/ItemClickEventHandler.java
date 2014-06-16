package me.ampayne2.ampmenus.events;

/**
 * Handles {@link me.ampayne2.ampmenus.events.ItemClickEvent}s.
 */
public interface ItemClickEventHandler {

    /**
     * Called when an Icon in the IconMenu is clicked.
     *
     * @param event The ItemClickEvent.
     */
    void onItemClick(ItemClickEvent event);
}
