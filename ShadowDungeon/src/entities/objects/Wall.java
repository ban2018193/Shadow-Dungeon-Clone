package entities.objects;

import bagel.util.Point;
import entities.Entity;

/**
 * Wall is an object that blocks player movement and can be attacked by projectiles
 */
public class Wall extends Entity {

    /**
     * Creates a wall at the specified position
     *
     * @param position The location of the wall
     */
    public Wall(Point position) {
        super(position, "res/wall.png");
    }


}
