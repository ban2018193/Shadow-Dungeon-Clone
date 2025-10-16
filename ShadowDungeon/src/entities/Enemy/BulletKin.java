package entities.Enemy;

import bagel.Image;
import bagel.util.Point;
import bagel.util.Vector2;

public class BulletKin extends Enemy{

    private int firingRate;
    private Vector2 shootDir;
    private int rechargeFireball;

    public BulletKin(Point position, boolean ashen) {
        super(position, "res/bullet_kin.png");

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

    // --- shooting ---


}
