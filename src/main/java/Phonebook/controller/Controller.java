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

    }

    @Override
    public void changePhone(Person p, PhoneNumber oldPn, PhoneNumber newPn) {

    }

    @Override
    public void deletePhone(Person p, PhoneNumber pn) {

    }

    @Override
    public void changeAddress(Person p, Address add)
    {
        model.changeAddress(p,add);
    }

    @Override
    public void deleteAddress(Person p) {

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
        return null;


    }

    @Override
    public void changeContact(Person p, String lastName, String firstName, String fatherName) {

    }

    @Override
    public void disconnect() { model.disconnect(); }
}
