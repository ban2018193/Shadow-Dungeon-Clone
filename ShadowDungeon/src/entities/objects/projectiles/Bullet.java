package entities.objects.projectiles;

import bagel.util.Point;
import bagel.util.Vector2;
import entities.Entity;
import entities.enemies.Enemy;
import entities.player.Player;
import entities.player.PlayerCharacter;

public class Bullet extends Projectile {

    public Bullet(Point position, Vector2 dir, Player player) {
        super(position, dir, "res/bullet.png");
        setSpeed(getConfig().BULLET_SPEED);
        setDamage(player.getDamage());

    }

    @Override
    public void triggerCollisionEvent(Entity entity, Player player) {

        if (entity.attackedByProjectile(this, player)) {
            deactivate();
        }
    }



}
