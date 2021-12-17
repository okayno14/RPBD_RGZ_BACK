package Phonebook.view;

import Phonebook.controller.Controller;
import Phonebook.controller.Phonebook;
import Phonebook.model.*;
import org.hibernate.HibernateException;

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

    public void run(){
        while (flagRun){
            Menu();
            System.out.print(">>");
            switchMenu = in.nextInt();
            switch (switchMenu){
                case 0:
                    flagRun = false;
                    System.out.println("Выход");
                    break;
                case 1:
                    clr();
                    try {
                        currentPerson = findPerson();
                    }catch (Exception e){

                    }
//                    in.next();
//                    clr();
                    break;
                case 2:
                    clr();
                    try {
                        currentPerson = addContact();
                        if (toRunMenuTwo())
                            pcRun();

                    }catch (Exception e){
                        System.out.println("Ошибка!");
                    }
//                    clr();
                    break;
                case 3:
                    clr();
                    findBy4();

                    break;
                case 4:
                    clr();
                    findList_FIO();
                    break;
                default:
                    clr();
                    System.out.println("Очепятка");
                    break;
            }
        }

    }
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

    public void pcRun(){
        boolean exitWhileLocal = true;
        while (exitWhileLocal){
            pcMenu();
            System.out.print(">>");
            switchMenu = in.nextInt();
            switch (switchMenu){
                case 0:
                    clr();
                    exitWhileLocal = false;
                    break;
                case 1:
                    clr();
                    updateFIOContact();
                    break;
                case 2:
                    clr();
                    addAddress();
                    break;
                case 3:
                    clr();
                    userInterface.deleteAddress(currentPerson);
                    break;
                case 4:
                    clr();
                    addPhoneNumber();
                    break;
                case 5:
                    clr();
                    updatePhoneNumber();
                    break;
                case 6:
                    clr();

                    break;
                case 7:
                    clr();
                    userInterface.deleteContact(currentPerson);
                    exitWhileLocal=false;
                    break;
                case 8:
                case 9:
                default:
                    System.out.println("Очепятка");
                    break;
            }
        }

    }

    public void Menu() {
        System.out.println(
                "\n\n"
                +"------------------------------------------------\n"
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
        System.out.println(
                "\n\n"
                +"------------------------------------------------\n"
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

    private Person findPerson(){
        System.out.println(
                "------------------------------------------------\n"
                +"----------------| Поиск контакта |--------------\n"
                +"------------------------------------------------"
        );
        String last = get_a_lastNamePerson();
        String first = get_a_firstNamePerson();
        String father = get_a_fatherNamePerson();
        Person p = new Person(last,first,father);
        try{
            //p = userInterface.findPerson(last,first,father);
        }catch (Exception e){

//            if (e.toString() == "-1")
//                noRes();
            ///прописать вывод по кодам
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
        Person p = new Person(last,first,father);
        try {
            p = userInterface.addContact(last,first,father);
        }catch (HibernateException e){

            //тунель
        }
        return p;
    }

    private void findBy4(){
        System.out.println(
                "------------------------------------------------\n"
                +"------- Поиск контакта по 4-м символам  --------\n"
                +"-------------- телефонного номера --------------\n"
                +"------------------------------------------------"
        );
        System.out.print("Введите 4-е символа телефонного номера контакта :");
        //ввод 4 чмсел
    }

    private void findList_FIO(){
        System.out.println(
                "------------------------------------------------\n"
                +"------------ Поиск контакта по ФИО  -----------\n"
                +"------------------------------------------------"
        );
        String last = get_a_lastNamePerson();
        String first = get_a_firstNamePerson();
        String father = get_a_fatherNamePerson();
        userInterface.findFIOALL(last,first,father);
    }

    private void updateFIOContact(){
        System.out.println(
                "------------------------------------------------\n"
                +"------------- Изменить ФИО контакту ------------\n"
                +"------------------------------------------------"
        );
        String last = get_a_lastNamePerson();
        String first = get_a_firstNamePerson();
        String father = get_a_fatherNamePerson();
        Person person = new Person(last,first,father);
        userInterface.changeContact(person);
    }

    private void addAddress(){
        System.out.println(
                "------------------------------------------------\n"
                +"------------ Добавить адрес контакту -----------\n"
                +"------------------------------------------------"
        );
        String nameStreet = get_a_addressName();
        int numberHome = get_a_numberhome();
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

    private void updatePhoneNumber(){
        System.out.println(
                "------------------------------------------------\n"
                +"-------- Редактировать номер контакту ----------\n"
                +"------------------------------------------------"
        );
        ///отрисовка
//        if (drawPhoneNumbers(currentPerson.getPhoneNumberSet())){
//
//        }
    }

    private void deletePhone(){

    }
}
