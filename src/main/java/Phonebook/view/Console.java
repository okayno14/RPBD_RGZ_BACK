package Phonebook.view;

import Phonebook.controller.Controller;
import Phonebook.controller.Phonebook;
import Phonebook.model.Person;

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
//                        currentPerson =
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {

                    }
                    break;
                case 2:
                    clr();

                    break;
                case 3:
                    clr();

                    break;
                case 4:
                    clr();

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
                    break;
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
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

    Person findPerson(){
        System.out.println(
                "------------------------------------------------"
                +"----------------| Поиск контакта |--------------"
                +"------------------------------------------------"
        );

        return null;
    }
}
