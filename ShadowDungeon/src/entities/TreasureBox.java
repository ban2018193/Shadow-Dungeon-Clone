package entities;

import utils.IOUtils;


/**
 * treasure box: child class of entity
 * when opened, player gains coins, and it will disappear
 */
public class TreasureBox extends Entity {

    // ----- constants -----
    private static int COIN_INDEX = 2; // coin format location in gameprop

    // ----- stats ----
    private boolean isOpened = false;
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

    public boolean collidesWith(Player player) {
        return getBoundingBox().intersects(player.getBoundingBox());
    }

    public void openBox(Player player) {
        if (isOpened || !collidesWith(player)){
            return; // do nothing if it's alr opened, or player's not touching it
        }
        isOpened = true;
        player.updateCoin(coinValue);
    }

    // ----- rendering -----

    @Override
    public void render() {
        if (!isOpened) {
           super.render(); // only render if unopen
        }
    }
}
