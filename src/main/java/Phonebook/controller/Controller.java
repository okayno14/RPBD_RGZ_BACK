package Phonebook.controller;

import Phonebook.view.Console;
import Phonebook.view.View;

public class Controller implements Phonebook
{
    View ui;

    public void setView(View ui) {this.ui = ui;}
}
