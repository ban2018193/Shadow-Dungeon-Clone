package entities.objects;

import bagel.util.Point;
import config.GameConfig;
import entities.Entity;
import entities.objects.projectiles.Projectile;
import entities.player.Player;


/**
 * river: children of entity
 * a form of obstacles, but can pass thru
 * damages player each frame player is in side
 */
public class River extends Entity {

    // ---- setting ----
    private final double damage;

    // ----  constructor ----
    public River(Point position) {
        super(position, "res/river.png");
        this.damage = GameConfig.getInstance().RIVER_DAMAGE_PER_FRAME;

    }

    // ----- interactions ----

    @Override
    public boolean isBlockable() {
        return false;
    }

    @Override
    public void triggerCollisionEvent(Entity entity, Player player) {
        if (entity instanceof Player) {
            player.gainDamage(damage, this);
        }
    }

}
