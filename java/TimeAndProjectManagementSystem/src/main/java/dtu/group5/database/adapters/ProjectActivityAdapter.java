package dtu.group5.database.adapters;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import dtu.group5.backend.model.Coworker;
import dtu.group5.backend.model.ProjectActivity;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static dtu.group5.backend.util.DateUtil.formatDate;
import static dtu.group5.backend.util.DateUtil.parseDate;

// Made by Elias (s241121)
public class ProjectActivityAdapter implements JsonSerializer<ProjectActivity>, JsonDeserializer<ProjectActivity> {

    private final Set<Coworker> loadedCoworkers;

    // Made by Elias (s241121)
    public ProjectActivityAdapter(Set<Coworker> loadedCoworkers) {
        this.loadedCoworkers = loadedCoworkers;
    }

    // Made by Elias (s241121)
    @Override
    public JsonElement serialize(ProjectActivity activity, Type type, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        // Activity Base fields
        jsonObject.addProperty("title", activity.getTitle());
        jsonObject.addProperty("finished", activity.isFinished());
        jsonObject.addProperty("description", activity.getDescription());

        jsonObject.addProperty("activityType", "project");

        JsonArray coworkerInitialsArray = new JsonArray(); // Assigned coworkers
        for (Coworker coworker : activity.getAssignedCoworkers()) {
            coworkerInitialsArray.add(coworker.getInitials());
        }
        jsonObject.add("coworkers", coworkerInitialsArray);

        // ProjectActivity specific fields
        jsonObject.addProperty("projectNumber", activity.getProjectNumber());
        jsonObject.addProperty("expectedHours", activity.getExpectedHours());
        jsonObject.addProperty("startYear", activity.getStartYear());
        jsonObject.addProperty("startWeekNumber", activity.getStartWeekNumber());
        jsonObject.addProperty("endYear", activity.getEndYear());
        jsonObject.addProperty("endWeekNumber", activity.getEndWeekNumber());

        JsonObject workedTimeJson = new JsonObject();

        for (Map.Entry<Coworker, Map<Date, Double>> entry : activity.getWorkedHoursPerCoworker().entrySet()) {
            JsonObject dailyHoursJson = new JsonObject();
            for (Map.Entry<Date, Double> dateEntry : entry.getValue().entrySet()) {
                String dateString = formatDate(dateEntry.getKey());
                dailyHoursJson.addProperty(dateString, dateEntry.getValue());
            }
            workedTimeJson.add(entry.getKey().getInitials(), dailyHoursJson);
        }

        jsonObject.add("workedTimePerCoworker", workedTimeJson);
        return jsonObject;
    }

    // Made by Elias (s241121)
    @Override
    public ProjectActivity deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        // Get activity fields
        String title = jsonObject.get("title").getAsString();
        boolean finished = jsonObject.has("finished") && jsonObject.get("finished").getAsBoolean();
        String description = jsonObject.has("description") ? jsonObject.get("description").getAsString() : "";

        // Get assigned coworkers from CoworkerRepository
        JsonArray coworkerInitialsArray = jsonObject.getAsJsonArray("coworkers");
        Set<Coworker> assignedCoworkers = new HashSet<>();
        for (JsonElement element : coworkerInitialsArray) {
            String initials = element.getAsString();

            // Find the coworker by initials from pointer to loaded coworkers
            Coworker coworker = loadedCoworkers.stream().filter(c -> c.getInitials().equals(initials)).findFirst().orElse(null);
            if (coworker == null) continue;
            assignedCoworkers.add(coworker);
        }


        String activityType = jsonObject.get("activityType").getAsString();

        // Deserialize the activity based on its type

        // Since its not Fixed, it must be a ProjectActivity
        int projectNumber = jsonObject.get("projectNumber").getAsInt();
        double expectedHours = jsonObject.has("expectedHours") ? jsonObject.get("expectedHours").getAsDouble() : 0;

        // Set default values for start and end week numbers to current week and year
        Calendar calender = Calendar.getInstance();
        int startYear = jsonObject.has("startYear") ? jsonObject.get("startYear").getAsInt() : (calender.get(Calendar.YEAR) % 100);
        int startWeekNumber = jsonObject.has("startWeekNumber") ? jsonObject.get("startWeekNumber").getAsInt() : calender.get(Calendar.WEEK_OF_YEAR);

        int endYear = jsonObject.has("endYear") ? jsonObject.get("endYear").getAsInt() : (calender.get(Calendar.YEAR) % 100);
        int endWeekNumber = jsonObject.has("endWeekNumber") ? jsonObject.get("endWeekNumber").getAsInt() : calender.get(Calendar.WEEK_OF_YEAR);

        // Deserialize workedTimePerCoworker
        JsonObject workedTimeJson = jsonObject.has("workedTimePerCoworker") ? jsonObject.getAsJsonObject("workedTimePerCoworker") : new JsonObject();
        Map<Coworker, Map<Date, Double>> workedTimePerCoworker = new HashMap<>();

        for (String key : workedTimeJson.keySet()) {
            Coworker coworker = loadedCoworkers.stream()
              .filter(c -> c.getInitials().equals(key))
              .findFirst()
              .orElse(null);
            if (coworker == null) continue;

            JsonObject dailyMapJson = workedTimeJson.getAsJsonObject(key);
            Map<Date, Double> dailyMap = new HashMap<>();

            for (Map.Entry<String, JsonElement> entry : dailyMapJson.entrySet()) {
                try {
                    Date date = parseDate(entry.getKey());
                    double hours = entry.getValue().getAsInt();
                    dailyMap.put(date, hours);
                } catch (Exception e) {
                    System.err.println("Error parsing date: " + entry.getKey() + " - " + e.getMessage());
                }
            }

            workedTimePerCoworker.put(coworker, dailyMap);
        }



        return new ProjectActivity(projectNumber, title, expectedHours, finished, description, assignedCoworkers, startYear, startWeekNumber, endYear, endWeekNumber, workedTimePerCoworker);
    }
}
