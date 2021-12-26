package Phonebook.view;

import Phonebook.controller.Controller;
import Phonebook.controller.Phonebook;
import Phonebook.model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.eclipse.jetty.io.WriterOutputStream;


import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Console extends View
{
    Phonebook userInterface;
    private Person currentPerson;
    private boolean flagRun;
    private int switchMenu;


    public Console(Controller con){
        userInterface = con;
        flagRun = true;
    }

    //Обработчики меню
    public void run() {
        clr();
        while (flagRun) {
            try {
                Menu();
                System.out.print(">>");
                switchMenu = in.nextInt();
                switch (switchMenu) {
                    case 0:
                        flagRun = false;
                        System.out.println("Выход");
                        break;
                    case 1:
                        clr();
                        try
                        {
                            currentPerson = findPerson();
                            drawPerson(currentPerson);
                            if (toRunMenuTwo())
                                pcRun();
                        } catch(Exception e) {e.printStackTrace();}
                        {}
                        messageEnter();
                        break;
                    case 2:
                        clr();
                        try {
                            currentPerson = addContact();
                            if (toRunMenuTwo())
                                pcRun();

                        } catch (Exception e) {
//                            System.out.println("Ошибка!");
                        }
                        messageEnter();
                        break;
                    case 3:
                        clr();
                        findBy4();
                        messageEnter();
                        break;
                    case 4:
                        clr();
                        findList_FIO();
                        messageEnter();
                        break;
                    default:
                        clr();
                        System.out.println("Очепятка");
                        messageEnter();
                        break;
                }
            } catch (InputMismatchException i) {
                System.out.println(i.toString());
                in.next();
                clr();
            }
        }
    }
    public void pcRun(){
        clr();
        boolean exitWhileLocal = true;
        int switchMenuPC;
        while (exitWhileLocal){
            pcMenu();
            System.out.print(">>");
            switchMenuPC = in.nextInt();
            switch (switchMenuPC){
                case 0:
                    clr();
                    exitWhileLocal = false;
                    break;
                case 1:
                    clr();
                    updateFIOContact();
                    messageEnter();
                    break;
                case 2:
                    clr();
                    addAddress();
                    messageEnter();
                    break;
                case 3:
                    clr();
                    deleteAddress();
                    messageEnter();
                    break;
                case 4:
                    clr();
                    addPhoneNumber();
                    messageEnter();
                    break;
                case 5:
                    clr();
                    updatePhoneNumber();
                    messageEnter();
                    break;
                case 6:
                    clr();
                    deletePhone();
                    messageEnter();
                    break;
                case 7:
                    clr();
                    deleteContact();
                    exitWhileLocal = false;
                    break;
                case 8:
                    clr();
                    try {
                        drawPhoneNumbers(currentPerson.getPhoneNumberSet());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    messageEnter();
                    break;
                case 9:
                    clr();
                    try {
                        drawAddress(currentPerson.getAddress());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    messageEnter();
                    break;
                default:
                    System.out.println("Очепятка");
                    messageEnter();
                    break;
            }
        }
    }

    //Отрисовка меню
    public void Menu() {
        System.out.println(
                "------------------------------------------------\n"
                +"--------------| Телефонная книга |--------------\n"
                +"------------------------------------------------\n"
                +"[1] - Найти контакт\n"
                +"[2] - Добавить контакт в ТК\n"
                +"------------------------------------------------\n"
                +"[3] - Найти контакты по последним 4-м цифрам номера\n"
                +"[4] - Найти контакты по ФИО\n"
                +"------------------------------------------------\n"
                +"[0] - Выход из программы"
        );
    }
    public void pcMenu() {
        drawPerson(currentPerson);
        System.out.println(
                "------------------------------------------------\n"
                +"--------------|  Редактор контакта |------------\n"
                +"------------------------------------------------\n"
                +"[1] - Обновить ФИО контакта\n"
                +"[2] - Обновить адрес контакту\n"
                +"[3] - Удалить адрес контакту\n"
                +"[4] - Добавить номер телефона контакту\n"
                +"[5] - Переписать номер контакта\n"
                +"[6] - Удалить номер контакту\n"
                +"[7] - Удалить контакт\n"
                +"[8] - Посмотреть номера контакта\n"
                +"[9] - Просмотреть адрес проживания контакта\n"
                +"------------------------------------------------\n"
                +"[0] - Выход из подменю\n"
        );
    }

    //Методы, используемые обработчиками
    private boolean toRunMenuTwo(){
        System.out.println(
                "Желаете редактировать контакт?\n"
                        +"[1] - Да;   [0] - Нет;"
        );
        int tmp;
        while (true){
            tmp = in.nextInt();
            if (tmp == 0)
                return false;
            else if (tmp == 1)
                return true;
            else
                System.out.println("Очепятка");
        }
    }
    private Person findPerson() throws Exception
    {
        System.out.println(
                "------------------------------------------------\n"
                +"----------------| Поиск контакта |--------------\n"
                +"------------------------------------------------"
        );
        String last = get_a_lastNamePerson();
        String first = get_a_firstNamePerson();
        String father = get_a_fatherNamePerson();
        Person p = new Person(last,first,father);
        try
        {
            p = userInterface.findPerson(last,first,father);
        }
        catch (Exception e)
        {
            String msg = e.getMessage();

            if (msg.equalsIgnoreCase("-1"))
                noRes();
            if (msg.equalsIgnoreCase("-2"))
                clones();

            throw new Exception();
        }


        return p;
    }
    private Person addContact(){
        System.out.println(
                "------------------------------------------------\n"
                +"---------------| Добавление контакта |----------\n"
                +"------------------------------------------------"
        );
        String last = get_a_lastNamePerson();
        String first = get_a_firstNamePerson();
        String father = get_a_fatherNamePerson();
        Person p;
        try {
            p = userInterface.addContact(last,first,father);
        }catch (Exception e){
            System.out.println("По неким причинам мне неудалоь добавить контакт");
            throw e;
        }
        return p;
    }

    private void deleteAddress()
    {
        try{ userInterface.deleteAddress(currentPerson);}
        catch (Exception e){System.out.println(e.getMessage());}
    }

    private void findBy4(){
        System.out.println(
                "------------------------------------------------\n"
                +"------| Поиск контакта по 4-м символам |--------\n"
                +"-------------| телефонного номера |-------------\n"
                +"------------------------------------------------"
        );
        System.out.print("Введите 4-е символа телефонного номера контакта : ");
        int numberBy4;
        while (true){
            numberBy4 = in.nextInt();
            if (numberBy4 < 10000 && numberBy4 >= 0){
                break;
            }
            System.out.println("Введите 4 числа");
        }
        //userInterface.findContactBy4NumberPhone((numberBy4/1000),((numberBy4%1000)/100),((numberBy4%100)/10),(numberBy4%10));
    }
    private void findList_FIO(){
        System.out.println(
                "------------------------------------------------\n"
                +"-----------| Поиск контакта по ФИО |-----------\n"
                +"------------------------------------------------"
        );
        String last = get_a_lastNamePerson();
        String first = get_a_firstNamePerson();
        String father = get_a_fatherNamePerson();
        //userInterface.findFIOALL(last,first,father);
    }
    private void updateFIOContact(){
        System.out.println(
                "------------------------------------------------\n"
                +"------------| Изменить ФИО контакту |-----------\n"
                +"------------------------------------------------"
        );
        String last = get_a_lastNamePerson();
        String first = get_a_firstNamePerson();
        String father = get_a_fatherNamePerson();
        userInterface.changeContact(currentPerson,last,first,father);
    }
    private void addAddress(){
        System.out.println(
                "------------------------------------------------\n"
                +"-----------| Добавить адрес контакту |----------\n"
                +"------------------------------------------------"
        );
        String nameStreet = get_a_addressName();
        int numberHome = get_a_numberHome();
        int numberApartment = get_a_numberApartment();
        Address address = new Address(new Street(nameStreet),numberHome,numberApartment);
        userInterface.changeAddress(currentPerson,address);
    }
    private void addPhoneNumber(){
        System.out.println(
                "------------------------------------------------\n"
                +"---------| Добавление номера контакту |---------\n"
                +"------------------------------------------------\n"
        );
        String number = get_a_Number();
        int type = get_a_type();

        PhoneNumber phoneNumber = new PhoneNumber(new PhoneType(type),number);
        userInterface.addPhone(currentPerson,phoneNumber);
    }
    private void updatePhoneNumber()
    {
        System.out.println(
                "------------------------------------------------\n"
                +"-------| Редактировать номер контакту |---------\n"
                +"------------------------------------------------"
        );
        drawPhoneNumbers(currentPerson.getPhoneNumberSet());

        int pos=-1;
        while(pos<0 || pos>currentPerson.getPhoneNumberSet().size())
        {
            System.out.println("Введите индекс обновляемого телефона");
            pos = in.nextInt();
        }

        String number = get_a_Number();
        int type = get_a_type();
        PhoneNumber phoneNumberNew = new PhoneNumber(new PhoneType(type),number);
        userInterface.changePhone(currentPerson,pos,phoneNumberNew);
    }

    private void deletePhone(){
        System.out.println(
                "------------------------------------------------\n"
                +"-------| Редактировать номер контакта |---------\n"
                +"------------------------------------------------"
        );
        drawPhoneNumbers(currentPerson.getPhoneNumberSet());
        int pos=-1;
        while(pos<0 || pos>currentPerson.getPhoneNumberSet().size())
        {
            System.out.println("Введите индекс удаляемого телефона");
            pos = in.nextInt();
        }
        userInterface.deletePhone(currentPerson,pos);
    }
    private void deleteContact(){
        System.out.println(
                "------------------------------------------------\n"
                +"----------------| Удаление контакта |-----------\n"
                +"------------------------------------------------"
        );
        userInterface.deleteContact(currentPerson);
    }
}
