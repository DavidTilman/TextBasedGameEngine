package org.uob.a2.commands;

import org.uob.a2.gameobjects.*;

import java.util.HashMap;
import java.util.StringJoiner;

/**
 * Represents the help command, providing the player with instructions or information
 * about various topics related to the game.
 * 
 * <p>
 * The help command displays information on how to play the game, including details about 
 * available commands, their syntax, and their purpose.
 * </p>
 */

public class Help extends Command {

    private final HashMap<String, String> commandHelp = new HashMap<String, String>();

    public Help(String topic) {
        this.commandType = CommandType.HELP;
        this.value = topic;

        commandHelp.put("MOVE Command:\n", "\tmove <direction>\n\tUse the 'move' command.\n\tMove to a different location as defined by an exit's name.");
        commandHelp.put("LOOK Command:\n", "\tlook <room | direction | features | item name | equipment name | feature name>\n\tUse the 'look' command.\n\tLook at a specific object");
        commandHelp.put("GET Command:\n", "\tget <item name | equipment name>\n\tUse the 'get' command.\n\tCollect an item or some equipment from your current room.");
        commandHelp.put("DROP Command:\n", "\tdrop <item name | equipment name>\n\tUse the 'drop' command.\n\tDrop an item or some equipment to your current room.");
        commandHelp.put("USE Command:\n", "\tuse <equipment name> on|with <feature|item>\n\tUse the 'use' command.\n\tUse an item in your inventory on its own on a feature or item (e.g.’use key on chest’).");
        commandHelp.put("COMBINE Command:\n", "\tcombine <equipment> and <item>\n\tUse the 'combine' command.\n\tCombine equipment and an item.");
        commandHelp.put("STATUS Command:\n", "\tstatus <inventory | player | item name | equipment name | map | score | game>\n\tUse the 'status' command.\n\tCheck information about your player or score, an item or equipment,\n\tthe map, or the game as a whole");
        commandHelp.put("HELP Command:\n", "\thelp <topic (optional)>\n\tUse the 'help' command.\n\tDisplay this message, or help about a specific topic.");
        commandHelp.put("QUIT Command:\n", "\tquit\n\tUse the 'quit' command.\n\tQuits the game.");
    }

    @Override
    public String execute(GameState gameState) {
        if (this.value == null) {
            StringJoiner joiner = new StringJoiner("\n");
            commandHelp.keySet().stream().map(key-> "- " + key + commandHelp.get(key)).forEach(joiner::add);
            return "Welcome to the game!\n" + joiner;
        }

        StringJoiner joiner = new StringJoiner("\n");
        commandHelp.keySet().stream().filter(key->key.toLowerCase().startsWith(value.toLowerCase())).map(key-> "- " + key + commandHelp.get(key)).forEach(joiner::add);

        if (joiner.length() > 0) {
            return joiner.toString();
        } else {
            return "No help available for the topic: " + value;
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
