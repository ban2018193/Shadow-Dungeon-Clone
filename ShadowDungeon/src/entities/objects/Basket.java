package entities.objects;

import bagel.util.Point;
import entities.Entity;
import entities.objects.projectiles.Bullet;
import entities.objects.projectiles.Projectile;
import entities.player.Player;
import entities.player.PlayerStats;

import javax.swing.table.TableRowSorter;

public class Basket extends Entity {

    private final int coins = getConfig().BASKET_COIN;

    public Basket(Point position) {
        super(position, "res/basket.png");
    }

    @Override
    public boolean attackedByProjectile(Projectile projectile, Player player) {
       if (projectile instanceof Bullet) {
           player.gainCoin(coins, this);
           setActive(false);
       }
        return true;
    }

    // --- getters ----

}
