package entities.player;

import bagel.Input;
import bagel.Keys;
import bagel.util.Point;
import config.GameConfig;

import java.util.function.BiPredicate;

public class PlayerCharacter {
    private Player player;
    private final int RIGHT = 0;
    private final int LEFT = 1;
    private boolean hasChose = false;

    // ---- player settings ----
    private final int movingSpeed;;

    public PlayerCharacter() {
        this.player = new Player();
        GameConfig config = GameConfig.getInstance();
        movingSpeed = config.MOVING_SPEED;
    }

    public void changeCharacter(Player player){
        this.player = player;
        this.hasChose = true;

    }

    public boolean hasChose() {
        return hasChose;
    }

    // try (no update) to move player according to the keyboard input
    public Point tryInput(Input input) {
        double newX = player.getPosition().x;
        double newY = player.getPosition().y;

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
        Point playerPosition = player.getPosition();

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
        boolean facingRight = cursor.x > player.getPosition().x;
        if (facingRight) {
            player.setImage(player.getPlayerR());
        } else {
            player.setImage(player.getPlayerL());
        }
    }

    // main update method
    public void update(Input input) {
        updateFacingDir(input.getMousePosition());
    }


    public Player getPlayer() {
        return player;
    }
}
