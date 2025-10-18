package entities.objects.projectiles;

import bagel.util.Point;
import bagel.util.Vector2;
import entities.Entity;
import entities.enemies.BulletKin;
import entities.enemies.Enemy;
import entities.player.Player;

public class Fireball extends Projectile{

    public Fireball(Point position, Vector2 dir, BulletKin bulletKin) {
        super(position, dir, "res/fireball.png");
        setSpeed(getConfig().FIREBALL_SPEED);
        setDamage(bulletKin.getDamage());

    }


    @Override
    public void triggerCollisionEvent(Entity entity, Player player) {
        if (entity instanceof Player) {
            player.gainDamage(getDamage(), this);
            deactivate();
        } else if (entity.attackedByProjectile(this, player) && !(entity instanceof Enemy)) {
            deactivate();
        }

    }

}
