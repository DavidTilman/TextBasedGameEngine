package org.uob.a2.commands;

import org.uob.a2.Game;
import org.uob.a2.gameobjects.*;

import java.util.ArrayList;
import java.util.StringJoiner;

/**
 * Represents the quit command, allowing the player to exit the game.
 * 
 * <p>
 * The quit command signals the end of the game session and provides a summary of the player's
 * current status before termination.
 * </p>
 */
public class Quit extends Command {
    public Quit() {
        commandType = CommandType.QUIT;
        value = null;
    }

    public String execute(GameState gameState) {
        ArrayList<Item> inventory = gameState.getPlayer().getInventory();
        return "Game over:\n\t" + gameState.getPlayer() + "\n" + Score.getScore();
    }
}
