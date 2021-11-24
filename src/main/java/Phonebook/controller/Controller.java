package Phonebook.controller;


import Phonebook.model.Address;
import Phonebook.model.Person;
import Phonebook.model.PhoneNumber;
import Phonebook.model.Model;
import Phonebook.view.Console;
import Phonebook.view.View;

import java.util.ArrayList;

public class Controller implements Phonebook
{
    View ui;
    Model model;

    public Controller(){
        try
        {
            model = new Model();
        }
        catch (Exception e)
        {
            System.err.println("Ошибка подключения к БД. Переход в офлайн режим");
        }
    }
    public void setView(View ui) {this.ui = ui;}

    @Override
    public Person addContact(String lastName, String firstName, String fatherName)
    {
        try {
            Person p = new Person(lastName, firstName, fatherName);
            model.insertPerson(p);
            ui.success();
            return p;
        }catch (Exception e){
            ui.fail();
            throw e;
        }
    }

    @Override
    public void changeContact(Person p,String lastName,String firstName,String fatherName) {
        try {
//            mode.changeContact(p,lastName,firstName,fatherName);
            ui.success();
        }catch (Exception e){
            ui.fail();
        }
    }

    @Override
    public void deleteContact(Person p) {
        try {
//            model.deleteContact(p);
            ui.success();
        }catch (Exception e){
            ui.fail();
        }
    }

    @Override
    public void addPhone(Person p, PhoneNumber pn) {
        try {
//            model.addPhone(p,pn);
            ui.success();
        }catch (Exception e){
            ui.fail();
        }
    }

    @Override
    public void changePhone(Person p, PhoneNumber oldPn, PhoneNumber newPn) {
        try {
//            model.changePhone(p,oldPn,newPn);
            ui.success();
        }catch (Exception e){
            ui.fail();
        }
    }

    @Override
    public void deletePhone(Person p, PhoneNumber pn) {
        try {
//            model.deletePhone(p,pn);
            ui.success();
        }catch (Exception e){
            ui.fail();
        }
    }

    @Override
    public void changeAddress(Person p, Address add) {
        try {
//            model.changeAddress(p,add);
            ui.success();
        }catch (Exception e){
            ui.fail();
        }
    }

    @Override
    public void deleteAddress(Person p) {
        try {
//            mode.deleteAddress(p);
            ui.success();
        }catch (Exception e){
            ui.fail();
        }
    }

    @Override
    public void findFIOALL(String lastName, String firstName, String fatherName) {
        try {
            ArrayList<Person> tmp = new ArrayList<>();// <------------- ito hren' vremennaya dla ubrania osibok | nuzno aktualka modelki | posle unichtozit'
//            tmp = model.findFIOALL(lastName,firstName,fatherName);
            for (int i = 0; i < tmp.size();i++){
                ui.drawPerson(tmp.get(i));
                ui.drawPhoneNumbers(tmp.get(i).getPhoneNumberSet());
            }
            if (tmp.size() == 0)
                ui.noRes();
        }catch (Exception e){
            ui.fail();
        }
    }

    @Override
    public void findContactBy4NumberPhone(int a, int b, int c, int d) {
//        testing
        System.out.println(
                "a = "+ a
                +"b = "+ b
                +"c = "+ c
                +"d = "+ d
        );
        try {
            ArrayList<Person> tmp = new ArrayList<>();
//            tmp = model.findContactBy4NumberPhone(a,b,c,d);
            for (int i = 0; i< tmp.size();i++){
                ui.drawPerson(tmp.get(i));
                ui.drawPhoneNumbers(tmp.get(i).getPhoneNumberSet());

                if (tmp.size() == 0)
                    ui.noRes();
            }
        }catch (Exception e){
            ui.fail();
        }
    }

    @Override
    public Person findPerson(String lastName, String firstName, String fatherName) throws Exception {
        try {
            // по UML я понял что этот поиск нужно менять. ктк нет модели пока это псевдо контролер

        }catch (Exception e){
            ui.fail();
        }

        return null;
    }

    @Override
    public void disconnect() { model.disconnect(); }
}
