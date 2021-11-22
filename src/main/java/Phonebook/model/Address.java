package Phonebook.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;

@Entity
public class Address implements Serializable, Comparable<Address>
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int id;

    int home;
    int appartement;
    @ManyToOne
    @JoinColumn(name="idstreet", referencedColumnName = "id")
    Street street;

    @OneToMany(mappedBy = "address")
    HashSet<Person> personHashSet;

    public Address(Street street, int home, int app)
    {
        this.street=street;
        this.home=home;
        this.appartement=app;
    }

    public Address(Address address)
    {
        this.id = address.id;
        this.home = address.home;
        this.appartement = address.appartement;
        this.street = new Street(address.street);
    }

    @Override
    public int compareTo(Address address)
    {
        if(
                id == address.id &&
                appartement == address.appartement &&
                street.compareTo(address.street)==0)
        return 0;
        else return 2;
    }

    public int getHome() {return home;}
    public int getAppartement() {return appartement;}
    public Street getStreet() {return new Street(street);}
}
