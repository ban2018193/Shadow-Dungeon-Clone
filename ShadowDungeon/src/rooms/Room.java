package rooms;

import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;
import config.GameConfig;
import entities.Entity;
import entities.objects.projectiles.Projectile;
import entities.player.PlayerCharacter;
import rooms.objects.Door;

import entities.player.Player;
import dungeon.Dungeon;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for all rooms in the dungeon.
 * Handles player movement, collision detection, projectiles, and door interactions.
 */
public abstract class  Room {

    private final GameConfig config = GameConfig.getInstance();

    // ----- room stats -----
    private final int index; // stage of room is in dungeon
    private final Image background = new Image("res/background.png");
    private int numEnemy = 0;
    private int numOfDoors = 0;

    // ----- contents -----
    private List<Door> doors = new ArrayList<>(); // 1st is primary door, 2nd is secondary
    private List<Projectile> projectiles = new ArrayList<>();
    private List<Projectile> toRemoveProj = new ArrayList<>();

    // ----- constructor -----

    /**
     * Constructs a room with a given index.
     *
     * @param index the index of this room in the dungeon
     */
    public Room(int index) {
        this.index = index;
    }

    // ---- updating room -----

    // try to move player, if valid move, move player
    private void updatePlayerMovement(PlayerCharacter player, Input input) {
        Player playerSelf = player.getPlayer();
        player.update(input, this);
        Point nextMove = player.tryInput(input);
        Point validMove = validateMove(player, nextMove);
        playerSelf.movePosition(validMove);
    }

    /*
     * when player enters new uncleared stage, auto ock the doors when player left the door
     * if player tried to enter doors, check if its valid
     * if yes, move to the room the door is associated with
     */
    private void handleDoorInteractions(Player player, Dungeon dungeon) {
        for (Door door: doors) {
            door.updateCurrentDoorSide(this);
            door.autoLock(player);

            int hasTryEnterDoor = door.enterDoor(player, this);
            if (hasTryEnterDoor >= 0) {
                player.movePosition(door.getSpawnLocation());
                dungeon.moveToRoom(hasTryEnterDoor);
                break;
            }
        }
    }

    /**
     * Main update method for the room.
     *
     * @param player the player character
     * @param input the current input state
     * @param dungeon the dungeon containing this room
     */
    public void update(PlayerCharacter player, Input input, Dungeon dungeon) {
        updatePlayerMovement(player, input);
        Player playerChar = player.getPlayer();
        handleDoorInteractions(playerChar, dungeon);
        updateProjectiles();

        // trigger collisions events if collides
        for (Projectile projectile: getProjectiles()) {
            for (Door door: doors) {
                if (projectile.collidesWith(door)) {
                    projectile.triggerCollisionEvent(door, playerChar);
                    projectile.deleteInactive(this);
                }
            }
        }
        projectiles.removeAll(toRemoveProj);
    }

    // ----- check movements -----

    /**
     * Validates the player's move, checking collisions with blockable entities
     * and window boundaries.
     *
     * @param player the player character
     * @param nextMove the intended next position
     * @return the valid move position
     */
    public Point validateMove(PlayerCharacter player, Point nextMove) {
        Player playerSelf = player.getPlayer();
        // temporarily move player to check if collides with locked doors
        if (temporarilyCheckCollision(playerSelf, nextMove, doors)) {
            return player.trySolveCollision(nextMove,
                    (x, y) -> temporarilyCheckCollision(playerSelf, new Point(x, y), doors));
        }

        // if no door collision, make sure is within window
        return fixWithinWindow(playerSelf, nextMove);
    }

    /**
     * Temporarily moves the player to a position to check collisions with entities.
     *
     * @param player the player
     * @param pos the position to test
     * @param entity the list of entities to check collisions with
     * @return true if a collision would occur
     */
    public <T extends Entity> boolean temporarilyCheckCollision(Player player, Point pos, List<T> entity) {
        Point original = player.getPosition();
        player.movePosition(pos);

        boolean collides = hasBlocked(player, entity);

        player.movePosition(original);
        return collides;
    }

    // keep player within window boundaries
    private Point fixWithinWindow(Player player, Point nextMove) {

        Rectangle windowBox = new Rectangle(0, 0,
                config.WINDOW_WIDTH, config.WINDOW_HEIGHT);
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


    /**
     * Checks if the player is blocked by any blockable entity in the list.
     *
     * @param player the player character
     * @param entities the list of entities
     * @return true if blocked
     */
    public <T extends Entity> boolean hasBlocked(Player player, List<T> entities) {
        for (T entity : entities) {
            if (entity.isBlockable()) {
                if (entity instanceof Door d && d.collidesWith(player, this)) {
                    return true;
                } else if (entity.collidesWith(player)) {
                    return true;
                }
            }
        }
        return false;
    }


    // ----- render -----

    /** Renders the room background */
    public void renderBackground() {
        background.drawFromTopLeft(0, 0);
    }

    private void renderDoors() {
        for (Door door: doors) {
            door.render(this);
        }
    }

    /** Renders all projectiles in this room */
    public void renderProjectiles() {
        for (Projectile projectile: projectiles) {
            projectile.render();
        }
    }

    /** Renders the room */
    public void render(){
        renderBackground();
        renderDoors();
    }

    private void updateProjectiles() {
        for (Projectile projectile: projectiles) {
            projectile.update();
        }
    }

    // ----- door management -----

    /** Adds a door to this room */
    public void addDoor(Door newDoor) {
        doors.add(newDoor);
        numOfDoors++;
    }

    /** Unlocks all doors when the room is cleared */
    public void roomCleared() {
        for (int i = 0; i < numOfDoors; i++) {
            doors.get(i).setUnlocked(true);
            doors.get(i).setStageNotClear(false);
            doors.get(i).disableAutoLock();
        }
    }

    /** Unlocks all doors when the room is cleared */
    public boolean allDoorLocked() {
        for (Door door : doors) {
            if (!door.isBlockable()){
                return false;
            }
        }
        return true;
    }

    // ----- getters -----

    /**
     * Returns the index of this room in the dungeon.
     *
     * @return the room index
     */
    public int getIndex() {
        return index;
    }

    /**
     * Returns the number of doors currently in this room.
     *
     * @return the number of doors
     */
    public int getNumOfDoors() {
        return numOfDoors;
    }

    /**
     * Returns the list of doors in this room.
     * The first door is the primary door, the second (if any) is secondary.
     *
     * @return the list of doors
     */
    public List<Door> getDoors() {
        return doors;
    }

    /**
     * Returns the GameConfig instance associated with this room.
     *
     * @return the game configuration
     */
    public GameConfig getConfig() {
        return config;
    }

    /**
     * Returns the list of active projectiles in this room.
     *
     * @return the projectiles in the room
     */
    public List<Projectile> getProjectiles() {
        return projectiles;
    }

    /**
     * Returns the list of projectiles marked for removal.
     *
     * @return the projectiles to be removed
     */
    public List<Projectile> getToRemoveProj() {
        return toRemoveProj;
    }

// ----- update / helper methods -----

    /**
     * Increments the number of doors in this room by 1.
     */
    public void updateNumOfDoors() {
        this.numOfDoors++;
    }

    /**
     * Decreases the count of remaining enemies by the specified amount.
     * Typically called when enemies are defeated.
     *
     * @param amount the number of defeated enemies
     */
    public void defeatedEnemy(int amount) {
        numEnemy -= amount;
    }

    /**
     * Increments the count of enemies in this room by 1.
     * Typically called when a new enemy is added to the room.
     */
    public void addEnemy() {
        numEnemy++;
    }

}
