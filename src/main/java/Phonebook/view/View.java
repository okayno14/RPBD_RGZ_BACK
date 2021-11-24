package Phonebook.view;

import Phonebook.model.Address;
import Phonebook.model.Person;
import Phonebook.model.PhoneNumber;

import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

//Абстрактный класс View для приложения
//Можно будет создавать разные пользовательские интерфейсы и прикручивать их к контроллеру
public abstract class View
{
    protected Scanner in = new Scanner(System.in);
    private Scanner cur_in = new Scanner(System.in);
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
        System.out.println(
                person.getLastname() + " "
                + person.getFirstname() + " "
                + person.getFathername()
        );
    }
    public void drawAddress(Address address){
        if (address != null){
            System.out.println(
                    address.getStreet().getStreetname() + " "
                    + address.getHome() + " "
                    + address.getAppartement()
            );
        }
        else System.out.println("Ошибка чтения");
    }
    public void drawPhoneNumber(PhoneNumber phoneNumber){//тут нужны проверки!
        if (phoneNumber != null){
            System.out.print(phoneNumber.getNumber());
            System.out.println("  "+phoneNumber.getPhoneType().getTypename());
        }
    }
    public void drawPhoneNumbers(Set<PhoneNumber> args){
        if (args.size() > 0){
            Iterator<PhoneNumber> iterator = args.iterator();
            int i = 0;
            while (iterator.hasNext()){
                System.out.print("["+ i + "] ");
                i++;
                drawPhoneNumber(args.iterator().next());
            }
        }
        else
            System.out.println("Ошибка. Нет номеров.");
    }//нужно протестить !!!
    public boolean checkFormat(String number){
        if (number.charAt(1) == '(' &&
            number.charAt(5) == ')' &&
            number.charAt(9) == '-' &&
            number.charAt(12) == '-' &&
            number.length() <= 15)
            return true;
        return false;
    }
    public boolean checkLine(String s){
        if (s.length() <= 20)
            return true;
        return false;
    }
    protected void clr(){
        try
        {
            if (os.contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else if (os.contains("Linux"))
                new ProcessBuilder("/usr/bin/clear").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        }
        catch (Exception e){e.printStackTrace();}
    }
    protected void messageEnter(){
        System.out.println("Для продолжения нажмите клавишу Enter");
        cur_in.nextLine();
        clr();
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
                "Введите тип номера телефона\n"
                +"[1] - моюильный \n"
                +"[2] - рабочий \n"
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
        while (true){
            System.out.print(
                    "Введите фамилию контакта : "
            );
            String name = in.next();
            if (checkLine(name))
                return name;
            else if (name.isEmpty())
                System.out.println("Пустота не считается");
            else
                System.out.println("Вы ввели много символов");
        }
    }
    public String get_a_firstNamePerson(){
        while (true) {
            System.out.print(
                    "Введите имя контакта : "
            );
            String name = in.next();
            if (checkLine(name))
                return name;
            else if (name.isEmpty())
                System.out.println("Пустота не считается");
            else
                System.out.println("У вас слишком длиное имя Желаетя поменять паспорт?)))(сарказм)");
        }
    }
    public String get_a_fatherNamePerson(){
        while (true) {
            System.out.print(
                    "Введите отчество контакта : "
            );
            String name = in.next();
            if (checkLine(name))
                return name;
            else if (name.isEmpty())
                System.out.println("Пустота не считается");
            else
                System.out.println("Вы ввели много символов");
        }
    }
}
