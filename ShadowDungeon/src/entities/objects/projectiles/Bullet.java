package entities.objects.projectiles;

import bagel.util.Point;
import bagel.util.Vector2;

import entities.Entity;
import entities.player.Player;

/**
 * Represents a bullet fired by the player.
 * The bullet moves in a given direction and deals damage upon collision.
 */
public class Bullet extends Projectile {

    // ----- constructor -----

    /**
     * Creates a bullet at a specific position and direction.
     *
     * @param position starting position of the bullet
     * @param dir normalized vector representing the direction of movement
     * @param player the player who fired the bullet (used to get damage)
     */
    public Bullet(Point position, Vector2 dir, Player player) {
        super(position, dir, "res/bullet.png");
        setSpeed(getConfig().BULLET_SPEED);
        setDamage(player.getDamage());

    }

    // ---- handle interactions ----

    /**
     * Called when the bullet collides with an entity.
     * Deactivates the bullet if the entity is affected by it.
     *
     * @param entity the entity that the bullet collided with
     * @param player the player information
     */
    @Override
    public void triggerCollisionEvent(Entity entity, Player player) {
        if (entity.attackedByProjectile(this, player)) {
            deactivate();
        }
    }

}
