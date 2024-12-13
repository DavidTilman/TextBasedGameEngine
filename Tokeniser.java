package org.uob.a2.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;

/**
 * The {@code Tokeniser} class is responsible for converting a string input into a list of tokens
 * that can be parsed into commands by the game.
 * 
 * <p>
 * The tokeniser identifies keywords, variables, and special symbols, assigning each a {@code TokenType}.
 * </p>
 */
public class Tokeniser {
    private ArrayList<Token> tokens;
    public Tokeniser() {}

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public String sanitise(String input) {
        return input.toLowerCase().trim();
    }

    public void tokenise(String input) {
        tokens = new ArrayList<>();
        Dictionary<String, TokenType> commands = new Hashtable<>();
        commands.put("use", TokenType.USE);
        commands.put("get", TokenType.GET);
        commands.put("drop", TokenType.DROP);
        commands.put("look", TokenType.LOOK);
        commands.put("move", TokenType.MOVE);
        commands.put("status", TokenType.STATUS);
        commands.put("help", TokenType.HELP);
        commands.put("combine", TokenType.COMBINE);
        commands.put("quit", TokenType.QUIT);

        ArrayList<String> prepositions = new ArrayList<>();
        prepositions.add("on");
        prepositions.add("with");
        prepositions.add("using");
        prepositions.add("and");
        Arrays.stream(input.split(" ")).map(String::trim).filter(s -> !s.isEmpty()).forEach(
                (inputToken) -> {
                    if (commands.get(inputToken) != null) {
                        tokens.add(new Token(commands.get(inputToken)));
                    } else if (prepositions.contains(inputToken)) {
                        tokens.add(new Token(TokenType.PREPOSITION, inputToken));
                    } else {
                        tokens.add(new Token(TokenType.VAR, inputToken));
                    }
                }
        );
        tokens.add(new Token(TokenType.EOL));
    }
}
