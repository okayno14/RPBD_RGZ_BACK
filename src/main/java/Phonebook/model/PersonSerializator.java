package Phonebook.model;

import com.google.gson.*;

import java.lang.reflect.Type;

public class PersonSerializator implements JsonSerializer<Person>, JsonDeserializer<Person>
{
    private GsonBuilder builder;

    public PersonSerializator()
    {
        builder = new GsonBuilder();
        builder.registerTypeAdapter(Street.class,new StreetSerializator());
        builder.registerTypeAdapter(Address.class,new AddressSerializator());
        builder.registerTypeAdapter(PhoneType.class,new PhoneTypeSerializator());
        builder.registerTypeAdapter(PhoneNumber.class,new PhoneNumberSerializer());
    }

    @Override
    public JsonElement serialize(Person person, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.addProperty("lastname", person.lastname);
        object.addProperty("firstname", person.firstname);
        object.addProperty("fathername", person.fathername);

        object.add("address",
                builder.create().toJsonTree(person.address));
        object.add("phoneNumberSet",
                builder.create().toJsonTree(person.phoneNumberSet));

        return object;
    }

    @Override
    public Person deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();

        String lastname = object.get("lastname").getAsString();
        String firstname = object.get("firstname").getAsString();
        String fathername = object.get("fathername").getAsString();
        return new Person(lastname,firstname,fathername);
    }
}
