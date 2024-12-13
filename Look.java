package org.uob.a2.commands;

import org.uob.a2.Game;
import org.uob.a2.gameobjects.*;

import java.util.ArrayList;
import java.util.StringJoiner;

/**
 * Represents the look command, allowing the player to examine various elements of the game world.
 * 
 * <p>
 * The look command can provide details about the current room, its exits, features, or specific items and equipment.
 * Hidden objects are not included in the output unless explicitly revealed.
 * </p>
 */

public class Look extends Command {
    public Look(String target) {
        this.commandType = CommandType.LOOK;
        this.value = target;
    }

    @Override
    public String execute(GameState gameState) {
        StringBuilder builder = new StringBuilder();
        Room currentRoom = gameState.getMap().getCurrentRoom();

        switch (value) {
            case "room":
                String roomDescription = currentRoom.getDescription();
                ArrayList<Item> roomItems = currentRoom.getItems();
                ArrayList<Equipment> roomEquipments = currentRoom.getEquipments();
                ArrayList<Feature> roomFeatures = currentRoom.getFeatures();
                builder.append(roomDescription);
                if (roomFeatures.isEmpty() && roomItems.isEmpty() && roomEquipments.isEmpty()) {
                    break;
                }
                builder.append("\nYou see:");
                for (Feature feature : roomFeatures) {
                    builder.append("\n\t").append(feature.getName()).append(": ").append(feature.getDescription());
                }
                for (Item item : roomItems) {
                    builder.append("\n\t").append(item.getName()).append(": ").append(item.getDescription());
                }
                for (Equipment equipment : roomEquipments) {
                    builder.append("\n\t").append(equipment.getName()).append(": ").append(equipment.getDescription());
                }
                break;

            case "exits":
                ArrayList<Exit> exits = currentRoom.getExits();

                builder.append("The available exits are:");
                for (Exit exit : exits) {
                    builder.append("\n\t").append(exit.getName() + " - " + exit.getDescription());
                }
                break;
            case "north":
                Exit northExit = currentRoom.getExits().stream().filter(e->e.getName().equals("north")).findFirst().orElse(null);
                if (northExit != null) {
                    builder.append(northExit.getDescription());
                } else {
                    builder.append("There is no exit to the north.");
                }
                break;
            case "south":
                Exit southExit = currentRoom.getExits().stream().filter(e->e.getName().equals("south")).findFirst().orElse(null);
                if (southExit != null) {
                    builder.append(southExit.getDescription());
                } else {
                    builder.append("There is no exit to the south.");
                }
                break;
            case "east":
                Exit eastExit = currentRoom.getExits().stream().filter(e->e.getName().equals("east")).findFirst().orElse(null);
                if (eastExit != null) {
                    builder.append(eastExit.getDescription());
                } else {
                    builder.append("There is no exit to the east.");
                }
                break;
            case "west":
                Exit westExit = currentRoom.getExits().stream().filter(e->e.getName().equals("north")).findFirst().orElse(null);
                if (westExit != null) {
                    builder.append(westExit.getDescription());
                } else {
                    builder.append("There is no exit to the west.");
                }
                break;
            case "features":
                ArrayList<Feature> features = currentRoom.getFeatures();
                if (features.isEmpty()) {
                    builder.append("There are no features in this room.");
                    break;
                }
                builder.append("You also see:");
                for (Feature feature : features) {
                    builder.append("\t").append(feature.getDescription());
                }
                break;
            default:
                Player player = gameState.getPlayer();
                if (currentRoom.hasItem(value)) {
                    Item item = currentRoom.getItemByName(value);
                    builder.append(item.getDescription());
                } else if (currentRoom.hasEquipment(value)) {
                    Equipment equipment = currentRoom.getEquipmentByName(value);
                    builder.append(equipment.getDescription());
                } else if (currentRoom.hasFeature(value)) {
                    Feature feature = currentRoom.getFeatureByName(value);
                    builder.append(feature.getDescription());
                } else if (currentRoom.hasExit(value)) {
                    Exit exit = currentRoom.getExitByName(value);
                    builder.append(exit.getDescription());
                } else if (player.hasItem(value)) {
                    Item item = player.getItem(value);
                    if (!item.getHidden()) {
                        builder.append(item.getDescription());
                    }
                } else if (player.hasEquipment(value)) {
                    Equipment equipment = player.getEquipment(value);
                    if (!equipment.getHidden()) {
                        builder.append(equipment.getDescription());
                    }
                }
                break;
        }
        return builder.toString();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
