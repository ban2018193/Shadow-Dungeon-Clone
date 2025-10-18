package entities.objects;

import bagel.util.Point;
import entities.Entity;
import entities.objects.projectiles.*;
import entities.player.Player;

/**
 * Table is a type of object in the game that can be destroyed by bullets
 * Can block player movements and be attacked by projectiles
 */
public class Table extends Entity {

    // ----- Constructor ----

    /**
     * Creates a Table at a specific position
     *
     * @param position The position of the table in the game
     */
    public Table(Point position) {
        super(position, "res/table.png");
    }

    /**
     * Handles interactions with projectiles
     * If hit by a Bullet, the table becomes inactive (destroyed).
     *
     * @param projectile the projectile hitting the table
     * @param player the player information
     * @return true to indicate can be attacked
     */
    @Override
    public boolean attackedByProjectile(Projectile projectile, Player player) {
        if (projectile instanceof Bullet) {
            setActive(false);
        }
        return true;
    }


}
