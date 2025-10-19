package entities.enemies;

import bagel.Image;
import bagel.util.Point;
import bagel.util.Vector2;
import entities.capabilities.Shootable;
import entities.objects.projectiles.*;
import entities.player.Player;
import rooms.Room;

/**
 * BulletKin: Enemy that can shoot fireballs at the player
 * Can have a normal or "ashen" bullet kin with different stats
 */
public class BulletKin extends Enemy implements Shootable {

    // ---- Settings ----

    private int firingRate;
    private int framesSinceLast = 0;
    private double damage = getConfig().FIREBALL_DAMAGE;


    // ---- Constructor -----

    /**
     * Creates a BulletKin at a given position with corresponding type
     * Type can be "ashen" or "normal" bullet kin
     *
     * @param position The spawn position of the BulletKin
     * @param ashen If true, creates an "ashen" bullet kin
     */
    public BulletKin(Point position, boolean ashen) {
        super(position, "res/bullet_kin.png");
        if (ashen) {
            initAshenBulletKin();
        }else {
            initBulletKin();
        }
    }


    // ----- Initializes normal bullet kin stats ----

    // Initialize into normal bullet kin stats
    private void initBulletKin() {
        setHealth(getConfig().BULLET_KIN_HEALTH);
        setCoins(getConfig().BULLET_KIN_COIN);
        this.firingRate = getConfig().BULLET_KIN_SHOOT_FREQUENCY;
    }

    // Initialize into ashen bullet kin stats
    private void initAshenBulletKin() {
        setImage(new Image("res/ashen_bullet_kin.png"));
        setHealth(getConfig().ASHEN_BULLET_KIN_HEALTH);
        setCoins(getConfig().ASHEN_BULLET_KIN_COIN);
        this.firingRate = getConfig().ASHEN_BULLET_KIN_SHOOT_FREQUENCY;
    }


    // ---- Behaviours ----

    /**
     * Shoots a projectile toward the target if not in cooldown
     *
     * @param currRoom The room to add the projectile to
     * @param target The position to aim at
     */
    @Override
    public void shoot(Room currRoom, Point target) {

        // Check if its still in cool down
        if (framesSinceLast < firingRate) {
            return;
        }
        framesSinceLast = 0;
        Vector2 shootDir = findShootDir(target, this.getPosition());
        Projectile proj = new Fireball(this.getPosition(), shootDir, damage);
        currRoom.getProjectiles().add(proj);

    }


    /**
     * Auto behaviours of the bullet kin each frames
     * Increases cooldown and try to shoot
     *
     * @param room the current room
     * @param player the player to target
     */
    @Override
    public void autoPilot(Room room, Player player) {
        framesSinceLast++;
        shoot(room, player.getPosition());
    }

}
