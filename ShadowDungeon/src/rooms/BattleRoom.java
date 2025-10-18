package rooms;

import bagel.*;
import bagel.util.Point;

import config.GameConfig;
import entities.*;
import dungeon.Dungeon;
import entities.enemies.*;
import entities.capabilities.Collidable;
import entities.objects.*;
import entities.objects.projectiles.Bullet;
import entities.objects.projectiles.Projectile;
import entities.player.*;
import rooms.objects.Door;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;


/**
 * battle room: child of room entity
 * has obstacles and enemies
 * need get keys to unlock doors
 */
public class BattleRoom extends Room {

    // ----- obstacles classes ------
    private List<Entity> entities = new ArrayList<>();
    private List<Enemy> enemies = new ArrayList<>();
    private List<Entity> toRemoveEntities = new ArrayList<>();
    private List<Enemy> toRemoveEnemies = new ArrayList<>();

    // ---- constructor -----

    /**
     * create a battle room that has obstacles and enemies
     *
     * @param index  is the current room index this battle room is in the dungeon
     * @param roomId is which battle room is this, a or b
     */
    public BattleRoom(int index, String roomId) {
        super(index);

        GameConfig config = getConfig();
        // name of property for positions of obstacles
        initSetUp(roomId, config);
    }

    // ----- interactions -----

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

    // ----- update upon interactions -----

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

        if (getEnemies().isEmpty() && getNumOfDoors() > 0) {
            roomCleared();
        }



    }

    // ---- creating obstacles ----
    public <T> List<T> createEntities(Point[] poss, Function<Point, T> constructor) {
        List<T> entities = new ArrayList<>();
        for (Point pos : poss) {
            entities.add(constructor.apply(pos));
        }
        return entities;
    }

    public List<Enemy> createBulletKin(Point[] poss, boolean ashen) {
        List<Enemy> enemies = new ArrayList<>();
        for (Point pos : poss) {
            enemies.add(new BulletKin(pos, ashen));
            addEnemy();
        }
        return enemies;
    }

    public KeyBulletKin createKeyBulletKin(Point[] poss) {
        List<Point> route = new ArrayList<>(Arrays.asList(poss));
        addEnemy();
        return new KeyBulletKin(route);
    }

    public TreasureBox createTreasure(String info) {
        return new TreasureBox(info);
    }

    // --- getters ----


    public List<Entity> getEntities() {
        return entities;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<Entity> getToRemoveEntities() {
        return toRemoveEntities;
    }

    public List<Enemy> getToRemoveEnemies() {
        return toRemoveEnemies;
    }


}



