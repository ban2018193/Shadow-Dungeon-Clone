package rooms;

import bagel.*;
import bagel.util.Point;

import config.GameConfig;
import entities.*;
import dungeon.Dungeon;
import entities.enemies.*;
import entities.objects.*;
import entities.objects.projectiles.*;
import entities.player.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;


/**
 * Represents a battle room in the dungeon.
 *
 * A battle room contains obstacles (like rivers, walls, tables, baskets),
 * enemies, and treasure boxes. Players must defeat enemies and collect keys
 * to unlock doors to progress to other rooms.
 */
public class BattleRoom extends Room {

    // ----- obstacles classes ------
    private List<Entity> entities = new ArrayList<>();
    private List<Enemy> enemies = new ArrayList<>();
    private List<Entity> toRemoveEntities = new ArrayList<>();
    private List<Enemy> toRemoveEnemies = new ArrayList<>();

    // ---- constructor -----

    /**
     * Constructs a battle room with a given index and room identifier.
     *
     * @param index  the index of this room within the dungeon
     * @param roomId the identifier of the room (e.g., "a" or "b") used to load
     *               obstacle and enemy positions from configuration
     */
    public BattleRoom(int index, String roomId) {
        super(index);

        GameConfig config = getConfig();
        // name of property for positions of obstacles
        initSetUp(roomId, config);
    }

    // ----- interactions -----

    /**
     * Sets up all entities and enemies for the room based on configuration.
     *
     * @param roomId the identifier of the room
     * @param config the game configuration object containing positions and properties
     */
    private void initSetUp(String roomId, GameConfig config) {
        // name of property for positions of obstacles
        List<String> cKeys = new ArrayList<>();
        List<String> eKeys = new ArrayList<>();

        cKeys.add("river." + roomId);
        cKeys.add("wall." + roomId);
        cKeys.add("basket." + roomId);
        cKeys.add("table." + roomId);

        eKeys.add("keyBulletKin." + roomId);
        eKeys.add("ashenBulletKin." + roomId);
        eKeys.add("bulletKin." + roomId);

        String treasureKey = "treasurebox." + roomId;

        // ----- obstacles placements -----
        String[] treasureInfo = config.getTreasureInfo(treasureKey);
        for (String info : treasureInfo) {
            entities.add(createTreasure(info));
        }

        // initialise the key required to open the door to number of key bullet kins

        // create all the entities
        List<Function<Point, Entity>> constructors = List.of(
                River::new,
                Wall::new,
                Basket::new,
                Table::new
        );

        for (int i = 0; i < constructors.size(); i++) {
            Function<Point, Entity> constructor = constructors.get(i);
            Point[] points = config.getPos(cKeys.get(i));
            entities.addAll(createEntities(points, constructor));
        }

        enemies.add(createKeyBulletKin(config.getPos(eKeys.get(0))));
        enemies.addAll(createBulletKin(config.getPos(eKeys.get(1)), true));
        enemies.addAll(createBulletKin(config.getPos(eKeys.get(2)), false));
    }

    /**
     * Validates the next move of the player considering walls, doors, and other obstacles.
     *
     * @param player   the player character trying to move
     * @param nextMove the next position the player wants to move to
     * @return the validated next position the player can move to
     */
    @Override
    public Point validateMove(PlayerCharacter player, Point nextMove) {
        // 1. check parent class validation (doors)
        Point doorValidatedMove = super.validateMove(player, nextMove);

        // if parent already blocked the move, go on to next move
        if (!doorValidatedMove.equals(nextMove)) {
            return doorValidatedMove;
        }

        // 2. check wall collision
        if (temporarilyCheckCollision(player.getPlayer(), nextMove, entities)) {
            return player.trySolveCollision(nextMove,
                    (x, y) -> temporarilyCheckCollision(player.getPlayer(), new Point(x, y), entities));
        }

        return nextMove;
    }


    // ----- render ----

    /**
     * Renders all entities, enemies, and projectiles in this room.
     */
    @Override
    public void render() {
        super.render();

        for (Entity entity : entities) {
            entity.render();
        }

        if (allDoorLocked()) {
            for (Enemy enemy : enemies) {
                enemy.render();
            }
        }

        renderProjectiles();
    }

    // ----- update -----

    /**
     * Updates the state of the room each frame.
     * Handles projectile collisions, player interactions with entities, enemy AI,
     * and removal of inactive objects.
     *
     * @param player  the player character
     * @param input   the current input state
     * @param dungeon the dungeon this room is part of
     */
    @Override
    public void update(PlayerCharacter player, Input input, Dungeon dungeon) {
        super.update(player, input, dungeon);
        Player playerSelf = player.getPlayer();

        // projectile collide with anything (objects, enemy, or player)
        for (Projectile projectile : getProjectiles()) {
            if (!(projectile instanceof Bullet) && projectile.collidesWith(playerSelf)) {
                projectile.triggerCollisionEvent(playerSelf, playerSelf);
            }
            for (Entity entity : entities) {
                if (projectile.collidesWith(entity)) {
                    projectile.triggerCollisionEvent(entity, playerSelf);
                }
            }
            for (Enemy enemy : enemies) {
                if (projectile.collidesWith(enemy)) {
                    projectile.triggerCollisionEvent(enemy, playerSelf);
                }
            }
            projectile.deleteInactive(this);
        }

        // any objects collide with the player
        for (Entity entity : entities) {
            // trigger event when interact with player
            if (entity.collidesWith(playerSelf)) {
                entity.triggerCollisionEvent(playerSelf, playerSelf);
                entity.tryInteract(input, playerSelf);
            }
            entity.deleteInactive(this);
        }


        // defeat enemies and gain keys
        for (Enemy enemy : enemies) {
            enemy.autoPilot(this, playerSelf);
            if (enemy.collidesWith(playerSelf)) {
                enemy.triggerCollisionEvent(playerSelf, playerSelf);

            }
            enemy.deleteInactive(this);
        }

        defeatedEnemy(toRemoveEnemies.size());

        getProjectiles().removeAll(getToRemoveProj());
        enemies.removeAll(toRemoveEnemies);
        entities.removeAll(toRemoveEntities);

        if (enemies.isEmpty() && getNumOfDoors() > 0) {
            roomCleared();
        }

    }

    // ---- creating obstacles ----


    private  <T> List<T> createEntities(Point[] poss, Function<Point, T> constructor) {
        List<T> entities = new ArrayList<>();
        for (Point pos : poss) {
            entities.add(constructor.apply(pos));
        }
        return entities;
    }

    private List<Enemy> createBulletKin(Point[] poss, boolean ashen) {
        List<Enemy> enemies = new ArrayList<>();
        for (Point pos : poss) {
            enemies.add(new BulletKin(pos, ashen));
            addEnemy();
        }
        return enemies;
    }

    private KeyBulletKin createKeyBulletKin(Point[] poss) {
        List<Point> route = new ArrayList<>(Arrays.asList(poss));
        addEnemy();
        return new KeyBulletKin(route);
    }

   private TreasureBox createTreasure(String info) {
        return new TreasureBox(info);
    }

    // --- getters ----

    /** @return the list of all static entities in this room */
    public List<Entity> getEntities() {
        return entities;
    }

    /** @return the list of entities to be removed at the end of the frame */
    public List<Entity> getToRemoveEntities() {
        return toRemoveEntities;
    }

    /** @return the list of enemies to be removed at the end of the frame */
    public List<Enemy> getToRemoveEnemies() {
        return toRemoveEnemies;
    }


}



