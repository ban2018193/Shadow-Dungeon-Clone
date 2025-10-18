package entities.player;

import entities.Entity;
import entities.enemies.BulletKin;

public class Robot extends Player{

    private final int BONUS_COINS = getConfig().ROBOT_EXTRA_COIN;

    // ----- constructor ----
    public Robot(Player player) {
        super(player, "res/robot_right.png");
        setPlayerL("res/robot_left.png");
        setPlayerR("res/robot_right.png");
        setPlayerStats(player.getPlayerStats());
        setDamage(player.getDamage());
        setChoseChar(true);
    }

    @Override
    public void gainCoin(double amount, Entity entity) {
        if (entity instanceof BulletKin) {
            getPlayerStats().updateCoin(amount + BONUS_COINS);
        } else {
            getPlayerStats().updateCoin(amount);
        }
    }

}
