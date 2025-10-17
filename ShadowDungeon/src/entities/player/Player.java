package entities.player;


import bagel.*;
import config.GameConfig;
import entities.Entity;


/**
 * player: children of entity class
 * take inputs to move around
 * has won stats of current situation (health, coins)
 */
public class Player extends Entity {

    // ------ player stats -----
    private double health;
    private double coins;
    private boolean choseChar = false;


    // ----image sources ----
    private Image playerR = new Image("res/player_right.png");
    private Image playerL = new Image("res/player_left.png");

    // ------ constructors -----
    public Player() {
        super(GameConfig.getInstance().PLAYER_START_POS, "res/player_right.png");
        GameConfig config = GameConfig.getInstance();
        this.health = config.INITIAL_HEALTH;
        this.coins = config.INITIAL_COINS;

    }

    public Player(Player player, String imagePath) {
        super(player.getPosition(), imagePath);
        this.health = player.health;
        this.coins = player.coins;
    }

    // ------ movements -----
    public void gainDamage(double damage, Entity entity) {
        if (health > 0) {
            health -= damage;
        }
    }

    // ---- collidable ----
    @Override
    public boolean collidesWith(Player player) {
        return false;
    }

    @Override
    public void triggerCollisionEvent(Player player) {
        return;
    }

    public void gainCoin(double amount, Entity entity) {
        coins += amount;
    }

    // ----- getters -----
    public double getHealth() {return health;}
    public double getCoins() {return coins;}

    public Image getPlayerR() {
        return playerR;
    }

    public Image getPlayerL() {
        return playerL;
    }

    public boolean hasChoseChar() {
        return choseChar;
    }

    // ---- setters ----
    public void updateHealth(double health) {
        this.health += health;
    }

    public void updateCoins(double coins) {
        this.coins += coins;
    }

    public void setPlayerR(String path) {
        this.playerR = new Image(path);
    }

    public void setPlayerL(String path) {
        this.playerL = new Image(path);
    }

    public void setChoseChar(boolean choseChar) {
        this.choseChar = choseChar;
    }
}
