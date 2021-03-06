package Phonebook.controller;


import Phonebook.model.*;
import Phonebook.view.Console;
import Phonebook.view.View;
import org.hibernate.HibernateException;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Controller implements Phonebook
{
    View ui;
    Model model;

    public Controller()
    {
        try
        {
            model = new Model();
        }
        catch (Throwable e)
        {
            //дёрнуть вьюшку
            System.err.println("Ошибка чтения конфигурационного файла");
        }
    }

    public void setView(View ui) {this.ui = ui;}

    @Override

    public Person addContact(String lastName, String firstName, String fatherName)
    {
        Person p = new Person(lastName,firstName,fatherName);
        model.insertPerson(p);
        return p;
    }

    @Override
    public void deleteContact(Person p) throws SearchException
    {
        model.deletePerson(p);
    }

    @Override
    public void addPhone(Person p, PhoneNumber pn) {
        model.addPhone(p,pn);
    }

    @Override
    public void changePhone(Person p, int pos, PhoneNumber newPn) throws SearchException
    {
        model.changePhone(p,pos,newPn);
    }

    @Override
    public void deletePhone(Person p, int pos) throws SearchException
    {
        model.deletePhone(p,pos);
    }

    @Override
    public void changeAddress(Person p, Address add)
    {
        model.changeAddress(p,add);
    }

    @Override
    public void deleteAddress(Person p) throws Exception
    {
        try {model.deleteAddress(p);}
        catch (Exception e){throw e;}
    }

    @Override
    public List<Person> findFIOALL(String lastName, String firstName, String fatherName) throws SearchException
    {
        List<Person> res = model.findFIOAll(lastName,firstName,fatherName);
        if(res.size()==0)
            throw new SearchException(0);
        else
            return res;
    }

    @Override
    public List<Person> findContactBy4NumberPhone(int a, int b, int c, int d) throws SearchException
    {
        List<Person> res = model.findContactBy4NumberPhone(a,b,c,d);
        if(res.size()==0)
            throw new SearchException(0);
        else
            return res;
    }

    @Override
    public Person getPerson(int id) throws SearchException
    {
        try
        {
            return model.getPerson(id);
        }
        catch (SearchException se)
        {
            throw se;
        }


    }

    @Override
    public Person findPerson(String lastName,
                             String firstName,
                             String fatherName) throws SearchException
    {

        Person data = new Person(lastName,firstName,fatherName);
        Person contact=null;
        try
        {
            contact = model.findPerson(data,true);
        }
        catch (SearchException se)
        {
            int res = se.quantity();
            if(res>1 || res == 0)
                try
                {
                    contact = model.findPerson(data,false);
                }
                catch(SearchException se1)
                {
                    throw se1;
                }
        }
        return contact;
    }

    public Person findPerson(String lastName,
                             String firstName,
                             String fatherName,
                             PhoneNumber pn) throws SearchException
    {
        Person data = new Person(lastName,firstName,fatherName);
        Person contact=null;

        try
        {
            contact =  model.findPerson(data,pn);
        }
        catch (SearchException se2)
        {
            throw se2;
        }

        return contact;
    }

    public Person findPerson(String lastName,
                             String firstName,
                             String fatherName,
                             PhoneNumber pn, Address add) throws SearchException
    {
        Person data = new Person(lastName,firstName,fatherName);
        Person contact=null;

        try
        {
            contact = model.findPerson(data,pn,add);
        }
        catch(SearchException se3)
        {
            throw se3;
        }

        return contact;
    }

    @Override
    public void changeContact(Person p, String lastName, String firstName, String fatherName) {
        model.changeContact(p, lastName, firstName, fatherName);
    }

    @Override
    public void disconnect() { model.disconnect(); }
}
