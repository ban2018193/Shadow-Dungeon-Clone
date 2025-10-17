package entities.objects;

import bagel.util.Point;
import entities.Entity;
import entities.objects.projectiles.Projectile;
import entities.player.Player;

public class Basket extends Entity {

    public Basket(Point position) {
        super(position, "res/basket.png");
    }

    public void triggerCollisionEvent(Player player) {
        // to be added
        return;
    }

    @Override
    public void attackedByProjectile(Projectile proj) {
        // to be added--. use isattackable to trigger this one,
        return;
    }

}
