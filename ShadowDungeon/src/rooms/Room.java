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
 * abstract base class for all the rooms in the dungeon
 * handle player's movement, collision, and every entities belong to the room
 */
public abstract class  Room {

    private final GameConfig config = GameConfig.getInstance();
    // ----- room stats -----
    private final int index; // stage of room is in dungeon

    // ----- doors -----
    private int numOfDoors = 0;
    private List<Door> doors = new ArrayList<>(); // 1st is primary door, 2nd is secondary
    private List<Projectile> projectiles = new ArrayList<>();

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
    private void updatePlayerMovement(PlayerCharacter player, Input input) {
        Player playerSelf = player.getPlayer();
        player.update(input, this);
        Point nextMove = player.tryInput(input);
        Point validMove = validateMove(player, nextMove);
        playerSelf.movePosition(validMove);
    }

    /**
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

    // main update method
    public void update(PlayerCharacter player, Input input, Dungeon dungeon) {
        updatePlayerMovement(player, input);
        Player playerChar = player.getPlayer();
        handleDoorInteractions(playerChar, dungeon);
        updateProjectiles();
    }

    // ----- check movements -----

    // validate player's move (collision with wall and window)
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

    // temporarily move player to check collision with walls
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


    // ----- rendering methods -----

    public void renderBackground() {
        background.drawFromTopLeft(0, 0);
    }

    public void renderDoors() {
        for (Door door: doors) {
            door.render(this);
        }
    }

    private void renderProjectiles() {
        for (Projectile projectile: projectiles) {
            projectile.render();
        }
    }

    private void updateProjectiles() {
        for (Projectile projectile: projectiles) {
            projectile.update();
        }
    }

    public void render(){
        renderBackground();
        renderDoors();
        renderProjectiles();
    }

    // ---- door managements ---

    public void addDoor(Door newDoor) {
        doors.add(newDoor);
        numOfDoors++;
    }

    // unlock all the doors in the room if all keys are obtained
    public void roomCleared() {
        for (int i = 0; i < numOfDoors; i++) {
            doors.get(i).setUnlocked(true);
            doors.get(i).setStageNotClear(false);
            doors.get(i).disableAutoLock();
        }
    }

    public boolean allDoorLocked() {
        for (Door door : doors) {
            if (!door.isBlockable()){
                return false;
            }
        }
        return true;
    }

    // ----- getters -----
    public int getIndex() {return index;}
    public int getNumOfDoors() {return numOfDoors;}

    public List<Door> getDoors() {
        return doors;
    }

    public GameConfig getConfig() {
        return config;
    }

    public List<Projectile> getProjectiles() {
        return projectiles;
    }

    // ----- update -----
    public void updateNumOfDoors() {
        this.numOfDoors++;
    }
}
