package breakoutadvance.scenes.menus;

import breakoutadvance.Breakout;
import breakoutadvance.scenes.PlayScene;
import breakoutadvance.utils.Constants;
import breakoutadvance.utils.WindowUtils;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import static breakoutadvance.utils.Fonts.getFont;

/**
 * The main menu scene displayed when the game is launched or returned to from
 * other scenes. It offers options to start the game, open settings, or quit.
 * This class extends {@link AbstractMenu}, which automatically initializes the
 * scene on the primary stage.
 */
public class MainMenu extends AbstractMenu {

    /** Stores the menu items (Text nodes) displayed to the user. */
    private Text[] textItems;

    /** The index of the currently highlighted menu item (e.g., 0 for the first item). */
    private int selectedBtn = 0;

    /**
     * Constructs the main menu, creates the menu items and high score display,
     * and sets up key-press events for navigation and selection.
     */
    public MainMenu() {
        // Calls the parent constructor to set up the scene
        super();

        // Create a VBox for menu items and populate it
        VBox vbox = createVBox();
        setupMenuItems(vbox);

        // Add the VBox to the scene's pane
        getPane().getChildren().add(vbox);

        // Display the high score at the bottom of the screen
        addHighScore();

        // Enable menu navigation via keyboard events
        setupKeyPressedEvents();
    }

    /**
     * Creates and configures the menu items displayed to the user, including
     * their associated actions (e.g., start game, open settings, quit).
     *
     * @param vbox The VBox container where the items will be added.
     */
    private void setupMenuItems(VBox vbox) {
        // Define the labels (text) and actions for each menu item
        String[] labels = {"Start", "Settings", "Quit"};
        Runnable[] actions = {this::startGame, this::openSettings, this::quitGame};

        // Create a Text item for each label and store it in textItems
        textItems = new Text[labels.length];
        for (int i = 0; i < labels.length; i++) {
            textItems[i] = createMenuItem(labels[i], actions[i]);
        }

        // Add all menu items to the VBox
        vbox.getChildren().addAll(textItems);

        // Highlight the initial menu item
        selectText(selectedBtn);
    }

    /**
     * Sets up key-press events on the scene to navigate the menu or select
     * the highlighted option. ENTER activates the selected item, whereas
     * UP/DOWN (or W/S) changes which item is highlighted.
     */
    private void setupKeyPressedEvents() {
        getScene().setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            if (code == KeyCode.ENTER) {
                btnEnter();
            } else {
                handleNavigation(code);
            }
        });
    }

    /**
     * Handles navigation through the menu items based on key presses:
     * - UP/W moves selection up one item.
     * - DOWN/S moves selection down one item.
     *
     * @param code The {@link KeyCode} of the key that was pressed.
     */
    private void handleNavigation(KeyCode code) {
        if (code == KeyCode.UP || code == KeyCode.W) {
            selectedBtn = (selectedBtn - 1 + textItems.length) % textItems.length;
        } else if (code == KeyCode.DOWN || code == KeyCode.S) {
            selectedBtn = (selectedBtn + 1) % textItems.length;
        }
        selectText(selectedBtn);
    }

    /**
     * Applies highlighting to the currently selected menu item and removes it
     * from the others.
     *
     * @param btnIndex The index of the item that should be highlighted.
     */
    private void selectText(int btnIndex) {
        for (int i = 0; i < textItems.length; i++) {
            highlightMenuItem(textItems[i], i == btnIndex);
        }
    }

    /**
     * Highlights the given {@link Text} node if it is the selected item,
     * otherwise resets it to the normal text color.
     *
     * @param item       The Text node to style.
     * @param isSelected True if this item should be highlighted.
     */
    private void highlightMenuItem(Text item, boolean isSelected) {
        item.setFill(isSelected ? Constants.HIGHLIGHT_TEXT_COLOR : Constants.NORMAL_TEXT_COLOR);
    }

    /**
     * Determines which action to perform when the ENTER key is pressed,
     * based on the currently selected menu item.
     */
    private void btnEnter() {
        switch (selectedBtn) {
            case 0 -> startGame();
            case 1 -> openSettings();
            case 2 -> quitGame();
            default -> throw new IllegalStateException("Unexpected button index: " + selectedBtn);
        }
    }

    /**
     * Displays the high score at the bottom of the screen.
     * Retrieves the score from the {@link breakoutadvance.persistentdata.DataManager} object
     * managed by the {@link Breakout} instance.
     */
    public void addHighScore() {
        Text displayHighScore = new Text("High Score: " + Breakout.getInstance().getDataManager().getData().getHighscore());
        displayHighScore.setFont(getFont(Constants.DEFAULT_FONT));
        displayHighScore.setStyle(Constants.HIGH_SCORE_STYLE);
        displayHighScore.setFill(Constants.HIGH_SCORE_COLOR);
        displayHighScore.setStrokeWidth(Constants.HIGH_SCORE_STROKE_WIDTH);
        displayHighScore.setStroke(Constants.HIGH_SCORE_STROKE_COLOR);

        getPane().getChildren().add(displayHighScore);
        alignHighScore(displayHighScore);
    }

    /**
     * Aligns the high score text at the bottom-left corner of the window,
     * dynamically adjusting the position if the window is resized.
     *
     * @param text The {@link Text} node displaying the high score.
     */
    private void alignHighScore(Text text) {
        // Recalculate the text position whenever its bounds change (e.g., on resize)
        text.boundsInLocalProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                double textHeight = newValue.getHeight();
                text.setX(10);
                text.setY(WindowUtils.getWindowHeight() - (textHeight / 2));
            });
        });
    }

    /**
     * Starts the game by transitioning to the {@link PlayScene}.
     */
    private void startGame() {
        try {
            Breakout.getInstance().setCurrentScene(new PlayScene());
        } catch (Exception e) {
            System.err.println("Failed to start the game: " + e.getMessage());
        }
    }

    /**
     * Opens the settings menu by transitioning to {@link SettingsMenu}.
     */
    private void openSettings() {
        Breakout.getInstance().setCurrentScene(new SettingsMenu());
    }

    /**
     * Exits the entire application.
     */
    private void quitGame() {
        System.exit(0);
    }
}
