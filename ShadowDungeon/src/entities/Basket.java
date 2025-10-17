package entities;

import bagel.util.Point;
import entities.player.Player;

public class Basket extends Entity{

    public Basket(Point position) {
        super(position, "res/basket.png");
    }

    public void triggerCollisionEvent(Player player) {
        // to be added
        return;
    }

    @Override
    public void attackedByProjectile(Projectile proj) {

    }




}
