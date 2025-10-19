package dungeon;

import bagel.*;
import rooms.*;
import entities.player.*;
import config.GameConfig;
import stores.Store;


/**
 * The Dungeon class is the control centre of the rooms
 * Handles room management, player progress, and game initialization
 */
public class Dungeon {

    // ----- Defaults -----

    public final GameConfig config = GameConfig.getInstance();
    private final int START_ROOM = 0;
    private final int END_ROOM;


    // ----- Dungeon info -----

    private final Room[] rooms;
    private Room activeRoom;
    private boolean hasLost = false;
    private final Store store = new Store();
    private final PlayerCharacter playerChar;


    // ---- Constructor -----

    /**
     * Creates a dungeon with the given rooms
     *
     * @param rooms The rooms that make up the dungeon
     */
    public Dungeon(Room[] rooms) {
        this.rooms = rooms;
        this.activeRoom = rooms[START_ROOM];
        this.playerChar = new PlayerCharacter();
        this.END_ROOM = rooms.length - 1;
    }


    // ----- Room transitions -----

    /**
     * Moves the player to a room based on room index in the dungeon
     *
     * @param roomIndex The index of the room to move to
     */
    public void moveToRoom(int roomIndex) {
        activeRoom = rooms[roomIndex];
    }


    /**
     * Checks if the player has lost (health <= 0)
     * If lost, move to the end room
     */
    public void checkIfLost() {
        Player player = playerChar.getPlayer();
        PlayerStats playerStats = player.getPlayerStats();
        if (playerStats.getHealth() <= 0) {
            ((EndRoom)rooms[END_ROOM]).setLostStatus(true);
            if (!hasLost) { // Only fix location for first called lost
                player.movePosition(config.PLAYER_START_POS);
            }
            hasLost = true;
            moveToRoom(END_ROOM);
        }
    }


    // ----- Rendering -----

    /**
     * Renders everything on screen:
     * the current room, player stats, and the player itself
     */
    public void render() {
        Player player = playerChar.getPlayer();
        activeRoom.render(); // Render the current active room
        player.getPlayerStats().renderStat(); // Render thet current stats of the player
        player.render(); // Finally render the character moving around
    }


    // ----- Updating -----

    /**
     * Updates the dungeon each frame
     *
     * @param input The current keyboard/mouse input
     */
    public void update(Input input) {
        render();

        // Pause updates of stats of all objects when store is opened
        if (!store.openStore(input, playerChar.getPlayer())) {
            activeRoom.update(playerChar, input, this);
        }
        checkIfLost();
    }

}
