package Phonebook.controller;

import Phonebook.model.Person;

//Интерфейс через который пользовательский класс View общается с
//контроллером
public interface Phonebook
{
    Person addContact(String lastName,String firstName,String fatherName);
}
