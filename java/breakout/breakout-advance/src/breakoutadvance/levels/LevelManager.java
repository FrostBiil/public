package breakoutadvance.levels;

import breakoutadvance.persistentdata.DataManager;
import breakoutadvance.utils.Constants;
import breakoutadvance.utils.FileUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LevelManager class is used to manage the level
 * Includes functions such as loading all levels, and choosing the next level
 */
public class LevelManager {

    private final DataManager dataManager; // Data manager

    private final List<Level> levels = new ArrayList<>(); // List of levels

    private Level currentLevel; // Current level

    /**
     * Setting the dataManager
     *
     * @param dataManager dataManager
     */
    public LevelManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    /**
     * Loading all levels, and adding them to levels list
     */
    public void load() {
        // Path to the levels directory
        String directoryPath = Constants.DEFAULT_PATH +"levels";

        try {
            // Get resource folder paths (resource listing workaround)
            List<String> resourceFiles = FileUtils.getResourceFiles(directoryPath);

            // Filter JSON files and load levels
            for (String resourceFilePath : resourceFiles) {
                if (!resourceFilePath.endsWith(".json")) continue;

                try (InputStreamReader reader = new InputStreamReader(
                        FileUtils.getResourceAsStream(resourceFilePath))) {

                    // Read JSON and parse the level
                    Level level = dataManager.getGson().fromJson(reader, Level.class);
                    if (level != null) {
                        levels.add(level);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Error reading data file: " + resourceFilePath + " - " + e.getMessage());
                }
            }

            // Set the first level
            this.currentLevel = getLevelByName("level_1");
        } catch (IOException e) {
            System.err.println("Error loading levels: " + e.getMessage());
        }
    }

    public Level getLevelByName(String name) {
        // Returning the level, based on the given String name
        return levels.stream().filter(level -> level.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void setNextLevel() {
        // Setting the next level, based on 'nextLevel' data in the current level file
        Level nextLevel = levels.stream().filter(level -> level.getName().equalsIgnoreCase(currentLevel.getNextLevel())).findFirst().orElse(null);

        // If nextLevel doesn't exist, choose a random level
        if (nextLevel == null) {
            currentLevel = levels.get((int) (Math.random() * levels.size()));
            return;
        }

        // If nextLevel does exist, update the level
        currentLevel = nextLevel;
    }
}

