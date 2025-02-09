package breakoutbasic;

import breakoutbasic.loop.GameLoop;
import breakoutbasic.scenes.AbstractScene;
import breakoutbasic.scenes.PlayScene;
import breakoutbasic.utils.UserInputUtils;
import breakoutbasic.utils.WindowUtils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class to set up the JavaFX game
 */
public class Breakout extends Application {

    public static Breakout instance; // Instance of breakout
    private AbstractScene currentScene; // Current displayed scene
    private GameLoop gameLoop; // Gameloop which calls the onTick function.

    public static Breakout getInstance() {
        return instance;
    }

    /**
     * Run() constructor, which launches the game
     * @return this
     */
    public Breakout run(String[] args) {
        launch(args);
        return this;
    }

    /**
     * Used to start window in JavaFX
     *
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        instance = this;
        // Setup Stage
        primaryStage.setTitle("Breakout");

        // Get the screen bounds
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();

        // Set the window size to the screen size (windowed fullscreen)
        primaryStage.setX(screenBounds.getMinX());
        primaryStage.setY(screenBounds.getMinY());
        primaryStage.setWidth(screenBounds.getMaxX());
        primaryStage.setHeight(screenBounds.getMaxY());

        // Disable resizing
        primaryStage.setResizable(false);

        // Adding Stage to window util
        WindowUtils.setPrimaryStage(primaryStage);

        // Setup Play Scene for Game
        Platform.runLater(this::setupPlayScene);

        // Start Game Loop
        gameLoop = new GameLoop(this::onTick);
        gameLoop.start();
        WindowUtils.getPrimaryStage().setOnCloseRequest(event -> {
            if (gameLoop != null) gameLoop.stop();
        });
    }

    /**
     * Setup starting PlayScene
     */
    public void setupPlayScene() {
        // Getting values from user
        int n = 1;
        int m = 1;

        String[] args = this.getParameters().getRaw().toArray(new String[0]);
        if (args.length >= 2) {
            try {
                n = Integer.parseInt(args[0]);
                m = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid arguments. Please enter valid integers.");
                System.exit(1);
                return;
            }
        } else {
            System.out.println("Could not find run arguments. Please enter the number of rows and columns instead.");
            do {
                n = UserInputUtils.getUserInputInteger("Enter number of rows: (must be between 1 and 10): ");
            } while (n < 1 || n > 10); // Keep asking until n is between 1 and 10 (inclusive)

            do {
                m = UserInputUtils.getUserInputInteger("Enter number of columns: (must be between 5 and 20): ");
            } while (m < 5 || m > 20); // Keep asking until m is between 5 and 20 (inclusive)
        }
        // Closing the scanner
        UserInputUtils.scan.close();

        if (n < 1 || n > 10 || m < 1|| m > 20) {
            System.out.println("Invalid arguments. Please enter valid integers.");
            System.exit(2);
            return;
        }

        System.out.println("The game is now running.");

        // Set Current Scene
        this.currentScene = new PlayScene(n, m);
    }

    /**
     * Called every tick from the gameLoop
     */
    public void onTick() {
        if (this.currentScene != null) {
            this.currentScene.onTick();
        }
    }

    public AbstractScene getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(AbstractScene currentScene) {
        this.currentScene = currentScene;
    }

    public GameLoop getGameLoop() {
        return gameLoop;
    }
}