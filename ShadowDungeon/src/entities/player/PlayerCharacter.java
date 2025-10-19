package entities.player;

import bagel.Input;
import bagel.Keys;
import bagel.MouseButtons;
import bagel.util.Point;
import config.GameConfig;
import rooms.Room;
import java.util.function.BiPredicate;

/**
 * Handles the overall control of the player in the game
 * This class manages player movement, direction, and shooting behavior, switching characters
 */
public class PlayerCharacter {

    // ---- Settings ----

    private Player player;
    private final int RIGHT = 0;
    private final int LEFT = 1;
    private final int movingSpeed;;


    // ---- Constructor -----

    /**
     * Creates a new controllable player with default player settings
     */
    public PlayerCharacter() {
        this.player = new Player();
        GameConfig config = GameConfig.getInstance();
        movingSpeed = config.MOVING_SPEED;
    }


    // ---- Methods ----

    /**
     * Replaces the current player with another character type.
     *
     * @param player the new player to control
     */
    public void changeCharacter(Player player){
        this.player = player;

    }


    /**
     * Calc the next position of the player based on keyboard input,
     * without actually updating the position yet.
     *
     * @param input the current keyboard input
     * @return the new potential position
     */
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
     * Tries to find a valid movement position to not collide with move blockable objects
     *
     * @param nextMove the intended new position
     * @param collisionCheck a function that checks if a position collides with something
     * @return the nearest valid movement position
     */
    public Point trySolveCollision(Point nextMove, BiPredicate<Double, Double> collisionCheck) {
        double newX = nextMove.x;
        double newY = nextMove.y;
        Point playerPosition = player.getPosition();

        boolean collideX = collisionCheck.test(newX, playerPosition.y);
        boolean collideY = collisionCheck.test(playerPosition.x, newY);
        boolean collideXY = collisionCheck.test(newX, newY);

        if (!collideXY) {
            return new Point(newX, newY); // Move if is valid
        } else if (!collideX) {
            return new Point(newX, playerPosition.y); // Try only continues moving to x if y collides
        } else if (!collideY) {
            return new Point(playerPosition.x, newY); // Try only continues moving to y if x collides
        } else {
            return playerPosition; // Stay in place if stuck
        }
    }


    // ----- Updates ----

    /**
     * Updates the player's facing direction based on mouse position
     *
     * @param cursor the mouse cursor position
     */
    public void updateFacingDir(Point cursor) {
        boolean facingRight = cursor.x > player.getPosition().x;
        if (facingRight) {
            player.setImage(player.getPlayerR());
        } else {
            player.setImage(player.getPlayerL());
        }
    }


    /**
     * Main update method for player actions per frame
     * Handles shooting, direction changes, and cooldown updates
     *
     * @param input the player's input
     * @param current the current room the player is in
     */
    public void update(Input input, Room current) {
        updateFacingDir(input.getMousePosition());
        player.updateFramesSinceLast();
        if (input.isDown(MouseButtons.LEFT)) {
            player.shoot(current, input.getMousePosition());
        }
    }


    // ---- Getters ----

    /**
     * Returns the current character being controlled
     *
     * @return the current Player
     */
    public Player getPlayer() {
        return player;
    }

}
