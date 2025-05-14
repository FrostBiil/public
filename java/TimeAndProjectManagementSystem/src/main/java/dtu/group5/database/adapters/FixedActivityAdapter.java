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
import dtu.group5.backend.model.FixedActivity;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

// Made by Elias (s241121)
public class FixedActivityAdapter implements JsonSerializer<FixedActivity>, JsonDeserializer<FixedActivity> {

    private final Set<Coworker> loadedCoworkers;

    // Made by Elias (s241121)
    public FixedActivityAdapter(Set<Coworker> loadedCoworkers) {
        this.loadedCoworkers = loadedCoworkers;
    }

    // Made by Elias (s241121)
    @Override
    public JsonElement serialize(FixedActivity activity, Type type, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        // Activity Base fields
        jsonObject.addProperty("title", activity.getTitle());
        jsonObject.addProperty("finished", activity.isFinished());
        jsonObject.addProperty("description", activity.getDescription());

        jsonObject.addProperty("activityType", "fixed");

        JsonArray coworkerInitialsArray = new JsonArray(); // Assigned coworkers
        for (Coworker coworker : activity.getAssignedCoworkers()) {
            coworkerInitialsArray.add(coworker.getInitials());
        }
        jsonObject.add("coworkers", coworkerInitialsArray);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        jsonObject.addProperty("startDate", (activity.getStartDate() != null ? dateFormat.format(activity.getStartDate()) : null));
        jsonObject.addProperty("endDate", (activity.getEndDate() != null ? dateFormat.format(activity.getEndDate()) : null));

        return jsonObject;
    }

    // Made by Elias (s241121)
    @Override
    public FixedActivity deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
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

        // Check if the activity is a Fixed activity
        Date startDate = getDateFromString(jsonObject.has("startDate") ? jsonObject.get("startDate").getAsString() : null);
        Date endDate = getDateFromString(jsonObject.has("endDate") ? jsonObject.get("endDate").getAsString() : null);

        return new FixedActivity(title, finished, description, assignedCoworkers, startDate, endDate);
    }

    // Made by Elias (s241121)
    public Date getDateFromString(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(dateString);
        } catch (java.text.ParseException e) {
           return null;
        }
    }
}
