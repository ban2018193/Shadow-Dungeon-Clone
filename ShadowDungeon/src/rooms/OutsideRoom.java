package rooms;

import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;

import dungeon.Dungeon;
import entities.player.PlayerCharacter;
import rooms.objects.Door;


/**
 * An outside room typically has only one door and includes a title display and a restart area.
 */
public abstract class OutsideRoom extends Room{

    // ----- Texts in the room -----

    private String title;
    private Point titleXY;
    private final double titleY;


    // ---- Settings -----
    private final Point restartAreaXY;
    private final Image restartAreaImage = new Image("res/restart_area.png");
    private final Rectangle restartArea;
    private final Font titleFont;


    // ----- Constructor -----

    /**
     * Constructs an OutsideRoom with a given index
     *
     * @param index the index of this room in the dungeon
     */
    public OutsideRoom(int index) {
        super(index);
        int fontSize = getConfig().TITLE_FONT_SIZE;
        this.titleFont = new Font(getConfig().FONT_PATH, fontSize);
        this.titleY =getConfig().TITLE_Y;
        this.restartAreaXY = getConfig().RESTART_AREA_PREP;
        this.restartArea = restartAreaImage.getBoundingBoxAt(restartAreaXY);
    }


    // ----- Settings managements -----

    /**
     * Calculates the starting X-coord to center the title text
     *
     * @param content the text content of the title
     * @param stringFont the font used to render the text
     * @return the X-coord for centering the text
     */
    public double findStartX(String content, Font stringFont) {
        double textWidth = stringFont.getWidth(content);
        return (getConfig().WINDOW_WIDTH - textWidth) / 2.0;
    }


    // Set up the title location
    private void setTitleSettings() {
        double titleX = findStartX(title, titleFont);
        titleXY = new Point(titleX, this.titleY);
    }


    /**
     * Adds a new door to the room
     * Automatically disables auto-lock for the door and marks the stage as clear.
     *
     * @param newDoor the door to add
     */
    @Override
    public void addDoor(Door newDoor) {
        super.addDoor(newDoor);
        updateNumOfDoors();
        newDoor.updateCurrentDoorSide(this);
        newDoor.disableAutoLock();
        newDoor.setStageNotClear(false);
    }


    // ----- Updates ----

    /**
     * Updates the room state
     * Checks for restart input within the restart area, then performs normal room updates
     *
     * @param player the player's character
     * @param input the current input state
     * @param dungeon the dungeon this room is part of
     */
    @Override
    public void update(PlayerCharacter player, Input input, Dungeon dungeon) {
        // Check for restart first
        if (input.wasPressed(Keys.ENTER) && restartArea.intersects(player.getPlayer().getPosition())) {
            app.ShadowDungeon.restart();
            return;
        }
        super.update(player, input, dungeon);
    }


    // ---- Render -----

    // Renders the title and the restart area for the outside room
    private void renderOutside() {
        titleFont.drawString(title, titleXY.x, titleXY.y);
        restartAreaImage.draw(restartAreaXY.x, restartAreaXY.y);
    }


    /** Renders the room including title, restart area, and projectiles */
    @Override
    public void render() {
        super.render();
        renderOutside();
        renderProjectiles();
    }


    // ----- Setter -----

    /**
     * Sets the title of the room and updates its position
     *
     * @param title the new title text
     */
    public void setTitle(String title) {
        this.title = title;
        setTitleSettings();
    }

}
