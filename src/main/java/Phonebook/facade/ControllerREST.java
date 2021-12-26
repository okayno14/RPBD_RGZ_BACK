package Phonebook.facade;

import Phonebook.controller.Controller;
import Phonebook.controller.Phonebook;
import Phonebook.model.*;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import spark.Spark;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static spark.Spark.*;

public class ControllerREST
{
    private static Phonebook repo;
    private static GsonBuilder builder = new GsonBuilder();

    public ControllerREST(String[] args, Controller repo)
    {
        this.repo = repo;

        //настройка builder, который будет парсить json
        builder.setPrettyPrinting();
        builder.registerTypeAdapter(Street.class,new StreetSerializator());
        builder.registerTypeAdapter(Address.class,new AddressSerializator());
        builder.registerTypeAdapter(PhoneType.class,new PhoneTypeSerializator());
        builder.registerTypeAdapter(PhoneNumber.class,new PhoneNumberSerializer());
        builder.registerTypeAdapter(Person.class, new PersonSerializator(builder));

        Spark.ipAddress("192.168.1.92");
        endpoints();
        System.out.println(Spark.port());

    }

    public static void endpoints()
    {
        String noAddr = "{\n" +
                "  \"lastname\": \"q\",\n" +
                "  \"firstname\": \"q\",\n" +
                "  \"fathername\": \"q\",\n" +
                "  \"phoneNumberSet\": [\n" +
                "    {\n" +
                "      \"number\": \"8(888)888-88-88\",\n" +
                "      \"phoneType\": {\n" +
                "        \"id\": 1\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        Person p = builder.create().fromJson(noAddr,Person.class);

        //Type token = new TypeToken<ArrayList<PhoneNumber>>() {}.getType();
        //ArrayList<PhoneNumber> phones = builder.create().fromJson(noAddr,token);

        get("/hello",(req,resp)->"Hello, World");
    }


}
