package rooms;

import bagel.*;
import bagel.util.Point;

import config.GameConfig;
import entities.*;
import dungeon.Dungeon;
import entities.player.Player;
import entities.player.PlayerCharacter;


/**
 * battle room: child of room entity
 * has obstacles and enemies
 * need get keys to unlock doors
 */
public class BattleRoom extends Room{

    // ----- obstacles classes ------
    private final River[] rivers;
    private final Wall[] walls;
    private final TreasureBox[] treasures;
    private final KeyBulletKin[] keyBulletKins;
    private int keyRequire; // key require to unlock the doors

    // ---- constructor -----

    /**
     * create a battle room that has obstacles and enemies
     * @param index is the current room index this battle room is in the dungeon
     * @param roomId is which battle room is this, a or b
     */
    public BattleRoom(int index, String roomId) {
        super(index);

        GameConfig config = getConfig();
        // name of property for positions of obstacles
        String wallKey = "wall." + roomId;
        String riverKey = "river." + roomId;
        String treasureKey = "treasurebox." + roomId;
        String enemyKey = "keyBulletKin." + roomId;

        // ----- obstacles placements -----
        Point[] wallPositions = config.getPos(wallKey);
        Point[] riverPositions = config.getPos(riverKey);
        String[] treasureInfo = config.getTreasureInfo(treasureKey);
        Point[] keyBulletKinPositions = config.getPos(enemyKey);

        // initialise the key required to open the door to number of key bullet kins
        this.keyRequire = keyBulletKinPositions.length;

        // create all the entities
        this.rivers = createRivers(riverPositions);
        this.walls = createWalls(wallPositions);
        this.treasures = createTreasures(treasureInfo);
        this.keyBulletKins = createKeyBulletKin(keyBulletKinPositions);
    }

    // ----- interactions -----

    @Override
    public Point validateMove(PlayerCharacter player, Point nextMove) {
        // 1. check parent class validation (doors)
        Point doorValidatedMove = super.validateMove(player, nextMove);

        // if parent already blocked the move, go on to next move
        if (!doorValidatedMove.equals(nextMove)) {
            return doorValidatedMove;
        }

        // 2. check wall collision
        if (temporarilyCheckWallCollision(player.getPlayer(), nextMove)) {
            return player.trySolveCollision(nextMove,
                    (x, y) -> temporarilyCheckWallCollision(player.getPlayer(), new Point(x, y)));
        }

        return nextMove;
    }

    // check if player collides with wall
    private boolean hasWallCollision(Player player) {
        for (Wall wall : walls) {
            if (wall.collidesWith(player)) {
                return true;
            }
        }
        return false;
    }

    // temporarily move player to check collision with walls
    private boolean temporarilyCheckWallCollision(Player player, Point pos) {
        Point original = player.getPosition();
        player.movePosition(pos);

        boolean collides = hasWallCollision(player);

        player.movePosition(original);
        return collides;
    }

    // ----- render ----

    @Override
    public void render() {
        super.render();

        for (Wall wall : walls) {wall.render();}
        for (River river : rivers) {river.render();}
        for (TreasureBox treasureBox: treasures) {treasureBox.render();}

        if (allDoorLocked()) {
            for (KeyBulletKin key: keyBulletKins) {key.render();}
        }
    }

    // ----- update upon interactions -----

    @Override
    public void update(PlayerCharacter player, Input input, Dungeon dungeon) {
        super.update(player, input, dungeon);
        Player playerSelf = player.getPlayer();

        // gain damage if went into river
        for (River river : rivers) {
            if (river.collidesWith(playerSelf)) {river.damagePlayer(playerSelf);}
        }

        // open treasure boxes
        if (input.isDown(Keys.K)) {
            for (TreasureBox treasure : treasures) {treasure.openBox(playerSelf);}
        }

        // defeat enemies and gain keys
        for (KeyBulletKin keyBulletKin : keyBulletKins) {
            if (keyBulletKin.defeat(player.getPlayer())) {
               keyRequire--;
               if (keyRequire == 0) {roomCleared();} // unlock all doors when got all keys
            }
        }
    }

    // ---- creating obstacles ----
    public River[] createRivers(Point[] riverPositions) {
        int numRivers = riverPositions.length;
        River[] rivers = new River[numRivers];
        for (int i = 0; i < numRivers; i++) {
            rivers[i] = new River(riverPositions[i]);
        }
        return rivers;
    }

    public Wall[] createWalls(Point[] wallPositions) {
        int numWalls = wallPositions.length;
        Wall[] walls = new Wall[numWalls];
        for (int i = 0; i < numWalls; i++) {
            walls[i] = new Wall(wallPositions[i]);
        }
        return walls;
    }

    public TreasureBox[] createTreasures(String[] treasureInfo) {
        int numTreasures = treasureInfo.length;
        TreasureBox[] treasures = new TreasureBox[numTreasures];
        for (int i = 0; i < numTreasures; i++) {
            treasures[i] = new TreasureBox(treasureInfo[i]);
        }
        return treasures;
    }

    public KeyBulletKin[] createKeyBulletKin(Point[] enemyPositions) {
        KeyBulletKin[] enemies = new KeyBulletKin[keyRequire];
        for (int i = 0; i < keyRequire; i++) {
            enemies[i] = new KeyBulletKin(enemyPositions[i]);
        }
        return enemies;
    }
}
