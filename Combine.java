package org.uob.a2.commands;

import org.uob.a2.gameobjects.*;

public class Combine extends Command {
    String equipmentId;
    String targetId;

    public Combine(String equipmentId, String targetId) {
        commandType = CommandType.COMBINE;
        this.value = equipmentId + " " + targetId;
        this.equipmentId = equipmentId;
        this.targetId = targetId;
    }

    @Override
    public String execute(GameState gameState) {
        Player player = gameState.getPlayer();
        if (!player.hasEquipmentById(equipmentId)) {
            return "You do not have " + equipmentId;
        }
        if (!player.hasItemById(targetId)) {
            return "You do not have " + targetId;
        }

        Equipment equipment = player.getEquipmentById(equipmentId);
        Item targetItem = player.getItemById(targetId);

        return equipment.combine(targetItem.getId(), gameState.getPlayer());
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
