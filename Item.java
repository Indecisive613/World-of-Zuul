/**
 * Class Item - An item in an adventure game
 * 
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * An "Item" represents an object that is found in a room.  
 * Each item has a description, a weight and a name.
 *
 * @Fiona Cheng (Student Number 101234672)
 * @version February 11, 2023
 * 
 * @Fiona Cheng (Student Number 101234672)
 * @version February 22, 2023
 */
public class Item
{
    private String description;
    private double weight;
    private String name;
    private double price; //New!
    
    /**
     * Create an item described by "description" with a weight of "weight" and a name "name".
     * 
     * @param description The item's description
     * @param weight The item's weight in kg
     * @param name The item's name
     * @param price The item's price
     */
    public Item(String description, double weight, String name, double price)
    {
        this.description = description;
        this.weight = weight;
        this.name = name;
        this.price = price; 
    }

    /**
     * Return a string representation of the item.
     *
     * @return The string reperesentation of the item
     */
    public String getDescription()
    {
        return "a " + description + " that weighs " + weight + "kg.";
    }
    
    /**
     * Return the name of the item.
     *
     * @return The name of the item
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Return the price of the item.
     *
     * @return The price of the item
     */
    public double getPrice()
    {
        return price;
    }
    
    /**
     * Sets the price of the item.
     *
     * @param The price of the item
     */
    public void setPrice(double price)
    {
        this.price = price;
    }
}
