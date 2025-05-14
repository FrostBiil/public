package dtu.group5.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import dtu.group5.backend.model.BaseActivity;
import dtu.group5.backend.model.Coworker;
import dtu.group5.backend.model.FixedActivity;
import dtu.group5.backend.model.Project;
import dtu.group5.backend.model.ProjectActivity;
import dtu.group5.database.adapters.CoworkerAdapter;
import dtu.group5.database.adapters.FixedActivityAdapter;
import dtu.group5.database.adapters.ProjectActivityAdapter;
import dtu.group5.database.adapters.ProjectAdapter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Made by Elias (s241121)
public class DataManager {
    // Instance for DataManager
    private static DataManager instance;

    // File names to save data
    private final String[] FILE_NAMES = {"coworker.json", "projects.json", "fixed-activities.json", "project-activities.json"};


    private final Gson gson;

    private final File dataDir;

    // DataFiles
    private final File coworkersFile;
    private final File projectsFile;
    private final File fixedActivitiesFile;
    private final File projectActivitiesFile;

    // Loaded data used as pointers in the adapters
    private final Set<Coworker> loadedCoworkers = new HashSet<>();
    private final Set<BaseActivity> loadedActivities = new HashSet<>();

    // Made by Elias (s241121)
    public DataManager() {
        instance = this;

        // Initialize Gson with custom adapters
        gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(Coworker.class, new CoworkerAdapter())
            .registerTypeAdapter(FixedActivity.class, new FixedActivityAdapter(loadedCoworkers))
            .registerTypeAdapter(ProjectActivity.class, new ProjectActivityAdapter(loadedCoworkers))
            .registerTypeAdapter(Project.class, new ProjectAdapter(loadedCoworkers, loadedActivities))
            .create();

        // Get data
        dataDir = getOrCreateDataFolder();
        if (!dataDir.exists()) {
            throw new RuntimeException("Could not create data folder: " + dataDir.getAbsolutePath());
        }

        // load or create json files
        File[] files = new File[FILE_NAMES.length];

        for (int i = 0; i < FILE_NAMES.length; i++) {
            files[i] = getOrCreateFile(FILE_NAMES[i]);
            if (files[i].exists()) continue;
            throw new RuntimeException("Could not create file: " + files[i].getAbsolutePath());
        }

        // Assign files to class variables
        this.coworkersFile = files[0];
        this.projectsFile = files[1];
        this.fixedActivitiesFile = files[2];
        this.projectActivitiesFile = files[3];
    }


    // Made by Elias (s241121)
    /**
     * Get or create the data folder
     * @return The data folder
     */
    private File getOrCreateDataFolder() {
        String directoryPath = "data"; // Removed leading slash for relative path

        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return directory;
    }

    // Made by Elias (s241121)
    /**
     * Get or create the data file
     * @param fileName The name of the data file
     * @return The data file
     */
    private File getOrCreateFile(String fileName) {
        if (!fileName.endsWith(".json")) {
            throw new RuntimeException("Only .json files are supported");
        }

        // Get or create the data file
        File dataFile = new File(dataDir, fileName);
        if (!dataFile.exists()) {

            // Setup default empty array in newly created file
            JsonElement data = createDefaultData();
            try (FileWriter fileWriter = new FileWriter(dataFile)) {
                gson.toJson(data, fileWriter);
            } catch (IOException e) {
                System.err.println("Error creating "+fileName+" file: " + e.getMessage());
            }
        }
        return dataFile;
    }

    // Made by Elias (s241121)
    /**
     * Load the coworkers from the json file
     * @return The list of coworkers
     */
    public List<Coworker> loadCoworkers() {
        List<Coworker> coworkers = new ArrayList<>();

        try (FileReader fileReader = new FileReader(coworkersFile)) {
            JsonArray data = gson.fromJson(fileReader, JsonArray.class);
            if (data == null) return coworkers;

            // Deserialize each element in the array
            for (JsonElement jsonElement : data) {
                Coworker coworker = gson.fromJson(jsonElement, Coworker.class);
                if (coworker == null) {
                    System.err.printf("Coworker %s is null%n", jsonElement);
                }
                coworkers.add(coworker);
            }
        } catch (IOException e) {
            System.err.println("Error reading "+FILE_NAMES[0]+" file: " + e.getMessage());
        }

        // Add loaded coworkers to the set for adapters
        this.loadedCoworkers.clear();
        this.loadedCoworkers.addAll(coworkers);

        return coworkers;
    }

    // Made by Elias (s241121)
    /**
     * Load the projects from the json file
     * @return The list of projects
     */
    public List<Project> loadProjects() {
        List<Project> projects = new ArrayList<>();

        try (FileReader fileReader = new FileReader(projectsFile)) {
            JsonArray data = gson.fromJson(fileReader, JsonArray.class);
            if (data == null) return projects;

            // Deserialize each element in the array
            for (JsonElement jsonElement : data) {
                Project project = gson.fromJson(jsonElement, Project.class);
                if (project == null) {
                    System.err.printf("Project %s is null%n", jsonElement);
                }
                projects.add(project);
            }
        } catch (IOException e) {
            System.err.println("Error reading "+FILE_NAMES[1]+" file: " + e.getMessage());
        }

        return projects;
    }

