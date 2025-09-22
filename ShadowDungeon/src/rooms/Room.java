package rooms;

import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;
import config.GameConfig;
import rooms.objects.Door;

import entities.Player;
import dungeon.Dungeon;

/**
 * abstract base class for all the rooms in the dungeon
 * handle player's movement, collision, and every entities belong to the room
 */
public abstract class  Room {

    private static final GameConfig config = GameConfig.getInstance();

    // ----- room stats -----
    private final int index; // stage of room is in dungeon
    private static final int MAX_DOOR = 2;

    // ----- doors -----
    private int numOfDoors = 0;
    private final Door[] doors = new Door[MAX_DOOR]; // 1st is primary door, 2nd is secondary

    // ----- background -----
    private final Image background = new Image("res/background.png");

    // ----- constructor -----

    /**
     * initialise the base settings of room (number of keys)
     * @param index is which stage is this in the dungeon class
     */
    public Room(int index) {
        this.index = index;
    }

    // ---- updating room -----

    // try to move player, if valid move, move player
    private void updatePlayerMovement(Player player, Input input) {
        player.update(input);
        Point nextMove = player.tryInput(input);
        Point validMove = validateMove(player, nextMove);
        player.movePosition(validMove);
    }

    /**
     * when player enters new uncleared stage, auto ock the doors when player left the door
     * if player tried to enter doors, check if its valid
     * if yes, move to the room the door is associated with
     */
    private void handleDoorInteractions(Player player, Dungeon dungeon) {
        for (int i = 0; i < numOfDoors; i++) {
            doors[i].updateCurrentDoorSide(this);
            doors[i].autoLock(player);

            int hasTryEnterDoor = doors[i].enterDoor(player.getBoundingBox(), this);
            if (hasTryEnterDoor >= 0) {
                player.movePosition(doors[i].getSpawnLocation());
                dungeon.moveToRoom(hasTryEnterDoor);
                break;
            }
        }
    }

    // main update method
    public void update(Player player, Input input, Dungeon dungeon) {
        updatePlayerMovement(player, input);
        handleDoorInteractions(player, dungeon);
    }

    // ----- check movements -----

    // validate player's move (collision with wall and window)
    public Point validateMove(Player player, Point nextMove) {
        // temporarily move player to check if collides with locked doors
        if (temporarilyCheckDoorCollision(player, nextMove)) {
            return player.trySolveCollision(nextMove,
                    (x, y) -> temporarilyCheckDoorCollision(player, new Point(x, y)));
        }

        // if no door collision, make sure is within window
        return fixWithinWindow(player, nextMove);
    }

    // temporarily move player to check door collision
    private boolean temporarilyCheckDoorCollision(Player player, Point pos) {
        Point originalPos = player.getPosition();
        player.movePosition(pos);

        boolean collides = hasDoorCollision(player);

        player.movePosition(originalPos);
        return collides;
    }

    // keep player within window boundaries
    private Point fixWithinWindow(Player player, Point nextMove) {

        Rectangle windowBox = new Rectangle(0, 0,
                GameConfig.getInstance().WINDOW_WIDTH, GameConfig.getInstance().WINDOW_HEIGHT);
        Rectangle playerBox = player.getImage().getBoundingBoxAt(nextMove);

        double x = nextMove.x;
        double y = nextMove.y;

        // ensure player is within width
        if (playerBox.left() < windowBox.left()) {
            x += (windowBox.left() - playerBox.left());
        } else if (playerBox.right() > windowBox.right()) {
            x -= (playerBox.right() - windowBox.right());
        }

        // ensure player is within height
        if (playerBox.top() < windowBox.top()) {
            y += (windowBox.top() - playerBox.top());
        } else if (playerBox.bottom() > windowBox.bottom()) {
            y -= (playerBox.bottom() - windowBox.bottom());
        }

        return new Point(x, y);
    }

    // check if player collides with wall
    private boolean hasDoorCollision(Player player) {
        for (Door door : getDoors()) {
            if (door == null) break;
            if (!door.isUnlocked() && door.collidesWith(player, this)) {
                return true;
            }
        }
        return false;
    }

    // ----- rendering methods -----

    public void renderBackground() {
        background.drawFromTopLeft(0, 0);
    }

    public void renderDoors() {
        for (int i = 0; i < numOfDoors; i++) {
            doors[i].render(this);
        }
    }

    public void render(){
        renderBackground();
        renderDoors();
    }

    // ---- door managements ---

    public void addDoor(Door newDoor) {
        doors[numOfDoors] = newDoor;
        numOfDoors++;
    }

    // unlock all the doors in the room if all keys are obtained
    public void roomCleared() {
        for (int i = 0; i < numOfDoors; i++) {
            doors[i].setUnlocked(true);
            doors[i].setStageNotClear(false);
            doors[i].disableAutoLock();
        }
    }

    public boolean allDoorLocked() {
        for (Door door : doors) {
            if (door.isUnlocked()){
                return false;
            }
        }
        return true;
    }

    // ----- getters -----
    public int getIndex() {return index;}
    public Door[] getDoors() {return doors;}
    public int getNumOfDoors() {return numOfDoors;}
    public static GameConfig getConfig() {return config;};

    // ----- update -----
    public void updateNumOfDoors() {
        this.numOfDoors++;
    }
}
