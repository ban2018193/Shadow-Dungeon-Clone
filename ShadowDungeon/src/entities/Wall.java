package entities;

import bagel.util.Point;
import entities.player.Player;

/**
 * wall: children of entity class
 * solid obstacle, player cant move thru
 */
public class Wall extends Entity{

    // ----- constructor ----
    public Wall(Point position) {
        super(position, "res/wall.png");
    }


}
