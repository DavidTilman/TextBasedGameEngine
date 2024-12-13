package org.uob.a2.commands;

import org.uob.a2.gameobjects.*;

/**
 * Represents the drop command, allowing the player to drop an item from their inventory into the current room.
 * 
 * <p>
 * This command checks if the player possesses the specified item and, if so, removes it from their inventory
 * and adds it to the current room. If the player does not have the item, an error message is returned.
 * </p>
 */
public class Drop extends Command {
    public Drop(String item) {
        this.commandType = CommandType.DROP;
        this.value = item;
    }

    @Override
    public String execute(GameState gameState) {
        Player player = gameState.getPlayer();
        Room currentRoom = gameState.getMap().getCurrentRoom();

        if (player.hasItem(value)) {
            Item item = player.getItem(value);
            player.removeItem(value);
            currentRoom.addItem(item);
            return "You drop: " + value;
        }
        else if (player.hasEquipment(value)) {
            Equipment equipment = player.getEquipment(value);
            currentRoom.addEquipment(equipment);
            player.removeEquipment(value);
            return "You drop: " + value;
        } else {
            return "You cannot drop " + value;
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
