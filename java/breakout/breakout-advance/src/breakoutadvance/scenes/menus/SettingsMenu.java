package breakoutadvance.scenes.menus;

import breakoutadvance.Breakout;
import breakoutadvance.scenes.components.UIComponentFactory;
import breakoutadvance.utils.Constants;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.List;

import static breakoutadvance.utils.Fonts.getFont;
import static breakoutadvance.utils.Images.getImage;

/**
 * The Settings menu scene, allowing the user to adjust various game options
 * such as volume, mute status, and the appearance (colors) of the ball and paddle.
 * Inherits from {@link AbstractMenu}, which handles scene initialization.
 */
public class SettingsMenu extends AbstractMenu {

    /** The current selected color index for the ball. */
    private int currentBallColorIndex;

    /** The current selected color index for the paddle. */
    private int currentPaddleColorIndex;

    /** Displays the currently selected ball color. */
    private ImageView ballImageView;

    /** Displays the currently selected paddle color. */
    private ImageView paddleImageView;

    /**
     * Constructs the Settings menu, creating UI components for volume control,
     * mute settings, ball/paddle color selectors, and a "Back" button.
     */
    public SettingsMenu() {
        super(); // Calls AbstractMenu constructor, which sets up the scene

        // Create a VBox for holding all settings elements, applying styling
        VBox vbox = createVBox();
        vbox.setStyle("-fx-padding: 20;");
        vbox.setAlignment(Pos.CENTER);

        // Create and configure the title
        Text title = UIComponentFactory.createText(
                "Settings",
                Constants.DEFAULT_FONT,
                Constants.DEFAULT_FONT_SIZE,
                Color.WHITE
        );

        // Create UI components for volume and mute
        VBox volumeBox = createVolumeBox();
        CheckBox muteCheckBox = createMuteCheckBox();

        // Create UI components to cycle through ball and paddle colors
        HBox ballColorSelector = createColorSelector(Constants.BALL_COLORS, currentBallColorIndex, true);
        HBox paddleColorSelector = createColorSelector(Constants.PADDLE_COLORS, currentPaddleColorIndex, false);

        // Create a "Back" button that returns the user to the MainMenu
        Text backBtn = createBackButton();

        // Add all components to the VBox and then to the scene
        vbox.getChildren().addAll(
                title,
                volumeBox,
                muteCheckBox,
                ballColorSelector,
                paddleColorSelector,
                backBtn
        );
        getPane().getChildren().add(vbox);

        // Set up key-press events for ESC or ENTER to save changes and return to the MainMenu
        setupKeyPressedEvents();
    }

    /**
     * Creates a VBox containing a Slider and a Label to adjust and display the volume level.
     * @return A VBox containing the volume slider and its label.
     */
    private VBox createVolumeBox() {
        // Create a slider for volume control
        Slider volumeSlider = UIComponentFactory.createSlider(
                0,
                100,
                Breakout.getInstance().getDataManager().getData().getVolume(),
                Constants.SETTINGS_VOLUME_SLIDER_WIDTH,
                true,
                10.0,
                true
        );

        // Create a label showing the current volume in percentage
        Label volumeLabel = UIComponentFactory.createLabel(
                String.format("Volume: %3d %%", (int) volumeSlider.getValue()),
                20,
                getFont(Constants.DEFAULT_FONT)
        );
        volumeLabel.setMinWidth(Constants.SETTINGS_VOLUME_LABEL_WIDTH);

        // Update the label and save user data whenever the slider value changes
        volumeSlider.valueProperty().addListener((observable, oldVal, newVal) -> {
            volumeLabel.setText(String.format("Volume: %3d %%", newVal.intValue()));
            Breakout.getInstance().getDataManager().getData().setVolume(newVal.intValue());
            Breakout.getInstance().getDataManager().saveData();
        });

        return new VBox(10, volumeSlider, volumeLabel);
    }

    /**
     * Creates a CheckBox that toggles mute on or off. Updates the game's data
     * store when checked or unchecked.
     * @return A CheckBox controlling the mute setting.
     */
    private CheckBox createMuteCheckBox() {
        CheckBox muteCheckBox = UIComponentFactory.createCheckBox(
                "Mute",
                Breakout.getInstance().getDataManager().getData().isMute(),
                getFont(Constants.DEFAULT_FONT)
        );
        muteCheckBox.selectedProperty().addListener((observable, oldVal, newVal) -> {
            Breakout.getInstance().getDataManager().getData().setMute(newVal);
            Breakout.getInstance().getDataManager().saveData();
        });
        return muteCheckBox;
    }

    /**
     * Creates a clickable "Back" button that returns the user to the MainMenu.
     * @return A Text node representing the back button.
     */
    private Text createBackButton() {
        Text backBtn = UIComponentFactory.createText(
                "Back",
                Constants.DEFAULT_FONT,
                Constants.DEFAULT_FONT_SIZE,
                Constants.HIGHLIGHT_TEXT_COLOR
        );
        backBtn.setOnMouseClicked(event -> Breakout.getInstance().setCurrentScene(new MainMenu()));
        return backBtn;
    }

