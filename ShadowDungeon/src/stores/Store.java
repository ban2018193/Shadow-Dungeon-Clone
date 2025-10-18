package stores;

import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Point;
import config.GameConfig;
import dungeon.Dungeon;
import entities.player.Player;
import entities.player.PlayerStats;

import javax.print.attribute.standard.MediaSize;


public class Store {

    // ----- fields -----
    private boolean isOpened = false;
    private GameConfig config = GameConfig.getInstance();
    private final int MAX_W_LVL = 2;

    // ----- constants -----
    private final int ADVANCE_W = 1;
    private final int ADVANCE_D;    // cost to upgrade weapon
    private final int ELITE_W = 2;
    private final int ELITE_D; // cost for elite weapon
    private final int BONUS_H;      // bonus health restored
    private final int HEALTH_COST;   // cost to restore health
    private final int WEAPON_COST;
    private final int HEALTH_RESTORE;// cost per weapon upgrade

    private Image image = new Image("res/store.png");
    private Point imageXY = config.STORE_POS;

    public Store() {
        ADVANCE_D = config.WEAPON_ADVANCE_DAMAGE;
        ELITE_D = config.WEAPON_ELITE_DAMAGE;
        BONUS_H = config.HEALTH_BONUS;
        HEALTH_COST = config.HEALTH_PURCHASE;
        WEAPON_COST = config.WEAPON_PURCHASE;
        HEALTH_RESTORE = config.HEALTH_BONUS;

    }


    // ----- public methods -----

    /**
     * Opens the store if input triggers it. stop other movement when returns true
     *
     * @param input the player's input
     * @return true if store is opened
     */
    public boolean openStore(Input input, Player player) {
        // For example: check if a specific key is pressed to open store
        if (input.wasPressed(Keys.SPACE)) {
            isOpened = !isOpened;
        }

        if (isOpened) {
            actions(input, player);
            render();
        }

        return isOpened;
    }

    private void actions(Input input, Player player) {
        if (input.wasPressed(Keys.E)) {
            purchaseHealth(player);
        } else if (input.wasPressed(Keys.L)) {
            upgradeWeapon(player);
        } else if (input.wasPressed(Keys.P)) {
            app.ShadowDungeon.restart();
        }
    }


    // ----- private helper methods -----
    private void upgradeWeapon(Player player) {
        PlayerStats playerStats = player.getPlayerStats();
        if (playerStats.getWeaponLevel() < MAX_W_LVL && player.spendCoin(WEAPON_COST)) {
            playerStats.updateWeapon();
            if (playerStats.getWeaponLevel() == ELITE_W) {
                player.updateDamage(ELITE_D);
            } else if (playerStats.getWeaponLevel() == ADVANCE_W) {
                player.updateDamage(ADVANCE_D);
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

