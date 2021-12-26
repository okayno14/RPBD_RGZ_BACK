package Phonebook.model;

import com.google.gson.*;

import java.lang.reflect.Type;

public class PhoneTypeSerializator implements JsonSerializer<PhoneType>, JsonDeserializer<PhoneType>
{
    @Override
    public JsonElement serialize(PhoneType phoneType, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.addProperty("id",phoneType.id);
        return object;
    }

    @Override
    public PhoneType deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        int id = object.get("id").getAsInt();
        return new PhoneType(id);
    }
}
