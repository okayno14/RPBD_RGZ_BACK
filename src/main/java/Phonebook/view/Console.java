package Phonebook.view;

import Phonebook.controller.Controller;
import Phonebook.controller.Phonebook;

public class Console extends View
{
    Phonebook userInterface;

    public Console(Controller con)
    {
        userInterface = con;
    }

    public void run()
    {
        System.out.println("Hello World!");
    }


}
