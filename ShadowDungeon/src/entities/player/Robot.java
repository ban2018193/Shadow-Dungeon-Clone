package entities.player;

import entities.Entity;
import entities.enemies.BulletKin;

/**
 * Robot: a player character variant that gains bonus coins
 * when defeating BulletKin enemies.
 * Inherits all other functionality from Player.
 */
public class Robot extends Player{

    // --- settings ----
    private final int BONUS_COINS = getConfig().ROBOT_EXTRA_COIN;

    // ----- constructor ----

    /**
     * Creates a new Robot player based on an existing player.
     * Copies position, stats, and damage from the original player.
     *
     * @param player the existing player to copy data from
     */
    public Robot(Player player) {
        super(player, "res/robot_right.png");
        setPlayerL("res/robot_left.png");
        setPlayerR("res/robot_right.png");
        setPlayerStats(player.getPlayerStats());
        setDamage(player.getDamage());
        setChoseChar(true);
    }


    /**
     * Increases the player's coins.
     * If the source entity is a BulletKin, adds extra coins on top of the base amount.
     *
     * @param amount base coin amount gained
     * @param entity the entity that gives the coins
     */
    @Override
    public void gainCoin(double amount, Entity entity) {
        if (entity instanceof BulletKin) {
            getPlayerStats().updateCoin(amount + BONUS_COINS);
        } else {
            getPlayerStats().updateCoin(amount);
        }
    }

}
