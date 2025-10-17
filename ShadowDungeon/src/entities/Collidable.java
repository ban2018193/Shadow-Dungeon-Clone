package entities;

import bagel.Input;
import entities.player.Player;

public interface Collidable{
    public boolean collidesWith(Player player);
    public void triggerCollisionEvent(Player player);
    public void attackedByProjectile(Projectile proj);
    public boolean isAttackable();
    public boolean isBlockable();
    public void tryInteract(Input input, Player player);
}
