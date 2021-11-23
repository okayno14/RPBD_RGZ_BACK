package Phonebook.controller;

import Phonebook.model.Model;
import Phonebook.model.Person;
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
}
