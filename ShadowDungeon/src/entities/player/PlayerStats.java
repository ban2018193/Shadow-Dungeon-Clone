package entities.player;

import bagel.Font;
import bagel.util.Point;
import config.GameConfig;

/**
 * Manages all player statistics such as health, coins, keys, and weapon level.
 * Responsible for updating player attributes and rendering the stats display
 * on the game screen.
 */
public class PlayerStats {

    // ---- stats ----
    private final GameConfig config = GameConfig.getInstance();
    private double health;
    private double coins;
    private int keys = 0;
    private int weaponLevel = 0;

    // --- settings ----
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
     * Creates a new PlayerStats object with default initial values.
     */
    public PlayerStats() {
        this.health = config.INITIAL_HEALTH;
        this.coins = 0;
    }

    /**
     * Creates a new PlayerStats object with specified initial health and coins.
     *
     * @param health Initial player health
     * @param coins  Initial coin count
     */
    public PlayerStats(double health, double coins) {
        this.health = health;
        this.coins = coins;
    }

    // ----- update methods -----

    /**
     * Updates the player's coin balance by a given amount.
     *
     * @param amount Amount to change the coin count by (can be negative)
     */
    public void updateCoin(double amount) {
        coins += amount;
    }

    /**
     * Updates the player's health by a given amount.
     *
     * @param amount Amount to change the health by (can be negative)
     */
    public void updateHealth(double amount) {
        health += amount;
    }

    /**
     * Updates the number of keys the player holds.
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
     * Increases the player's weapon level by one.
     */
    public void updateWeapon() {
        weaponLevel++;
    }

    /**
     * Resets player stats to default starting values.
     */
    public void reset() {
        health = config.INITIAL_HEALTH;
        coins = 0;
    }

    // --- renders ----

    /**
     * Renders the player's statistics on the screen.
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


    // ---- getters ----

    /**
     * @return Current player health
     */
    public double getHealth() {
        return health;
    }

    /**
     * @return Current player health
     */
    public double getCoins() {
        return coins;
    }

    /**
     * @return Current player health
     */
    public int getKeys() {
        return keys;
    }

    /**
     * @return Current player health
     */
    public int getWeaponLevel() {
        return weaponLevel;
    }

}
