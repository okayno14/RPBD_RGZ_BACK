import Phonebook.controller.Controller;
import Phonebook.model.*;
import Phonebook.view.Console;



public class Main
{
    static public void main(String[] args)
    {
        Controller controller = new Controller();
        Console cli = new Console(controller);
        controller.setView(cli);
        cli.run();

        cli.userInterface.addContact("Vasily", "Vasilyevich","Topolev");


    }
}
