package entities.player;

import entities.Entity;
import entities.objects.River;


/**
 * The Marine inherits all player behaviors but is immune to damage from rivers
 */
public class Marine extends Player{

    /**
     * Creates a new Marine character based on a current player stats
     *
     * @param player The current player to copy stats from
     */
    public Marine(Player player) {
        super(player, "res/marine_right.png");
        setPlayerL("res/marine_left.png");
        setPlayerR("res/marine_right.png");
        setPlayerStats(player.getPlayerStats());
        setDamage(player.getDamage());
        setChoseChar(true);
    }

    /**
     * Applies damage to the Marine, but immune to water
     *
     * @param damage The amount of damage to apply
     * @param entity The entity that caused the damage
     */
    @Override
    public void gainDamage(double damage, Entity entity) {
        if (!(entity instanceof River)) {
            super.gainDamage(damage, entity);
        }


    }
}
