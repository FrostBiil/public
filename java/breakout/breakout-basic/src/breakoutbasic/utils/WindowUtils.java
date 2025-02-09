package breakoutbasic.utils;

import javafx.stage.Stage;

/**
 * Utils to handle the primary stage
 */
public class WindowUtils {
    private static Stage primaryStage;

    public static double getWindowHeight() {
        return primaryStage.getHeight();
    }

    public static double getWindowWidth() {
        return primaryStage.getWidth();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }
}
