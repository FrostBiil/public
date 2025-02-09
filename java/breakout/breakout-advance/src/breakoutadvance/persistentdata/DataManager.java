package breakoutadvance.persistentdata;

import breakoutadvance.persistentdata.data.Data;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DataManager {

    private Gson gson;
    private Data data;

    public void load() {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        String directoryPath = "data"; // Removed leading slash for relative path

        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File dataFile = new File(directory, "data.json");
        if (!dataFile.exists()) {
            data = createDefaultData();
            try (FileWriter fileWriter = new FileWriter(dataFile)) {
                gson.toJson(data, fileWriter);
            } catch (IOException e) {
                System.err.println("Error creating data file: " + e.getMessage());
            }
        } else {
            try (FileReader fileReader = new FileReader(dataFile)) {
                data = gson.fromJson(fileReader, Data.class);
            } catch (IOException e) {
                System.err.println("Error reading data file: " + e.getMessage());
            }
        }
    }

    public void saveData() {
        String directoryPath = "data"; // Removed leading slash for relative path

        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File dataFile = new File(directory, "data.json");
        try (FileWriter fileWriter = new FileWriter(dataFile)) {
            gson.toJson(data, fileWriter);
        } catch (IOException e) {
            System.err.println("Error saving data file: " + e.getMessage());
        }
    }

    public Gson getGson() {
        return gson;
    }

    private Data createDefaultData() {
        return new Data();
    }

    public Data getData() {
        return data;
    }
}