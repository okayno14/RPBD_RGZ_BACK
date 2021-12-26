package Phonebook.model;

import com.google.gson.*;

import java.lang.reflect.Type;

public class PhoneNumberSerializer implements JsonSerializer<PhoneNumber>, JsonDeserializer<PhoneNumber>
{
    GsonBuilder builder;

    public PhoneNumberSerializer()
    {
        builder = new GsonBuilder();
        builder.registerTypeAdapter(PhoneType.class,new PhoneTypeSerializator());
    }

    @Override
    public JsonElement serialize(PhoneNumber phoneNumber, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.addProperty("number",phoneNumber.number);
        JsonElement element = builder.create().toJsonTree(phoneNumber.phoneType);
        object.add("phoneType",element);
        return object;
    }

    @Override
    public PhoneNumber deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        String number = object.get("number").getAsString();
        PhoneType phoneType = builder.create().
                fromJson(object.get("phoneType"),PhoneType.class);
        return new PhoneNumber(phoneType,number);
    }
}
