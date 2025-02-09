package breakoutbasic.scenes;

import breakoutbasic.utils.WindowUtils;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * This class is used to display the game is over scene
 * You can also restart the game after the game has been lost
 */
public class GameOverScene extends AbstractScene {

    // Text for scene
    private Text gameOver;

    /**
     * Constructor for game over scene, consisting of basic setup
     */
    public GameOverScene() {
        super();
        // Screen color
        this.getScene().setFill(Color.BLACK);

        // Adding game over text
        addGameOverText();
    }

    /**
     * Adding different text pieces
     */
    public void addGameOverText() {
        // Create a StackPane for centering
        StackPane stackPane = new StackPane();
        stackPane.setPrefSize(WindowUtils.getWindowWidth(), WindowUtils.getWindowHeight());

        // Create and style the Game Over text
        this.gameOver = new Text("Game over!");
        this.gameOver.setStyle("-fx-font-size: 100px;");
        this.gameOver.setFill(Color.DARKRED);

        // Add the text to the StackPane
        stackPane.getChildren().add(this.gameOver);

        // Replace the Pane with the StackPane
        this.getPane().getChildren().add(stackPane);
    }



    public void onTick() {

    }
}
