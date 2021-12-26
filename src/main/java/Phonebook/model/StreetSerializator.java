package Phonebook.model;

import com.google.gson.*;

import java.lang.reflect.Type;

public class StreetSerializator implements JsonSerializer<Street>, JsonDeserializer<Street>
{

    @Override
    public JsonElement serialize(Street street, Type type, JsonSerializationContext jsonSerializationContext)
    {
        JsonObject object = new JsonObject();
        object.addProperty("streetname",street.streetname);
        return object;
    }

    @Override
    public Street deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        String streetName = object.get("streetname").getAsString();
        return new Street(streetName);
    }
}
