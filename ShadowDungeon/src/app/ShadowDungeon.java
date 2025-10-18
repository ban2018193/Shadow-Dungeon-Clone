package app;

import bagel.*;
import bagel.util.Point;

import config.GameConfig;
import dungeon.Dungeon;
import entities.*;
import rooms.*;
import rooms.objects.Door;


/**
 * The main driver class for the Shadow Dungeon game
 * This class initializes and manages the game window, sets up rooms, doors, and
 * handles the main update loop. It acts as the central for the game,
 * coordinating between user input, dungeon logic, and rendering
 */
public class ShadowDungeon extends AbstractGame {

    // ----- Settings -----
    public final GameConfig config = GameConfig.getInstance();
    private static ShadowDungeon instance;
    private Dungeon shadowDungeon;

    // ---- Constructor ----

    /***
     * Initializes the game window using the dimensions and title from
     * GameConfig, creates the dungeon structure
     */
    public ShadowDungeon() {
        super(GameConfig.getInstance().WINDOW_WIDTH,
                GameConfig.getInstance().WINDOW_HEIGHT, GameConfig.getInstance().TITLE);

        instance = this;
        this.shadowDungeon = new Dungeon(createRooms());
    }

    // ---- Methods -----

    // Create rooms for the dungeon
    private Room[] createRooms() {
        Room[] rooms = new Room[4];

        // Manually create rooms
        rooms[0] = new PrepRoom(0);
        rooms[1] = new BattleRoom(1, "A");
        rooms[2] = new BattleRoom(2, "B");
        rooms[3] = new EndRoom(3);

        // Create doors
        Door doorPrepA = new Door(new Room[]{rooms[0], rooms[1]},
                new Point[] {config.DOOR_PREP,
                        config.PRIMARY_DOOR_A});
        Door doorAB = new Door(new Room[]{rooms[1], rooms[2]},
                new Point[] {config.SECONDARY_DOOR_A,
                        config.PRIMARY_DOOR_B});
        Door doorBEND = new Door(new Room[]{rooms[2], rooms[3]},
                new Point[] {config.SECONDARY_DOOR_B,
                        config.DOOR_END});

        // Add doors into the rooms
        rooms[0].addDoor(doorPrepA);
        rooms[1].addDoor(doorPrepA);
        rooms[1].addDoor(doorAB);
        rooms[2].addDoor(doorAB);
        rooms[2].addDoor(doorBEND);
        rooms[3].addDoor(doorBEND);

        return rooms;
    }

    // ----- Updates -----

    /**
     * Render the relevant screen based on the keyboard input
     * given by the user and the status of the gameplay
     *
     * @param input The current mouse/keyboard input
     */
    protected void update(Input input) {
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }

        shadowDungeon.update(input);
    }

    // Restarts the game by recreating a new dungeon
    public static void restart() {
        instance.shadowDungeon = new Dungeon(instance.createRooms());
    }

    // ----- Main -----

    /**
     * The main entry point of the Shadow Dungeon game
     * Initializes the Dungeon
     * Starts the game loop
     *
     * @param args Command-line arguments (not used in this game)
     */
    public static void main(String[] args) {
        ShadowDungeon game = new ShadowDungeon();
        game.run();
    }
}
