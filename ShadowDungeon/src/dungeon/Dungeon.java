package dungeon;

import bagel.*;
import bagel.util.Point;

import rooms.*;
import entities.Player;
import config.GameConfig;


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

    // ---- the game stats of the player ----
    private final String healthTitle;
    private final String coinsTitle;
    private final Point healthStat;
    private final Point coinStat;
    private final Player player;
    private final Font titleFont;


    // ---- constructor -----

    /**
     * create a dungeon with rooms inside, initialize all the stats for a new game
     * @param rooms the rooms to be included in the dungeon, not empty
     */
    public Dungeon(Room[] rooms) {
        this.rooms = rooms;
        this.totalRooms = rooms.length;
        this.activeRoom = rooms[START_ROOM];

        this.healthTitle = config.HEALTH_DISPLAY;
        this.coinsTitle = config.COIN_DISPLAY;
        this.healthStat = config.HEALTH_STAT_POS;
        this.coinStat = config.COIN_STAT_POS;
        this.player = new Player();
        this.END_ROOM = rooms.length - 1;
        this.titleFont = new Font(config.FONT_PATH,
                config.PLAYER_STATS_FONT_SIZE);
    }

    // ----- moving between rooms ----

    // move to the room according to its index in dungeon
    public void moveToRoom(int roomIndex) {
        activeRoom = rooms[roomIndex];
    }

    // check if player has lost the game or not, move to end room if lost
    public void checkIfLost() {
        if (player.getHealth() <= 0) {
            ((EndRoom)rooms[END_ROOM]).setLostStatus(true);
            if (!hasLost) {
                player.movePosition(config.PLAYER_START_POS);
            }
            hasLost = true;
            moveToRoom(END_ROOM);
        }
    }

    // ----- rendering the whole dungeon -----

    // render the stats entities in this dungeon
    private void renderStat() {
        double health = player.getHealth();
        String healthText = String.format(healthTitle + " %.1f", health);

        double coins = player.getCoins();
        String coinText = String.format(coinsTitle + " %.1f", coins);

        // render stats of player
        titleFont.drawString(healthText, healthStat.x, healthStat.y);
        titleFont.drawString(coinText, coinStat.x, coinStat.y);
    }

    // ----- main render -----
    public void render() {
        activeRoom.render(); // render the current active room
        renderStat(); // render thet current stats of the player
        player.render(); // finally render the character moving around
    }

    // ---- updating frames -----
    public void update(Input input) {
        activeRoom.update(player, input, this);
        render();
        checkIfLost();

    }

    // ---- getters -----
    public Player getPlayer() {return player;}

}
