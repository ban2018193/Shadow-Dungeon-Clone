package entities.enemies;

import bagel.util.Point;
import java.util.ArrayList;
import java.util.List;
import entities.objects.Key;
import entities.player.Player;
import rooms.*;


/**
 * KeyBulletKin: Enemy that drops a key when defeated
 * Has a route to travel
 */
public class KeyBulletKin extends Enemy {

    // ---- Setting -----

    private int movingSpeed = getConfig().KEY_BULLET_KIN_SPEED;


    // ---- Routes ----

    private List<Point> route = new ArrayList<>();
    private int routeSize;
    private int currentDesIdx = 0;


    // ----- Constructor -----

    /**
     * Creates a KeyBulletKin enemy at the starting point of route.
     *
     * @param route List of points of location where the enemy will go thru
     */
    public KeyBulletKin(List<Point> route) {
        super(route.get(0), "res/key_bullet_kin.png");
        setHealth(getConfig().KEY_BULLET_KIN_HEALTH);
        this.route = route;
        this.routeSize = route.size();

    }


    // ------ Behaviours -----

    /**
     * Moves the enemy automatically according to the route
     *
     * @param room The current room where the enemy is
     * @param player The player information
     */
    @Override
    public void autoPilot(Room room, Player player) {
        Point currentDes = route.get(currentDesIdx);

        // Check if close enough to destination
        double dx = currentDes.x - getPosition().x;
        double dy = currentDes.y - getPosition().y;
        double distance = Math.sqrt(dx*dx + dy*dy);

        // If within movingSpeed distance, consider it "reached"
        if (distance <= movingSpeed) {
            currentDesIdx = (currentDesIdx + 1) % routeSize;
        }

        autoMove(route.get(currentDesIdx));
    }


    /**
     * Move enemy toward target point
     *
     * @param towards Destination point
     */
    public void autoMove(Point towards) {
        Point position = getPosition();

        double dx = towards.x - position.x;
        double dy = towards.y - position.y;

        // Compute distance
        double distance = Math.sqrt(dx*dx + dy*dy);

        // If already at the target, do nothing
        if (distance == 0) return;

        // Compute step scaled by movingSpeed
        double stepX = movingSpeed * dx / distance;
        double stepY = movingSpeed * dy / distance;

        // New position
        double x = position.x + stepX;
        double y = position.y + stepY;

        movePosition(new Point(x, y));
    }


    /**
     * Marks for removal from the room if inactive and
     * spawns a key at current position
     *
     * @param currRoom The current room
     */
    public void deleteInactive(Room currRoom) {
        if (!isActive() && currRoom instanceof BattleRoom room) {
            room.getToRemoveEnemies().add(this);
            room.getEntities().add(new Key(getPosition()));
        }
    }

}
