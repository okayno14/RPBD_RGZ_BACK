package Phonebook.model;

import com.google.gson.*;


import java.lang.reflect.Type;

public class AddressSerializator implements JsonSerializer<Address>, JsonDeserializer<Address>
{
    private GsonBuilder builder;

    public AddressSerializator()
    {
        builder = new GsonBuilder().registerTypeAdapter(Street.class,new StreetSerializator());
        builder.registerTypeAdapter(Street.class,new StreetSerializator());
    }

    @Override
    public JsonElement serialize(Address address, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.addProperty("home",address.home);
        object.addProperty("appartement",address.appartement);
        JsonElement jsonElement=builder.create().toJsonTree(address.street);
        object.add("street",jsonElement);
        return object;
    }

    @Override
    public Address deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        int home = object.get("home").getAsInt();
        int appartement = object.get("appartement").getAsInt();
        Street street = builder.create().fromJson(object.get("street"),Street.class);
        return new Address(street,home,appartement);
    }
}
