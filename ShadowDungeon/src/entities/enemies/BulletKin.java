package entities.enemies;

import bagel.Image;
import bagel.util.Point;
import bagel.util.Vector2;
import entities.capabilities.Shootable;
import entities.objects.projectiles.Bullet;
import entities.objects.projectiles.Fireball;
import entities.objects.projectiles.Projectile;
import rooms.Room;

public class BulletKin extends Enemy implements Shootable {

    private int firingRate;
    private Vector2 shootDir;
    private int rechargeFireball;
    private double damage = getConfig().FIREBALL_DAMAGE;

    public BulletKin(Point position, boolean ashen) {
        super(position, "res/bullet_kin.png");
        if (ashen) {
            initAshenBulletKin();
        }else {
            initBulletKin();
        }

    }

    // ---creating bullet kin ----
    private void initBulletKin() {
        setHealth(getConfig().BULLET_KIN_HEALTH);
        setCoins(getConfig().BULLET_KIN_COIN);
        this.firingRate = getConfig().BULLET_KIN_SHOOT_FREQUENCY;
    }

    private void initAshenBulletKin() {
        setImage(new Image("res/ashen_bullet_kin.png"));
        setHealth(getConfig().ASHEN_BULLET_KIN_HEALTH);
        setCoins(getConfig().ASHEN_BULLET_KIN_COIN);
        this.firingRate = getConfig().ASHEN_BULLET_KIN_SHOOT_FREQUENCY;
    }

    @Override
    public void shoot(Room currRoom, Point target) {

        Vector2 shootDir = findShootDir(target, this.getPosition());
        Projectile proj = new Fireball(this.getPosition(), shootDir, this);
        currRoom.getProjectiles().add(proj);


    }

    // --- getters ----

    public double getDamage() {
        return damage;
    }
}
