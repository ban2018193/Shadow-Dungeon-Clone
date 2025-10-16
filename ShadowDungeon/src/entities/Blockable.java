package entities;

public interface Blockable extends Collidable{
    public void attackedByProjectile(Projectile proj);
}
