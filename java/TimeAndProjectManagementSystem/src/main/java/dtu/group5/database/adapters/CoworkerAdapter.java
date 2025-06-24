package dtu.group5.database.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import dtu.group5.backend.model.Coworker;

import java.lang.reflect.Type;

public class CoworkerAdapter implements JsonSerializer<Coworker>, JsonDeserializer<Coworker> {
    @Override
    public JsonElement serialize(Coworker coworker, Type type, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        // Coworker fields
        jsonObject.addProperty("name", coworker.getName());
        jsonObject.addProperty("initials", coworker.getInitials());

        return jsonObject;
    }

    @Override
    public Coworker deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        // Coworker fields
        String name = jsonObject.get("name").getAsString();
        String initials = jsonObject.get("initials").getAsString();

        return new Coworker(initials, name);
    }
}
