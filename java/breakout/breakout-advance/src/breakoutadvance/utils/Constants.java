package breakoutadvance.utils;

import javafx.geometry.Pos;
import javafx.scene.paint.Color;

/**
 * This class is used to save constants used throughout the program
 */
public class Constants {

    // Paddle constants
    public static final int PADDLE_WIDTH = 200;
    public static final int PADDLE_HEIGHT = 16;
    public static final double MAX_PADDLE_SIZE_TO_SCREEN = 0.25;
    public static final double PADDLE_EXTEND_PER_POWERUP = 1.2;
    public static final String[] PADDLE_COLORS = new String[]{"beige", "black", "blue", "blue2", "brown", "green", "pink", "red", "yellow"};

    // Ball constants
    public static final int BALL_RADIUS = 16;
    public static final double MINIMUN_BALL_Y_VELOCITY = 0.2;
    public static final String[] BALL_COLORS = new String[]{"beige", "black", "blue", "blue2", "brown", "green", "pink", "red", "yellow"};

    // Asset paths
    public static final String DEFAULT_PATH = "assets/";
    public static final String IMAGE_PATH = DEFAULT_PATH+"img/";
    public static final String SOUND_PATH = DEFAULT_PATH+"sound/";
    public static final String BACKGROUND_FILEPATH = DEFAULT_PATH+"img/backgrounds/";
    public static final String BALL_FILEPATH = DEFAULT_PATH+"img/OpenGameArt/balls/";
    public static final String PADDLE_FILEPATH = DEFAULT_PATH+"img/OpenGameArt/paddles/";
    public static final String BLOCK_FILEPATH = DEFAULT_PATH+"img/OpenGameArt/bricks/";
    public static final String FONT_FILEPATH = DEFAULT_PATH+"fonts/";

    // Font constants
    public static final String DEFAULT_FONT = FONT_FILEPATH + "BlackwoodCastle.ttf";
    public static final int DEFAULT_FONT_SIZE = 64;

    // Color constants
    public static final Color NORMAL_TEXT_COLOR = Color.WHITE;
    public static final Color HIGHLIGHT_TEXT_COLOR = Color.YELLOW;

    // Menu Background
    public static final String MENU_BACKGROUND = BACKGROUND_FILEPATH + "background2.png";

    // Starting hearts
    public static final int STARTING_HEARTS = 3;

    // Grid constants
    public static final int OFFSET_BETWEEN_BLOCKS = 3;

    // Layout constants
    public static final int DEFAULT_VBOX_SPACING = 10;
    public static final Pos DEFAULT_VBOX_ALIGNMENT = Pos.CENTER;

    // High score
    public static final String HIGH_SCORE_STYLE = "-fx-font-size: "+ DEFAULT_FONT_SIZE*1.25 +"px;";
    public static final Color HIGH_SCORE_COLOR = Color.YELLOW;
    public static final Color HIGH_SCORE_STROKE_COLOR = Color.BLACK;
    public static final double HIGH_SCORE_STROKE_WIDTH = 3.0;

    // Game Over
    public static final String GAME_OVER_TEXT_STYLE = "-fx-font-size:" + DEFAULT_FONT_SIZE*2 + ";";
    public static final String SCORE_TEXT_STYLE = "-fx-font-size: " + DEFAULT_FONT_SIZE*1.125 + ";";
    public static final String BUTTON_BOX_STYLE = "-fx-border-color: white; -fx-border-width: 5; -fx-border-style: segments(10, 15, 15, 15) line-cap round;";
    public static final Color GAME_OVER_TEXT_COLOR = Color.DARKRED;

    // Settings menu
    public static final double SETTINGS_VOLUME_SLIDER_WIDTH = 300;
    public static final double SETTINGS_VOLUME_LABEL_WIDTH = 400;
    public static final String ARROW_FONT = FONT_FILEPATH + "BLACEB__.ttf";
    public static final int ARROW_FONT_SIZE = DEFAULT_FONT_SIZE*3/4;
    public static final double COLOR_SELECTOR_SPACING = 20;
}

