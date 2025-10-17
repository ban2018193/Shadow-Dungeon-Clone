package entities.player;


import bagel.*;
import bagel.util.Point;
import bagel.util.Vector2;
import config.GameConfig;
import entities.Entity;
import entities.capabilities.Shootable;
import entities.objects.projectiles.Bullet;
import entities.objects.projectiles.Projectile;
import rooms.Room;


/**
 * player: children of entity class
 * take inputs to move around
 * has won stats of current situation (health, coins)
 */
public class Player extends Entity implements Shootable {

    // ------ player stats -----
    private PlayerStats playerStats = new PlayerStats();
    private double damage = getConfig().FIREBALL_DAMAGE;
    private boolean choseChar = false;
    private double firingRate = getConfig().BULLET_FREQ;
    private int framesSinceLast = 0;


    // ----image sources ----
    private Image playerR = new Image("res/player_right.png");
    private Image playerL = new Image("res/player_left.png");

    // ------ constructors -----
    public Player() {
        super(GameConfig.getInstance().PLAYER_START_POS, "res/player_right.png");
        GameConfig config = GameConfig.getInstance();
    }

    public Player(Player player, String imagePath) {
        super(player.getPosition(), imagePath);
    }

    // ------ movements -----


    // ----- getters -----


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

    public void setPlayerR(String path) {
        this.playerR = new Image(path);
    }

    public void setPlayerL(String path) {
        this.playerL = new Image(path);
    }

    public void setChoseChar(boolean choseChar) {
        this.choseChar = choseChar;
    }

    public double getDamage() {
        return damage;
    }


    @Override
    public void shoot(Room currRoom, Point target) {
        if (!hasChoseChar()) {
            System.out.println("Can't shoot - no character chosen");  // Debug
            return;
        }

        if (framesSinceLast < firingRate) {
            return; // still cooling down
        }
        framesSinceLast = 0;

        Vector2 shootDir = findShootDir(target, this.getPosition());
        Projectile proj = new Bullet(this.getPosition(), shootDir, this);
        currRoom.getProjectiles().add(proj);

    }

    public void updateFramesSinceLast() {
       framesSinceLast++;
    }


    public void gainCoin(double amount, Entity entity) {
        playerStats.gainCoin(amount);
    }

    public void gainDamage(double damage, Entity entity) {
        if (playerStats.getHealth() > 0) {
            playerStats.gainDamage(damage);
        }
    }

    public void gainKey() {
        playerStats.gainKey();
    }

    public boolean useKey() {
        if (playerStats.getKeys() > 0) {
            playerStats.useKey();
            return true;
        }
        return false;

    }

    public PlayerStats getPlayerStats() {
        return playerStats;
    }
}
