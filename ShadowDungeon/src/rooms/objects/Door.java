package rooms.objects;

import bagel.util.Point;
import bagel.*;
import bagel.util.Rectangle;
import rooms.Room;
import entities.Player;


/**
 * door: exists within rooms
 * manage rooms connection, one door access to two sides (A, B)
 * 1st is A (enter from cleared stage), 2nd is B (exit to new stage)
 * handles collision, player interactions and rendering
 */
public class Door {

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
     * initilize a new door
     * @param rooms the rooms door connected. 1st is A (enter from cleared stage), 2nd is B (exit to new stage)
     * @param startXY positions of doors in each room. 1st is position in room A, 2nd is position in room B
     */
    public Door(Room[] rooms, Point[] startXY) { // create door logic at room
        for (int i = 0; i < CONNECTED_ROOMS; i++) {
            this.rooms[i] = rooms[i];
            this.startXY[i] = startXY[i];
            this.doorBox[i] = doorLocked.getBoundingBoxAt(startXY[i]);
        }
    }

    // updates currentDoorSide based on which room the player is in
    public void updateCurrentDoorSide(Room currentRoom) {
        if (rooms[0] == currentRoom) currentDoor= DoorSide.ROOM_A;
        else if (rooms[1] == currentRoom) currentDoor = DoorSide.ROOM_B;
        else currentDoor = null;
    }

    // converts DoorSide enum to array index for internal arrays
    private int sideToIndex(DoorSide side) {
        return side == DoorSide.ROOM_A ? 0 : 1;
    }

    // check if player collides with door
    public boolean collidesWith(Player player, Room currentRoom) {
        updateCurrentDoorSide(currentRoom);
        int doorIndex = sideToIndex(currentDoor);

        // get the correct location of the box in current room
        Rectangle doorBox = doorLocked.getBoundingBoxAt(startXY[doorIndex]);

        return doorBox.intersects(player.getBoundingBox());
    }


    // ---- manage auto locks -----

    public void disableAutoLock() {
        this.stageNotClear[sideToIndex(currentDoor)] = false;
    }

    // auto lock the door once player left door if stage is not clear + auto lock is turned on
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

    // get next room door spawning location
    public Point getSpawnLocation() {
        // find spawning location for next room this door connects to
        DoorSide destination = (currentDoor == DoorSide.ROOM_A) ? DoorSide.ROOM_B : DoorSide.ROOM_A;
        return startXY[sideToIndex(destination)];
    }

    /**
     * handles player entering the door
     * @param playerBox player's physical box
     * @param currentRoom the current room this door is in
     * @return destination room index if successfully entered, otherwise return FAILED
     */
    public int enterDoor(Rectangle playerBox, Room currentRoom) {
        // find current door side
        updateCurrentDoorSide(currentRoom);
        int sideIndex = sideToIndex(currentDoor);

        // return failed if door is locked/stage uncleared/player has not left door upon entering
        if (!isUnlocked || stageNotClear[sideIndex] || recentlyInteracted) {
            return FAILED;
        }

        if (doorBox[sideIndex].intersects(playerBox)) {
            // set destination of next room base on the curr room door is in
            DoorSide destination = (currentDoor == DoorSide.ROOM_A) ? DoorSide.ROOM_B : DoorSide.ROOM_A;
            int destinationIndex = rooms[sideToIndex(destination)].getIndex();
            recentlyInteracted = true;
            return destinationIndex;
        }
        return FAILED;
    }

    // ----- rendering purpose ----
    public void render(Room currentRoom) {

        updateCurrentDoorSide(currentRoom);
        int sideIndex = sideToIndex(currentDoor);
        if (sideIndex != FAILED) {
            // render unlocked door if door is unlocked, vice versa
            Image door = isUnlocked ? doorUnlock : doorLocked;
            door.draw(startXY[sideIndex].x, startXY[sideIndex].y);
        }
    }


    // ----- getters ----
    public boolean isUnlocked() {
        return isUnlocked;
    }

    // ---- setters ----

    public void setUnlocked(boolean unlocked) {isUnlocked = unlocked;}

    public void setStageNotClear(boolean stageNotClear) {
        this.stageNotClear[sideToIndex(currentDoor)] = stageNotClear;
    }
}
