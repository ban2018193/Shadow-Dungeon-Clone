package entities.enemies;

import bagel.util.Point;
import entities.objects.Key;
import rooms.BattleRoom;
import rooms.Room;

import java.util.ArrayList;
import java.util.List;


/**
 * key bullet kin: children of entity class
 * upon touch, get defeated and gain keys to unlock the doors
 * only render once player left doors
 */
public class KeyBulletKin extends Enemy {

    // ---- status -----
    private boolean isDefeated = false;
    private int movingSpeed = getConfig().KEY_BULLET_KIN_SPEED;
    private List<Point> route = new ArrayList<>();

    // ----- constructor -----
    public KeyBulletKin(List<Point> route) {

        super(route.get(0), "res/key_bullet_kin.png");
        setHealth(getConfig().KEY_BULLET_KIN_HEALTH);
        this.route = route;

    }

    // ------ interactions -----

    public void dropKey(BattleRoom room) {
        // implemetn later, add key into room, take positions
    }

    /**
     * move key bullet kin to the new position
     * @param towards is the destination goal
     */
    public void autoMove(Point towards) {
        Point position = getPosition();
        if (position.x < towards.x) {
            Point newPos = new Point(position.x + movingSpeed, position.y);
            movePosition(newPos);
        } else if (position.y < towards.y) {
            Point newPos = new Point(position.x, position.y + movingSpeed);
            movePosition(newPos);
        } else if (position.x > towards.x) {
            Point newPos = new Point(position.x - movingSpeed, position.y);
            movePosition(newPos);
        } else if (position.y > towards.y) {
            Point newPos = new Point(position.x, position.y - movingSpeed);
            movePosition(newPos);
        }

    }

    public void deleteInactive(Room currRoom) {
        if (!isActive() && currRoom instanceof BattleRoom room) {
            room.getToRemoveEnemies().add(this);
            room.getEntities().add(new Key(getPosition()));
        }
    }

}
