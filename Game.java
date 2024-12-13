package org.uob.a2;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import org.uob.a2.commands.*;
import org.uob.a2.gameobjects.*;
import org.uob.a2.parser.*;
import org.uob.a2.utils.*;

/**
 * Main class for the game application. Handles game setup, input parsing, and game execution.
 * 
 * <p>
 * This class initializes the game state, reads user input, processes commands, and maintains the game loop.
 * </p>
 */

public class Game {
    static Scanner scanner = new Scanner(System.in);
    static Tokeniser tokeniser = new Tokeniser();
    static Parser parser = new Parser();

    public static void main(String[] args) {

        System.out.print("Loading game...");
        GameState gamestate = GameStateFileParser.parse("shipGame.txt");

        if (gamestate == null || gamestate.getPlayer() == null || gamestate.getMap() == null) {
            System.out.println("Game state invalid.");
            return;
        }

        System.out.println("Done");

        Command command;

        while (true) {
            System.out.println("Current location: " + gamestate.getMap().getCurrentRoom().getName());
            System.out.print("> ");
            String rawInput = scanner.nextLine();
            String sanitisedInput = tokeniser.sanitise(rawInput);
            tokeniser.tokenise(sanitisedInput);
            ArrayList<Token> tokens = tokeniser.getTokens();
            try {
                command = parser.parse(tokens);
            } catch (CommandErrorException e) {
                System.out.println("Invalid command: " + e.getMessage());
                continue;
            }
            System.out.println(command.execute(gamestate));
            if (command.commandType == CommandType.QUIT) {
                break;
            }
        }
    }
}
