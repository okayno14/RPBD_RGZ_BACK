package Phonebook.controller;

import Phonebook.model.Address;
import Phonebook.model.Person;
import Phonebook.model.PhoneNumber;
import Phonebook.model.SearchException;

//Интерфейс через который пользовательский класс View общается с
//контроллером
public interface Phonebook {
    Person addContact(String lastName,String firstName,String fatherName);
    void changeContact(Person p,String lastName,String firstName,String fatherName);
    void deleteContact(Person p);
    void addPhone(Person p, PhoneNumber pn);
    void changePhone(Person p,int pos,PhoneNumber newPn);
    void deletePhone(Person p, int pos);
    void changeAddress(Person p, Address add);
    void deleteAddress(Person p) throws Exception;
    void findFIOALL(String lastName,String firstName, String fatherName);
    void findContactBy4NumberPhone(int a,int b,int c,int d);
    Person findPerson(String lastName,String firstName, String fatherName) throws SearchException;
    Person findPerson(String lastName,
                             String firstName,
                             String fatherName,
                             PhoneNumber pn) throws SearchException;
    Person findPerson(String lastName,
                             String firstName,
                             String fatherName,
                             PhoneNumber pn, Address add) throws SearchException;
    void disconnect();
}
