package org.uob.a2.commands;

import org.uob.a2.gameobjects.*;

/**
 * Represents the move command, allowing the player to navigate to a different room in the game world.
 * 
 * <p>
 * The move command checks if the specified direction corresponds to an available exit in the current room.
 * If a matching exit is found, the player's location is updated to the connected room.
 * </p>
 */
public class Move extends Command {
    public Move (String direction) {
        this.commandType = CommandType.MOVE;
        this.value = direction;
    }

    @Override
    public String execute(GameState gameState) {
        Room currentRoom = gameState.getMap().getCurrentRoom();
        if (currentRoom != null && currentRoom.canMove(value)) {
            Exit exit = currentRoom.getExitByName(value);
            assert exit != null;
            String nextRoomId = exit.getNextRoom();
            Map map = gameState.getMap();
            Room nextRoom = map.getRoom(nextRoomId);
            map.setCurrentRoom(nextRoom.getId());
            Score.visitRoom();
            return "Moving towards " + this.value + "\n";
        }
        return "No exit found in that direction.";
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
