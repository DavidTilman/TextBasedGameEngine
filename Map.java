package org.uob.a2.gameobjects;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Represents the game map, which consists of a collection of rooms and the current room the player is in.
 * 
 * <p>
 * The map allows for navigation between rooms, adding new rooms, and managing the current room context.
 * </p>
 */
public class Map {
    private final ArrayList<Room> rooms;
    private Room currentRoom;
    private boolean graphed = false;

    public Map() {
        rooms = new ArrayList<>();
        currentRoom = null;
    }

    public void addRoom(Room room) {
        if (rooms.isEmpty()) {
            currentRoom = room;
        }
        rooms.add(room);
        graphed = false;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(String roomId) {
        currentRoom = rooms.stream().filter(r -> r.getId().equals(roomId)).findFirst().orElse(null);
    }

    public Room getRoom(String roomId) {
        return rooms.stream().filter(r -> r.getId().equals(roomId)).findFirst().orElse(null);
    }


    public void graphRooms() {
        Room source = rooms.get(0);
        source.setPosition(new Position(0,0));
        ArrayList<String> visited = new ArrayList<>();
        visited.add(source.getId());
        graphRoomsDFSRec(visited, source);
        normaliseRoomPositions();
        graphed = true;
    }

    private void graphRoomsDFSRec(ArrayList<String> visited, Room source) {
        visited.add(source.getId());
        for (Exit exit : source.getExits()) {
            String nextRoomId = exit.getNextRoom();
            Room nextRoom = getRoom(nextRoomId);
            if (nextRoom == null) {
                throw new InvalidMapException("Room has exit to null room");
            }
            Position nextPosition = getNextPosition(source, exit);

            if (visited.stream().anyMatch((s -> getRoom(s).getPosition().equals(nextPosition)))) {
                throw new InvalidMapException("Multiple rooms in same position.");
            }

            nextRoom.setPosition(nextPosition);

            if (!visited.contains(nextRoomId)) {
                graphRoomsDFSRec(visited, nextRoom);
            }
        }
    }

    private static Position getNextPosition(Room source, Exit exit) {
        Position sourcePos = source.getPosition();
        return switch (exit.getName().toLowerCase()) {
            case "north" -> new Position(sourcePos.x, sourcePos.y - 1);
            case "south" -> new Position(sourcePos.x, sourcePos.y + 1);
            case "east" -> new Position(sourcePos.x + 1, sourcePos.y);
            case "west" -> new Position(sourcePos.x - 1, sourcePos.y);
            default -> throw new InvalidMapException("Invalid exit name (not a direction): " + exit.getName());
        };
    }

    public void normaliseRoomPositions() {
        int[] minimums = getMinimums();

        for (Room room : rooms) {
            Position position = room.getPosition();
            Position normalPosition = new Position(position.x - minimums[0], position.y - minimums[1]);
            room.setPosition(normalPosition);
        }
    }

    public int[] getMinimums() {
        ArrayList<Integer> xPositions = new ArrayList<>();
        ArrayList<Integer> yPositions = new ArrayList<>();
        rooms.forEach((room) -> xPositions.add(room.getPosition().x));
        rooms.forEach((room) -> yPositions.add(room.getPosition().y));
        int[] result = new int[2];
        result[0] = Collections.min(xPositions);
        result[1] = Collections.min(yPositions);
        return result;
    }

    public int[] getDimensions() {
        ArrayList<Integer> xPositions = new ArrayList<>();
        ArrayList<Integer> yPositions = new ArrayList<>();
        rooms.forEach((room) -> xPositions.add(room.getPosition().x));
        rooms.forEach((room) -> yPositions.add(room.getPosition().y));
        int[] result = new int[2];
        result[0] = Collections.max(xPositions) + 1;
        result[1] = Collections.max(yPositions) + 1;
        return result;
    }

    public String display() {
        Position playerPosition = new Position(-1, -1);

        if (!graphed) {
            graphRooms();
        }

        if (currentRoom != null) {
            playerPosition = currentRoom.getPosition();
        }

        int[] maximums = getDimensions();
        int[] columnWidths = new int[maximums[0]];

        for (int i = 0; i < maximums[0]; i++) {
            int finalI = i;
            int columnWidth = rooms.stream().filter(r -> r.getPosition().x == finalI)
                    .map(r -> r.getId().length()).max(Integer::compareTo).orElse(1);
            columnWidths[i] = columnWidth;
        }

        StringBuilder mapBuilder = new StringBuilder();
        ArrayList<String> horizontalDividers = new ArrayList<>();
        Arrays.stream(columnWidths).forEach(i -> horizontalDividers.add("─".repeat(i)));
        mapBuilder.append('┌').append(String.join("┬", horizontalDividers)).append("┐\n");
        for (int y = 0; y < maximums[1]; y++) {
            StringBuilder colSb = new StringBuilder();
            for (int x = 0; x < maximums[0]; x++) {
                Position mapPosition = new Position(x, y);
                int finalX = x;
                colSb.append('│');
                if (x == playerPosition.x
                        && y == playerPosition.y) {
                    colSb.append("\u001B[31m");
                }
                colSb.append(
                        rooms.stream()
                                .filter(r -> r.getPosition().x == mapPosition.x
                                        && r.getPosition().y == mapPosition.y)
                                .findFirst()
                                .map(r->String.format("%-" + columnWidths[finalX] + "s", r.getId()))
                                .orElse(String.format("%-" + columnWidths[finalX] + "s", " "))
                );
                if (x == playerPosition.x
                        && y == playerPosition.y) {
                    colSb.append("\u001B[0m");
                }
            }
            colSb.append("│\n");
            mapBuilder.append(colSb);
            if (y < maximums[1] - 1) {
                mapBuilder.append('├').append(String.join("┼", horizontalDividers)).append("┤\n");
            }
        }
        mapBuilder.append('└').append(String.join("┴", horizontalDividers)).append("┘");
        return mapBuilder.toString();
    }


    /**
     * Returns a string representation of the map, including all rooms.
     *
     * @return a string describing the map and its rooms
     */
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("Map:\n");
        for (Room r : this.rooms) {
            out.append(r.toString()).append("\n");
        }
        return out.toString();
    }
}

