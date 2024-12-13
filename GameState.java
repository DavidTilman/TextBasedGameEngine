package org.uob.a2.gameobjects;

/**
 * Represents the current state of the game, including the map and the player.
 * 
 * <p>
 * The game state contains all necessary information about the game's progress, such as
 * the player's status and the state of the game map.
 * </p>
 */
public class GameState {
   private Map map;
   private Player player;

    public GameState() {
        map = null;
        player = null;
    }

   public GameState(Map map, Player player) {
        this.map = map;
        this.player = player;
   }

   public Player getPlayer() {
        return player;
   }

   public Map getMap() {
        return map;
   }

    /**
     * Returns a string representation of the game state, including the map and player details.
     *
     * @return a string describing the game state
     */
    @Override
    public String toString() {
        return "GameState {" +
               "map=" + (map != null ? map.toString() : "null") + ", " +
               "player=" + (player != null ? player.toString() : "null") +
               '}';
    }
}
