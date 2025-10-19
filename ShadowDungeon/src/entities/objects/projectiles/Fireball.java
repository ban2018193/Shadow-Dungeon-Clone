package entities.objects.projectiles;

import bagel.util.Point;
import bagel.util.Vector2;
import entities.Entity;
import entities.enemies.*;
import entities.player.Player;

/**
 * Fireball: a projectile fired by a BulletKin
 * Moves in a given direction and deals damage to the player
 */
public class Fireball extends Projectile{

    // ---- Constructor ----

    /**
     * Creates a fireball at a specific position and direction
     *
     * @param position starting position of the fireball
     * @param dir normalized vector of the direction of movement
     * @param damage the damage this fireball contains
     */
    public Fireball(Point position, Vector2 dir, double damage) {
        super(position, dir, "res/fireball.png");
        setSpeed(getConfig().FIREBALL_SPEED);
        setDamage(damage);
    }


    // ---- Handle interactions ----

    /**
     * Trigger consequences after collide with an entity
     * If the entity is the player, player takes damage
     * If the entity is not an enemy and can be attacked, deactivate the fireball
     *
     * @param entity the entity that the fireball collided with
     * @param player the player in the game
     */
    @Override
    public void triggerCollisionEvent(Entity entity, Player player) {
        if (entity instanceof Player) { // Player lost health if collides with fireball
            player.gainDamage(getDamage(), this);
            deactivate();
        } else if (entity.attackedByProjectile(this, player) && !(entity instanceof Enemy)) {
            deactivate();
        }
    }

}
