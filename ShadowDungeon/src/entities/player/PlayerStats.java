package entities.player;

import config.GameConfig;

public class PlayerStats {

    private static final GameConfig config = GameConfig.getInstance();
    private static double health = config.INITIAL_HEALTH;
    private static double coins = 0;

    private PlayerStats() {}

    public static void gainCoin(double amount) {
        coins += amount;
    }

    public static void gainDamage(double damage) {
        if (health > 0) {
            health -= damage;
        }
    }

    public static void reset() {
        health = config.INITIAL_HEALTH;
        coins = 0;
    }

    public static double getHealth() {
        return health;
    }

    public static double getCoins() {
        return coins;
    }
}
