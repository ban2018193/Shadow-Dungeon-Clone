package entities.player;

import entities.Entity;
import entities.objects.River;

public class Marine extends Player{

    public Marine(Player player) {
        super(player, "res/marine_right.png");
        setPlayerL("res/marine_left.png");
        setPlayerR("res/marine_right.png");
        setChoseChar(true);
    }

    @Override
    public void gainDamage(double damage, Entity entity) {
        if (!(entity instanceof River)) {
            super.gainDamage(damage, entity);
        }
    }
}
