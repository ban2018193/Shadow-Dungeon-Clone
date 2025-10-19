package entities.objects.projectiles;

import bagel.util.Point;
import bagel.util.Rectangle;
import bagel.util.Vector2;
import entities.Entity;
import rooms.Room;

/**
 * Abstract class for projectiles in the game
 * Handles movement, collision detection, and deactivation when outside the window.
 */
public abstract class Projectile extends Entity {

    // ---- Settings ----
    private double speed;
    private final Vector2 moveDir;
    private double damage;

    // --- Constructor ----

    /**
     * Creates a projectile at a given position with a direction and image
     *
     * @param position  starting position of the projectile
     * @param moveDir   direction of movement
     * @param imagePath path to the projectile image
     */
    public Projectile(Point position, Vector2 moveDir, String imagePath) {
        super(position, imagePath);
        this.moveDir = moveDir;

    }


    // ---- Handle interactions -----

    /**
     * Checks collision with another entity
     *
     * @param entity entity to check against
     * @return true if boxes intersect
     */
    public  boolean collidesWith(Entity entity) {
        return getBoundingBox().intersects(entity.getBoundingBox());
    }

    // ---- Behaviours ----

    /**
     * Check if projectile is outside window boundaries
     * @return true if projectile has left the visible window area
     */
    private boolean isOutsideWindow() {
        Rectangle bounds = getBoundingBox();

        // If any part of the projectile is outside the window, consider it hit the boundary
        boolean hitLeft = bounds.left() <= 0;
        boolean hitRight = bounds.right() >= getConfig().WINDOW_WIDTH;
        boolean hitTop = bounds.top() <= 0;
        boolean hitBottom = bounds.bottom() >= getConfig().WINDOW_HEIGHT;

        return hitLeft || hitRight || hitTop || hitBottom;
    }


    /** Deactivates the projectile */
    public void deactivate() {
        setActive(false);
    }


    /**
     * Projectiles do not block player movement by default
     *
     * @return false by default
     */
    @Override
    public boolean isBlockable() {
        return false;
    }


    /**
     * Add inactive projectiles into a buffer list in room to be deleted
     *
     * @param currRoom the room this projectile is in
     */
    @Override
    public void deleteInactive(Room currRoom) {
        if (!isActive()) {
            currRoom.getToRemoveProj().add(this);
        }
    }


    // ---- Updates -----

    // Moves the projectile according to its speed and direction
    private void move() {
        Point current = getPosition();

        // Calc movement offset: normalized direction (get speed)
        Vector2 movement = moveDir.mul(speed);

        // Move to new position
        Point offset = movement.asPoint();
        Point newPosition = new Point(current.x + offset.x, current.y + offset.y);
        movePosition(newPosition);
    }


    /**
     * Updates the projectile each frame
     * Moves and deactivates it if it goes outside the window.
     */
    public void update() {
        move();
        if (isOutsideWindow()) {
            deactivate();
        }
    }


    // --- Setter ---

    /**
     * Sets the speed of the projectile
     *
     * @param speed pixels per frame
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }


    /**
     * Sets the damage dealt by this projectile
     *
     * @param damage damage value
     */
    public void setDamage(double damage) {
        this.damage = damage;
    }


    // --- Getters ---

    /**
     * Get the damage this projectile cna deal
     *
     * @return the damage this projectile deals
     * */
    public double getDamage() {
        return damage;
    }

}
