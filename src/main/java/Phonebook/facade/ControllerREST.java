package Phonebook.facade;

import Phonebook.controller.Controller;
import Phonebook.controller.Phonebook;
import Phonebook.model.*;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import spark.Spark;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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

        staticFileLocation("/static");


        Spark.ipAddress("192.168.1.92");
        endpoints();
        System.out.println(Spark.port());

    }

    private JsonElement contacts(List<Person> contacts)
    {
        return builder.create().toJsonTree(contacts);
    }

    public static void endpoints()
    {


        post("/add/person",(req,resp)->
        {
            resp.type("application/json");
            Person p = builder.create().fromJson(req.body(),Person.class);
            p = repo.addContact(p.getLastname(),
                                p.getFirstname(),
                                p.getFathername());

            JsonObject o =  new JsonObject();
            o.addProperty("id",p.getId());

            return builder.create().toJson
                    (new Data(builder.create().toJsonTree(o)));
        });

//        get("/find/person/:lastname/:firstname/:fathername/empty",(req,resp)->
//        {
//            resp.type("application/json");
//
//            String lastname = req.params(":lastname");
//            String firstname = req.params(":firstname");
//            String fathername = req.params(":fathername");
//
//            Person finded=null;
//            try
//            {
//                finded = repo.findPerson(lastname,firstname,fathername);
//                resp.status(200);
//                return builder.create().toJson
//                        (new Data(builder.create().toJsonTree(finded)));
//            }
//            catch (SearchException se)
//            {
//                StringBuffer stringBuffer = new StringBuffer();
//                stringBuffer.append("Error. Finded ");
//                stringBuffer.append(Integer.toString(se.quantity()));
//                stringBuffer.append(" elements");
//
//                if(se.quantity() ==0)
//                    resp.status(404);
//                else
//                    resp.status(402);
//
//            return  builder.create().toJson
//                        (new Error(stringBuffer.toString()));
//            }
//        });

        path("/find/person/:lastname/:firstname/:fathername",()->
        {
                get("/empty",(req,resp)->
                {
                    resp.type("application/json");

                    String lastname = req.params(":lastname");
                    String firstname = req.params(":firstname");
                    String fathername = req.params(":fathername");

                    Person finded=null;
                    try
                    {
                        finded = repo.findPerson(lastname,firstname,fathername);
                        resp.status(200);
                        return builder.create().toJson
                                (new Data(builder.create().toJsonTree(finded)));
                    }
                    catch (SearchException se)
                    {
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append("Error. Finded ");
                        stringBuffer.append(Integer.toString(se.quantity()));
                        stringBuffer.append(" elements");

                        if(se.quantity() ==0)
                            resp.status(404);
                        else
                            resp.status(402);

                        return  builder.create().toJson
                                (new Error(stringBuffer.toString()));
                    }
                });

                get("/:pn/:type",(req,resp)->
                {
                    System.out.println("Зашли в ендпоинт");

                    resp.type("application/json");

                    String lastname = req.params(":lastname");
                    String firstname = req.params(":firstname");
                    String fathername = req.params(":fathername");


                    String number = req.params(":pn");

                    int type = Integer.parseInt(req.params(":type"));
                    PhoneNumber pn = new PhoneNumber(new PhoneType(type),number);

                    Person finded=null;

                    try
                    {
                        finded = repo.findPerson(lastname,
                                                firstname,
                                                fathername,
                                                pn);

                        System.out.println(finded.getId());
                        return builder.create().toJson
                                (new Data(builder.create().toJsonTree(finded)));
                    }
                    catch (SearchException se)
                    {
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append("Error. Finded ");
                        stringBuffer.append(Integer.toString(se.quantity()));
                        stringBuffer.append(" elements");

                        if(se.quantity() ==0)
                            resp.status(404);
                        else
                            resp.status(402);

                        return  builder.create().toJson
                                (new Error(stringBuffer.toString()));
                    }
                });

        });



        get("/hello",(req,resp)->"Hello, World");

    }


}
