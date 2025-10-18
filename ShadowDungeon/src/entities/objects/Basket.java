package entities.objects;

import bagel.util.Point;
import entities.Entity;
import entities.objects.projectiles.*;
import entities.player.Player;

/**
 * Basket object in the game that gives coins to the player when hit by a bullet
 * Can block player movement and be attacked by projectiles
 */
public class Basket extends Entity {

    /**
     * Creates a Basket at a specific position
     *
     * @param position position of the basket in the game
     */
    private final int coins = getConfig().BASKET_COIN;

    public Basket(Point position) {
        super(position, "res/basket.png");
    }

    /**
     * Trigger consequences when hit by a projectile
     * Only reacts to Bullet projectiles
     *
     * @param projectile The projectile that hit the basket
     * @param player The player information
     * @return true to indicate can be attacked
     */
    @Override
    public boolean attackedByProjectile(Projectile projectile, Player player) {
       if (projectile instanceof Bullet) {
           player.gainCoin(coins, this);
           setActive(false);
       }
        return true;
    }

}
