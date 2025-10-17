package entities.objects.projectiles;

import bagel.util.Point;
import bagel.util.Rectangle;
import bagel.util.Vector2;
import entities.Entity;

public abstract class Projectile extends Entity {
    private double speed;
    private final Vector2 moveDir;
    private boolean isActive = true;
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

    // Private helper methods
    private void rechargeShoot() {
        // TODO: logic to recharge shooting ability
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


    @Override
    public void triggerCollisionEvent(Entity entity){
        if (entity.isBlockable()) {
            deactivate();
        }
    }

    public  boolean collidesWith(Entity entity) {
        return getBoundingBox().intersects(entity.getBoundingBox());
    }


    /**
     * Check if projectile is outside window boundaries
     * @return true if projectile has left the visible window area
     */
    protected boolean isOutsideWindow() {
        Rectangle bounds = getBoundingBox();

        // Check if completely off screen on any side
        boolean leftOfWindow = bounds.right() < 0;
        boolean rightOfWindow = bounds.left() > getConfig().WINDOW_WIDTH;
        boolean aboveWindow = bounds.bottom() < 0;
        boolean belowWindow = bounds.top() > getConfig().WINDOW_HEIGHT;

        return leftOfWindow || rightOfWindow || aboveWindow || belowWindow;
    }

    public void deactivate() {
        isActive = false;
    }

    @Override
    public boolean isBlockable() {
        // Bullets don't block movement
        return false;
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

    public boolean isActive() {
        return isActive;
    }
}
