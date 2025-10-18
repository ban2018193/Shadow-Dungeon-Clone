package entities.player;


import bagel.*;
import bagel.util.Point;
import bagel.util.Vector2;
import config.GameConfig;
import entities.Entity;
import entities.capabilities.Shootable;
import entities.objects.projectiles.*;
import rooms.Room;


/**
 * The Player can move, shoot, collect coins and keys, and take or heal damage.
 * The player’s state (health, coins, weapon levels, and keys) is tracked using PlayerStats.
 */
public class Player extends Entity implements Shootable {

    // ------ Settings -----
    private double damage = getConfig().FIREBALL_DAMAGE;
    private double firingRate = getConfig().BULLET_FREQ;

    // ---- Track stats
    private boolean choseChar = false;
    private int framesSinceLast = 0;
    private PlayerStats playerStats = new PlayerStats();

    // ----Image sources ----
    private Image playerR = new Image("res/player_right.png");
    private Image playerL = new Image("res/player_left.png");

    // ------ Constructors -----

    /**
     * Creates a default player starting at the initial position defined in GameConfig
     */
    public Player() {
        super(GameConfig.getInstance().PLAYER_START_POS, "res/player_right.png");
        GameConfig config = GameConfig.getInstance();
    }

    /**
     * Creates a new player by copying an existing player’s state.
     *
     * @param player    The player to copy position and state from
     * @param imagePath The path to the new player's image
     */
    public Player(Player player, String imagePath) {
        super(player.getPosition(), imagePath);
    }

    // ---- Shooting ----

    /**
     * Fires a Bullet toward the given target if not in cooldown
     *
     * @param currRoom The current room where the Bullet should be spawned
     * @param target   The target position the Bullet is fired toward
     */
    @Override
    public void shoot(Room currRoom, Point target) {
        if (!hasChoseChar()) {
            return;
        }

        // Check if still in cooldown
        if (framesSinceLast < firingRate) {
            return;
        }
        framesSinceLast = 0;

        Vector2 shootDir = findShootDir(target, this.getPosition());
        Projectile proj = new Bullet(this.getPosition(), shootDir, this);
        currRoom.getProjectiles().add(proj);

    }


    /**
     * Updates cooldown for firing bullets
     */
    public void updateFramesSinceLast() {
       framesSinceLast++;
    }

    // ----- Stat management -----

    /**
     * Increases the player’s coins
     *
     * @param amount The number of coins gained
     * @param entity The source entity granting the coins
     */
    public void gainCoin(double amount, Entity entity) {
        playerStats.updateCoin(amount);
    }

    /**
     * Attempts to spend coins
     *
     * @param amount The number of coins to spend
     * @return true if successful, false if not enough coins
     */
    public boolean spendCoin(double amount) {
        if (playerStats.getCoins() >= amount) {
            playerStats.updateCoin(-amount);
            return true;
        }
        return false;
    }

    /**
     * Applies damage to the player
     *
     * @param damage The damage deal to the player
     * @param entity The entity that caused the damage
     */
    public void gainDamage(double damage, Entity entity) {
        if (playerStats.getHealth() > 0) {
            playerStats.updateHealth(-damage);
        }
    }


    /**
     * Heals the player
     *
     * @param amount Amount of health restored
     */
    public void gainHealth(double amount) {
        playerStats.updateHealth(amount);

    }


    /**
     * Player obtains a key
     */
    public void gainKey() {
        playerStats.updateKey(true);
    }

    /**
     * Consumes 1 key if available
     *
     * @return true if a key was successfully used, else false
     */
    public boolean useKey() {
        if (playerStats.getKeys() > 0) {
            playerStats.updateKey(false);
            return true;
        }
        return false;

    }

    // --- Getters ----

    /**
     * Returns the player's current stats, including health, coins, and keys
     *
     * @return the current PlayerStats object
     */
    public PlayerStats getPlayerStats() {
        return playerStats;
    }

    /**
     * Get the right player image
     *
     * @return the Image the player facing right
     */
    public Image getPlayerR() {
        return playerR;
    }

    /**
     * Get the left player image
     *
     * @return the Image the player facing left
     */
    public Image getPlayerL() {
        return playerL;
    }

    /**
     * Check if the player has already chosen a character
     *
     * @return true if a character has been chosen, else false
     */
    public boolean hasChoseChar() {
        return choseChar;
    }

    /**
     * Get the damage player can deal
     *
     * @return the damage player can deal
     */
    public double getDamage() {
        return damage;
    }


    // ---- Setters ----

    /**
     * Set the right player image
     * @param path The file path to the image
     */
    public void setPlayerR(String path) {
        this.playerR = new Image(path);
    }

    /**
     * Set the left player image
     * @param path The file path to the image
     */
    public void setPlayerL(String path) {
        this.playerL = new Image(path);
    }

    /**
     * Set whether the player has chosen a character
     * @param choseChar true if a character has been chosen, else false
     */
    public void setChoseChar(boolean choseChar) {
        this.choseChar = choseChar;
    }

    /**
     * Copy PlayerStats from player into new character
     *
     * @param playerStats The new player stats to set
     */
    public void setPlayerStats(PlayerStats playerStats) {
        this.playerStats = playerStats;
    }

    /**
     * Updates the player's damage
     *
     * @param damage the new damage value
     */
    public void setDamage(double damage) {
        this.damage = damage;
    }

}
