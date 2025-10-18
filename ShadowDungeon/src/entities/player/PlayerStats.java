package entities.player;

import bagel.Font;
import bagel.util.Point;
import config.GameConfig;
import entities.objects.Wall;


public class PlayerStats {

    private final GameConfig config = GameConfig.getInstance();
    private double health;
    private double coins;
    private int keys = 0;
    private int weaponLevel = 0;

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

    public PlayerStats() {
        this.health = config.INITIAL_HEALTH;
        this.coins = 0;
    }

    public PlayerStats(double health, double coins) {
        this.health = health;
        this.coins = coins;
    }

    // ---- setters / modifiers ----
    public void updateCoin(double amount) {
        coins += amount;
    }

    public void updateHealth(double amount) {
        health += amount;
    }

    public void updateKey(boolean gain) {
        if (gain) {
            keys++;
        } else {
            keys--;
        }
    }

    public void updateWeapon() {
        weaponLevel++;
    }




    // render the stats entities in this dungeon
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

    public int getWeaponLevel() {
        return weaponLevel;
    }

    // ---- optional helper ----
    public boolean isDead() {
        return health <= 0;
    }
}
