package entities.objects;

import bagel.util.Point;
import entities.Entity;
import entities.objects.projectiles.Bullet;
import entities.objects.projectiles.Projectile;
import entities.player.Player;

public class Key extends Entity {

    public Key(Point position) {
        super(position, "res/key.png");
    }

    @Override
    public boolean isBlockable() {
        return false;
    }

    @Override
    public boolean attackedByProjectile(Projectile proj, Player player) {
        return false;
    }

    @Override
    public void triggerCollisionEvent(Entity entity, Player player) {
        if (entity instanceof Player) {
            setActive(false);
            player.gainKey();
        }
    }
}
