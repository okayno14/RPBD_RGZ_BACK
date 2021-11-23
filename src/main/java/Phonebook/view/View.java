package Phonebook.view;

import Phonebook.model.Address;
import Phonebook.model.Person;
import Phonebook.model.PhoneNumber;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

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
    public void drawPhoneNumber(PhoneNumber phoneNumber){//тут нужны проверки!
        if (phoneNumber != null){
            System.out.println(phoneNumber.getNumber());
            System.out.println(phoneNumber.getPhoneType().getTypename());
        }
    }
    public void drawPhoneNumbers(Set<PhoneNumber> args){
        if (args.size() > 0){
            Iterator<PhoneNumber> iterator = args.iterator();
            for (int i = 0; i < args.size();i++){
                System.out.print("["+ i + "] ");
//                drawPhoneNumber(args.iterator().next());
                //Нужно понять как вытащить элемент из сета по индексу
            }
        }
        else
            System.out.println("\"Ошибка. Нет номеров.");
    }
    public boolean checkFormat(String number){
        if (number.charAt(1) == '(' &&
            number.charAt(5) == ')' &&
            number.charAt(9) == '-' &&
            number.charAt(12) == '-' &&
            number.charAt(15) == '\0')
            return true;
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
    protected void messageEnter(){
        System.out.println("Для продолжения нажмите клавишу Enter");
    }


    //методы для инпута данных
    public String get_a_Number(){
        System.out.println(
                "Введите номер телефона\n"
                +"X(XXX)XXX-XX-XX"
        );
        String number = in.next();
        while (!checkFormat(number)){
            System.out.println("Введите заново:");
            number = in.next();
        }
        return number;
    }
    public String get_a_addressName(){
        System.out.print("Введите название улицы проживания :");
        String name = in.next();
        return name;
    }
    public int get_a_type(){
        System.out.println(
                "Введите тип номера телефона"
                +"[1] - моюильный "
                +"[2] - рабочий "
                +"[3] - домашний"
        );
        int type = in.nextInt();
        while (true){
            if (type > 0 || type <= 3)
                return type;
            else
                System.out.println("Введитте существующий тип");
        }
    }
    public int get_a_numberApartment(){
        System.out.println("Введите номер квартиры :");
        int number = in.nextInt();
        return number;
    }
    public int get_a_numberHome(){
        System.out.println("Введите номер дома");
        int number = in.nextInt();
        return number;
    }
    public String get_a_lastNamePerson(){
        System.out.print(
                "Введите фамилию контакта : "
        );
        String name = in.next();
        return name;
    }//нужно добавить проверку ввода кол-во 20 символов. Т.к в бд всего 20 допустимо символов
    public String get_a_firstNamePerson(){
        System.out.print(
                "Введите имя контакта : "
        );
        String name = in.next();
        return name;
    }//
    public String get_a_fatherNamePerson(){
        System.out.print(
                "Введите отчество контакта : "
        );
        String name = in.next();
        return name;
    }//
}
