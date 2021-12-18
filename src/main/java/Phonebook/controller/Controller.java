package Phonebook.controller;


import Phonebook.model.*;
import Phonebook.view.Console;
import Phonebook.view.View;
import org.hibernate.HibernateException;

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
    public void deleteContact(Person p)
    {
        model.deletePerson(p);
    }

    @Override
    public void addPhone(Person p, PhoneNumber pn) {
        model.addPhone(p,pn);
    }

    @Override
    public void changePhone(Person p, int pos, PhoneNumber newPn)
    {
        model.changePhone(p,pos,newPn);
    }

    @Override
    public void deletePhone(Person p, int pos)
    {
        model.deletePhone(p,pos);
    }

    @Override
    public void changeAddress(Person p, Address add)
    {
        model.changeAddress(p,add);
    }

    @Override
    public void deleteAddress(Person p) {
        model.deleteAddress(p);
    }

    @Override
    public void findFIOALL(String lastName, String firstName, String fatherName) {

    }

    @Override
    public void findContactBy4NumberPhone(int a, int b, int c, int d) {

    }

    @Override
    public Person findPerson(String lastName, String firstName, String fatherName) throws Exception {
        if (lastName == null || firstName == null || fatherName == null){
            System.out.println("не все данные!");
            Exception exception = new Exception("-1");
            throw exception;
        }
        Person data = new Person(lastName,firstName,fatherName);
        Person contact=null;
        PhoneNumber pn=null;
        Address add=null;
        try
        {
            contact = model.findPerson(data,true);
        }
        catch (SearchException se)
        {
            int res = se.quantity();
            //if(res==0)
            //    throw new Exception("-1");
            if(res>1 || res == 0)
                try
                {
                    contact = model.findPerson(data,false);
                }
                catch(SearchException se1)
                {
                    res=se1.quantity();
                    if(res==0)
                        throw new Exception("-1");
                    if(res>1)
                        try
                        {
                            String number = ui.get_a_Number();
                            int type = ui.get_a_type();
                            pn = new PhoneNumber(new PhoneType(type),number);
                            contact =  model.findPerson(data,pn);
                        }
                        catch (SearchException se2)
                        {
                            res = se2.quantity();
                            if(res==0)
                                throw new Exception("-1");
                            if(res>1 && pn != null)
                                try
                                {
                                    String streetname = ui.get_a_addressName();
                                    int home = ui.get_a_numberHome();
                                    int appartement = ui.get_a_numberApartment();
                                    add = new Address(new Street(streetname),home,appartement);
                                    contact = model.findPerson(data,pn,add);

                                }
                                catch(SearchException se3)
                                {
                                    res = se3.quantity();
                                    if (res==0)
                                        throw new Exception("-1");
                                    if(res>1)
                                        throw new Exception("-2");
                                }
                        }

                }

        }
        return contact;
    }

    @Override
    public void changeContact(Person p, String lastName, String firstName, String fatherName) {

    }

    @Override
    public void disconnect() { model.disconnect(); }
}
