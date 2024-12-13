package org.uob.a2.parser;

import org.uob.a2.commands.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.NoSuchElementException;

/**
 * The {@code Parser} class processes a list of tokens and converts them into {@code Command} objects
 * that can be executed by the game.
 * 
 * <p>
 * The parser identifies the type of command from the tokens and creates the appropriate command object.
 * If the command is invalid or incomplete, a {@code CommandErrorException} is thrown.
 * </p>
 */
public class Parser {
    public Parser() {}

    public Command parse(ArrayList<Token> tokens) throws CommandErrorException {
        Enumeration<Token> tokenEnumerator = Collections.enumeration(tokens);

        Token token = tokenEnumerator.nextElement();

        Command command = null;
        switch (token.getTokenType()) {
            case DROP:
                try{
                    Token nextToken = tokenEnumerator.nextElement();
                    if (nextToken.getTokenType() != TokenType.VAR) {
                        throw new CommandErrorException("Invalid token type. Expected VAR got " + nextToken.getTokenType());
                    }
                    command = new Drop(nextToken.getValue());
                } catch (NoSuchElementException e) {
                    throw new CommandErrorException("Incorrect number of tokens: " + tokens.size());
                }
                break;
            case GET:
                try {
                    Token nextToken = tokenEnumerator.nextElement();
                    if (nextToken.getTokenType() != TokenType.VAR) {
                        throw new CommandErrorException("Invalid token type. Expected VAR got " + nextToken.getTokenType());
                    }
                    command = new Get(nextToken.getValue());
                } catch (NoSuchElementException e) {
                    throw new CommandErrorException("Incorrect number of tokens: " + tokens.size());
                }
                break;
            case HELP:
                try {
                    Token nextToken = tokenEnumerator.nextElement();
                    if (nextToken.getTokenType() != TokenType.EOL) {
                        command = new Help(nextToken.getTokenType().toString());
                    } else {
                        command = new Help(null);
                    }

                } catch (NoSuchElementException e) {
                    throw new CommandErrorException("Incorrect number of tokens: " + tokens.size());
                }
                break;
            case LOOK:
                try{
                    Token nextToken = tokenEnumerator.nextElement();
                    if (nextToken.getTokenType() == TokenType.VAR) {
                        command = new Look(nextToken.getValue());
                    } else {
                        throw new CommandErrorException("Invalid token type. Expected VAR got " + nextToken.getTokenType());
                    }
                } catch (NoSuchElementException e) {
                    throw new CommandErrorException("Incorrect number of tokens: " + tokens.size());
                }
                break;
            case MOVE:
                try {
                    Token nextToken = tokenEnumerator.nextElement();
                    if (nextToken.getTokenType() != TokenType.VAR) {
                        throw new CommandErrorException("Invalid token type. Expected VAR got " + nextToken.getTokenType());
                    }
                    command = new Move(nextToken.getValue());
                } catch (NoSuchElementException e) {
                    throw new CommandErrorException("Incorrect number of tokens: " + tokens.size());
                }
                break;
            case USE:
                try{
                    Token var1 = tokenEnumerator.nextElement();
                    Token prep = tokenEnumerator.nextElement();
                    Token var2 = tokenEnumerator.nextElement();
                    if (var1.getTokenType() != TokenType.VAR) {
                        throw new CommandErrorException("Invalid token type. Expected VAR got " + var1.getTokenType());
                    }
                    if (prep.getTokenType() != TokenType.PREPOSITION) {
                        throw new CommandErrorException("Invalid token type. Expected PREPOSITION got " + prep.getTokenType());
                    }
                    if (var2.getTokenType() != TokenType.VAR) {
                        throw new CommandErrorException("Invalid token type. Expected VAR got " + var2.getTokenType());
                    }
                    command = new Use(var1.getValue(), prep.getValue(),var2.getValue());

                } catch(NoSuchElementException e) {
                    throw new CommandErrorException("Incorrect number of tokens: " + tokens.size());
                }
                break;
            case STATUS:
                try {
                    Token nextToken = tokenEnumerator.nextElement();
                    if (nextToken.getTokenType() != TokenType.VAR) {
                        throw new CommandErrorException("Invalid token type. Expected VAR got " + nextToken.getTokenType());
                    }
                    command = new Status(nextToken.getValue());
                } catch (NoSuchElementException e) {
                    throw new CommandErrorException("Incorrect number of tokens: " + tokens.size());
                }
                break;
            case COMBINE:
                try{
                    Token item1 = tokenEnumerator.nextElement();
                    Token conn = tokenEnumerator.nextElement();
                    Token item2 = tokenEnumerator.nextElement();
                    if (item1.getTokenType() != TokenType.VAR) {
                        throw new CommandErrorException("Invalid token type. Expected VAR got " + item1.getTokenType());
                    }
                    if (conn.getTokenType() != TokenType.PREPOSITION) {
                        throw new CommandErrorException("Invalid token type. Expected PREPOSITION got " + conn.getTokenType());
                    }
                    if (item2.getTokenType() != TokenType.VAR) {
                        throw new CommandErrorException("Invalid token type. Expected VAR got " + item2.getTokenType());
                    }
                    command = new Combine(item1.getValue(), item2.getValue());

                } catch(NoSuchElementException e) {
                    throw new CommandErrorException("Incorrect number of tokens: " + tokens.size());
                }
                break;
            case QUIT:
                command = new Quit();
                break;
        }

        if (command == null) {
            throw new CommandErrorException("Command not found");
        }
        return command;
    }
}
