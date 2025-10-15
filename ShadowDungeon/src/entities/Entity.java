package entities;

import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;
import config.GameConfig;


/**
 * handles all the entities in the game
 * includes enemies, player, river, wall, treasure box
 */
public abstract class Entity {

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
