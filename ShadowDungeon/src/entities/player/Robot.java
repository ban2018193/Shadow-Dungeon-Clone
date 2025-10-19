package entities.player;

import entities.Entity;
import entities.enemies.BulletKin;

/**
 * Robot inherits all player behaviors, but gain extra coin
 * when defeating BulletKin enemies
 */
public class Robot extends Player{

    // --- Settings ----
    private final int BONUS_COINS = getConfig().ROBOT_EXTRA_COIN;

    // ----- Constructor ----

    /**
     * Creates a new Robot player based on current player stats
     *
     * @param player the player to copy data from
     */
    public Robot(Player player) {
        super(player, "res/robot_right.png");
        setPlayerL("res/robot_left.png");
        setPlayerR("res/robot_right.png");
        setPlayerStats(player.getPlayerStats());
        setDamage(player.getDamage());
        setChoseChar(true);
    }

    // ---- Methods ----

    /**
     * Increases coins
     * If the source entity is a BulletKin, adds extra coins
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
