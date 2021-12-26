package Phonebook.model;

import com.google.gson.*;


import java.lang.reflect.Type;

public class AddressSerializator implements JsonSerializer<Address>, JsonDeserializer<Address>
{
    GsonBuilder builder;

    public AddressSerializator()
    {
        builder = new GsonBuilder().registerTypeAdapter(Street.class,new StreetSerializator());
        builder.registerTypeAdapter(Street.class,new StreetSerializator());
    }


    @Override
    public Address deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return null;
    }

    @Override
    public JsonElement serialize(Address address, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.addProperty("home",address.home);
        object.addProperty("appartement",address.appartement);
        //object.addProperty("street", builder.create().
        //        toJsonTree(address.street).getAsString();
        JsonElement jsonElement=builder.create().toJsonTree(address.street);
        object.add("street",jsonElement);
        //tree.hashCode();
        return object;
    }
}
