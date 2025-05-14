package dtu.group5.database.adapters;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import dtu.group5.backend.model.BaseActivity;
import dtu.group5.backend.model.Coworker;
import dtu.group5.backend.model.Project;
import dtu.group5.backend.model.ProjectType;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

// Made by Elias (s241121)
public class ProjectAdapter implements JsonSerializer<Project>, JsonDeserializer<Project> {

    private final Set<Coworker> loadedCoworkers;
    private final Set<BaseActivity> loadedActivities;

    // Made by Elias (s241121)
    public ProjectAdapter(Set<Coworker> loadedCoworkers, Set<BaseActivity> loadedActivities) {
        this.loadedCoworkers = loadedCoworkers;
        this.loadedActivities = loadedActivities;
    }

    // Made by Elias (s241121)
    @Override
    public JsonElement serialize(Project project, Type type, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        // Project fields
        jsonObject.addProperty("projectNumber", project.getProjectNumber());
        jsonObject.addProperty("title", project.getTitle());
        jsonObject.addProperty("description", project.getDescription());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        jsonObject.addProperty("startDate", (project.getStartDate() != null ? dateFormat.format(project.getStartDate()) : null));
        jsonObject.addProperty("endDate", (project.getEndDate() != null ? dateFormat.format(project.getEndDate()) : null));
        jsonObject.addProperty("projectLeader", (project.getProjectLeader() != null ? project.getProjectLeader().getInitials() : null));

        jsonObject.addProperty("type", project.getType().name());

        // coworkers
        JsonArray coworkerInitialsArray = new JsonArray();
        for (Coworker coworker : project.getCoworkers()) {
            coworkerInitialsArray.add(coworker.getInitials());
        }
        jsonObject.add("coworkers", coworkerInitialsArray);

        jsonObject.addProperty("status", project.getStatus().name());
        return jsonObject;
    }

    // Made by Elias (s241121)
    @Override
    public Project deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        int projectNumber = jsonObject.get("projectNumber").getAsInt();
        String title = jsonObject.get("title").getAsString();
        String description = jsonObject.has("description") && !jsonObject.get("description").isJsonNull()
                ? jsonObject.get("description").getAsString()
                : null;

        ProjectType projectType = jsonObject.has("type") ? ProjectType.fromString(jsonObject.get("type").getAsString()) : ProjectType.CLIENT;

        // Handle Dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDate = null;
        try {
            // Attempt to get and parse the dates
            startDate = jsonObject.has("startDate") && !jsonObject.get("startDate").isJsonNull()
                    ? dateFormat.parse(jsonObject.get("startDate").getAsString())
                    : null;
            endDate = jsonObject.has("endDate") && !jsonObject.get("endDate").isJsonNull()
                    ? dateFormat.parse(jsonObject.get("endDate").getAsString())
                    : null;
        } catch (java.text.ParseException e) {
            throw new JsonParseException("Error parsing date", e);
        }

        // Deserialize project leader
        Coworker projectLeader = null;
        if (jsonObject.has("projectLeader") && !jsonObject.get("projectLeader").isJsonNull()) {
            String leaderInitials = jsonObject.get("projectLeader").getAsString();

            // Find the project leader from the pointer to the loaded coworkers
            Coworker coworker = loadedCoworkers.stream().filter(c -> c.getInitials().equals(leaderInitials)).findFirst().orElse(null);
            if (coworker != null)
                projectLeader = coworker;
        }

        // Deserialize coworkers
        JsonArray coworkerInitialsArray = jsonObject.getAsJsonArray("coworkers");
        Set<Coworker> coworkers = new HashSet<>();
        for (JsonElement element : coworkerInitialsArray) {
            String initials = element.getAsString();

            // Find the coworker by initials from pointer to loaded coworkers
            Coworker coworker = loadedCoworkers.stream().filter(c -> c.getInitials().equals(initials)).findFirst().orElse(null);
            if (coworker == null) continue;
            coworkers.add(coworker);
        }

        Project.ProjectStatus status = Project.ProjectStatus.fromString(jsonObject.has("status") ? jsonObject.get("status").getAsString() : null);
        if (status == null) status = Project.ProjectStatus.NOT_STARTED;

        return new Project(projectNumber, title, description, startDate, endDate, projectLeader, coworkers, status, projectType);
    }
}