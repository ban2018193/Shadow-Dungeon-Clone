package entities;

import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;

import config.GameConfig;
import entities.capabilities.Collidable;
import entities.player.Player;
import rooms.*;


/**
 * Represents any interactive or visual element in the game world.
 *
 * This includes all enemies, the player, environmental objects, and other entities
 * that can appear within rooms. Entities have positions, images, and active states,
 * and can interact with other entities or be removed from the game when inactive.
 */
public abstract class Entity implements Collidable {

    private static final GameConfig config = GameConfig.getInstance();

    // ---- settings ----
    private Point position;
    private Image image;
    private boolean isActive = true;

    // ----- constructor ----

    /**
     * Creates a new entity in the game world.
     *
     * @param position  the position of this entity in the game
     * @param imagePath the path to the entity's image file; if null, image will be set later
     */
    public Entity(Point position, String imagePath) {
        this.position = position;
        if (imagePath != null) {
            this.image = new Image(imagePath);
        }
    }

    // ----- rendering -----

    /**
     * Moves the entity to a new position.
     *
     * @param position the new position to move to
     */
    public void movePosition(Point position) {
        this.position = position;
    }


    /**
     * Renders the entity's image on the screen if it is active.
     */
    public void render() {
        if (isActive) {
            image.draw(position.x, position.y);
        }
    }

    // ---- handle interactions ----

    /**
     * Checks if this entity collides with the player.
     *
     * @param player the player to check collision with
     * @return true if their bounding boxes intersect
     */
    @Override
    public boolean collidesWith(Player player) {
        return getBoundingBox().intersects(player.getBoundingBox());
    }

    /**
     * Defines what happens when this entity collides with another.
     * Default implementation does nothing; subclasses can override.
     *
     * @param entity the other entity involved in the collision
     * @param player the player instance in the game
     */
    @Override
    public void triggerCollisionEvent(Entity entity, Player player) {
    }

    /**
     * Handles player interaction (e.g., opening a box or using an item).
     * Default implementation does nothing; subclasses can override.
     *
     * @param input  current player input
     * @param player the player interacting with this entity
     */
    @Override
    public void tryInteract(Input input, Player player) {
    }

    /**
     * Removes the entity from the current room if it is inactive.
     * Only applies to entities inside BattleRoom instances.
     *
     * @param currRoom the current room where this entity exists
     */
    @Override
    public void deleteInactive(Room currRoom) {
        if (!isActive && currRoom instanceof BattleRoom room) {
           room.getToRemoveEntities().add(this);
        }
    }



    // ----- getters ----

    /**
     * Returns the entity's bounding box for collision detection.
     *
     * @return a Rectangle representing the entity's bounds
     */
    public Rectangle getBoundingBox() {return image.getBoundingBoxAt(position);}

    /**
     * Returns the current position of the entity.
     *
     * @return the entity's position
     */
    public Point getPosition() {return position;}

    /**
     * Returns the entity's image.
     *
     * @return the image used for rendering this entity
     */
    public Image getImage() {return image;}

    /**
     * Returns the global game configuration.
     *
     * @return the shared game configuration instance
     */
    public GameConfig getConfig() {
        return config;
    }

    /**
     * Checks whether the entity is currently active in the game.
     *
     * @return true if active, false otherwise
     */
    public boolean isActive() {
        return isActive;
    }

    // ---- setters ----

    /**
     * Updates the entity's image.
     *
     * @param image the new image to assign
     */
    public void setImage(Image image) {this.image = image;}

    /**
     * Sets whether this entity is active.
     *
     * @param active true if the entity should remain active, false if removed
     */
    public void setActive(boolean active) {
        isActive = active;
    }

}
