/**
 * Class Beamer - An Beamer in an adventure game
 * 
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Beamer" is a type of Item that can be charged and fired.
 * When the beamer is fired, it should take the player to the
 * location where the beamer was charged.
 *
 * @Fiona Cheng (Student Number 101234672)
 * @version February 21, 2023
 */
public class Beamer extends Item
{
    private Room chargedRoom;

    /**
     * Creates a Beamer.
     */
    public Beamer()
    {
        super("wonderful beamer", 20.1, "beamer", 250.0);
        chargedRoom = null;
    }

    /**
     * Return whether or not the beamer is charged.
     *
     * @return Whether or not the beamer is charged
     */
    public boolean isCharged()
    {
        return chargedRoom != null;
    }
    
    /**
     * Return the room to move to after the beamer is fired.
     *
     * @return location The room to move to
     */
    public Room fire()
    {
        Room location = chargedRoom;
        chargedRoom = null;
        return location;
    }
    
    /**
     * Charges the beamer. 
     * Sets the room in which the beamer was charged to "currentRoom".
     *
     * @param currentRoom The room in which the beamer was charged
     */
    public void charge(Room currentRoom)
    {
        chargedRoom = currentRoom;
    }
}
