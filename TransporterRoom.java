import java.util.ArrayList;
import java.util.Random;

/**
 * Class TransporterRoom - An Transporter Room in an adventure game
 * 
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A Transporter Room is a type of Room that transports the player
 * to a random room when they leave.
 *
 * @Fiona Cheng (Student Number 101234672)
 * @version February 21, 2023
 */
public class TransporterRoom extends Room
{
    private static ArrayList<Room> roomList = new ArrayList<Room>();
    
    /**
     * Creates a transporter room.
     * 
     * @param roomList The list of other rooms
     */
    public TransporterRoom(ArrayList<Room> roomList){
        super("in a transporter room");
        this.roomList = roomList;
    }
    /**
     * Returns a random room, independent of the direction parameter.
     *
     * @param direction Ignored.
     * @return A randomly selected room.
     */
    @Override
    public Room getExit(String direction)
    {
        return findRandomRoom();
    }

    /**
     * Choose a random room.
     *
     * @return The room we end up in upon leaving this one.
     */
    private Room findRandomRoom()
    {
        Random rand = new Random();
        int rand_int = rand.nextInt(roomList.size()); //generates a random index for roomList
        
        return roomList.get(rand_int);
    }
}