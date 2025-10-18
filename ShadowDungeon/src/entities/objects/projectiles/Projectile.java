package entities.objects.projectiles;

import bagel.util.Point;
import bagel.util.Rectangle;
import bagel.util.Vector2;
import entities.Entity;
import rooms.BattleRoom;
import rooms.Room;

public abstract class Projectile extends Entity {
    private double speed;
    private final Vector2 moveDir;
    private double damage;

    /**
     * create entity in the game
     *
     * @param position  position of this entity in the game
     * @param imagePath if it's not null, initialise the image. will be set later if its null
     */
    public Projectile(Point position, Vector2 moveDir, String imagePath) {
        super(position, imagePath);
        this.moveDir = moveDir;

    }


    public boolean hitOnObstacle(Entity obstacle) {
        // Only check collision with blockable entities
        return obstacle.isBlockable();
    }

    public void update() {
        // Move the bullet in its direction
        move();

        // Check if bullet went outside window
        if (isOutsideWindow()) {
            deactivate();
        }

    }


    private void move() {
        // Get current position
        Point current = getPosition();

        // Calculate movement offset: normalized direction × speed (pixels per frame)
        // Since moveDir is normalized (length = 1), this gives exactly 'speed' pixels of movement
        Vector2 movement = moveDir.mul(speed);

        // Convert movement vector to point
        Point offset = movement.asPoint();

        // Calculate new position by adding movement to current position
        Point newPosition = new Point(current.x + offset.x, current.y + offset.y);

        // Update the projectile's position
        movePosition(newPosition);
    }




    public  boolean collidesWith(Entity entity) {
        return getBoundingBox().intersects(entity.getBoundingBox());
    }


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


    public void deactivate() {
        setActive(false);
    }

    @Override
    public boolean isBlockable() {
        // Bullets don't block movement
        return false;
    }

    @Override
    public void deleteInactive(Room currRoom) {
        if (!isActive()) {
            currRoom.getToRemoveProj().add(this);
        }
    }

    // --- setter ---
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    // ---getters ---

    public double getDamage() {
        return damage;
    }

}
