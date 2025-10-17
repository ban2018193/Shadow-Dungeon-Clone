package entities.capabilities;

import bagel.util.Vector2;
import bagel.util.Point;
import rooms.Room;

public interface Shootable {

    default Vector2 findShootDir(Point target, Point spawn) {
        Vector2 dir = target.asVector().sub(spawn.asVector());
        return dir.normalised();
    }

    public void shoot(Room currRoom, Point target);
}


