package entities.objects;

import bagel.util.Point;
import entities.Entity;
import entities.objects.projectiles.Projectile;
import entities.player.Player;

/**
 * Key object that the player can collect to unlock treasure box
 */
public class Key extends Entity {

    /**
     * Creates a Key at a specific position
     *
     * @param position position of the key in the game
     */
    public Key(Point position) {
        super(position, "res/key.png");
    }

    /**
     * Keys do not block the player
     *
     * @return false
     */
    @Override
    public boolean isBlockable() {
        return false;
    }

    /**
     * Keys can't be attacked
     *
     * @param proj the projectile that hit the key
     * @param player the player information
     * @return false
     */
    @Override
    public boolean attackedByProjectile(Projectile proj, Player player) {
        return false;
    }

    /**
     * Trigger consequence when an object collides with the key
     * Key becomes inactive and player gains one key if collides with player
     *
     * @param entity The entity that collides with this key
     * @param player The player information
     */
    @Override
    public void triggerCollisionEvent(Entity entity, Player player) {
        if (entity instanceof Player) {
            setActive(false);
            player.gainKey();
        }
    }

}
