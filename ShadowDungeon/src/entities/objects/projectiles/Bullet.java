package entities.objects.projectiles;

import bagel.util.Point;
import bagel.util.Vector2;
import entities.Entity;
import entities.player.Player;

/**
 * Bullet: a projectile fired by the player
 * The bullet moves in a given direction and deals damage
 */
public class Bullet extends Projectile {

    // ----- Constructor -----

    /**
     * Creates a bullet at a specific position and direction
     *
     * @param position Starting position of the bullet
     * @param dir Normalized vector of the direction of movement
     * @param player the player who fired the bullet
     */
    public Bullet(Point position, Vector2 dir, Player player) {
        super(position, dir, "res/bullet.png");
        setSpeed(getConfig().BULLET_SPEED);
        setDamage(player.getDamage());

    }


    // ---- Handle interactions ----

    /**
     * Trigger consequneces of Bullet hitting on other entity
     * Bullet will disperse when hit certain objects
     *
     * @param entity The entity that the bullet collided with
     * @param player the player information
     */
    @Override
    public void triggerCollisionEvent(Entity entity, Player player) {
        if (entity.attackedByProjectile(this, player)) {
            deactivate();
        }
    }

}
