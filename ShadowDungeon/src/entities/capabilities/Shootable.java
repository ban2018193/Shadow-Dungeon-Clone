package entities.capabilities;

import bagel.util.Vector2;
import bagel.util.Point;
import rooms.Room;

/**
 * Interface for entities that can shoots projectiles
 */
public interface Shootable {

    /**
     * Calc the normalized direction vector from a spawn point to a target point
     *
     * @param target The target point to shoot at
     * @param spawn The point where the projectile is at
     * @return a normalized Vector2 pointing from spawn to target
     */
    default Vector2 findShootDir(Point target, Point spawn) {
        Vector2 dir = target.asVector().sub(spawn.asVector());
        return dir.normalised();
    }


    /**
     * Performs the shooting action in the current room
     *
     * @param currRoom The curr room
     * @param target The point the entity aims at
     */
    public void shoot(Room currRoom, Point target);

}


