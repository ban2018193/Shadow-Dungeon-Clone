package entities;

import bagel.util.Point;
import entities.player.Player;

public class Basket extends Entity implements Blockable{

    private boolean isDestroyable = true;

    public Basket(Point position) {
        super(position, "res/basket.png");
    }

    public boolean triggerCollisionEvent(Player player) {
        // to be added
        return true;
    }

    @Override
    public void attackedByProjectile(Projectile proj) {

    }
}
