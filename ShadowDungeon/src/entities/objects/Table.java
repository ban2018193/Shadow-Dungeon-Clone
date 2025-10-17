package entities.objects;

import bagel.util.Point;
import entities.Entity;
import entities.objects.projectiles.Projectile;

public class Table extends Entity {
    // ----- constructor ----
    public Table(Point position) {
        super(position, "res/table.png");
    }

    public void attackedByProjectile(Projectile proj) {
        // to be destroyed
    }


}
