package entities;

import entities.player.Player;

public interface Collidable{
    public boolean collidesWith(Player player);
    public boolean triggerCollisionEvent(Player player);
}
