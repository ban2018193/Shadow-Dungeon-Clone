package entities.enemies;

import bagel.util.Point;
import entities.Entity;
import entities.objects.projectiles.*;
import entities.player.Player;
import rooms.*;

/**
 * Abstract class for all enemy types
 * Handles basic enemy stats, collisions, and interaction with projectiles
 */
public abstract class Enemy extends Entity {

    // ---- Stats ----
    private double health;
    private int coins = 0;
    private final double damage = 0.2;


    // ---- Constructor ----

    /**
     * Creates an enemy at a specific position with its image
     *
     * @param position Starting position
     * @param imagePath Path to the enemy's image
     */
    public Enemy(Point position, String imagePath) {
        super(position, imagePath);
    }


    // ---- Handle interactions ----

    /**
     * Trigger consequences of attacked by projectile
     * Take damage if attacked by Bullet
     *
     * @param projectile The projectile that hits this enemy
     * @param player The player information
     * @return always true to indicates can be attacked
     */
    @Override
    public boolean attackedByProjectile(Projectile projectile, Player player) {
        if (projectile instanceof Bullet) {
            health -= projectile.getDamage();
            if (health <= 0) {
                setActive(false);
                player.gainCoin(coins, this);
            }
        }
        return true;
    }


    /**
     * Handles collision with another entity
     * Deals damage to player if collided with player
     *
     * @param entity The entity this enemy collides with
     * @param player The player involved in the collision
     */
    @Override
    public void triggerCollisionEvent(Entity entity, Player player) {
        if (entity instanceof Player p) {
            player.gainDamage(damage, this);
        }
    }


    // ---- Behaviours ----

    /**
     * Marks this enemy for removal from the room if inactive
     *
     * @param currRoom The room the enemy is in
     */
    @Override
    public void deleteInactive(Room currRoom) {
        if (!isActive() && currRoom instanceof BattleRoom room) {
            room.getToRemoveEnemies().add(this);
        }
    }


    /**
     * Automated behavior for enemies
     *
     * @param room Current room
     * @param player The player to interact with
     */
    public abstract void autoPilot(Room room, Player player);


    // --- Setters ----

    /**
     * Set enemy health
     *
     * @param health The heatlh of enemy
     */
    public void setHealth(double health) {
        this.health = health;
    }


    /**
     * Set the amount of coins dropped when defeated
     *
     * @param coins The amount of coins after defeated
     */
    public void setCoins(int coins) {
        this.coins = coins;
    }

}
