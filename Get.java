package org.uob.a2.commands;

import org.uob.a2.gameobjects.*;

/**
 * Represents the get command, allowing the player to pick up an item from the current room and add it to their inventory.
 * 
 * <p>
 * This command checks if the specified item is present in the current room. If the item exists and the player
 * does not already have it, the item is added to the player's inventory and removed from the room. Otherwise,
 * an appropriate message is returned.
 * </p>
 */
public class Get extends Command {
    public Get(String item) {
        this.commandType = CommandType.GET;
        this.value = item;
    }

    @Override
    public String execute(GameState gameState) {
        Player player = gameState.getPlayer();
        Room room = gameState.getMap().getCurrentRoom();
        if (player.hasItem(this.value) || player.hasEquipment(this.value)) {
            return "You already have " + this.value;
        } else if (room.hasItem(this.value)) {
            Item item = room.getItemByName(this.value);
            player.addItem(item);
            room.removeItem(this.value);
            return "You pick up: " + this.value;
        } else if (room.hasEquipment(this.value)) {
            Equipment equipment = room.getEquipmentByName(this.value);
            player.addEquipment(equipment);
            room.removeEquipment(this.value);
            return "You pick up: " + this.value;
        } else {
            return "No " + this.value + " to get.";
        }
    }
}
