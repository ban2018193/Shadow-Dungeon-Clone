package entities;

import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;
import config.GameConfig;
import entities.player.Player;


/**
 * handles all the entities in the game
 * includes enemies, player, river, wall, treasure box
 */
public abstract class Entity implements Collidable{

    private static final GameConfig config = GameConfig.getInstance();

    // ---- entity basic settings ----
    private Point position;
    private Image image;

    // ----- constructor ----

    /**
     * create entity in the game
     * @param position position of this entity in the game
     * @param imagePath if it's not null, initialise the image. will be set later if its null
     */
    public Entity(Point position, String imagePath) {
        this.position = position;
        if (imagePath != null) {
            this.image = new Image(imagePath);
        }
    }

    // ----- placement of entities -----

    // move the entity to new position
    public void movePosition(Point position) {
        this.position = position;
    }

    // ----- render this entity  ----
    public void render() {
        image.draw(position.x, position.y);
    }

    // ---- collidable ----
    @Override
    public boolean collidesWith(Player player) {
        return getBoundingBox().intersects(player.getBoundingBox());
    }

    @Override
    public void triggerCollisionEvent(Player player) {
        return;
    }

    @Override
    public void attackedByProjectile(Projectile proj) {
        return;
    }

    @Override
    public boolean isAttackable() {
        return true;
    }

    @Override
    public boolean isBlockable() {
        return true;
    }

    @Override
    public void tryInteract(Input input, Player player) {
        return;
    }



    // ----- getters ----
    public Rectangle getBoundingBox() {return image.getBoundingBoxAt(position);}
    public Point getPosition() {return position;}
    public Image getImage() {return image;}
    public GameConfig getConfig() {
        return config;
    }

    // ---- setters ----
    public void setImage(Image image) {this.image = image;}


}
