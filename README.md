# World of Zuul

## Overview  
World of Zuul is a simple text based adventure game. Players can walk around the university, teleport, eat, pick up items, sell items, and buy items.

## Languages  
This project uses Java.

## Description 
- Game.java: The main class that creates and initialises all the others
- Parser.java: Interprets user inputs
- CommandWords.java: Contains a list of the valid commands
- Command.java: A Command holds information about the command issued by the player
- Room.java: A Room represents a room in the university
- TransporterRoom.java: A TransporterRoom is a teleportation room that inherets from Room
- Item.java: An Item represents an item
- Beamer.java: A Beamer is a teleportation device that inherits from Item

## Usage
To play, create a Game object and call Game's play() method. Type commands in the terminal to interact with World of Zuul. The valid commands are 'help', 'quit', 'look', 'go', 'eat', 'take', 'drop', 'back', 'stackBack', 'charge', 'fire', 'sell', and 'buy'.

### 'help'
Typing 'help' into the terminal brings up a list of the possible command words.

### 'quit'
The 'quit' command terminates the game.

### 'look'
This command gives you a snapshot of your current room. It gives you a description of the room, the directions of the exits, all the items in the room, any items that you are carrying, and how much money you have.

### 'go'
'go' is a two word command that allows you to go in the direction of the second word of the command. If the second word of the command matches one of the available exits, then you go to the neighboring room. Upon entering a new room, you are shown the same information provided by look. When you enter a new room using the go command, you have a 25% change of getting an encounter.

### Encounters
An encounter is when you are forced to decode wizard's message using hints. If you correctly decode the message, the wizard gives you a prize. If you incorrectly decode the message, the wizard steals your item and some of your money.

### 'eat'
The 'eat' command allows you to eat any cookies that you are currently holding. Once you have eaten a cookie, you may 'take' up to five items. You may only hold one item at a time.

### 'take'
'take' is a two word command that allows you to pick up an item in the room. The item to pick up is specified by the second word of the command. You may not take items unless you have eaten a cookie, and you may only hold one item at a time. 

### 'drop'
The command 'drop' allows you to drop the item you are currently holding. The item will land in the current room.

### 'back'
Typing 'back' into the terminal brings you into the room you were in previously. Upon entering the room, you are shown the information provided by 'look'.

### 'stackBack'
The 'stackBack' command brings you back to the previous room, but does not add your most recent movement to the room history. This way, you can use consecutive calls to 'stackBack' to retrace your steps all the way to the start of the game. Upon entering the room, you are shown the information provided by 'look'.

### Beamer
A Beamer is an item that can be charged and fired. When you charge a beamer, it remembers the current room. When the beamer is fired, you are teleported to the room in which the beamer was charged. Once charged, the beamer must be fired before it can be recharged. 

### 'charge'
If you are holding a Beamer, then 'charge' will charge the beamer. 

### 'fire'
If you are holding a Beamer, then 'fire' will fire the beamer.

### 'sell'
The 'sell' command exchanges the item you are holding for money. You must be in the store to sell an item. Items that are sold will appear in the store.

### 'buy'
Te 'buy' command brings up a list of items in the store, and allows you to make a purchase as long as you are not in debt. You must be in the store to buy an item. Due to inflation, the items in the store grow more costly each time you use the 'buy' command.

### Transporter Room
A Transporter Room is a type of room that brings you to a random room when you use the 'go' command.

## Credits
- Michael Kolling and David J. Barnes came up with the idea for the World of Zuul. (March 30, 2006)  
- Lynn Marshall implemented the basic game in which you could move from room to room. (October 21, 2012)  
- Fiona Cheng added additional functionality including 'look', 'eat', 'take', 'drop', 'back', 'stackBack',  'charge', 'fire', 'sell', 'buy', the Teleportation Room, and the Encounters. (February 22, 2023)
