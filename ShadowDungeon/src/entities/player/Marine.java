package entities.player;

import entities.Entity;
import entities.River;

public class Marine extends Player{

    public Marine(Player player) {
        super(player, "res/marine_right.png");
        setPlayerL("res/marine_left.png");
        setPlayerR("res/marine_right.png");
    }

    @Override
    public void gainDamage(double damage, Entity entity) {
        if (!(entity instanceof River)) {
            super.gainDamage(damage, entity);
        }
    }
}
