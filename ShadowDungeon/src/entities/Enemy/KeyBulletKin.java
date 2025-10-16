package entities.Enemy;

import bagel.util.Point;
import entities.Entity;
import entities.player.Player;
import rooms.BattleRoom;


/**
 * key bullet kin: children of entity class
 * upon touch, get defeated and gain keys to unlock the doors
 * only render once player left doors
 */
public class KeyBulletKin extends Enemy {

    // ---- status -----
    private boolean isDefeated = false;
    private int movingSpeed = getConfig().KEY_BULLET_KIN_SPEED;

    // ----- constructor -----
    public KeyBulletKin(Point position) {

        super(position, "res/key_bullet_kin.png");
        setHealth(getConfig().KEY_BULLET_KIN_HEALTH);

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

    @Override
    public boolean isDefeated() {
        return isDefeated;
    }

}
