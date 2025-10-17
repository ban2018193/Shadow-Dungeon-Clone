package entities.enemies;

import bagel.util.Point;
import entities.Entity;
import entities.objects.projectiles.Projectile;
import entities.player.Player;

public abstract class Enemy extends Entity {

    private boolean isDefeated = false;
    private double health;
    private int coins;
    private final double damage = 0.2;

    public Enemy(Point position, String imagePath) {
        super(position, imagePath);
    }


    @Override
    public void attackedByProjectile(Projectile proj) {
        // --- to be implement
        return;
    }

    @Override
    public void triggerCollisionEvent(Entity entity) {
        if (entity instanceof Player player) {
            player.gainDamage(damage, this);
        }
    }

    public void takeDamage() {

    }

    @Override
    public void render() {
        if (!isDefeated) {
            super.render();
        }
    }

    // ---- getters ---

    @Override
    public boolean isAttackable(Projectile projectile) {
        return true;
    }

    public boolean isDefeated() {
        return isDefeated;
    }

    // --- setters ----

    public void setHealth(double health) {
        this.health = health;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }


}
