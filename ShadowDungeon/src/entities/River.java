package entities;

import bagel.util.Point;
import config.GameConfig;
import entities.player.Player;


/**
 * river: children of entity
 * a form of obstacles, but can pass thru
 * damages player each frame player is in side
 */
public class River extends Entity{

    // ---- setting ----
    private final double damage;

    // ----  constructor ----
    public River(Point position) {
        super(position, "res/river.png");
        this.damage = GameConfig.getInstance().RIVER_DAMAGE_PER_FRAME;

    }

    // ----- interactions ----
    public boolean collidesWith(Player player) {
        return getBoundingBox().intersects(player.getBoundingBox());
    }

    public void damagePlayer(Player player) {
        player.gainDamage(damage, this);
    }
}
