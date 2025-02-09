package breakoutadvance.scenes.menus;

import breakoutadvance.scenes.AbstractScene;
import breakoutadvance.utils.Constants;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import static breakoutadvance.utils.Fonts.getFont;
import static breakoutadvance.utils.Images.getImage;

/**
 * An abstract base class for all menu scenes in the game. This class extends
 * {@link AbstractScene}, which automatically sets the scene on the primary stage
 * via WindowUtils. Concrete menu classes (e.g., MainMenu, SettingsMenu) should
 * inherit from this class and define their own layout and behavior.
 */
public abstract class AbstractMenu extends AbstractScene {

    /**
     * Default constructor that initializes the menu scene on the primary stage,
     * sets a black fill color, and attempts to apply a background image.
     */
    public AbstractMenu() {
        // The parent constructor calls WindowUtils.getPrimaryStage()
        super();

        // Set the default scene fill to black
        this.getScene().setFill(Color.BLACK);

        // Attempt to load and set a background image for the menu
        addBackgroundImage();
    }

    /**
     * Attempts to load a background image from {@link Constants#MENU_BACKGROUND}.
     * If loading fails, a black background is used as a fallback.
     */
    protected void addBackgroundImage() {
        Image image;
        try {
            image = getImage(Constants.MENU_BACKGROUND);
        } catch (Exception e) {
            System.err.println("Error loading background image, using black background instead. " + e.getMessage());
            image = null;
        }
        setPaneBackground(image);
    }

    /**
     * Sets the background of the pane to the given image. If the image is null,
     * a solid black background is used instead.
     *
     * @param image The image to use as the background (may be null).
     */
    private void setPaneBackground(Image image) {
        if (image != null) {
            // Repeat the background image to fill the entire screen
            BackgroundImage backgroundImage = new BackgroundImage(
                    image,
                    BackgroundRepeat.REPEAT,
                    BackgroundRepeat.REPEAT,
                    BackgroundPosition.CENTER,
                    BackgroundSize.DEFAULT
            );
            getPane().setBackground(new Background(backgroundImage));
        } else {
            // Use a solid black background if loading failed or if the image is null
            getPane().setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));
        }
    }

    /**
     * Creates a {@link Text} object that serves as a clickable menu item.
     * The text uses a default font and color from {@link Constants}, and runs
     * the specified action when clicked.
     *
     * @param label         The text content to display.
     * @param onClickAction The code to be executed when this text is clicked.
     * @return A configured {@link Text} node representing the menu item.
     */
    protected Text createMenuItem(String label, Runnable onClickAction) {
        Text text = new Text(label);
        text.setFont(getFont(Constants.DEFAULT_FONT));
        text.setFill(Constants.NORMAL_TEXT_COLOR);
        text.setOnMouseClicked(e -> onClickAction.run());
        return text;
    }

    /**
     * Creates a new {@link VBox} with default spacing and alignment as defined
     * in {@link Constants}, then centers it within the scene's pane.
     *
     * @return The configured {@link VBox}.
     */
    protected VBox createVBox() {
        VBox vbox = new VBox(Constants.DEFAULT_VBOX_SPACING);
        vbox.setAlignment(Constants.DEFAULT_VBOX_ALIGNMENT);
        centerNodeInPane(vbox);

        return vbox;
    }

    /**
     * Centers a given {@link Region} (such as a VBox) within this menu's pane
     * by binding its layout position to half the difference in width/height.
     *
     * @param node The region to be centered.
     */
    protected void centerNodeInPane(Region node) {
        node.layoutXProperty().bind(getPane().widthProperty().subtract(node.widthProperty()).divide(2));
        node.layoutYProperty().bind(getPane().heightProperty().subtract(node.heightProperty()).divide(2));
    }
}
