package entities;

import bagel.Input;
import bagel.Keys;
import entities.player.Player;
import utils.IOUtils;


/**
 * treasure box: child class of entity
 * when opened, player gains coins, and it will disappear
 */
public class TreasureBox extends Entity {

    // ----- constants -----
    private static int COIN_INDEX = 2; // coin format location in gameprop
    private int numKeys = 0;
    // ----- stats ----
    private boolean isUsed = false;
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
        if (isUsed || !collidesWith(player)){
            return; // do nothing if it's alr opened, or player's not touching it
        }
        isUsed = true;
        player.gainCoin(coinValue, this);
    }

    // ----- rendering -----

    @Override
    public void render() {
        if (!isUsed) {
           super.render(); // only render if unopen
        }
    }

    @Override
    public void tryInteract(Input input, Player player) {
        if (input.isDown(Keys.K)) {
            openBox(player);
        }
    }

    public boolean isUsed() {
        return isUsed;
    }

    @Override
    public boolean isBlockable() {
        return false;
    }


}
