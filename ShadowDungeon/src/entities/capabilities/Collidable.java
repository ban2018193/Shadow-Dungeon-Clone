package entities.capabilities;

import bagel.Input;
import entities.Entity;
import entities.objects.projectiles.Projectile;
import entities.player.Player;
import rooms.Room;

/**
 * Objects in the game that can collide with other objects
 * It can block movement, respond to projectiles,
 * and handle interactions with other objects (but not necessarily).
 */
public interface Collidable{

    /**
     * Checks if this object collides with the player
     *
     * @param player the player to check collision with
     * @return true if a collision occurs
     */
    public boolean collidesWith(Player player);


    /**
     * When collision occurs, entity do consequence actions
     *
     * @param entity the entity that collided with this entity
     * @param player the player's information
     */
    public void triggerCollisionEvent(Entity entity, Player player);


    /**
     * Determines if this object can be attacked (default is true)
     * Trigger consequences after getting attacked
     *
     * @param proj the projectile that attacked this entity
     * @param player the player's information
     * @return true if this object is blockable
     */
    default boolean attackedByProjectile(Projectile proj, Player player) {
        return true;
    }


    /**
     * Determines if this object blocks player movement (default is true)
     *
     * @return true if this object is blockable
     */
    default boolean isBlockable() {
        return true;
    }


    /**
     * Handles player input interaction attempts with this object
     *
     * @param input the current input
     * @param player the player interacting
     */
    public void tryInteract(Input input, Player player);


    /**
     * Add object that are not active into a buffer list in room to be deleted
     *
     * @param currRoom the room this object is in
     */
    public void deleteInactive(Room currRoom);
}
