import java.util.Stack;
import java.util.ArrayList;
import java.util.Random;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around the university, teleport, eat, and pick up items.
 *  New additions include the ability to buy and sell items in the store,
 *  and an encounter that occurs randomnly after the go command.
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
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

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Room previousRoom;
    private Stack<Room> roomHistory;
    private Item myItem;
    private int itemsHeld;
    private double money; //New!
    private Random rand; //New!
    private ArrayList<Item> storeItems; //New!
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        
        myItem = null;
        itemsHeld = 5; //Start the game unable to take any items
        money = 0; 
        rand = new Random();
        
        storeItems = new ArrayList<Item>();
        storeItems.add(new Beamer());
        storeItems.add(new Item("chocolate chip cookie", 1, "cookie", 1.50));
        storeItems.add(new Item("new chemistry textbook", 2.8, "textbook", 120));
        storeItems.add(new Item("sheef of lined paper", 2.8, "paper", 3.4));
        
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theatre, pub, lab, office, transporter, store;
      
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theatre = new Room("in a lecture theatre");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        store = new Room("in the university bookstore");
        
        //Create the arrayList of Rooms for the transporter
        ArrayList<Room> roomList = new ArrayList<Room>();
        roomList.add(outside);
        roomList.add(theatre);
        roomList.add(pub);
        roomList.add(lab);
        roomList.add(office);
        roomList.add(store);
        
        transporter = new TransporterRoom(roomList);
        
        // initialise room exits and items
        outside.setExit("east", theatre);
        outside.setExit("south", lab);
        outside.setExit("west", pub);
        outside.setExit("north", transporter);
        outside.addItem("comfortable bench", 40.3, "bench", 30.5);
        outside.addItem("large rock", 204.5, "rock", 0.99);
        outside.addItem("chocolate chip cookie", 1, "cookie", 1.50);
        outside.addItem(new Beamer());

        theatre.setExit("west", outside);
        theatre.setExit("north", store);
        theatre.addItem("big old TV", 66., "TV", 450.8);
        theatre.addItem("blue oversized jacket", 2.5, "jacket", 40.1);
        theatre.addItem("peanut butter cookie", 1, "cookie", 2.5);
        theatre.addItem(new Beamer());

        pub.setExit("east", outside);
        pub.addItem("three-legged stool", 10.0, "stool", 50.0);
        pub.addItem("empty cup", 0.5, "cup", 24.6);
        pub.addItem("oatmeal raisin cookie", 1, "cookie", 1.75);

        lab.setExit("north", outside);
        lab.setExit("east", office);
        lab.addItem("lab computer", 9.8, "computer", 345.67);
        lab.addItem("dusty beaker", 0.2, "beaker", 1.99);
        lab.addItem("sugar cookie", 1, "cookie", 2.75);

        office.setExit("west", lab);
        office.addItem("fancy desk", 42.5, "desk", 189.3);
        office.addItem("pink calculator", 1.3, "calculator", 56.8);
        office.addItem("ginger cookie", 1, "cookie", 5.10);
        
        transporter.setExit("south", outside);
        transporter.setExit("east", store);
        transporter.addItem("rusted spring", 0.5, "spring", 0.5);
        transporter.addItem("giant deck of playing cards", 2.3, "cards", 75.6);
        
        store.setExit("south", theatre);
        store.setExit("west", transporter);
        store.addItem("penny", 0.01, "penny", 0.01);

        currentRoom = outside;  // start game outside
        roomHistory = new Stack<Room>(); // start game with an empty room history
        previousRoom = null;
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.print(currentRoom.getLongDescription());
        displayMyItem();
        displayMoney();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * 
     * @param command The command to be processed
     * @return true If the command ends the game, false otherwise
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        } 
        else if (commandWord.equals("look")) {
            look(command);
        }
        else if (commandWord.equals("eat")) {
            eat(command);
        }
        else if (commandWord.equals("back")) {
            back(command);
        }
        else if (commandWord.equals("stackBack")) {
            stackBack(command);
        }
        else if (commandWord.equals("take")) {
            take(command);
        }
        else if (commandWord.equals("drop")) {
            drop(command);
        }
        else if (commandWord.equals("charge")) {
            charge(command);
        }
        else if (commandWord.equals("fire")) {
            fire(command);
        }
        else if (commandWord.equals("sell")) {
            sell(command);
        }
        else if (commandWord.equals("buy")) {
            buy(command);
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print a cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.getCommands());
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     * 
     * @param command The command to be processed
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom;
        if(currentRoom.getShortDescription().equals("in a Transporter Room")){
            TransporterRoom transpo = (TransporterRoom)currentRoom; //cast to TransporterRoom type in order to access the additional functionality
            nextRoom = transpo.getExit(direction);
        } else{
             nextRoom = currentRoom.getExit(direction);
        }

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            roomHistory.push(currentRoom); //adds room to movement history
            
            //moves to the next room
            previousRoom = currentRoom; 
            currentRoom = nextRoom;
            
            System.out.print(currentRoom.getLongDescription());
            displayMyItem();
            displayMoney();
            
            //possible encounter
            int randInt = rand.nextInt(4);
            if(randInt == 1){ //25% chance of an encounter
                encounter();
            }
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * 
     * @param command The command to be processed
     * @return true, if this command quits the game, false otherwise
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    /** 
     * Displays information about the current room. Checks the rest of the 
     * command to see if it specifies where to look.
     * 
     * @param command The command to be processed
     */
    private void look(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("Look where?");
            return;
        }
        System.out.print(currentRoom.getLongDescription());
        displayMyItem();
        displayMoney();
    }
    
    /** 
     * Displays confirmation that you have eaten.
     * Checks the rest of the command to see if it specifies what to eat.
     * 
     * @param command The command to be processed
     */
    private void eat(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("Eat what?");
            return;
        }
        if(myItem == null){ //need to be holding a cookie
            System.out.println("You have nothing to eat.");
            return;
        }
        if(myItem.getName().equals("cookie")){
            System.out.println("You have eaten and are no longer hungry");
            myItem = null;
            itemsHeld = 0;
        }else{ //the item held by the player is not a cookie
            System.out.println("You have nothing to eat.");
        }
    }
    
    /** 
     * Goes to the previous room.
     * 
     * @param command The command to be processed
     */
    private void back(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("Back what?");
            return;
        }
        if(previousRoom == null){ 
            System.out.println("No room to go back to.");
            return;
        }
        roomHistory.push(currentRoom); //adds room to the movement history
        
        //Swaps current and previous rooms
        Room temp = previousRoom;
        previousRoom = currentRoom;
        currentRoom = temp;
        
        System.out.print(currentRoom.getLongDescription());
        displayMyItem();
        displayMoney();
    }
    
    /** 
     * Goes to the previous room until you reach the beginning 
     * of the game.
     * 
     * @param command The command to be processed
     */
    private void stackBack(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("stackBack what?");
            return;
        }
        if(roomHistory.empty()){
            System.out.println("No room to go stack back to.");
            return;
        }
        previousRoom = currentRoom;
        
        currentRoom = roomHistory.pop(); //go to previous room
        
        System.out.print(currentRoom.getLongDescription());
        displayMyItem();
        displayMoney();
    }
    
    /** 
     * Displays the the item the player is carrying.
     */
    private void displayMyItem()
    {
        if(myItem == null) {
            System.out.println("You are not carrying anything.");
            return;
        }
        System.out.println("You are carrying:");
        System.out.println("\t" + myItem.getName() + ": " + myItem.getDescription());
    }
    
    /** 
     * Picks up an item from the room specified by command.
     * The item must be found in the current room, the player must
     * not be holding another item, and the player must have held
     * less than 5 items since eating their last cookie.
     * 
     * @param command The command to be processed
     */
    private void take(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Take what?");
            return;
        }
        
        String itemName = command.getSecondWord();
        
        if(myItem != null){ //can only hold one item at a time
            System.out.println("You are already holding something.");
            return;
        }
        if(itemsHeld == 5 && !itemName.equals("cookie")){ //check hunger condition
            System.out.println("You must take and eat a cookie before taking anything else.");
            return;
        }
        if(currentRoom.getItems().indexOf(itemName) == -1){ //the item must be in the room
            System.out.println("That item is not in the room.");
            return;
        }
        
        myItem = currentRoom.takeItem(itemName);
        System.out.println("You picked up " + itemName);
        itemsHeld += 1;
    }
    
    /** 
     * Drops the item that the player is holding.
     * The item lands inside the current room.
     * Checks the rest of the command to see if it specifies what to drop.
     * 
     * @param command The command to be processed
     */
    private void drop(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Drop what?");
            return;
        }
        else {
            if(myItem == null){
                System.out.println("You have nothing to drop.");
                return;
            }
            currentRoom.addItem(myItem); //dropped item lands in the current room
            
            System.out.println("You have dropped " + myItem.getName() + ".");
            myItem = null;
        }
    }
    
    /** 
     * Charges the beamer.
     * The player must be holding an uncharged beamer.
     * 
     * @param command The command to be processed
     */
    private void charge(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("Charge what?");
            return;
        }
        if(myItem == null){ //not holding a beamer
            System.out.println("You have no beamer to charge.");
            return;
        }
        if(!myItem.getName().equals("beamer")){ //not holding a beamer
            System.out.println("You have no beamer to charge.");
            return;
        }
        
        Beamer beam = (Beamer)myItem; //converting from Item to Beamer in order to interact with the Beamer-specific methods
        
        if(beam.isCharged()){
            System.out.println("Your beamer is already charged. Fire the beamer in order to charge it again.");
            return;
        }
        
        beam.charge(currentRoom);
        System.out.println("beamer successfully charged.");
    }
    
    /** 
     * Fires the beamer.
     * The player must be holding a charged beamer.
     * 
     * @param command The command to be processed
     */
    private void fire(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("Fire what?");
            return;
        }
         if(myItem == null){ //player must be holding a beamer
            System.out.println("You are not holding a beamer.");
            return;
        }
        if(!myItem.getName().equals("beamer")){ //player must be holding a beamer
            System.out.println("You are not holding a beamer.");
            return;
        }
        
        Beamer beam = (Beamer)myItem; //converting from Item to Beamer in order to interact with the Beamer-specific methods
        
        if(!beam.isCharged()){ //player must be holding a charged beamer
            System.out.println("You must charge the beamer before firing it.");
            return;
        }
        
        Room nextRoom = beam.fire();
        
        roomHistory.push(currentRoom); //adds room to movement history
            
        //moves to the next room
        previousRoom = currentRoom; 
        currentRoom = nextRoom;
        
        System.out.println("beamer successfully fired.");
        System.out.print(currentRoom.getLongDescription());
        displayMyItem();
        displayMoney();
    }
    
    /** 
     * Displays the player's money.
     */
    private void displayMoney()
    {
        if(money < 0){
            System.out.printf("The balance on your campus card is -$%.2f.%n", Math.abs(money));
        }
        else{
            System.out.printf("The balance on your campus card is $%.2f.%n", money);
        }
    }
    
    /** 
     * Sells the player's item.
     * The player must be carrying an item and inside the store. 
     * 
     * @param command The command to be processed
     */
    private void sell(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("Sell what?");
            return;
        }
        if(!currentRoom.getShortDescription().equals("in the university bookstore")){ //must be in the store
            System.out.println("You must be in the store to sell an item.");
            return;
        }
        if(myItem == null){ //must have an item to sell
            System.out.println("You are not carrying any items to sell.");
            return;
        }
        
        money += myItem.getPrice();
        System.out.printf("Your %s was sold for $%.2f%n", myItem.getName(), myItem.getPrice());
        
        storeItems.add(myItem); //add the item to the store
        myItem = null;
        
        displayMoney();
    }
    
    /** 
     * Plays the routine for buying an item.
     * The player must not be carrying an item and inside the store. 
     * 
     * @param command The command to be processed
     */
    private void buy(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("Buy what?");
            return;
        }
        if(!currentRoom.getShortDescription().equals("in the university bookstore")){ //must be in the store
            System.out.println("You must be in the store to buy an item.");
            return;
        }
        if(myItem != null){ //must have space for a new item
            System.out.println("You have no space to carry a new item. Drop your current item before making a purchase.");
            return;
        }
        if(money < 0){ //must not be in debt (0 is ok)
            System.out.println("You are in debt. The university does not trust you enough to let you make a purchase.");
            return;
        }
        if(storeItems.size() == 0){ //store is empty
            System.out.println("There are no items for sale.");
            return;
        }
        
        inflation(); //this is how the store makes money :)
        
        ArrayList<String> itemNames = getValidItemNames();
        
        //gets the name of the item the user wishes to buy
        String itemToBuy;
        while (true) {
            System.out.println("Which item would you like to buy? If you would not like to buy an item, say 'cancel'.");
            displayStoreItems();
            
            itemToBuy = parser.getItemName();
            if(itemToBuy.equals("cancel")){
                System.out.println("Purchase successfully cancelled.");
                return;
            }
            if(itemNames.contains(itemToBuy)){
                break;
            }
            System.out.println("Sorry, that item was not recongnised. Please try again.");
        }
        
        //buy the item with the associated name
        for(Item item: storeItems){
            if(item.getName().equals(itemToBuy)){
                money -= item.getPrice(); //pay
                myItem = item;
                break;
            }
        }
        storeItems.remove(myItem); //remove item from store
        
        System.out.println("Thank you for your purchase.");
        displayMoney();
    }
    
    /** 
     * Display the items in the store.
     */
    private void displayStoreItems()
    {
        System.out.println("Items for sale:");
        for(Item item: storeItems) {
            System.out.printf("\ta %s that costs $%.2f%n", item.getName(), item.getPrice());
        }
    }
    
    /** 
     * Increases the price of all the items in the store.
     * The inflation is set at 3%.
     */
    private void inflation()
    {
        for(Item item: storeItems) {
            double newPrice = Math.ceil(item.getPrice()*1.03);
            item.setPrice(newPrice);
        }
    }
    
    /** 
     * Return a list of item names that can be purchased through the store.
     * 
     * @return itemNames The list of item names
     */
    private ArrayList<String> getValidItemNames()
    {
        ArrayList<String> itemNames = new ArrayList<String>();
        for(Item item: storeItems) {
            itemNames.add(item.getName());
        }
        return itemNames;
    }
    
    /** 
     * Plays the routine for the encounter.
     */
    private void encounter(){
        int cipherKey = rand.nextInt(10) + 1; //generates a random number for the ceasar cipher
        String[] possibleMessages = {"hello world", "adventure game", "software engineering", "ceasar cipher", "carleton university", "happy birthday"};
        int randInt = rand.nextInt(6); //chooses a possible message
        
        String codedMessage = generateCodedMessage(possibleMessages[randInt], cipherKey);
        
        System.out.println("Encounter!\nA wizard named Ceasar: \"Decode my message or suffer the consequences!");
        System.out.println("The message is '" + codedMessage + "'and the magic number is " + cipherKey + ".\"");
        
        //checks user input against the correct answer
        String answer = parser.getAnswer();
        if(answer.equals(possibleMessages[randInt])){
            System.out.println("A wizard named Ceasar: \"That is correct! Have $50 for your hard work.\"");
            money += 50;
        }else{
            System.out.println("A wizard named Ceasar: \"Wrong! The correct answer was " + possibleMessages[randInt] + ".\""); 
            System.out.println("A wizard named Ceasar: \"As punishment, I will take your item and $20.\""); 
            myItem = null;
            money -= 20;
        }
    }
    
    /** 
     * Generates the coded message using the ceasar cipher.
     * 
     * @param message The message to be coded
     * @param key The key for the ceasar cipher
     * @return codedMessage The coded message
     */
    private String generateCodedMessage(String message, int key){
        String codedMessage = "";
        for(int i = 0; i < message.length(); i++){
            if(message.charAt(i) == ' '){
                codedMessage += " ";
            }else{
                int temp = (int)message.charAt(i);
                temp += key;
                if(temp > 122){
                    temp -= 26;
                }
                codedMessage += (char)temp;
            }
        }
        return codedMessage;
    }

}
