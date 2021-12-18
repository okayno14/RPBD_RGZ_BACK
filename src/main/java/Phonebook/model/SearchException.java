package Phonebook.model;

public class SearchException extends Exception
{
    private int q;

    public SearchException(int q){this.q=q;}

    public int quantity(){return q;}
}
