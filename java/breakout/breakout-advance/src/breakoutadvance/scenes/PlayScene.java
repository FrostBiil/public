package breakoutadvance.scenes;

import breakoutadvance.core.Game;
import breakoutadvance.objects.Ball;
import breakoutadvance.objects.LivesDisplay;
import breakoutadvance.utils.Constants;
import breakoutadvance.utils.Images;
import breakoutadvance.utils.WindowUtils;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import static breakoutadvance.utils.Fonts.getFont;

/**
 * This class contains all graphical content in the game
 */
public class PlayScene extends AbstractScene {

    private final LivesDisplay livesDisplay; // Lives display
    private final Game game; // game instance
    private Text startOrPauseText; // Start or pause text
    private Text infoText; // Info text
    private Text addInfoText; // Additional info text
    private Text deathPauseText; // Text to display how many lives you have left
    private Text deathInfoText; // Info text when dead
    private Text displayScoreText; // Displaying score text


    /**
     * Constructor for play scene, consisting of basic setup
     */
    public PlayScene() {
        // Creating new instance of game
        this.game = new Game(this);

        // Adding background images
        this.addBackgroundImage();

        // Add start or pause text
        addStartOrPauseText();

        // Add death note text
        addDeathPauseText();

        // Setup Keyboard events
        setupKeyPressedEvents();

        // Add lives
        this.livesDisplay = new LivesDisplay();
        this.livesDisplay.updateLives(this, this.game.getLives());

        // Add score
        addDisplayScore();
    }

