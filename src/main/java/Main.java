import Phonebook.controller.Controller;
import Phonebook.facade.ControllerREST;
import Phonebook.view.Console_server;


public class Main
{
    static public void main(String[] args)
    {
        Controller controller = new Controller();
        Console_server console_server = new Console_server(controller);
        controller.setView(console_server);

        //в конструкторе инициализируются нужные для сервера объекты
        //и эндпойнты
        ControllerREST controllerREST = new ControllerREST(args, controller);
    }
}
