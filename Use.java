package org.uob.a2.commands;

import org.uob.a2.gameobjects.*;

/**
 * Represents the use command, allowing the player to use equipment on a specific target in the game.
 * 
 * <p>
 * The use command checks if the player has the specified equipment and whether it can interact with
 * the target. The target can be a feature, item, or the current room, depending on the game context.
 * </p>
 */
public class Use extends Command {
    String equipmentName;
    String target;

    public Use(String equipmentName, String target) {
        commandType = CommandType.USE;
        this.value = equipmentName + " " + target;
        this.equipmentName = equipmentName;
        this.target = target;
    }

    public Use(String equipmentName, String preposition, String target) {
           commandType = CommandType.USE;
           this.value = equipmentName + " " + preposition + " " + target;
           this.equipmentName = equipmentName;
           this.target = target;
    }

    @Override
    public String execute(GameState gameState) {
        Room currentRoom = gameState.getMap().getCurrentRoom();
        Player player = gameState.getPlayer();
        if (!player.hasEquipment(equipmentName)) {
            return "You do not have " + equipmentName;
        }
        Equipment equipment = player.getEquipment(equipmentName);

        GameObject targetObject = null;
        if (currentRoom.hasEquipment(target)) {
            targetObject = currentRoom.getEquipmentByName(target);
        } else if (currentRoom.hasItem(target)) {
            targetObject = currentRoom.getItemByName(target);
        } else if (currentRoom.hasFeature(target)) {
            targetObject = currentRoom.getFeatureByName(target);
        } else if (currentRoom.getName().equals(target)) {
            targetObject = currentRoom;
        }

        if (targetObject == null) {
            return "Invalid use target";
        }

        return equipment.use(targetObject, gameState);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}