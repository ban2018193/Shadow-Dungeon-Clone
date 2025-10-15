package config;

import java.util.Properties;
import bagel.util.Point;
import utils.IOUtils;

/**
 * contains all the important game settings
 */
public class GameConfig {

    // ----- settings -----
    private final Properties gameProps = IOUtils.readPropertiesFile("res/app.properties");
    private final Properties messageProps = IOUtils.readPropertiesFile("res/message.properties");
    private static GameConfig instance = null;

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
    public final double SELECT_MESSAGE_Y;
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

    // character sprite positions
    public final Point ROBOT_POS;
    public final Point MARINE_POS;

    // store position
    public final Point STORE_POS;

    // UI element positions
    public final Point WEAPON_STAT_POS;
    public final Point KEY_STAT_POS;
    public final Point MARINE_MESSAGE_POS;
    public final Point ROBOT_MESSAGE_POS;
    public final Point END_MESSAGE_POS;

    // game properties
    public final int ROBOT_EXTRA_COIN;
    public final int BASKET_COIN;

    // bullet kin properties
    public final int BULLET_KIN_HEALTH;
    public final int BULLET_KIN_COIN;
    public final int BULLET_KIN_SHOOT_FREQUENCY;

    // ashen bullet kin properties
    public final int ASHEN_BULLET_KIN_HEALTH;
    public final int ASHEN_BULLET_KIN_COIN;
    public final int ASHEN_BULLET_KIN_SHOOT_FREQUENCY;

    // key bullet kin properties
    public final int KEY_BULLET_KIN_HEALTH;
    public final int KEY_BULLET_KIN_SPEED;

    // fireball properties
    public final int FIREBALL_DAMAGE;
    public final int FIREBALL_SPEED;

    // shop and weapon properties
    public final int HEALTH_PURCHASE;
    public final int HEALTH_BONUS;
    public final int WEAPON_PURCHASE;
    public final int WEAPON_STANDARD_DAMAGE;
    public final int WEAPON_ADVANCE_DAMAGE;
    public final int WEAPON_ELITE_DAMAGE;

    // bullet properties
    public final double BULLET_SPEED;
    public final int BULLET_FREQ;

    // messages
    public final String TITLE;
    public final String MOVE_MESSAGE;
    public final String SELECT_MESSAGE;
    public final String HEALTH_DISPLAY;
    public final String COIN_DISPLAY;
    public final String WEAPON_DISPLAY;
    public final String KEY_DISPLAY;
    public final String ROBOT_DESCRIPTION;
    public final String MARINE_DESCRIPTION;
    public final String GAME_END_LOST;
    public final String GAME_END_WON;

    // ----- constructor -----

    /**
     * initialize all the game configurations
     */
    private GameConfig() {

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
        SELECT_MESSAGE_Y = Double.parseDouble(gameProps.getProperty("selectMessage.y"));
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

        // character sprite positions
        ROBOT_POS = IOUtils.getPointProperty(gameProps, "Robot");
        MARINE_POS = IOUtils.getPointProperty(gameProps, "Marine");

        // store position
        STORE_POS = IOUtils.getPointProperty(gameProps, "store");

        // UI element positions
        WEAPON_STAT_POS = IOUtils.getPointProperty(gameProps, "weaponStat");
        KEY_STAT_POS = IOUtils.getPointProperty(gameProps, "keyStat");
        MARINE_MESSAGE_POS = IOUtils.getPointProperty(gameProps, "marineMessage");
        ROBOT_MESSAGE_POS = IOUtils.getPointProperty(gameProps, "robotMessage");
        END_MESSAGE_POS = IOUtils.getPointProperty(gameProps, "endmessage");

        // game properties
        ROBOT_EXTRA_COIN = Integer.parseInt(gameProps.getProperty("robotExtraCoin"));
        BASKET_COIN = Integer.parseInt(gameProps.getProperty("basketCoin"));

        // bullet kin properties
        BULLET_KIN_HEALTH = Integer.parseInt(gameProps.getProperty("bulletKinHealth"));
        BULLET_KIN_COIN = Integer.parseInt(gameProps.getProperty("bulletKinCoin"));
        BULLET_KIN_SHOOT_FREQUENCY = Integer.parseInt(gameProps.getProperty("bulletKinShootFrequency"));

        // ashen bullet kin properties
        ASHEN_BULLET_KIN_HEALTH = Integer.parseInt(gameProps.getProperty("ashenBulletKinHealth"));
        ASHEN_BULLET_KIN_COIN = Integer.parseInt(gameProps.getProperty("ashenBulletKinCoin"));
        ASHEN_BULLET_KIN_SHOOT_FREQUENCY = Integer.parseInt(gameProps.getProperty("ashenBulletKinShootFrequency"));

        // key bullet kin properties
        KEY_BULLET_KIN_HEALTH = Integer.parseInt(gameProps.getProperty("keyBulletKinHealth"));
        KEY_BULLET_KIN_SPEED = Integer.parseInt(gameProps.getProperty("keyBulletKinSpeed"));

        // fireball properties
        FIREBALL_DAMAGE = Integer.parseInt(gameProps.getProperty("fireballDamage"));
        FIREBALL_SPEED = Integer.parseInt(gameProps.getProperty("fireballSpeed"));

        // shop and weapon properties
        HEALTH_PURCHASE = Integer.parseInt(gameProps.getProperty("healthPurchase"));
        HEALTH_BONUS = Integer.parseInt(gameProps.getProperty("healthBonus"));
        WEAPON_PURCHASE = Integer.parseInt(gameProps.getProperty("weaponPurchase"));
        WEAPON_STANDARD_DAMAGE = Integer.parseInt(gameProps.getProperty("weaponStandardDamage"));
        WEAPON_ADVANCE_DAMAGE = Integer.parseInt(gameProps.getProperty("weaponAdvanceDamage"));
        WEAPON_ELITE_DAMAGE = Integer.parseInt(gameProps.getProperty("weaponEliteDamage"));

        // bullet properties
        BULLET_SPEED = Double.parseDouble(gameProps.getProperty("bulletSpeed"));
        BULLET_FREQ = Integer.parseInt(gameProps.getProperty("bulletFreq"));

        // messages
        TITLE = messageProps.getProperty("title");
        MOVE_MESSAGE = messageProps.getProperty("moveMessage");
        SELECT_MESSAGE = messageProps.getProperty("selectMessage");
        HEALTH_DISPLAY = messageProps.getProperty("healthDisplay");
        COIN_DISPLAY = messageProps.getProperty("coinDisplay");
        WEAPON_DISPLAY = messageProps.getProperty("weaponDisplay");
        KEY_DISPLAY = messageProps.getProperty("keyDisplay");
        ROBOT_DESCRIPTION = messageProps.getProperty("robotDescription");
        MARINE_DESCRIPTION = messageProps.getProperty("marineDescription");
        GAME_END_LOST = messageProps.getProperty("gameEnd.lost");
        GAME_END_WON = messageProps.getProperty("gameEnd.won");
    }

    // ----- getters -----
    public static GameConfig getInstance() {
        if (instance == null) {
            instance = new GameConfig();
        }
        return instance;
    }
    public Properties getGameProps() {return gameProps;}
    public Properties getMessageProps() {return messageProps;}

    public Point[] getPos(String key){
        return IOUtils.parseMultipleCoords(gameProps.getProperty(key));
    }

    public String[] getTreasureInfo(String treasureKey) {
        return IOUtils.parseContents(gameProps.getProperty(treasureKey), ";");
    }

}