package config;

import java.util.Properties;
import bagel.util.Point;
import utils.IOUtils;

/**
 * contains all the important game settings
 */
public class GameConfig {

    // ----- settings -----

    // settings
    private static GameConfig instance;
    private final Properties gameProps;
    private final Properties messageProps;

    // window settings
    public final int WINDOW_WIDTH;
    public final int WINDOW_HEIGHT;
    public final int MAX_FRAMES;

    // font settings
    public final String FONT_PATH;
    public final int TITLE_FONT_SIZE;
    public final double TITLE_Y;
    public final int PROMPT_FONT_SIZE;
    public final double MOVE_MESSAGE_Y;
    public final int PLAYER_STATS_FONT_SIZE;

    // player defaults settings
    public final int INITIAL_HEALTH;
    public final int INITIAL_COINS;
    public final int MOVING_SPEED;
    public final double RIVER_DAMAGE_PER_FRAME;

    // player positions
    public final Point PLAYER_START_POS;

    // stats positions
    public final Point HEALTH_STAT_POS;
    public final Point COIN_STAT_POS;
    public final Point RESTART_AREA_PREP;
    public final Point RESTART_AREA_END;

    // door positions
    public final Point DOOR_PREP;
    public final Point DOOR_END;
    public final Point PRIMARY_DOOR_A;
    public final Point PRIMARY_DOOR_B;
    public final Point SECONDARY_DOOR_A;
    public final Point SECONDARY_DOOR_B;

    // messages
    public final String TITLE;
    public final String MOVE_MESSAGE;
    public final String HEALTH_DISPLAY;
    public final String COIN_DISPLAY;
    public final String GAME_END_LOST;
    public final String GAME_END_WON;

    // ----- constructor -----

    /**
     * initialize all the game configurations
     * @param gameProps game configurations
     * @param messageProps message contents
     */
    public GameConfig(Properties gameProps, Properties messageProps) {
        this.gameProps = gameProps;
        this.messageProps = messageProps;
        instance = this;

        // window settings
        WINDOW_WIDTH = Integer.parseInt(gameProps.getProperty("window.width"));
        WINDOW_HEIGHT = Integer.parseInt(gameProps.getProperty("window.height"));
        MAX_FRAMES = Integer.parseInt(gameProps.getProperty("gamePlay.maxFrames"));

        // font settings
        FONT_PATH = gameProps.getProperty("font");
        TITLE_FONT_SIZE = Integer.parseInt(gameProps.getProperty("title.fontSize"));
        TITLE_Y = Double.parseDouble(gameProps.getProperty("title.y"));
        PROMPT_FONT_SIZE = Integer.parseInt(gameProps.getProperty("prompt.fontSize"));
        MOVE_MESSAGE_Y = Double.parseDouble(gameProps.getProperty("moveMessage.y"));
        PLAYER_STATS_FONT_SIZE = Integer.parseInt(gameProps.getProperty("playerStats.fontSize"));

        // player default settings
        INITIAL_HEALTH = Integer.parseInt(gameProps.getProperty("initialHealth"));
        INITIAL_COINS = Integer.parseInt(gameProps.getProperty("initialCoins"));
        MOVING_SPEED = Integer.parseInt(gameProps.getProperty("movingSpeed"));
        RIVER_DAMAGE_PER_FRAME = Double.parseDouble(gameProps.getProperty("riverDamagePerFrame"));

        // player positions
        PLAYER_START_POS = IOUtils.getPointProperty(gameProps, "player.start");

        // stats positions
        HEALTH_STAT_POS = IOUtils.getPointProperty(gameProps, "healthStat");
        COIN_STAT_POS = IOUtils.getPointProperty(gameProps, "coinStat");
        RESTART_AREA_PREP = IOUtils.getPointProperty(gameProps, "restartarea.prep");
        RESTART_AREA_END = IOUtils.getPointProperty(gameProps, "restartarea.end");

        // door positions
        DOOR_PREP = IOUtils.getPointProperty(gameProps, "door.prep");
        DOOR_END = IOUtils.getPointProperty(gameProps, "door.end");
        PRIMARY_DOOR_A = IOUtils.getPointProperty(gameProps, "primarydoor.A");
        PRIMARY_DOOR_B = IOUtils.getPointProperty(gameProps, "primarydoor.B");
        SECONDARY_DOOR_A = IOUtils.getPointProperty(gameProps, "secondarydoor.A");
        SECONDARY_DOOR_B = IOUtils.getPointProperty(gameProps, "secondarydoor.B");

        // messages
        TITLE = messageProps.getProperty("title");
        MOVE_MESSAGE = messageProps.getProperty("moveMessage");
        HEALTH_DISPLAY = messageProps.getProperty("healthDisplay");
        COIN_DISPLAY = messageProps.getProperty("coinDisplay");
        GAME_END_LOST = messageProps.getProperty("gameEnd.lost");
        GAME_END_WON = messageProps.getProperty("gameEnd.won");
    }

    // ----- getters -----
    public static GameConfig getInstance() {return instance;}
    public Properties getGameProps() {return gameProps;}
    public Properties getMessageProps() {return messageProps;}

}