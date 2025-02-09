package breakoutbasic.scenes;

import breakoutbasic.utils.WindowUtils;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 * Abstract class to handle different kinds of scenes
 */
public abstract class AbstractScene {
    private final Scene scene; // Scene that is displayed

    private final Pane pane; // Pane which spans the full screen.

    /**
     * Create a basic Scene
     */
    public AbstractScene() {
        this.pane = new Pane();
        this.scene = new Scene(this.pane);

        scene.setRoot(pane);

        displayScene();
    }

    public Scene getScene() {
        return scene;
    }

    public Pane getPane() {
        return pane;
    }

    /**
     * Display screen on the current stage
     */
    public void displayScene() {
        // Adding Scene to Stage
        WindowUtils.getPrimaryStage().setScene(scene);
        WindowUtils.getPrimaryStage().show();
    }

    /**
     * A function that is called every tick.
     */
    public abstract void onTick();
}
