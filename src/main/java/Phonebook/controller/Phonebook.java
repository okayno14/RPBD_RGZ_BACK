package Phonebook.controller;

import Phonebook.model.Address;
import Phonebook.model.Person;
import Phonebook.model.PhoneNumber;

//Интерфейс через который пользовательский класс View общается с
//контроллером
public interface Phonebook {
    Person addContact(String lastName,String firstName,String fatherName);
    void changeContact(Person p,String lastName,String firstName,String fatherName);
    void deleteContact(Person p);
    void addPhone(Person p, PhoneNumber pn);
    void changePhone(Person p,int pos,PhoneNumber newPn);
    void deletePhone(Person p, PhoneNumber pn);
    void changeAddress(Person p, Address add);
    void deleteAddress(Person p);
    void findFIOALL(String lastName,String firstName, String fatherName);
    void findContactBy4NumberPhone(int a,int b,int c,int d);
    Person findPerson(String lastName,String firstName, String fatherName) throws Exception;
    void disconnect();
}
