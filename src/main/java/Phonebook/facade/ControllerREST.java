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
        get("/hello",(req,resp)->"Hello, World");
    }


}
