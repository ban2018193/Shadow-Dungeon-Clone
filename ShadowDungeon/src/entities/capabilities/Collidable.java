package entities.capabilities;

import bagel.Input;
import entities.Entity;
import entities.objects.projectiles.Projectile;
import entities.player.Player;

public interface Collidable{
    public boolean collidesWith(Player player);
    public void triggerCollisionEvent(Entity entity);
    public void attackedByProjectile(Projectile proj);

    // can block bullets
    default boolean isAttackable(Projectile projectile) {
        return true;
    }

    default boolean isBlockable() {
        return true;
    }

    public void tryInteract(Input input, Player player);
}