    // Made by Elias (s241121)
    /**
     * Load the activities from the json file
     * @return The list of activities
     */
    public List<BaseActivity> loadFixedActivities() {
        List<BaseActivity> activities = new ArrayList<>();

        try (FileReader fileReader = new FileReader(fixedActivitiesFile)) {
            JsonArray data = gson.fromJson(fileReader, JsonArray.class);
            if (data == null) return activities;

            // Deserialize each element in the array
            for (JsonElement jsonElement : data) {
                BaseActivity activity = gson.fromJson(jsonElement, FixedActivity.class);
                if (activity == null) {
                    System.err.printf("Activity %s is null%n", jsonElement);
                }
                activities.add(activity);
            }
        } catch (IOException e) {
            System.err.println("Error reading "+FILE_NAMES[2]+" file: " + e.getMessage());
        }

        // Add loaded activities to the set for adapters
        this.loadedActivities.addAll(activities);

        return activities;
    }


    // Made by Elias (s241121)
    /**
     * Load the activities from the json file
     * @return The list of activities
     */
    public List<BaseActivity> loadProjectActivities() {
        List<BaseActivity> activities = new ArrayList<>();

        try (FileReader fileReader = new FileReader(projectActivitiesFile)) {
            JsonArray data = gson.fromJson(fileReader, JsonArray.class);
            if (data == null) return activities;

            // Deserialize each element in the array
            for (JsonElement jsonElement : data) {
                BaseActivity activity = gson.fromJson(jsonElement, ProjectActivity.class);
                if (activity == null) {
                    System.err.printf("Activity %s is null%n", jsonElement);
                }
                activities.add(activity);
            }
        } catch (IOException e) {
            System.err.println("Error reading "+FILE_NAMES[3]+" file: " + e.getMessage());
        }

        // Add loaded activities to the set for adapters
        this.loadedActivities.addAll(activities);

        return activities;
    }

    // Made by Elias (s241121)
    /**
     * Save the coworkers to the json file
     * @param coworkers The list of coworkers
     */
    public void saveCoworkers(List<Coworker> coworkers) {
        File dataFile = new File(dataDir, FILE_NAMES[0]);
        try (FileWriter fileWriter = new FileWriter(dataFile)) {
            List<JsonElement> elements = new ArrayList<>();
            for (Coworker coworker : coworkers) {
                elements.add(gson.toJsonTree(coworker)); // Removed JsonElement.class
            }
            gson.toJson(elements, fileWriter);
        } catch (IOException e) {
            System.err.println("Error saving data file: " + e.getMessage());
        }
    }

    // Made by Elias (s241121)
    /**
     * Save the projects to the json file
     * @param projects The list of projects
     */
    public void saveProjects(List<Project> projects) {
        File dataFile = new File(dataDir, FILE_NAMES[1]);
        try (FileWriter fileWriter = new FileWriter(dataFile)) {
            List<JsonElement> elements = new ArrayList<>();
            for (Project project : projects) {
                elements.add(gson.toJsonTree(project)); // Removed JsonElement.class
            }
            gson.toJson(elements, fileWriter);
        } catch (IOException e) {
            System.err.println("Error saving data file: " + e.getMessage());
        }
    }

    // Made by Elias (s241121)
    public void saveActivities(List<BaseActivity> activities) {
        saveFixedActivities(activities.stream().filter(activity -> activity instanceof FixedActivity).map(activity -> (FixedActivity) activity).toList());
        saveProjectActivities(activities.stream().filter(activity -> activity instanceof ProjectActivity).map(activity -> (ProjectActivity) activity).toList());
    }

    // Made by Elias (s241121)
    /**
     * Save the fixed activities to the json file
     * @param activities The list of activities
     */
    public void saveFixedActivities(List<FixedActivity> activities) {
        File dataFile = new File(dataDir, FILE_NAMES[2]);
        try (FileWriter fileWriter = new FileWriter(dataFile)) {
            List<JsonElement> elements = new ArrayList<>();
            for (FixedActivity activity : activities) {
                elements.add(gson.toJsonTree(activity)); // Removed JsonElement.class
            }
            gson.toJson(elements, fileWriter);
        } catch (IOException e) {
            System.err.println("Error saving data file: " + e.getMessage());
        }
    }

    // Made by Elias (s241121)
    /**
     * Save the project activities to the json file
     * @param activities The list of activities
     */
    public void saveProjectActivities(List<ProjectActivity> activities) {
        File dataFile = new File(dataDir, FILE_NAMES[3]);
        try (FileWriter fileWriter = new FileWriter(dataFile)) {
            List<JsonElement> elements = new ArrayList<>();
            for (ProjectActivity activity : activities) {
                elements.add(gson.toJsonTree(activity)); // Removed JsonElement.class
            }
            gson.toJson(elements, fileWriter);
        } catch (IOException e) {
            System.err.println("Error saving data file: " + e.getMessage());
        }
    }

    // Made by Elias (s241121)
    /**
     * Create default data for the json file
     * @return The default data
     */
    private JsonElement createDefaultData() {
        return new JsonArray();
    }

    // Made by Elias (s241121)
    /**
     * Get the instance of DataManager
     * @return The instance of DataManager
     */
    public static DataManager getInstance() {
        return instance;
    }
}