    /**
     * Setting up key pressed events, making the user able to press keys,
     * and call a function which determines what happens
     */
    public void setupKeyPressedEvents() {
        // When key is pressed
        this.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                // When pressed ENTER, start the game, or pause if the game has been started
                this.game.togglePlaying();
                addInfoText.setVisible(false);

                if (!this.game.isPlaying()) this.startOrPauseText.setText("Press ENTER to continue");
            }
            if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.KP_LEFT || event.getCode() == KeyCode.A) {
                // Move left if left key is pressed
                this.game.getPaddle().setMoveLeft(true);
            }

            if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.KP_RIGHT || event.getCode() == KeyCode.D) {
                // Move right if right key is pressed
                this.game.getPaddle().setMoveRight(true);
            }
        });

        // When key is released
        this.getScene().setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.KP_LEFT || event.getCode() == KeyCode.A) {
                // Stop moving left, when left key is released
                this.game.getPaddle().setMoveLeft(false);
            }

            if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.KP_RIGHT || event.getCode() == KeyCode.D) {
                // Stop moving right, when right key is released
                this.game.getPaddle().setMoveRight(false);
            }
        });
    }

    /**
     * Trying to add background image
     * Will fill the screen with a black color, and display this, if background image can't load
     */
    public void addBackgroundImage() {
        // Filling background
        this.getScene().setFill(Color.BLACK);

        // Trying to set background image
        try {
            // Image
            Image image = Images.getImage(Constants.BACKGROUND_FILEPATH + "background12.png");

            // Will repeat the background to fill out the whole screen
            BackgroundImage backgroundimage = new BackgroundImage(image,
                    BackgroundRepeat.REPEAT,
                    BackgroundRepeat.REPEAT,
                    BackgroundPosition.CENTER,
                    BackgroundSize.DEFAULT);

            // Setting background
            Background background = new Background(backgroundimage);

            // Adding background to pane
            this.getPane().setBackground(background);
        } catch (Exception ex) {
            System.err.println("Error loading background image");
        }
    }

    /**
     * Adding start text
     */
    public void addStartOrPauseText() {
        // How to start the game text
        this.startOrPauseText = new Text("Press ENTER to start");
        this.startOrPauseText.setFont(getFont(Constants.FONT_FILEPATH + "BlackwoodCastle.ttf"));
        this.startOrPauseText.setStyle("-fx-font-size: 48px; -fx-font-weight: bold;");
        this.startOrPauseText.setFill(Color.WHITE);
        this.startOrPauseText.setStroke(Color.BLACK);
        this.startOrPauseText.setStrokeWidth(0.75);
        this.getPane().getChildren().add(this.startOrPauseText);

        // Movement info text
        this.infoText = new Text("Press 'a' to move left and 'd' to move right");
        this.infoText.setFont(getFont(Constants.FONT_FILEPATH + "BlackwoodCastle.ttf"));
        this.infoText.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");
        this.infoText.setFill(Color.WHITE);
        this.infoText.setStroke(Color.BLACK);
        this.infoText.setStrokeWidth(0.5);
        this.getPane().getChildren().add(this.infoText);

        // How to pause text
        this.addInfoText = new Text("When started, you can press ENTER to pause the game");
        this.addInfoText.setFont(getFont(Constants.FONT_FILEPATH + "BlackwoodCastle.ttf"));
        this.addInfoText.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");
        this.addInfoText.setFill(Color.WHITE);
        this.addInfoText.setStroke(Color.BLACK);
        this.addInfoText.setStrokeWidth(0.5);
        this.getPane().getChildren().add(this.addInfoText);

        // Center the text after it is added to the scene as it needs to be visible and text changes
        // This makes sure that it is centered no matter what
        addCenterTextListener(this.startOrPauseText, 2, 2);
        addCenterTextListener(this.infoText, 2, 1.8);
        addCenterTextListener(this.addInfoText, 2, 1.6);
    }

    /**
     * Adding text when player loses a life
     */
    public void addDeathPauseText() {
        // Text to display start or pause information
        this.deathPauseText = new Text("You Died. You have " + this.game.getLives() + " left.");
        this.deathPauseText.setFont(getFont(Constants.FONT_FILEPATH + "BlackwoodCastle.ttf"));
        this.deathPauseText.setStyle("-fx-font-size: 48px; -fx-font-weight: bold;");
        this.deathPauseText.setFill(Color.WHITE);
        this.deathPauseText.setStroke(Color.BLACK);
        this.deathPauseText.setStrokeWidth(0.5);
        this.getPane().getChildren().add(this.deathPauseText);
        this.deathPauseText.setVisible(false);

        // Text to display controls
        this.deathInfoText = new Text("Press Enter to start again");
        this.deathInfoText.setFont(getFont(Constants.FONT_FILEPATH + "BlackwoodCastle.ttf"));
        this.deathInfoText.setStyle("-fx-font-size: 32px;-fx-font-weight: bold;");
        this.deathInfoText.setFill(Color.WHITE);
        this.deathInfoText.setStroke(Color.BLACK);
        this.deathInfoText.setStrokeWidth(0.5);
        this.getPane().getChildren().add(this.deathInfoText);
        this.deathInfoText.setVisible(false);

        // Center the text after it is added to the scene as it needs to be visible and text changes
        // This makes sure that it is centered no matter what
        addCenterTextListener(this.deathPauseText, 2, 2);
        addCenterTextListener(this.deathInfoText, 2, 1.8);
    }

    /**
     * Text to display score
     */
    public void addDisplayScore() {
        // Text to display start or pause information
        this.displayScoreText = new Text("Score: " + this.game.getScore());
        this.displayScoreText.setFont(getFont(Constants.FONT_FILEPATH + "BlackwoodCastle.ttf", 80));
        this.displayScoreText.setFill(Color.WHITE);
        this.displayScoreText.setStroke(Color.BLACK);
        this.displayScoreText.setStrokeWidth(1.5);
        this.displayScoreText.setVisible(true);
        this.getPane().getChildren().add(this.displayScoreText);

        // Make sure the text will stay at the same relative position, when window size is changed
        addCenterTextListener(this.displayScoreText, 20, 1);
    }

    /**
     * Makes sure the text is dynamic
     *
     * @param text Text given
     * @param xOffset x-offset
     * @param yOffSet y-offset
     */
    private void addCenterTextListener(Text text, double xOffset, double yOffSet) {
        // If text is null, return
        if (text == null) return;

        // Make sure the text will stay at the same relative position, when window size is changed
        text.boundsInLocalProperty().addListener((observable, oldValue, newValue) -> {

            // Make sure bounds of text are updated correctly before calculating new position
            Platform.runLater(() -> {
                double textWidth = newValue.getWidth();
                double textHeight = newValue.getHeight();

                text.setX((WindowUtils.getWindowWidth() - textWidth) / xOffset);
                text.setY((WindowUtils.getWindowHeight() - textHeight) / yOffSet);
            });
        });
    }


    /**
     * Every tick, this function will run
     */
    public void onTick() {
        // Handle text on screen
        if (handleTextChangeAndVisibility()) return;

        // Check for Victory and return if victory has been reached
        if (this.game.checkVictory()) return;

        // Check if each ball is out of bounds
        this.game.checkBallOutOfBounds();

        // Move paddle left
        if (this.game.getPaddle().isMoveLeft()) {
            this.game.getPaddle().updatePosXLeft();
        }

        // Move paddle right
        if (this.game.getPaddle().isMoveRight()) {
            this.game.getPaddle().updatePosXRight();
        }

        // Check Collisions for paddle and ball
        this.game.checkBallPaddleCollision();

        // Handle ontick for each ball
        this.game.getBalls().forEach(Ball::onTick);

        // Remove powerups under screen or collides with paddle
        this.game.checkPowerupCollision();

        // handle ontick for each powerup
        this.game.getPowerups().forEach(p -> p.onTick(this.game.getLevel().getPowerUpSpeed()));
    }

    /**
     * Handles when text should be displayed on the screen.
     * It also handles which text should be displayed!
     *
     * @return if text should be displayed and stop game from running when displayed
     */
    private boolean handleTextChangeAndVisibility() {
        // If game is not playing, and player has died, display deathInfo Texts
        // If game is not playing, and player has not died, display startInfo Texts
        if (!this.game.isPlaying()) {
            if (this.game.isDied()) {
                startOrPauseText.setVisible(false);
                infoText.setVisible(false);
                deathPauseText.setVisible(true);
                deathInfoText.setVisible(true);
            } else {
                deathInfoText.setVisible(false);
                deathPauseText.setVisible(false);
                startOrPauseText.setVisible(true);
                infoText.setVisible(true);
            }
            return true;
        }

        // Dont display info text
        startOrPauseText.setVisible(false);
        infoText.setVisible(false);

        // Setting deathInfo Texts visibility to false
        this.game.setDied(false);
        deathPauseText.setVisible(false);
        deathInfoText.setVisible(false);
        return false;
    }

    public Text getDisplayScoreText() {
        return displayScoreText;
    }

    public LivesDisplay getLivesDisplay() {
        return livesDisplay;
    }

    public Text getDeathPauseText() {
        return deathPauseText;
    }

    public Game getGame() {
        return game;
    }
}
