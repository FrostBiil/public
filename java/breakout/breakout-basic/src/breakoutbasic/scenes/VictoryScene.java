package breakoutbasic.scenes;

import breakoutbasic.utils.WindowUtils;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * This class is used to display the victory screen scene
 * You can also restart the game after the game has been won
 */
public class VictoryScene extends AbstractScene {
    // Text for scene
    private Text victoryText; // Victory Text

    /**
     * Constructor for victory scene, consisting of basic setup
     */
    public VictoryScene() {
        // Setting screen color to black
        this.getScene().setFill(Color.BLACK);

        // Adding game over text
        addGameOverText();

    }

    /**
     * Adding different text pieces to the screen
     */
    public void addGameOverText() {
        // Game over text
        this.victoryText = new Text("Victory!");
        this.victoryText.setStyle("-fx-font-size: 100px;");
        this.victoryText.setFill(Color.GREEN);
        this.getPane().getChildren().add(this.victoryText);

        // Center the text after it is added to the scene as it needs to be visible and text changes
        // This makes sure that it is centered no matter what
        this.victoryText.boundsInLocalProperty().addListener((observable, oldValue, newValue) -> {
            double textWidth = newValue.getWidth();
            double textHeight = newValue.getHeight();
            this.victoryText.setX((WindowUtils.getWindowWidth() - textWidth) / 2);
            this.victoryText.setY((WindowUtils.getWindowHeight() - textHeight) / 2);
        });
    }

    public void onTick() {

    }
}
