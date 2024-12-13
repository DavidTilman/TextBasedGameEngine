package org.uob.a2.gameobjects;

public class Score {
    static final int SOLVE_POINTS = 10;
    private static int score = 0;
    private static int roomsVisited = 0;

    public static void useItem() {
        score += 100;
    }

    public static void combineItems() {
        score += 50;
    }

    public static void visitRoom() {
        roomsVisited++;
    }

    public static String getScore() {
        return "Current score: " + score + "\nRooms visited: " + roomsVisited;
    }
}
