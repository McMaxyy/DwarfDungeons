package abilities;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class AbilitiesDeserializer implements JsonDeserializer<Abilities> {

    @Override
    public Abilities deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        int id = jsonObject.get("ID").getAsInt();
        switch (id) {
            case 1:
                return context.deserialize(jsonObject, OverheadSwing.class);
            case 2:
                return context.deserialize(jsonObject, Decapitate.class);
            default:
                throw new JsonParseException("Unknown ability ID: " + id);
        }
    }
}

