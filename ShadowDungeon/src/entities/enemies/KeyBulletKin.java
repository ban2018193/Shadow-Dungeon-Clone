package entities.enemies;

import bagel.util.Point;
import entities.objects.Key;
import entities.player.Player;
import rooms.BattleRoom;
import rooms.Room;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * key bullet kin: children of entity class
 * upon touch, get defeated and gain keys to unlock the doors
 * only render once player left doors
 */
public class KeyBulletKin extends Enemy {

    // ---- status -----
    private int movingSpeed = getConfig().KEY_BULLET_KIN_SPEED;
    private List<Point> route = new ArrayList<>();
    private int routeSize;
    private int currentDesIdx = 0;

    // ----- constructor -----
    public KeyBulletKin(List<Point> route) {

        super(route.get(0), "res/key_bullet_kin.png");
        setHealth(getConfig().KEY_BULLET_KIN_HEALTH);
        this.route = route;
        this.routeSize = route.size();

    }

    // ------ interactions -----

    @Override
    public void autoPilot(Room room, Player player) {
       Point currentDes = route.get(currentDesIdx);
       if (currentDes.equals(getPosition())) {
           currentDesIdx = (currentDesIdx + 1) % routeSize;
       }
       autoMove(route.get(currentDesIdx));
    }

    /**
     * move key bullet kin to the new position
     * @param towards is the destination goal
     */
    public void autoMove(Point towards) {
        Point position = getPosition();

        double dx = towards.x - position.x;
        double dy = towards.y - position.y;

        // compute distance
        double distance = Math.sqrt(dx*dx + dy*dy);

        // if already at the target, do nothing
        if (distance == 0) return;

        // compute step scaled by movingSpeed
        double stepX = movingSpeed * dx / distance;
        double stepY = movingSpeed * dy / distance;

        // new position
        double x = position.x + stepX;
        double y = position.y + stepY;

        // prevent overshooting

        if (Math.abs(x - towards.x) < movingSpeed)  x = towards.x;
        if (Math.abs(y - towards.y) < movingSpeed)  y = towards.y;

        movePosition(new Point(x, y));
    }


    public void deleteInactive(Room currRoom) {
        if (!isActive() && currRoom instanceof BattleRoom room) {
            room.getToRemoveEnemies().add(this);
            room.getEntities().add(new Key(getPosition()));
        }
    }

}
