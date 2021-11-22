package Phonebook.view;

import Phonebook.model.Address;
import Phonebook.model.Person;
import Phonebook.model.PhoneNumber;

import java.util.ArrayList;
import java.util.Scanner;

//Абстрактный класс View для приложения
//Можно будет создавать разные пользовательские интерфейсы и прикручивать их к контроллеру
public abstract class View
{
    protected Scanner in = new Scanner(System.in);
    private final String os = System.getProperty("os.name");

    //Обработчики меню
    abstract public void run();

    //Сигналы для юзера
    public void success(){
        System.out.println("Success!!!!))");
    }
    public void fail(){
        System.out.println("Fail!!:(");
    }
    public void noRes(){
        System.out.println("Совпадений не найдено");
    }
    public void clones(){
        System.out.println("Атака клонов");
    }

    //вспомогательные методы
    public void drawPerson(Person person){

    }
    public void drawAddress(Address address){}
    public void drawPhoneNumber(PhoneNumber phoneNumber){}
    public void drawPhoneNumbers(ArrayList<PhoneNumber> args){}
    public boolean checkFormat(String number){

        return false;
    }
    protected void clr()
    {
        try
        {
            if (os.contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        }
        catch (Exception e){e.printStackTrace();}
    }


    //методы для инпута данных
    public String get_a_bumber(){
//        return in.nextLine();
        return null;
    }
    public String get_a_addressName(){
        return null;
    }
    public int get_a_type(){
        return 0;
    }
    public int get_a_numberApartment(){
        return 0;
    }
    public int get_a_numberhome(){
        return 0;
    }
    public String get_a_lastNamePerson(){
        System.out.print(
                "Введите фамилию контакта : "
        );
        String name = in.nextLine();
        return name;
    }
    public String get_a_firstNamePerson(){
        System.out.print(
                "Введите имя контакта : "
        );
        String name = in.nextLine();
        return name;
    }
    public String get_a_fatherNamePerson(){
        System.out.print(
                "Введите отчество контакта : "
        );
        String name = in.nextLine();
        return name;
    }
}
