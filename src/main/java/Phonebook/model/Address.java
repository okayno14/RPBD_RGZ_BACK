package Phonebook.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Address implements Serializable, Comparable<Address>, Cloneable
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
    Set<Person> personHashSet;

    public Address(Street street, int home, int app)
    {
        this.street=street;
        street.addressSet.add(this);
        this.home=home;
        this.appartement=app;

        personHashSet = new HashSet<Person>();


    }

    public Address()
    {
        personHashSet = new HashSet<Person>();
    }

    //поля - копируем ссылку
    //поля-сущности - глубокая копия
    @Override
    public Object clone()
    {
        Address address = new Address();
        address.id = this.id;
        address.home = this.home;
        address.appartement = this.appartement;
        address.street = (Street) this.street.clone();
        return address;
    }

    @Override
    public int compareTo(Address address)
    {
        if(address == null && this == null)
            return 0;
        if(
                id == address.id &&
                appartement == address.appartement &&
                street.compareTo(address.street)==0)
        return 0;
        else return 2;
    }

    public int getHome() {return home;}
    public int getAppartement() {return appartement;}
    public Street getStreet() {return (Street) street.clone();}
    void deleteStreet()
    {
        street.addressSet.remove(this);
        street = null;
    }
    @Override
    public boolean equals(Object o)
    {
       if(!(o instanceof Address))
           return false;

       Address address = (Address) o;

        if(this.compareTo(address)==0)
           return true;
        else return false;
    }
}