    /**
     * Configures key-press events so that pressing ESC or ENTER will save the
     * current settings and return the user to the MainMenu.
     */
    private void setupKeyPressedEvents() {
        getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE || event.getCode() == KeyCode.ENTER) {
                Breakout.getInstance().getDataManager().saveData();
                Breakout.getInstance().setCurrentScene(new MainMenu());
            }
        });
    }

    /**
     * Updates the provided ImageView with a new image path corresponding to a
     * color change. Saves the updated configuration to the user data.
     *
     * @param imageView The ImageView to update.
     * @param newColor  The new color name (e.g., "Blue", "Red").
     * @param filePath  The file path prefix for the resource (ball or paddle).
     */
    private void updateColorImageView(ImageView imageView, String newColor, String filePath) {
        imageView.setImage(getImage(filePath + newColor + ".png"));
        Breakout.getInstance().getDataManager().saveData();
    }

    /**
     * Changes the color index for either the ball or the paddle and updates
     * the associated ImageView to reflect the newly selected color.
     *
     * @param colors    A list of possible colors (e.g., ["Red", "Blue", "Green"]).
     * @param direction The increment or decrement step (-1 for previous color, +1 for next color).
     * @param isBall    True if changing the ball's color, false if changing the paddle's color.
     * @param <T>       The type of the color entries (usually String).
     */
    private <T> void changeColor(List<T> colors, int direction, boolean isBall) {
        // Determine the current index from ball or paddle
        int currentIndex = isBall ? currentBallColorIndex : currentPaddleColorIndex;

        // Compute the new index in a cyclic manner
        currentIndex = (currentIndex + direction + colors.size()) % colors.size();

        // Retrieve the new color and update data accordingly
        String newColor = colors.get(currentIndex).toString();
        if (isBall) {
            currentBallColorIndex = currentIndex;
            Breakout.getInstance().getDataManager().getData().setBallColor(newColor);
            updateColorImageView(ballImageView, newColor, Constants.BALL_FILEPATH);
        } else {
            currentPaddleColorIndex = currentIndex;
            Breakout.getInstance().getDataManager().getData().setPaddleColor(newColor);
            updateColorImageView(paddleImageView, newColor, Constants.PADDLE_FILEPATH);
        }
    }

    /**
     * Creates a Text-based arrow (e.g. "<" or ">") which, when clicked, triggers
     * the provided action (e.g., navigating through a list of available colors).
     *
     * @param content The visible text for the arrow (e.g., "<" or ">").
     * @param action  The action to run when the arrow is clicked.
     * @return A Text node representing the arrow.
     */
    private Text createArrow(String content, Runnable action) {
        Text arrow = UIComponentFactory.createText(
                content,
                Constants.ARROW_FONT,
                Constants.ARROW_FONT_SIZE,
                Constants.NORMAL_TEXT_COLOR
        );
        arrow.setOnMouseClicked(event -> action.run());
        return arrow;
    }

    /**
     * Creates an ImageView for either the ball or the paddle based on a color
     * name, setting the appropriate dimensions depending on whether it's a ball
     * or a paddle.
     *
     * @param colorName The color name to be applied (e.g. "Blue", "Green").
     * @param isBall    True if creating an ImageView for the ball, false for the paddle.
     * @return An ImageView showing the specified color for the ball or paddle.
     */
    private ImageView createColorImageView(String colorName, boolean isBall) {
        // Construct the path to the image based on whether it's a ball or a paddle
        String imagePath = isBall
                ? Constants.BALL_FILEPATH + colorName + ".png"
                : Constants.PADDLE_FILEPATH + colorName + ".png";

        Image image = getImage(imagePath);
        ImageView imageView = new ImageView(image);

        // Set the dimensions based on whether it's a ball or a paddle
        if (isBall) {
            imageView.setFitWidth(Constants.BALL_RADIUS * 2);
            imageView.setFitHeight(Constants.BALL_RADIUS * 2);
        } else {
            imageView.setFitWidth(Constants.PADDLE_WIDTH);
            imageView.setFitHeight(Constants.PADDLE_HEIGHT);
        }
        return imageView;
    }

    /**
     * Creates an HBox containing left and right arrow Text nodes and an ImageView
     * to allow the user to cycle through an array of possible colors for the ball
     * or paddle. Clicking an arrow calls {@link #changeColor(List, int, boolean)}
     * to update the current color selection.
     *
     * @param colors       The array of color options (e.g., {"Red", "Green", "Blue"}).
     * @param currentIndex The initially selected color index.
     * @param isBall       True if editing the ball's color, false if editing the paddle's color.
     * @param <T>          The type of the color array (usually String).
     * @return An HBox containing the arrows and the color ImageView.
     */
    private <T> HBox createColorSelector(T[] colors, int currentIndex, boolean isBall) {
        // Convert the array of colors into a List
        List<T> colorList = Arrays.asList(colors);

        // Create the initial ImageView based on the given color index
        ImageView colorImageView = createColorImageView(colorList.get(currentIndex).toString(), isBall);

        // Assign the created ImageView to the appropriate field (ball or paddle)
        if (isBall) {
            ballImageView = colorImageView;
            ballImageView.setFitWidth(Constants.BALL_RADIUS * 2);
            ballImageView.setFitHeight(Constants.BALL_RADIUS * 2);
        } else {
            paddleImageView = colorImageView;
            paddleImageView.setFitWidth(Constants.PADDLE_WIDTH);
            paddleImageView.setFitHeight(Constants.PADDLE_HEIGHT);
        }

        // Create arrows for switching colors
        Text leftArrow = createArrow("<", () -> changeColor(colorList, -1, isBall));
        Text rightArrow = createArrow(">", () -> changeColor(colorList, 1, isBall));

        // Build and configure the HBox container
        HBox hbox = new HBox(
                Constants.COLOR_SELECTOR_SPACING,
                leftArrow,
                colorImageView,
                rightArrow
        );
        hbox.setAlignment(Pos.CENTER);

        return hbox;
    }
}
