package entities.Enemy;

import bagel.util.Point;
import entities.Blockable;
import entities.Entity;
import entities.Projectile;
import entities.player.Player;
import org.lwjgl.system.NativeType;

public abstract class Enemy extends Entity implements Blockable {

    private boolean isDestroyable = true;
    private boolean isDefeated = false;
    private double health;
    private int coins;
    private final double damage = 0.2;

    public Enemy(Point position, String imagePath) {
        super(position, imagePath);
    }


    @Override
    public void attackedByProjectile(Projectile proj) {
        return;
    }

    @Override
    public boolean triggerCollisionEvent(Player player) {
        player.gainDamage(damage, this);
        return false;
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

    public boolean isDestroyable() {
        return isDestroyable;
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
