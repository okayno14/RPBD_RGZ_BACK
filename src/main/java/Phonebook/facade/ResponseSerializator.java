package Phonebook.facade;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class ResponseSerializator implements JsonSerializer<Response>
{
    @Override
    public JsonElement serialize(Response response, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        if(response.status != -1)
            object.addProperty("status",response.status);
        if(response.message != null)
            object.addProperty("message",response.message);
        if(response.data != null)
            object.add("data",response.data);
        return object;
    }
}
