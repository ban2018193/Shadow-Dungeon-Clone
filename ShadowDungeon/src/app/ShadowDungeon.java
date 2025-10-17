package app;


import bagel.*;
import bagel.util.Point;

import config.GameConfig;
import dungeon.Dungeon;
import entities.*;
import entities.player.PlayerStats;
import rooms.*;
import rooms.objects.Door;

import javax.swing.*;


public class ShadowDungeon extends AbstractGame {

    // ----- settings -----
    public final GameConfig config = GameConfig.getInstance(); // contains all of teh configurations settings
    private static ShadowDungeon instance;
    private Dungeon shadowDungeon;

    // ----- constructor ----
    public ShadowDungeon() {
        super(GameConfig.getInstance().WINDOW_WIDTH,
                GameConfig.getInstance().WINDOW_HEIGHT, GameConfig.getInstance().TITLE);

        instance = this;
        this.shadowDungeon = new Dungeon(createRooms());
    }

    // ---- methods -----

    // create rooms for the dungeon
    private Room[] createRooms() {
        Room[] rooms = new Room[4];

        // manually create rooms
        rooms[0] = new PrepRoom(0);
        rooms[1] = new BattleRoom(1, "A");
        rooms[2] = new BattleRoom(2, "B");
        rooms[3] = new EndRoom(3);

        // create doors
        Door doorPrepA = new Door(new Room[]{rooms[0], rooms[1]},
                new Point[] {config.DOOR_PREP,
                        config.PRIMARY_DOOR_A});
        Door doorAB = new Door(new Room[]{rooms[1], rooms[2]},
                new Point[] {config.SECONDARY_DOOR_A,
                        config.PRIMARY_DOOR_B});
        Door doorBEND = new Door(new Room[]{rooms[2], rooms[3]},
                new Point[] {config.SECONDARY_DOOR_B,
                        config.DOOR_END});

        // add doors into the rooms
        rooms[0].addDoor(doorPrepA);
        rooms[1].addDoor(doorPrepA);
        rooms[1].addDoor(doorAB);
        rooms[2].addDoor(doorAB);
        rooms[2].addDoor(doorBEND);
        rooms[3].addDoor(doorBEND);

        return rooms;
    }

    // ----- updates -----

    /**
     * Render the relevant screen based on the keyboard input given by the user and the status of the gameplay.
     * @param input The current mouse/keyboard input.
     */
    protected void update(Input input) {
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }

        shadowDungeon.update(input);
    }

    // restart the game by creating a new dungeon
    public static void restart() {

        instance.shadowDungeon = new Dungeon(instance.createRooms());
    }

    // ----- main -----

    /**
     * The main entry point of the Shadow Dungeon game.
     * This method loads the game properties and message files, initializes the game,
     * and starts the game loop.
     *
     * @param args Command-line arguments (not used in this game).
     */
    public static void main(String[] args) {
        ShadowDungeon game = new ShadowDungeon();
        game.run();
    }
}
