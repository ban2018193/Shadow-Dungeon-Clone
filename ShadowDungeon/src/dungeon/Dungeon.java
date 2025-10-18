package dungeon;

import bagel.*;
import bagel.util.Point;

import rooms.*;
import entities.player.*;
import config.GameConfig;
import stores.Store;


/**
 * dungeon acts as the control centre
 * handle room managements, and initializing the game itself
 */
public class Dungeon {

    // ----- defaults -----
    public final GameConfig config = GameConfig.getInstance();
    private final int START_ROOM = 0;
    private final int END_ROOM;

    // ----- dungeon info -----
    private final Room[] rooms;
    private final int totalRooms;
    private Room activeRoom;
    private boolean hasLost = false;
    private Store store = new Store();

    // ---- the game stats of the player ----
    private final PlayerCharacter player;



    // ---- constructor -----

    /**
     * create a dungeon with rooms inside, initialize all the stats for a new game
     * @param rooms the rooms to be included in the dungeon, not empty
     */
    public Dungeon(Room[] rooms) {
        this.rooms = rooms;
        this.totalRooms = rooms.length;
        this.activeRoom = rooms[START_ROOM];


        this.player = new PlayerCharacter();
        this.END_ROOM = rooms.length - 1;
    }

    // ----- moving between rooms ----

    // move to the room according to its index in dungeon
    public void moveToRoom(int roomIndex) {
        activeRoom = rooms[roomIndex];
    }

    // check if player has lost the game or not, move to end room if lost
    public void checkIfLost() {
        Player playerSelf = player.getPlayer();
        PlayerStats playerStats = playerSelf.getPlayerStats();
        if (playerStats.getHealth() <= 0) {
            ((EndRoom)rooms[END_ROOM]).setLostStatus(true);
            if (!hasLost) {
                playerSelf.movePosition(config.PLAYER_START_POS);
            }
            hasLost = true;
            moveToRoom(END_ROOM);
        }
    }

    // ----- rendering the whole dungeon -----

    // ----- main render -----
    public void render() {
        Player playerChar = player.getPlayer();
        activeRoom.render(); // render the current active room
        playerChar.getPlayerStats().renderStat(); // render thet current stats of the player
        playerChar.render(); // finally render the character moving around
    }

    // ---- updating frames -----
    public void update(Input input) {
        render();
        if (!store.openStore(input, player.getPlayer())) {
            activeRoom.update(player, input, this);
        }
        checkIfLost();


    }

}
