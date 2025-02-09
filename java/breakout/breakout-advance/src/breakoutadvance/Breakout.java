package breakoutadvance;

import breakoutadvance.core.GameLoop;
import breakoutadvance.levels.LevelManager;
import breakoutadvance.persistentdata.DataManager;
import breakoutadvance.scenes.AbstractScene;
import breakoutadvance.scenes.PlayScene;
import breakoutadvance.scenes.menus.MainMenu;
import breakoutadvance.utils.Fonts;
import breakoutadvance.utils.Images;
import breakoutadvance.utils.Sound;
import breakoutadvance.utils.WindowUtils;
import javafx.application.Application;
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

    private GameLoop gameLoop; // Game loop which calls the onTick function

    private DataManager dataManager; // Manager to handle persistent data

    private LevelManager levelManager; // Manager to handle levels

    public static Breakout getInstance() {
        return instance;
    }

    /**
     * Run() constructor, which launches the game
     * @return this
     */
    public Breakout run() {
        launch();
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

        // Load attributes
        Sound.load();
        Images.load();
        Fonts.load();

        // Load data manager
        this.dataManager = new DataManager();
        this.dataManager.load();

        // Load level manager
        this.levelManager = new LevelManager(this.dataManager);
        this.levelManager.load();

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
        setupPlayScene(primaryStage);

        // Start Game Loop
        gameLoop = new GameLoop(this::onTick);
        gameLoop.start();

        // Save data and save game on close
        WindowUtils.getPrimaryStage().setOnCloseRequest(event -> {
            if (dataManager != null && dataManager.getData() != null) dataManager.saveData();
            if (gameLoop != null) gameLoop.stop();
        });
    }

    /**
     * Setup starting PlayScene
     */
    private void setupPlayScene(Stage primaryStage) {
        // Set Current Scene
        this.currentScene = new MainMenu();
    }

    /**
     * Called every tick from the gameLoop
     */
    public void onTick() {
        if (this.currentScene != null) {
            if (currentScene instanceof PlayScene playScene)
                playScene.onTick();
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

    public DataManager getDataManager() {
        return dataManager;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }
}