package entities.enemies;

import bagel.util.Point;
import entities.Entity;
import entities.objects.projectiles.Bullet;
import entities.objects.projectiles.Projectile;
import entities.player.Player;
import rooms.BattleRoom;
import rooms.Room;

public abstract class Enemy extends Entity {

    private double health;
    private int coins;
    private final double damage = 0.2;

    public Enemy(Point position, String imagePath) {
        super(position, imagePath);
    }


    @Override
    public boolean attackedByProjectile(Projectile projectile, Player player) {
        if (projectile instanceof Bullet) {
            health -= projectile.getDamage();
        }
        return true;
    }

    @Override
    public void triggerCollisionEvent(Entity entity) {
        if (entity instanceof Player player) {
            player.gainDamage(damage, this);
        }
    }

    @Override
    public void deleteInactive(Room currRoom) {
        if (!isActive() && currRoom instanceof BattleRoom room) {
            room.getToRemoveEnemies().add(this);
        }
    }


    // ---- getters ---

    // --- setters ----

    public void setHealth(double health) {
        this.health = health;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }


}
