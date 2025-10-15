package entities.player;

import bagel.util.Point;
import bagel.*;
import java.util.function.BiPredicate;
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

    // ---- player settings ----
    private final int movingSpeed;

    // ----image sources ----
    private Image playerR = new Image("res/player_right.png");
    private Image playerL = new Image("res/player_left.png");

    // ------ constructors -----
    public Player() {
        super(GameConfig.getInstance().PLAYER_START_POS, "res/player_right.png");
        GameConfig config = GameConfig.getInstance();
        this.movingSpeed = config.MOVING_SPEED;
        this.health = config.INITIAL_HEALTH;
        this.coins = config.INITIAL_COINS;

    }

    public Player(Player player, String imagePath) {
        super(player.getPosition(), imagePath);
        this.health = player.health;
        this.coins = player.coins;
        this.movingSpeed = player.movingSpeed;
    }

    // ------ movements -----

    // try (no update) to move player according to the keyboard input
    public Point tryInput(Input input) {
        double newX = getPosition().x;
        double newY = getPosition().y;

        if (input.isDown(Keys.S)) newY += movingSpeed;
        if (input.isDown(Keys.W)) newY -= movingSpeed;
        if (input.isDown(Keys.D)) newX += movingSpeed;
        if (input.isDown(Keys.A)) newX -= movingSpeed;

        return new Point(newX, newY);
    }

    /**
     * if the tryInput move is invalid, try to slide player to valid axis
     * @param nextMove       the next position player trying to move to
     * @param collisionCheck check if player collides with x, y
     * @return Point of the new valid move
     */
    public Point trySolveCollision(Point nextMove, BiPredicate<Double, Double> collisionCheck) {
        double newX = nextMove.x;
        double newY = nextMove.y;
        Point playerPosition = getPosition();

        boolean collideX = collisionCheck.test(newX, playerPosition.y);
        boolean collideY = collisionCheck.test(playerPosition.x, newY);
        boolean collideXY = collisionCheck.test(newX, newY);

        if (!collideXY) {
            return new Point(newX, newY); // move if is valid
        } else if (!collideX) {
            return new Point(newX, playerPosition.y); // try only continues moving to x if y collides
        } else if (!collideY) {
            return new Point(playerPosition.x, newY); // try only continues moving to y if x collides
        } else {
            return playerPosition; // stay in place if stuck
        }
    }

    // ----- updates ----

    public void updateFacingDir(Point cursor) {
        boolean facingRight = cursor.x > getPosition().x;
        if (facingRight) {
            setImage(playerR);
        } else {
            setImage(playerL);
        }
    }

    // main update method
    public void update(Input input) {
        updateFacingDir(input.getMousePosition());
    }

    public void gainDamage(double damage, Entity entity) {
        if (health > 0) {
            health -= damage;
        }
    }

    public void gainCoin(double amount, Entity entity) {
        coins += amount;
    }

    // ----- getters -----
    public double getHealth() {return health;}
    public double getCoins() {return coins;}

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
        this.playerR = new Image(path);
    }

    public void setChoseChar(boolean choseChar) {
        this.choseChar = choseChar;
    }
}
