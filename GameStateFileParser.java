package org.uob.a2.utils;

import org.uob.a2.gameobjects.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * Utility class for parsing a game state from a file.
 * 
 * <p>
 * This class reads a structured text file to create a {@code GameState} object,
 * including the player, map, rooms, items, equipment, features, and exits.
 * </p>
 */
public class GameStateFileParser {
    public static GameState parse(String filename) {
        FileReader fileReader;
        try {
            fileReader = new FileReader(filename);
        } catch (FileNotFoundException e) {
            return null;
        }

        BufferedReader reader = new BufferedReader(fileReader);

        Player player = null;
        Map map = null;
        String startRoomId = null;

        Room editing = null;
        try {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] elements = Arrays.stream(line.split("[:,]")).map(String::trim).toArray(String[]::new);
                String object = elements[0];
                switch (object) {
                    case "player":
                        if (elements.length != 2) {
                            return new GameState();
                        }
                        String playerName = elements[1];
                        player = new Player(playerName);
                        break;
                    case "map":
                        map = new Map();
                        break;
                    case "room":
                        if (editing != null && map != null) {
                            map.addRoom(editing);
                        }

                        Room room = getRoom(elements);
                        if (room == null) {
                            return new GameState();
                        }
                        editing = room;
                        break;
                    case "start_room":
                        if (editing != null && map != null) {
                            map.addRoom(editing);
                        }

                        Room start_room = getRoom(elements);
                        if (start_room == null) {
                            return new GameState();
                        }
                        editing = start_room;
                        startRoomId = start_room.getId();
                        break;
                    case "equipment":
                        if (editing == null) {
                            return new GameState();
                        }
                        Equipment equipment = getEquipment(elements);
                        if (equipment == null) {
                            return new GameState();
                        }
                        editing.addEquipment(equipment);
                        break;
                    case "item":
                        if (editing == null) {
                            return new GameState();
                        }
                        Item item = getItem(elements);
                        if (item == null) {
                            return new GameState();
                        }
                        editing.addItem(item);
                        break;
                    case "exit":
                        if (editing == null) {
                            return new GameState();
                        }
                        Exit exit = getExit(elements);
                        if (exit == null) {
                            return new GameState();
                        }
                        editing.addExit(exit);
                        break;
                    case "container":
                        if (editing == null) {
                            return new GameState();
                        }
                        Container container = getContainer(elements);
                        if (container == null) {
                            return new GameState();
                        }
                        editing.addFeature(container);
                    case "combine":
                        CombineInfo info = getCombineInfo(elements);
                        if (info == null) {
                            return new GameState();
                        }
                        Equipment.addCombineInfo(info);
                }
            }
            if (map == null) {
                return new GameState();
            }
            map.addRoom(editing);
            if (startRoomId != null) {
                map.setCurrentRoom(startRoomId);
            }
        } catch (IOException e) {
            return new GameState();
        }

        return new GameState(map, player);
    }

    private static Room getRoom(String[] elements) {
        if (elements.length != 5) {
            return null;
        }
        String roomId = elements[1];
        String roomName = elements[2];
        String roomDescription = elements[3];
        boolean roomHidden = Boolean.parseBoolean(elements[4]);
        return new Room(roomId, roomName, roomDescription, roomHidden);
    }

    private static Equipment getEquipment(String[] elements) {
        if (elements.length != 9) {
            return null;
        }

        String equipmentId = elements[1];
        String equipmentName = elements[2];
        String equipmentDescription = elements[3];
        boolean equipmentHidden = Boolean.parseBoolean(elements[4]);
        String equipmentUseAction = elements[5];
        String equipmentUseTarget = elements[6];
        String equipmentUseResult = elements[7];
        String equipmentUseDescription = elements[8];
        UseInformation useInformation = new UseInformation(false, equipmentUseAction,
                equipmentUseTarget, equipmentUseResult, equipmentUseDescription);
        return new Equipment(equipmentId, equipmentName, equipmentDescription,
                equipmentHidden, useInformation);
    }

    private static Container getContainer(String[] elements) {
        if (elements.length != 5) {
            return null;
        }
        String containerId = elements[1];
        String containerName = elements[2];
        String containerDescription = elements[3];
        boolean containerHidden = Boolean.parseBoolean(elements[4]);
        return new Container(containerId, containerName, containerDescription, containerHidden);
    }

    private static Item getItem(String[] elements) {
        if (elements.length != 5) {
            return null;
        }
        String itemId = elements[1];
        String itemName = elements[2];
        String itemDescription = elements[3];
        boolean itemHidden = Boolean.parseBoolean(elements[4]);
        return new Item(itemId, itemName, itemDescription, itemHidden);
    }

    private static Exit getExit(String[] elements) {
        if (elements.length != 6) {
            return null;
        }
        String exitId = elements[1];
        String exitName = elements[2];
        String exitDescription = elements[3];
        String exitNextRoom = elements[4];
        boolean exitHidden = Boolean.parseBoolean(elements[5]);
        return new Exit(exitId, exitName, exitDescription, exitNextRoom, exitHidden);
    }

    private static CombineInfo getCombineInfo(String[] elements) {
        if (elements.length != 5) {
            return null;
        }
        String item1 = elements[1];
        String item2 = elements[2];
        String notCombinedMsg = elements[3];
        boolean combineRequired = Boolean.parseBoolean(elements[4]);
        return new CombineInfo(item1, item2, notCombinedMsg, combineRequired);
    }
}
