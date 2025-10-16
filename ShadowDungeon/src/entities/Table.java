package entities;

import bagel.util.Point;
import entities.player.Player;

public class Table extends Entity implements Blockable{

    private boolean isDestsroyable = true;
    // ----- constructor ----
    public Table(Point position) {
        super(position, "res/table.png");
    }

    public void attackedByProjectile(Projectile proj) {
        // to be destroyed
    }

    public boolean triggerCollisionEvent(Player player) {
        // to be added
        return true;
    }


}
