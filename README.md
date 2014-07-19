AmpMenus
========

An object-oriented approach to handling Inventory Menus/GUIs in Bukkit.

AmpMenus is intended for advanced use cases such as the following examples:

![Example1](https://github.com/ampayne2/AmpMenus/blob/master/Example1.png)

Attribute Allocation Menu
* Displays the player's current attribute amounts on the attribute items
* Increase and decrease the points to add by clicking the wool above/below the attribute items
* Display the increase points item as green wool if the player has enough attribute points to increase the attribute
* Display the decrease points item as red wool if the player has actually increased the attribute
* Confirm or cancel point selection

![Example2](https://github.com/ampayne2/AmpMenus/blob/master/Example2.png)

Capture The Flag Shop
* Display the next available tier of different classes and perks

Because the items in these menus behave differently depending on the player and certain data or conditions, they would not be possible with a traditional plugin such as http://dev.bukkit.org/bukkit-plugins/chest-commands/ and would be impractical to create with a simpler utility such as https://forums.bukkit.org/threads/icon-menu.108342/

For Developers
--------------

If you're using [Maven](http://maven.apache.org/download.html) to manage project dependencies, simply include the following in your `pom.xml`:

    <repository>
      <id>greatman-repo</id>
      <url>http://repo.greatmancode.com/content/groups/public/</url>
    </repository>

    <dependency>
      <groupId>me.ampayne2</groupId>
      <artifactId>ampmenus</artifactId>
      <version>1.0</version>
      <scope>compile</scope>
    </dependency>

Basic Usage:

    // Create an ItemMenu instance - you only need one of these. Don't create one every time you need it...
    ItemMenu menu = new ItemMenu("Shop", 18, plugin);
    
    // Adding items to your ItemMenu
    menu.setItem(17, new CloseItem());
    
    // Opening the menu for a Player
    menu.open(player);

Advanced Usage:

    public class ShopMenu extends ItemMenu {
        public ShopMenu(JavaPlugin plugin) {
            super("Shop", 18, plugin);

            // Adding items to your ItemMenu
            menu.setItem(17, new CloseItem());
        }
    
        // Useful in case you only want the back item to appear if the menu has a parent
        @Override
        public void setParent(Menu parent) {
            super.setParent(parent);
            if (parent != null) {
                getIconMenu().setItem(16, new BackItem());
            }
        }
    }

    // Setting parent menus so that the BackItem knows where to go
    ItemMenu subMenu = new ItemMenu("Vote Perks", 45, plugin);
    subMenu.setParent(mainMenu);
    OR
    ItemMenu subMenu = new ItemMenu("Vote Perks", 45, plugin, mainMenu);

Creating Menu Items:

    /**
    * A MenuItem that makes the player perform the "kill" command
    */
    public class SuicideItem extends MenuItem {
        private static final String DISPLAY_NAME = ChatColor.BLUE + "Click for OP!";
        private static final ItemStack ICON = new ItemStack(Material.DIAMOND);
    
        public SuicideItem() {
            super(DISPLAY_NAME, ICON);
        }
    
        // This method controls what happens when the MenuItem is clicked
        @Override
        public void onItemClick(ItemClickEvent event) {
            event.getPlayer().performCommand("kill");
        }
    
        // This method lets you modify the ItemStack before it is displayed, based on the player opening the menu
        @Override
        public ItemStack getFinalIcon(Player player) {
            ItemStack finalIcon = super.getFinalIcon(player);
            if (player.hasPermission("you.cant.fool.me")) {
                finalIcon.setType(Material.LEASH);
                ItemMeta meta = finalIcon.getItemMeta();
                meta.setDisplayName(ChatColor.DARK_RED + "Suicide");
                finalIcon.setItemMeta(meta);
            }
            return finalIcon;
        }
    }

Compilation
-----------

AmpMenus uses Maven to handle its dependencies.

* Download and install [Maven 3](http://maven.apache.org/download.html)  
* Checkout this repo and run: `mvn clean install`

The License
-----------

AmpMenus is licensed under the [GNU Lesser General Public License Version 3](https://github.com/ampayne2/AmpMenus/blob/master/LICENSE.txt)

Contributing
------------

Guidelines:
* All new files must include the license header. This can be done automatically with Maven by running mvn clean install.
* Generally follow the Oracle code conventions and the current style.
* Use four spaces for indentation, not tabs.
* No trailing whitespace (spaces/tabs on new lines and end of lines).
* 200 column limit for readability.