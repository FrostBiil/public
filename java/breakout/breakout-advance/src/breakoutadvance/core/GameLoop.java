package breakoutadvance.core;

import javafx.application.Platform;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Game loop for the game that uses ScheduledExecutorService from java.utils
 * to handle the loop. The loop will run on the same thread as JavaFX.
 * <p>
 * The Loop will call the callback function every one millisecond.
 */
public class GameLoop {

    private final Runnable callback; // Callback function
    private ScheduledExecutorService executorService; // Loop handler
    private boolean isRunning; // true if it is running, otherwise false

    /**
     * Constructor for the GameLoop
     *
     * @param callback The function to be called every one millisecond
     */
    public GameLoop(Runnable callback) {
        this.callback = callback;
    }

    /**
     * Wait for a specified amount of time before executing the callback.
     *
     * @param timeInMilliseconds The time to wait in milliseconds
     * @param callback           The callback to execute after the wait
     */
    public static void wait(int timeInMilliseconds, Runnable callback) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(() -> {
            Platform.runLater(callback); // Ensures the callback runs on the JavaFX Thread
            scheduler.shutdown();
        }, timeInMilliseconds, TimeUnit.MILLISECONDS);
    }

    /**
     * Start the loop if it is not already running
     */
    public void start() {
        // Make sure not to start two loops at the same time
        if (isRunning) return;
        isRunning = true;

        // Start the loop while making sure it is running on the same thread as JavaFX
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            Platform.runLater(callback); // Ensures the callback runs on the JavaFX Thread
        }, 0, 1, TimeUnit.MILLISECONDS); // 1 ms interval
    }

    /**
     * Stop the loop if it is running
     */
    public void stop() {
        if (!isRunning) return;
        isRunning = false;

        // Stop the loop
        if (executorService != null) {
            executorService.shutdown();
            executorService = null;
        }
    }

    /**
     * Check if the loop is running
     *
     * @return true if it is running, otherwise false
     */
    public boolean isRunning() {
        return isRunning;
    }
}
