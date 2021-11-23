package Phonebook.controller;


import Phonebook.model.Address;
import Phonebook.model.Person;
import Phonebook.model.PhoneNumber;
import Phonebook.model.Model;
import Phonebook.view.Console;
import Phonebook.view.View;

public class Controller implements Phonebook
{
    View ui;
    Model model;

    public Controller()
    {
        model = new Model();
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
    public void changeContact(Person p) {

    }

    @Override
    public void deleteContact(Person p) {

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
    public void changeAddress(Person p, Address add) {

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
    }
}
