package breakoutadvance.scenes.menus;

import breakoutadvance.Breakout;
import breakoutadvance.scenes.PlayScene;
import breakoutadvance.utils.Constants;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import static breakoutadvance.utils.Fonts.getFont;

/**
 * This class represents the "Game Over" menu scene that appears after a player
 * loses the game. It displays a final score, and provides options to either
 * replay the game or return to the main menu.
 */
public class GameOverMenu extends AbstractMenu {

    /** Index of the currently selected menu item (e.g., 0 = first item). */
    private int selectedBtn = 0;

    /** Array of Text nodes representing the clickable menu items. */
    private final Text[] textItems;

    /**
     * Constructs the "Game Over" menu scene. Initializes menu items, displays
     * the player's score, and sets up event handling to navigate through and
     * select menu options.
     *
     * @param score The final score to display.
     */
    public GameOverMenu(int score) {
        // Call the parent constructor (AbstractMenu) to set up the scene
        super();

        // Create text elements
        Text gameOverText = createStyledText(
                "Game Over!",
                Constants.GAME_OVER_TEXT_STYLE,
                Constants.GAME_OVER_TEXT_COLOR,
                Color.BLACK
        );
        Text scoreText = createStyledText(
                "Score: " + score,
                Constants.SCORE_TEXT_STYLE,
                Constants.NORMAL_TEXT_COLOR,
                Color.BLACK
        );
        Text playAgainText = createMenuItem("Play Again", this::loadPlayScene);
        Text returnToMenuText = createMenuItem("Return to Menu", this::loadMainMenu);

        // Build layout elements
        VBox vboxTexts = new VBox(Constants.DEFAULT_VBOX_SPACING, gameOverText, scoreText);
        vboxTexts.setAlignment(Constants.DEFAULT_VBOX_ALIGNMENT);

        VBox vboxButtons = createMenuVBox(playAgainText, returnToMenuText);

        VBox mainVBox = new VBox(vboxTexts, vboxButtons);
        mainVBox.setAlignment(Pos.CENTER);
        mainVBox.setSpacing(Constants.DEFAULT_VBOX_SPACING);

        // Center and add the main layout container to the scene
        centerNodeInPane(mainVBox);
        getPane().getChildren().add(mainVBox);

        // Initialize the array of menu items
        textItems = new Text[] { playAgainText, returnToMenuText };

        // Highlight the first button initially
        selectText(selectedBtn);

        // Set up key-press events for navigation and selection
        setupKeyPressedEvents();

        // Attempt to load a background image (overrides black default if successful)
        addBackgroundImage();
    }

    /**
     * Highlights the currently selected menu item based on the given index,
     * and unhighlights the others.
     *
     * @param btnIndex The index of the menu item to highlight.
     */
    private void selectText(int btnIndex) {
        for (int i = 0; i < textItems.length; i++) {
            highlightMenuItem(textItems[i], i == btnIndex);
        }
    }

    /**
     * Highlights or unhighlights a specific Text node to reflect whether it
     * is currently selected by the user.
     *
     * @param item       The Text node representing a menu item.
     * @param isSelected True if the item should appear highlighted, false otherwise.
     */
    private void highlightMenuItem(Text item, boolean isSelected) {
        item.setFill(isSelected ? Constants.HIGHLIGHT_TEXT_COLOR : Constants.NORMAL_TEXT_COLOR);
    }

    /**
     * Creates a styled Text node using the default font, additional CSS styles,
     * fill color, and stroke color.
     *
     * @param content The text content to display.
     * @param style   A CSS style string (e.g., "-fx-font-size: 36px;").
     * @param fill    The fill color (text color).
     * @param stroke  The stroke color (outline color).
     * @return A newly created Text node.
     */
    private Text createStyledText(String content, String style, Color fill, Color stroke) {
        Text text = new Text(content);
        text.setFont(getFont(Constants.DEFAULT_FONT));
        text.setStyle(style);
        text.setFill(fill);
        text.setStroke(stroke);
        return text;
    }

    /**
     * Creates a VBox container that holds one or more Text nodes representing
     * menu items. The VBox uses default spacing, is centered, and applies a
     * background style defined in {@link Constants#BUTTON_BOX_STYLE}.
     *
     * @param texts One or more Text nodes to be arranged in the VBox.
     * @return A configured VBox containing the given Text nodes.
     */
    private VBox createMenuVBox(Text... texts) {
        VBox vbox = new VBox(Constants.DEFAULT_VBOX_SPACING, texts);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle(Constants.BUTTON_BOX_STYLE);
        return vbox;
    }

    /**
     * Navigates through the menu items in response to UP/DOWN or W/S key presses.
     * Cycles through the available items, updating which one is highlighted.
     *
     * @param code The {@link KeyCode} of the pressed key.
     */
    private void navigateMenu(KeyCode code) {
        if (code == KeyCode.UP || code == KeyCode.W) {
            selectedBtn = (selectedBtn - 1 + textItems.length) % textItems.length;
        } else if (code == KeyCode.DOWN || code == KeyCode.S) {
            selectedBtn = (selectedBtn + 1) % textItems.length;
        }
        selectText(selectedBtn);
    }

    /**
     * Sets up key-press events on the scene. Pressing ENTER triggers an action
     * based on the currently selected menu item, while UP/DOWN (or W/S) navigates
     * through the items.
     */
    public void setupKeyPressedEvents() {
        getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnEnter();
            } else {
                navigateMenu(event.getCode());
            }
        });
    }

    /**
     * Called when the ENTER key is pressed. Executes the action corresponding
     * to the currently selected menu item.
     */
    private void btnEnter() {
        switch (selectedBtn) {
            case 0 -> loadPlayScene();
            case 1 -> loadMainMenu();
            default -> throw new IllegalStateException("Unexpected button index: " + selectedBtn);
        }
    }

    /**
     * Loads the main menu scene, allowing the user to navigate to other parts
     * of the game.
     */
    private void loadMainMenu() {
        Breakout.getInstance().setCurrentScene(new MainMenu());
    }

    /**
     * Loads the play scene, effectively restarting the game.
     */
    private void loadPlayScene() {
        Breakout.getInstance().setCurrentScene(new PlayScene());
    }
}
