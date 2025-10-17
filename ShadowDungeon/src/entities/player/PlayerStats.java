package entities.player;

import config.GameConfig;

public class PlayerStats {

    private final GameConfig config = GameConfig.getInstance();
    private double health;
    private double coins;
    private int keys = 0;

    public PlayerStats() {
        this.health = config.INITIAL_HEALTH;
        this.coins = 0;
    }

    public PlayerStats(double health, double coins) {
        this.health = health;
        this.coins = coins;
    }

    // ---- setters / modifiers ----
    public void gainCoin(double amount) {
        coins += amount;
    }

    public void gainDamage(double damage) {
        if (health > 0) {
            health -= damage;
            if (health < 0) health = 0;
        }
    }

    public void gainKey() {
        keys++;
    }

    public void useKey() {
        keys--;
    }



    public void reset() {
        health = config.INITIAL_HEALTH;
        coins = 0;
    }

    // ---- getters ----
    public double getHealth() {
        return health;
    }

    public double getCoins() {
        return coins;
    }

    public int getKeys() {
        return keys;
    }

    // ---- optional helper ----
    public boolean isDead() {
        return health <= 0;
    }
}
