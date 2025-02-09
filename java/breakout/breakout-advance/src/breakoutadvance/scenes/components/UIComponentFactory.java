package breakoutadvance.scenes.components;

import breakoutadvance.utils.Constants;
import javafx.animation.PauseTransition;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import static breakoutadvance.utils.Fonts.getFont;

/**
 * A factory class for creating commonly used UI components (Text, Label, CheckBox, Slider, etc.)
 * with default styling or optional custom parameters.
 */
public class UIComponentFactory {

    /**
     * Creates a JavaFX Text node with the specified content, size, color, and font.
     *
     * @param content the text content to be displayed.
     * @param size    the font size to use if {@code font} is {@code null}.
     * @param color   the color to fill the text with; if {@code null}, defaults to {@link Color#WHITE}.
     * @return a {@link Text} node with the specified content, font size, color, and font.
     */
    public static Text createText(String content, String fontpath, int size, Color color) {
        Text text = new Text(content);
        text.setFont(getFont(fontpath));
        text.setStyle("-fx-font-size: " + size + ";");
        text.setFill(color != null ? color : Constants.NORMAL_TEXT_COLOR);
        return text;
    }

    /**
     * Creates a JavaFX Text node that highlights itself briefly when clicked,
     * then runs a user-specified action.
     * <p>
     * The highlight effect is created via a {@link PauseTransition} of the specified duration.
     *
     * @param content             the text content to be displayed.
     * @param size                the font size to use if {@code font} is {@code null}.
     * @param normalColor         the normal (default) text color, e.g. {@link Color#WHITE}.
     * @param highlightColor      the color used to highlight the text on click, e.g. {@link Color#YELLOW}.
     * @param highlightDurationMs how long (in milliseconds) the text remains highlighted before reverting.
     * @param fontname            the string name of the custom font to use; if {@code null}, an Arial font at the specified size is used.
     * @param onClickAction       a {@link Runnable} representing the action to be executed after the highlight.
     * @return a {@link Text} node that briefly highlights itself when clicked, then runs the user action.
     */
    public static Text createClickableText(
            String content,
            int size,
            Color normalColor,
            Color highlightColor,
            long highlightDurationMs,
            String fontname,
            Runnable onClickAction
    ) {
        Text text = createText(content, fontname, size, normalColor);

        text.setOnMouseClicked(e -> {
            // Change color to highlight color
            text.setFill(highlightColor);

            // Pause, then revert color and call onClickAction
            PauseTransition pause = new PauseTransition(Duration.millis(highlightDurationMs));
            pause.setOnFinished(event -> {
                text.setFill(normalColor);
                onClickAction.run();
            });
            pause.play();
        });

        return text;
    }

    /**
     * Creates a JavaFX Label with the specified content, size, and optional custom font.
     * <p>
     * The label text is white by default.
     *
     * @param content the text content to be displayed.
     * @param size    the font size to use if {@code font} is {@code null}.
     * @param font    the custom font to use; if {@code null}, an Arial font at the specified size is used.
     * @return a {@link Label} with the specified text content, font size, and styling.
     */
    public static Label createLabel(String content, int size, Font font) {
        Label label = new Label(content);
        label.setFont(font != null ? font : Font.font("Arial", size));
        label.setTextFill(Color.WHITE);
        return label;
    }

    /**
     * Creates a JavaFX CheckBox with optional custom font, initial “selected” state, and a default style.
     * <p>
     * The CheckBox is given a small padding style for improved appearance.
     *
     * @param label      the text label for the checkbox.
     * @param isSelected whether the checkbox should be initially selected.
     * @param font       the custom font to use; if {@code null}, an Arial font of size 20 is used.
     * @return a {@link CheckBox} with the specified label, selected state, font, and styling.
     */
    public static CheckBox createCheckBox(String label, boolean isSelected, Font font) {
        CheckBox checkBox = new CheckBox(label);
        checkBox.setSelected(isSelected);
        checkBox.setFont(font != null ? font : Font.font("Arial", 20));
        checkBox.setStyle("-fx-padding: 10;");
        return checkBox;
    }

    /**
     * Creates a JavaFX Slider with the specified min, max, initial value, and preferred width.
     *
     * @param min   the minimum value of the slider.
     * @param max   the maximum value of the slider.
     * @param value the initial value (thumb position).
     * @param width the preferred width of the slider, in pixels.
     * @return a {@link Slider} configured with the given parameters.
     */
    public static Slider createSlider(double min, double max, double value, double width, boolean showTicks, double majorTickUnit, boolean snapToTicks) {
        Slider slider = new Slider(min, max, value);
        slider.setPrefWidth(width);
        slider.setShowTickLabels(showTicks);
        slider.setShowTickMarks(showTicks);
        slider.setMajorTickUnit(majorTickUnit);
        slider.setSnapToTicks(snapToTicks);
        return slider;
    }


    /**
     * Creates a JavaFX HBox (horizontal box) with a given spacing and an array of child nodes.
     *
     * @param spacing  the horizontal spacing (in pixels) between child nodes.
     * @param children one or more JavaFX {@link Node} elements to add to the HBox.
     * @return an {@link HBox} containing the specified child nodes, spaced horizontally.
     */
    public static HBox createHBox(int spacing, Node... children) {
        HBox hbox = new HBox(spacing);
        hbox.getChildren().addAll(children);
        return hbox;
    }
}
