import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 * 
 * @author Lynn Marshall
 * @version October 21, 2012
 * 
 * @author Fiona Cheng (Student Number 101234672)
 * @version February 11, 2023
 * 
 * @Fiona Cheng (Student Number 101234672)
 * @version February 22, 2023
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room
    private ArrayList<Item> items;  //stores items of this room

    /**
     * Create a room described "description". Initially, it has
     * no exits and no items. "description" is something like "a kitchen" or
     * "an open court yard".
     * 
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<String, Room>();
        items = new ArrayList<Item>();
    }

    /**
     * Define an exit from this room.
     * 
     * @param direction The direction of the exit
     * @param neighbour The room to which the exit leads
     */
    public void setExit(String direction, Room neighbour) 
    {
        exits.put(direction, neighbour);
    }

    /**
     * Returns a short description of the room, i.e. the one that
     * was defined in the constructor
     * 
     * @return The short description of the room
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a long description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     *     Items:
     *          a fir tree that weights 500.0kg.
     *     
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString() + "\n" + getItems();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * 
     * @return Details of the room's exits
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * 
     * @param direction The exit's direction
     * @return The room in the given direction
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    /**
     * Add an object to the room.
     * 
     * @param description The item's description
     * @param weight The item's weight
     * @param name The item's name
     */
    public void addItem(String description, double weight, String name, double price) 
    {
        items.add(new Item(description, weight, name, price));
    }
    
    /**
     * Add an object to the room.
     * 
     * @param item The item to be added to the room
     */
    public void addItem(Item item) 
    {
        items.add(item);
    }
    
    /**
     * Return a string representation of the items in the room, for example:
     *      Items:
     *          a blue jacket that weighs 0.5kg.
     *          a pink waterbottle that weighs 2.1kg.
     * 
     * @return result The string representation of all the items in the room
     */
    public String getItems() 
    {
        if(items.size() == 0){
            return "There are no items in this room.\n";
        }
        String result = "Items:\n";
        for(Item item: items) {
            result += "\t" + item.getDescription() + "\n";
        }
        return result;
    }
    
    /**
     * Return an item specified by name.
     * The item is removed from the items in the room.
     * 
     * @param name The name of the item to be taken
     * @return item The item to be returned
     */
    public Item takeItem(String name) 
    {
        for(Item item: items) {
            if(item.getName().equals(name)){
                items.remove(item);
                return item;
            }
        }
        return null; //the item is not in the list (should never get here)
    }
}

