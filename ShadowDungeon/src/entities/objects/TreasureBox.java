package entities.objects;

import bagel.Input;
import bagel.Keys;
import entities.Entity;
import entities.player.Player;
import entities.player.PlayerStats;
import utils.IOUtils;


/**
 * treasure box: child class of entity
 * when opened, player gains coins, and it will disappear
 */
public class TreasureBox extends Entity {

    // ----- constants -----
    private static int COIN_INDEX = 2; // coin format location in gameprop
    // ----- stats ----
    private final double coinValue;

    // ---- constructors -----

    /**
     * create a treasure box, initialise position and coins it has
     * @param contentsRaw raw string in format of (x, y, coins gain)
     */
    public TreasureBox(String contentsRaw) {
        super(IOUtils.parseCoords(contentsRaw), "res/treasure_box.png");
        String content = IOUtils.parseContents(contentsRaw, ",")[COIN_INDEX];
        this.coinValue = Double.parseDouble(content);
    }

    // ---- interactions ----



    public void openBox(Player player) {
        if (!isActive() || !collidesWith(player)){
            return; // do nothing if it's alr opened, or player's not touching it
        }
        setActive(false);
        player.gainCoin(coinValue, this);
    }

    // ----- rendering -----

    @Override
    public void render() {
        if (isActive()) {
           super.render(); // only render if unopen
        }
    }

    @Override
    public void tryInteract(Input input, Player player) {
        if (input.isDown(Keys.K) && player.useKey()) {
           openBox(player);
        }
    }

    @Override
    public boolean isBlockable() {
        return false;
    }


}
