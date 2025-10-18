package rooms;

import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;

import dungeon.Dungeon;
import entities.player.Player;
import entities.player.PlayerCharacter;
import rooms.objects.Door;


// this is the parent for rooms that has nothing to do with battles, only has one door
public abstract class OutsideRoom extends Room{

    // ----- the main title of the room -----
    private String title;
    private Point titleXY;
    private final double titleY;

    // ---- settings -----
    private final Point restartAreaXY;
    private final Image restartAreaImage = new Image("res/restart_area.png");
    private final Rectangle restartArea;
    private final Font titleFont;

    // ----- constructor -----
    public OutsideRoom(int index) {
        super(index);
        int fontSize = getConfig().TITLE_FONT_SIZE;
        this.titleFont = new Font(getConfig().FONT_PATH, fontSize);
        this.titleY =getConfig().TITLE_Y;
        this.restartAreaXY = getConfig().RESTART_AREA_PREP;
        this.restartArea = restartAreaImage.getBoundingBoxAt(restartAreaXY);
    }

    // ----- settings managements -----

    // calc the start of x coordinate to place title
    public double findStartX(String content, Font stringFont) {
        double textWidth = stringFont.getWidth(content);
        return (getConfig().WINDOW_WIDTH - textWidth) / 2.0;
    }

    // set up the title location
    public void setTitleSettings() {
        double titleX = findStartX(title, titleFont);
        titleXY = new Point(titleX, this.titleY);
    }

    // add new doors into the room
    @Override
    public void addDoor(Door newDoor) {
        super.addDoor(newDoor);
        updateNumOfDoors();
        newDoor.updateCurrentDoorSide(this);
        newDoor.disableAutoLock();
        newDoor.setStageNotClear(false);
    }

    // ----- updates ----

    @Override
    public void update(PlayerCharacter player, Input input, Dungeon dungeon) {
        // check for restart first
        if (input.wasPressed(Keys.ENTER) && restartArea.intersects(player.getPlayer().getPosition())) {
            app.ShadowDungeon.restart();
            return;
        }

        // normal room updates
        super.update(player, input, dungeon);
    }

    // ---- render -----

    // render title and restart area
    public void renderOutside() {
        titleFont.drawString(title, titleXY.x, titleXY.y);
        restartAreaImage.draw(restartAreaXY.x, restartAreaXY.y);
    }

    @Override
    public void render() {
        super.render();
        renderOutside();
        renderProjectiles();
    }

    // ----- setter -----
    public void setTitle(String title) {
        this.title = title;
        setTitleSettings();
    }
}
