package entities;

import bagel.util.Point;
import entities.player.Player;


/**
 * key bullet kin: children of entity class
 * upon touch, get defeated and gain keys to unlock the doors
 * only render once player left doors
 */
public class KeyBulletKin extends Entity {

    // ---- status -----
    private boolean isDefeated = false;

    // ----- constructor -----
    public KeyBulletKin(Point position) {
        super(position, "res/key_bullet_kin.png");
    }

    // ------ interactions -----

    public boolean collidesWith(Player player) {
        return getBoundingBox().intersects(player.getBoundingBox());
    }

    public boolean defeat(Player player) {
        if (isDefeated || !collidesWith(player)){
            return isDefeated;
        }
        isDefeated = true;
        return true;
    }

    // ----- render -----

    @Override
    public void render() {
        if (!isDefeated) { // only render if its not defeated
            super.render();
        }
    }
}
