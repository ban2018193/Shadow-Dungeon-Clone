package rooms;

import bagel.*;
import bagel.util.Point;
import dungeon.Dungeon;
import entities.player.*;


/**
 * prep room: child fo outside room class
 * the first room, no enemies no nothing
 */
public class PrepRoom extends OutsideRoom {

    private final int N_CHARACTERS = 2;
    // ----- prompts messages ----
    private final Font promptFont = new Font(getConfig().FONT_PATH, getConfig().PROMPT_FONT_SIZE);
    private final Font spriteFont = new Font(getConfig().FONT_PATH, getConfig().PLAYER_STATS_FONT_SIZE);
    private final String prompt = getConfig().MOVE_MESSAGE;
    private final Point promptXY;
    private final Image[] spriteImage = {new Image("res/marine_sprite.png"),
            new Image("res/robot_sprite.png")};
    private final Point[] spriteXY = {getConfig().MARINE_POS, getConfig().ROBOT_POS};
    private final Point[] spriteTextXY = {getConfig().MARINE_MESSAGE_POS, getConfig().ROBOT_MESSAGE_POS};
    private final String[] spriteText = {getConfig().MARINE_DESCRIPTION, getConfig().ROBOT_DESCRIPTION};
    private final String selectMes = getConfig().SELECT_MESSAGE;
    private final Point selectXY;

    // ----- constructors ----
    /**
     * create a prep room, has message prompts, and its own title
     * @param index room index in the dungeon
     */
    public PrepRoom(int index) {
        super(index);
        setTitle(getConfig().TITLE); // set room tittle
        // set up prompt messages settings
        double promptY = getConfig().MOVE_MESSAGE_Y;
        double promptX = findStartX(prompt, promptFont);
        double selectY = getConfig().SELECT_MESSAGE_Y;
        double selectX = findStartX(selectMes, promptFont);
        promptXY = new Point(promptX, promptY);
        selectXY = new Point(selectX, selectY);
    }

    // ---- door handling ----
    private boolean unlockPrimaryDoor() {
        if (getNumOfDoors() > 0) {
            if (!getDoors().get(0).isBlockable()){
                return false;
            }
            getDoors().get(0).setUnlocked(true);
        }
        return true;
    }

    // ---- updates ----

    @Override
    public void update(PlayerCharacter player, Input input, Dungeon dungeon) {
        super.update(player, input, dungeon);

        if (player.getPlayer().hasChoseChar() && input.wasPressed(Keys.R)) {
            if (unlockPrimaryDoor()) {
                return;
            }
        }

        if (input.wasPressed(Keys.R)) {
            player.changeCharacter(new Robot(player.getPlayer()));
        }else if (input.wasPressed(Keys.M)) {
            player.changeCharacter(new Marine(player.getPlayer()));
        }

    }

    // ----- render ------

    public void renderPrep() {
        promptFont.drawString(prompt, promptXY.x, promptXY.y);
        promptFont.drawString(selectMes, selectXY.x, selectXY.y);
        for (int i = 0; i < N_CHARACTERS; i++) {
            spriteFont.drawString(spriteText[i], spriteTextXY[i].x, spriteTextXY[i].y);
            spriteImage[i].draw(spriteXY[i].x, spriteXY[i].y);
        }
    }

    @Override
    public void render() {
        super.render();
        renderPrep();
    }
}
