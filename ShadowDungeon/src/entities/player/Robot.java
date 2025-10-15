package entities.player;

import entities.Entity;
import entities.KeyBulletKin;

public class Robot extends Player{

    private final int BONUS_COINS = getConfig().ROBOT_EXTRA_COIN;

    // ----- constructor ----
    public Robot(Player player) {
        super(player, "res/robot_right.png");
        setPlayerL("res/robot_left.png");
        setPlayerR("res/robot_right.png");
    }

    @Override
    public void gainCoin(double amount, Entity entity) {
        if (entity instanceof KeyBulletKin) {
            updateCoins(amount + BONUS_COINS);
        }
    }

}
