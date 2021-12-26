package Phonebook.view;

import Phonebook.controller.Controller;
import Phonebook.model.Address;
import Phonebook.model.Person;
import Phonebook.model.PhoneNumber;

import java.util.Set;

public class Console_server extends Console
{
    //legacy, надо дропнуть импорт контроллера
    public Console_server(Controller con)
    {
        super(con);
    }

    @Override
    public void success() {
        super.success();
    }

    @Override
    public void fail() {
        super.fail();
    }

    @Override
    public void noRes() {
        return;
    }

    @Override
    public void clones() {
        super.clones();
    }

    @Override
    public void drawPerson(Person person) {
        return;
    }

    @Override
    public void drawAddress(Address address) {
        return;
    }

    @Override
    public void drawPhoneNumber(PhoneNumber phoneNumber) {
        return;
    }

    @Override
    public void drawPhoneNumbers(Set<PhoneNumber> args) {
        super.drawPhoneNumbers(args);
    }

    @Override
    public boolean checkFormat(String number) {
        return super.checkFormat(number);
    }

    @Override
    public boolean checkLine(String s) {
        return super.checkLine(s);
    }

    @Override
    protected void clr() {
        super.clr();
    }

    @Override
    protected void messageEnter() {
        super.messageEnter();
    }

    @Override
    public String get_a_Number() {
        return "";
    }

    @Override
    public String get_a_addressName() {
        return "";
    }

    @Override
    public int get_a_type() {
        return -1;
    }

    @Override
    public int get_a_numberApartment() {
        return -1;
    }

    @Override
    public int get_a_numberHome() {
        return -1;
    }

    @Override
    public String get_a_lastNamePerson() {
        return super.get_a_lastNamePerson();
    }

    @Override
    public String get_a_firstNamePerson() {
        return super.get_a_firstNamePerson();
    }

    @Override
    public String get_a_fatherNamePerson() {
        return super.get_a_fatherNamePerson();
    }
}
