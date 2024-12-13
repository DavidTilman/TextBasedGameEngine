package org.uob.a2.commands;

import org.uob.a2.gameobjects.*;

import java.util.ArrayList;
import java.util.StringJoiner;

/**
 * Represents the status command, allowing the player to retrieve information
 * about their inventory, specific items, or their overall status.
 * 
 * <p>
 * The status command can display a list of items in the player's inventory, 
 * provide details about a specific item, or show the player's general status.
 * </p>
 */

public class Status extends Command {
    public Status(String topic) {
        commandType = CommandType.STATUS;
        value = topic;
    }

    @Override
    public String execute(GameState gameState) {
        Player player = gameState.getPlayer();
        switch (value) {
            case "inventory":
                StringJoiner inventoryJoiner = new StringJoiner("\t");
                ArrayList<Item> inventory = player.getInventory();
                ArrayList<Equipment> equipment = player.getEquipment();
                inventory.stream().map(Item::getName).forEach(inventoryJoiner::add);
                equipment.stream().map(Equipment::getName).forEach(inventoryJoiner::add);

                return "Inventory:\n\t" + inventoryJoiner;
            case "player":
                StringJoiner playerInventoryJoiner = new StringJoiner("\t");
                ArrayList<Item> playerInventory = player.getInventory();
                ArrayList<Equipment> playerEquipment = player.getEquipment();
                playerInventory.stream().map(Item::getName).forEach(playerInventoryJoiner::add);
                playerEquipment.stream().map(Equipment::getName).forEach(playerInventoryJoiner::add);

                return "Player: " + player.getName() + "\nInventory:\t"
                        + playerInventoryJoiner + "\n" + Score.getScore();
            case "map":
                return gameState.getMap().display();
            case "game":
                StringJoiner gameJoiner = new StringJoiner("\n");
                gameJoiner.add("Game state:");
                gameJoiner.add("Player: " + gameState.getPlayer().getName());
                StringJoiner gameInventoryJoiner = new StringJoiner("\t");
                player.getInventory().stream().map(Item::getName).forEach(gameInventoryJoiner::add);
                gameJoiner.add("Inventory:\t" + gameInventoryJoiner);
                StringJoiner gameEquipmentJoiner = new StringJoiner("\t");
                player.getEquipment().stream().map(Equipment::getName).forEach(gameEquipmentJoiner::add);
                gameJoiner.add("Equipment:\t" + gameEquipmentJoiner);
                gameJoiner.add("Map:");
                gameJoiner.add(gameState.getMap().display());
                return gameJoiner.toString();
            case "score":
                return Score.getScore();
            default:
                if (player.hasItem(value)) {
                    Item item = player.getItem(value);
                    assert item != null;
                    return "Item: " + item.getName() + "\n\t" + item.getDescription();
                } else if (player.hasEquipment(value)) {
                    Equipment statusEquipment = player.getEquipment(value);
                    assert statusEquipment != null;
                    return "Equipment: " + statusEquipment.getName() + "\n\t" + statusEquipment.getDescription();
                } else if (value.equals("map")) {
                    gameState.getMap().display();
                } else {
                    return "";
                }
        }
        return "";
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
