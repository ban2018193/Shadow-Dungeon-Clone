package entities.objects;

import bagel.Input;
import bagel.Keys;
import entities.Entity;
import entities.player.Player;
import utils.IOUtils;


/**
 * TreasureBox is an object that gives coins to the player when opened
 * Does not block player movements, but can be attacked by projectiles
 */
public class TreasureBox extends Entity {

    // ----- Constants -----

    private static int COIN_INDEX = 2;
    private final double coinValue;


    // ---- Constructors -----

    /**
     * Creates a TreasureBox at a position with a certain coin value
     *
     * @param contentsRaw Raw string in format "(x, y, coins)"
     */
    public TreasureBox(String contentsRaw) {
        super(IOUtils.parseCoords(contentsRaw), "res/treasure_box.png");
        String content = IOUtils.parseContents(contentsRaw, ",")[COIN_INDEX];
        this.coinValue = Double.parseDouble(content);
    }


    // ---- Interactions ----

    /**
     * Opens the treasure box and player gain coins
     *
     * @param player The player opening the box
     */
    public void openBox(Player player) {
        // Do nothing if it's alr opened, or player's not touching it
        if (!isActive() || !collidesWith(player)){
            return;
        }
        setActive(false);
        player.gainCoin(coinValue, this);
    }


    /**
     * Handles player interaction
     * Player can open the box by pressing k and colliding with it
     *
     * @param input Player input
     * @param player The player interacting
     */
    @Override
    public void tryInteract(Input input, Player player) {
        if (input.isDown(Keys.K) && player.useKey()) {
            openBox(player);
        }
    }


    /**
     * TreasureBox does not block movement
     *
     * @return false
     */
    @Override
    public boolean isBlockable() {
        return false;
    }


    // ----- Rendering -----

    /**
     * Render the box if it hasn't been opened yet
     */
    @Override
    public void render() {
        if (isActive()) {
           super.render(); // Only render if unopen
        }
    }

}
