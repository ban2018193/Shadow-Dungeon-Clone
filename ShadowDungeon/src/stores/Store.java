package stores;

import bagel.*;
import bagel.util.Point;
import config.GameConfig;
import entities.player.*;


/**
 * A store where players can purchase health or upgrade weapons
 * The store can be opened or closed by pressing space
 * Pause other entities updates when being opened
 */
public class Store {

    // ----- Fields -----

    private boolean isOpened = false;
    private GameConfig config = GameConfig.getInstance();
    private final int MAX_W_LVL = 2;


    // ----- Constants -----

    private final int ADVANCE_W = 1;
    private final int ADVANCE_D;
    private final int ELITE_W = 2;
    private final int ELITE_D;
    private final int HEALTH_COST;
    private final int WEAPON_COST;
    private final int HEALTH_RESTORE;
    private Image image = new Image("res/store.png");
    private Point imageXY = config.STORE_POS;


    // ---- Constructor ----

    /**
     * Creates a Store object, initializing costs and bonuses from the game config
     */
    public Store() {
        ADVANCE_D = config.WEAPON_ADVANCE_DAMAGE;
        ELITE_D = config.WEAPON_ELITE_DAMAGE;
        HEALTH_COST = config.HEALTH_PURCHASE;
        WEAPON_COST = config.WEAPON_PURCHASE;
        HEALTH_RESTORE = config.HEALTH_BONUS;

    }


    // ----- Public methods -----

    /**
     * Opens or closes the store when player press space
     * While open, can purchase health or upgrading weapons, or reset
     *
     * @param input  the player's input
     * @param player the player interacting with the store
     * @return true if the store is currently opened
     */
    public boolean openStore(Input input, Player player) {
        if (input.wasPressed(Keys.SPACE)) {
            isOpened = !isOpened;
        }

        if (isOpened) {
            actions(input, player);
            render();
        }

        return isOpened;
    }


    // The actions made by the player through input on store
    private void actions(Input input, Player player) {
        if (input.wasPressed(Keys.E)) {
            purchaseHealth(player);
        } else if (input.wasPressed(Keys.L)) {
            upgradeWeapon(player);
        } else if (input.wasPressed(Keys.P)) {
            app.ShadowDungeon.restart();
        }
    }


    // ----- Private helper methods -----

    private void upgradeWeapon(Player player) {
        PlayerStats playerStats = player.getPlayerStats();
        if (playerStats.getWeaponLevel() < MAX_W_LVL && player.spendCoin(WEAPON_COST)) {
            playerStats.updateWeapon();

            // Update weapon damage according to level
            if (playerStats.getWeaponLevel() == ELITE_W) {
                player.setDamage(ELITE_D);
            } else if (playerStats.getWeaponLevel() == ADVANCE_W) {
                player.setDamage(ADVANCE_D);
            }
        }
    }

    private void purchaseHealth(Player player) {
        if (player.spendCoin(HEALTH_COST)) {
            player.gainHealth(HEALTH_RESTORE);
        }
    }

    private void render() {
        image.draw(imageXY.x, imageXY.y);
    }

}

