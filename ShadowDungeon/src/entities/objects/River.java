package entities.objects;

import bagel.util.Point;
import config.GameConfig;
import entities.Entity;
import entities.objects.projectiles.Projectile;
import entities.player.Player;


/**
 * River is an object that the player can pass thru
 * Deals damage to the player when player is inside
 */
public class River extends Entity {

    // ---- Setting ----
    private final double damage;

    // ----  Constructor ----

    /**
     * Creates a River at a specific position
     *
     * @param position the position of the river in the game
     */
    public River(Point position) {
        super(position, "res/river.png");
        this.damage = GameConfig.getInstance().RIVER_DAMAGE_PER_FRAME;

    }

    // ----- Interactions ----

    /**
     * Rivers do not block player movement
     *
     * @return false
     */
    @Override
    public boolean isBlockable() {
        return false;
    }

    /**
     * Trigger consequences when collide with entity.
     * Deal damage to the player if entity is player itself
     *
     * @param entity The entity interacting with the river
     * @param player The player taking damage
     */
    @Override
    public void triggerCollisionEvent(Entity entity, Player player) {
        if (entity instanceof Player) {
            player.gainDamage(damage, this);
        }
    }

    /**
     * Rivers can't be attacked by projectiles
     *
     * @param proj The projectile hitting the river
     * @param player The player information
     * @return false
     */
    @Override
    public boolean attackedByProjectile(Projectile proj, Player player) {
        return false;
    }
}
