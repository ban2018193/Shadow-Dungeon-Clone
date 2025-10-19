package entities.player;

import bagel.Font;
import bagel.util.Point;
import config.GameConfig;

/**
 * Manages all player statistics such as health, coins, keys, and weapon level
 * Render the stats
 */
public class PlayerStats {

    // ---- Stats ----

    private final GameConfig config = GameConfig.getInstance();
    private double health;
    private double coins;
    private int keys = 0;
    private int weaponLevel = 0;


    // --- Settings ----

    private String healthTitle = config.HEALTH_DISPLAY;
    private String coinsTitle = config.COIN_DISPLAY;
    private String weaponTitle = config.WEAPON_DISPLAY;
    private String keyTitle = config.KEY_DISPLAY;
    private Point healthStat = config.HEALTH_STAT_POS;
    private Point coinStat = config.COIN_STAT_POS;
    private Point weaponStat = config.WEAPON_STAT_POS;
    private Point keyStat = config.KEY_STAT_POS;
    private Font titleFont = new Font(config.FONT_PATH,
                              config.PLAYER_STATS_FONT_SIZE);


    // ----- Constructors -----

    /**
     * Creates a new PlayerStats object
     */
    public PlayerStats() {
        this.health = config.INITIAL_HEALTH;
        this.coins = 0;
    }


    // ----- Update methods -----

    /**
     * Updates the player's coin amount by a given amount
     *
     * @param amount Amount to change the coin count by (can be negative)
     */
    public void updateCoin(double amount) {
        coins += amount;
    }


    /**
     * Updates the player's health by a given amount
     *
     * @param amount Amount to change the health by (can be negative)
     */
    public void updateHealth(double amount) {
        health += amount;
    }


    /**
     * Updates the number of keys the player holds
     *
     * @param gain True to gain a key, false to lose one
     */
    public void updateKey(boolean gain) {
        if (gain) {
            keys++;
        } else {
            keys--;
        }
    }


    /**
     * Increases the player's weapon level by one
     */
    public void updateWeapon() {
        weaponLevel++;
    }


    // --- Renders ----

    /**
     * Renders the player's stats on the screen
     */
    public void renderStat() {
        String healthText = String.format(healthTitle + " %.1f", health);
        String coinText = String.format(coinsTitle + " %.1f", coins);
        String weaponText = String.format(weaponTitle + " %d", weaponLevel);
        String keyText = String.format(keyTitle + " %d", keys);

        // render stats of player
        titleFont.drawString(healthText, healthStat.x, healthStat.y);
        titleFont.drawString(coinText, coinStat.x, coinStat.y);
        titleFont.drawString(weaponText, weaponStat.x, weaponStat.y);
        titleFont.drawString(keyText, keyStat.x, keyStat.y);
    }


    // ---- Getters ----

    /**
     * Gets the player's current health
     *
     * @return the current health value
     */
    public double getHealth() {
        return health;
    }


    /**
     * Gets the player's current number of coins
     *
     * @return the current coin count
     */
    public double getCoins() {
        return coins;
    }


    /**
     * Gets the player's current number of keys
     *
     * @return the number of keys the player holds
     */
    public int getKeys() {
        return keys;
    }


    /**
     * Gets the player's current weapon level
     *
     * @return the weapon level (0 = basic, 1 = advance, 2 = elite)
     */
    public int getWeaponLevel() {
        return weaponLevel;
    }

}
