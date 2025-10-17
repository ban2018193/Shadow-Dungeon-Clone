package entities.capabilities;

import bagel.Input;
import entities.Entity;
import entities.objects.projectiles.Projectile;
import entities.player.Player;
import rooms.Room;

// by default, everything can block movement of charater, and can be attacked
public interface Collidable{
    public boolean collidesWith(Player player);
    // hit smth that affect palyer
    public void triggerCollisionEvent(Entity entity, Player player);


    //return true if can be attacked, also deal with what happen to stats when get attack
    default boolean attackedByProjectile(Projectile proj, Player player) {
        return true;
    }

    default boolean isBlockable() {
        return true;
    }

    public void tryInteract(Input input, Player player);

    public void deleteInactive(Room currRoom);
}
