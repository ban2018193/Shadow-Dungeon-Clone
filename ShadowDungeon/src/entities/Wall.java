package entities;

import bagel.util.Point;

/**
 * wall: children of entity class
 * solid obstacle, player cant move thru
 */
public class Wall extends Entity{

    // ----- constructor ----
    public Wall(Point position) {
        super(position, "res/wall.png");
    }

    // ----- interaction -----
    public boolean collidesWith(Player player) {
        return getBoundingBox().intersects(player.getBoundingBox());
    }


}
