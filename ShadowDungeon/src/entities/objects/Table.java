package entities.objects;

import bagel.util.Point;
import entities.Entity;
import entities.objects.projectiles.Bullet;
import entities.objects.projectiles.Projectile;
import entities.player.Player;

public class Table extends Entity {
    // ----- constructor ----

    public Table(Point position) {
        super(position, "res/table.png");
    }

    @Override
    public boolean attackedByProjectile(Projectile projectile, Player player) {
        if (projectile instanceof Bullet) {
            setActive(false);
        }
        return true;
    }


}
