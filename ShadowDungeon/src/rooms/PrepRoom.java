package rooms;

import bagel.*;
import bagel.util.Point;
import dungeon.Dungeon;
import entities.Player;


/**
 * prep room: child fo outside room class
 * the first room, no enemies no nothing
 */
public class PrepRoom extends OutsideRoom {

    // ----- prompts messages ----
    private final Font promptFont;
    private final String prompt;
    private final Point promptXY;

    // ----- constructors ----

    /**
     * create a prep room, has message prompts, and its own title
     * @param index room index in the dungeon
     */
    public PrepRoom(int index) {
        super(index);
        setTITLE(getConfig().TITLE); // set room tittle

        // set up prompt messages settings
        int promptSize = getConfig().PROMPT_FONT_SIZE;
        promptFont = new Font(getConfig().FONT_PATH, promptSize);
        this.prompt = getConfig().MOVE_MESSAGE;
        double promptY = getConfig().MOVE_MESSAGE_Y;
        double promptX = findStartX(prompt, promptFont);
        promptXY = new Point(promptX, promptY);

    }

    // ---- door handling ----
    private void unlockPrimaryDoor() {
        if (getNumOfDoors() > 0) {
            getDoors()[0].setUnlocked(true);
        }
    }

    // ---- updates ----

    @Override
    public void update(Player player, Input input, Dungeon dungeon) {
        super.update(player, input, dungeon);

        if (input.wasPressed(Keys.R)) {
            unlockPrimaryDoor();
        }
    }

    // ----- render ------

    public void renderPrompt() {
        promptFont.drawString(prompt, promptXY.x, promptXY.y);
    }

    @Override
    public void render() {
        super.render();
        renderPrompt();
    }
}
