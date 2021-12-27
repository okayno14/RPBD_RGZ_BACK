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

    private static JsonElement contacts(List<Person> contacts)
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

                get("/:pn/:type/:home/:appartement/:streetname",(req,resp)->
                {
                    resp.type("application/json");
                    String lastname = req.params(":lastname");
                    String firstname = req.params(":firstname");
                    String fathername = req.params(":fathername");
                    String number = req.params(":pn");
                    int type = Integer.parseInt(req.params(":type"));
                    PhoneNumber pn = new PhoneNumber(new PhoneType(type),number);

                    int home = Integer.parseInt(req.params(":home"));
                    int appartement = Integer.parseInt(req.params(":appartement"));
                    String street = req.params(":streetname");
                    Address add = new Address(new Street(street),home,appartement);

                    Person finded=null;

                    try
                    {
                        finded = repo.findPerson(lastname,
                                firstname,
                                fathername,
                                pn,add);
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

                get("/list",(req,resp)->
                {
                    resp.type("application/json");

                    String lastname = req.params(":lastname");
                    String firstname = req.params(":firstname");
                    String fathername = req.params(":fathername");

                    List<Person> arr;

                    try
                    {
                        arr=repo.findFIOALL( lastname,
                                firstname,
                                fathername);

                        return builder.create().toJson(
                                new Data(
                                        contacts(arr)));
                    }
                    catch (SearchException se)
                    {
                        return builder.create().toJson(new Error("nothing"));
                    }
                });

        });

        get("/find/person/:a/:b/:c/:d",(req,resp)->
        {
            resp.type("application/json");
            int a = Integer.parseInt(req.params(":a"));
            int b = Integer.parseInt(req.params(":b"));
            int c = Integer.parseInt(req.params(":c"));
            int d = Integer.parseInt(req.params(":d"));

            List<Person> arr;

            try
            {
                arr=repo.findContactBy4NumberPhone(a,b,c,d);

                return builder.create().toJson(
                        new Data(
                                contacts(arr)));
            }
            catch (SearchException se)
            {
                return builder.create().toJson(new Error("nothing"));
            }
        });

        get("/hello",(req,resp)->"Hello, World");

        put("/update/address/:id",(req,resp)->
        {
            resp.type("application/json");
            try
            {
                repo.changeAddress(repo.getPerson(Integer.parseInt(req.params(":id"))),
                        builder.create().fromJson(req.body(),Address.class));

                resp.status(200);
                return builder.create().toJson(new Notification());
            }
            catch (SearchException se)
            {
                resp.status(404);
                return builder.create().toJson(new Error("Incorrect id"));
            }
        });

        delete("/delete/address/:id",(req,resp)->
        {
            resp.type("application/json");
            try
            {
                System.out.println("Звходим в метод");
                System.out.println(Integer.parseInt(req.params(":id")));
                System.out.println(repo.getPerson(
                        Integer.parseInt(req.params(":id"))).getFathername());
                repo.deleteAddress(
                        repo.getPerson(
                                Integer.parseInt(req.params(":id"))
                        ));
                System.out.println("Вызвал удаление");
                return builder.create().toJson(new Notification());
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
                return builder.create().toJson(new Error("Incorrect id"));
            }

        });

    }


}
