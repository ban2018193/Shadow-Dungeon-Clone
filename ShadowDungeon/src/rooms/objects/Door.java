package rooms.objects;

import bagel.util.Point;
import bagel.*;
import bagel.util.Rectangle;
import entities.Entity;
import rooms.Room;
import entities.player.Player;


/**
 * Represents a door connecting two rooms in the game.
 *
 * A door has two sides (Room A and Room B) and can be locked or unlocked.
 * It handles collision detection with the player, interaction logic, and rendering.
 * Doors can auto-lock if the stage is not cleared, and manage player entry between rooms.
 */
public class Door extends Entity {

    // ---- constants -----
    private enum DoorSide {ROOM_A, ROOM_B};
    private final static int CONNECTED_ROOMS = 2;
    private final static int FAILED = -1;

    // -----rooms and position the door would be in -----
    private final Room[] rooms = new Room[CONNECTED_ROOMS]; //first is Primary room, sec is Secondary
    private final Point[] startXY = new Point[CONNECTED_ROOMS];
    private final Rectangle[] doorBox = new Rectangle[CONNECTED_ROOMS];

    // ----- door stats -----
    private boolean isUnlocked = false;
    private DoorSide currentDoor = null;

    // indicates if the room this door is in has been cleared or not
    private final boolean[] stageNotClear = new boolean[] {true, true};
    // determines if player just enter the door from other side
    private boolean recentlyInteracted = false;

    // ----- images of doors -----
    private static final Image doorUnlock = new Image("res/unlocked_door.png");
    private static final Image doorLocked = new Image("res/locked_door.png");

    // ---- constructor -----

    /**
     * Creates a new door connecting two rooms.
     *
     * @param rooms   an array of two rooms this door connects; first is Room A, second is Room B
     * @param startXY positions of the door in each room; first for Room A, second for Room B
     */
    public Door(Room[] rooms, Point[] startXY) {
        super(startXY[0],"res/locked_door.png" );
        // create door logic at room
        for (int i = 0; i < CONNECTED_ROOMS; i++) {
            this.rooms[i] = rooms[i];
            this.startXY[i] = startXY[i];
            this.doorBox[i] = doorLocked.getBoundingBoxAt(startXY[i]);
        }
    }

    /**
     * Updates which side of the door the player is currently at.
     *
     * @param currentRoom the room the player is currently in
     */
    public void updateCurrentDoorSide(Room currentRoom) {
        if (rooms[0] == currentRoom) currentDoor= DoorSide.ROOM_A;
        else if (rooms[1] == currentRoom) currentDoor = DoorSide.ROOM_B;
        else currentDoor = null;
    }

    // converts DoorSide enum to array index for internal arrays
    private int sideToIndex(DoorSide side) {
        return side == DoorSide.ROOM_A ? 0 : 1;
    }

    // --- handles interactions ----
    /**
     * Checks if the player collides with the door in the current room.
     *
     * @param player      the player to check
     * @param currentRoom the current room the player is in
     * @return true if the player intersects the door's bounding box
     */
    public boolean collidesWith(Player player, Room currentRoom) {
        updateCurrentDoorSide(currentRoom);
        int doorIndex = sideToIndex(currentDoor);

        // get the correct location of the box in current room
        Rectangle doorBox = doorLocked.getBoundingBoxAt(startXY[doorIndex]);

        return doorBox.intersects(player.getBoundingBox());
    }


    // ---- manage auto locks -----

    /**
     * Disables the door's auto-locking feature for the current side.
     */
    public void disableAutoLock() {
        this.stageNotClear[sideToIndex(currentDoor)] = false;
    }


    /**
     * Automatically locks the door if the stage is not cleared and the player has left its area.
     *
     * @param player the player in the room
     */
    public void autoLock(Player player) {
        int sideIndex = sideToIndex(currentDoor);
        if (stageNotClear[sideIndex]
                && !doorBox[sideIndex].intersects(player.getBoundingBox())) {
            isUnlocked = false;
            recentlyInteracted = false;
        }else if (!doorBox[sideIndex].intersects(player.getBoundingBox())) {
            recentlyInteracted = false;
        }
    }

    // ----- move room logic method ----

    /**
     * Returns the spawn location for the room connected to the door.
     *
     * @return the spawn point for the destination room
     */
    public Point getSpawnLocation() {
        // find spawning location for next room this door connects to
        DoorSide destination = (currentDoor == DoorSide.ROOM_A) ? DoorSide.ROOM_B : DoorSide.ROOM_A;
        return startXY[sideToIndex(destination)];
    }

    /**
     * Handles the player entering the door and returns the destination room index.
     *
     * @param player      the player attempting to enter
     * @param currentRoom the room the player is currently in
     * @return the index of the destination room if entry succeeds, otherwise -1
     */
    public int enterDoor(Player player, Room currentRoom) {
        // find current door side
        updateCurrentDoorSide(currentRoom);
        int sideIndex = sideToIndex(currentDoor);

        // return failed if door is locked/stage uncleared/player has not left door upon entering
        if (!isUnlocked || stageNotClear[sideIndex] || recentlyInteracted) {
            return FAILED;
        }

        if (doorBox[sideIndex].intersects(player.getBoundingBox())) {
            // set destination of next room base on the curr room door is in
            DoorSide destination = (currentDoor == DoorSide.ROOM_A) ? DoorSide.ROOM_B : DoorSide.ROOM_A;
            int destinationIndex = rooms[sideToIndex(destination)].getIndex();
            recentlyInteracted = true;
            return destinationIndex;
        }
        return FAILED;
    }

    // ----- render ----

    /**
     * Renders the door in the current room.
     *
     * The door image is updated based on its locked/unlocked state.
     *
     * @param currentRoom the room to render the door in
     */
    public void render(Room currentRoom) {

        updateCurrentDoorSide(currentRoom);
        int sideIndex = sideToIndex(currentDoor);
        if (sideIndex != FAILED) {
            // render unlocked door if door is unlocked, vice versa
            Image door = isUnlocked ? doorUnlock : doorLocked;
            setImage(door);
            movePosition(startXY[sideIndex]);
            super.render();
        }
    }


    // ----- getters ----

    /**
     * Determines if the door blocks player movement.
     *
     * @return true if the door is locked, false if it is unlocked
     */
    @Override
    public boolean isBlockable() {
        return !isUnlocked;
    }




    // ---- setters ----

    /**
     * Sets the door's unlocked status.
     *
     * @param unlocked true to unlock the door, false to lock it
     */
    public void setUnlocked(boolean unlocked) {isUnlocked = unlocked;}

    /**
     * Sets whether the stage on the current side of the door is cleared or not.
     *
     * @param stageNotClear true if the stage is not cleared, false if it is cleared
     */
    public void setStageNotClear(boolean stageNotClear) {
        this.stageNotClear[sideToIndex(currentDoor)] = stageNotClear;
    }


}
