package rooms;

import bagel.*;
import bagel.util.Point;
import dungeon.Dungeon;
import entities.player.*;


/**
 * Represents the prep room in the dungeon.
 *
 * This is the first room, with no enemies, used for displaying prompts and allowing the player
 * to choose a character (Marine or Robot).
 */
public class PrepRoom extends OutsideRoom {

    private final int N_CHARACTERS = 2;

    // ----- font settings ----
    private final Font promptFont = new Font(getConfig().FONT_PATH, getConfig().PROMPT_FONT_SIZE);
    private final Font spriteFont = new Font(getConfig().FONT_PATH, getConfig().PLAYER_STATS_FONT_SIZE);

    // ---- messages ----
    private final String prompt = getConfig().MOVE_MESSAGE;
    private final String[] spriteText = {getConfig().MARINE_DESCRIPTION, getConfig().ROBOT_DESCRIPTION};
    private final String selectMes = getConfig().SELECT_MESSAGE;

    // ---- positions of messages ----
    private final Point promptXY;
    private final Point[] spriteXY = {getConfig().MARINE_POS, getConfig().ROBOT_POS};
    private final Point[] spriteTextXY = {getConfig().MARINE_MESSAGE_POS, getConfig().ROBOT_MESSAGE_POS};
    private final Point selectXY;

    // ---- images ----
    private final Image[] spriteImage = {new Image("res/marine_sprite.png"),
            new Image("res/robot_sprite.png")};

    // ----- constructors ----

    /**
     * Constructs a prep room.
     *
     * @param index the index of this room in the dungeon
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

    /**
     * Updates the prep room state.
     *
     * Handles character selection input and unlocks the door when a character is chosen.
     *
     * @param player the player's character
     * @param input the current input state
     * @param dungeon the dungeon this room is part of
     */
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

    private void renderPrep() {
        promptFont.drawString(prompt, promptXY.x, promptXY.y);
        promptFont.drawString(selectMes, selectXY.x, selectXY.y);
        for (int i = 0; i < N_CHARACTERS; i++) {
            spriteFont.drawString(spriteText[i], spriteTextXY[i].x, spriteTextXY[i].y);
            spriteImage[i].draw(spriteXY[i].x, spriteXY[i].y);
        }
    }

    /**
     * Renders the prep room, including prompts, characters, and projectiles.
     */
    @Override
    public void render() {
        super.render();
        renderPrep();
        renderProjectiles();
    }
}
