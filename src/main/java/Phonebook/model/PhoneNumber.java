package Phonebook.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
public class PhoneNumber implements Serializable, Comparable<PhoneNumber>, Cloneable
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int id;

    String number;

    @ManyToOne
    @JoinColumn(name="idtype",referencedColumnName = "id")
    PhoneType phoneType;

    @ManyToMany(mappedBy = "phoneNumberSet")
    Set<Person> personHashSet;

    public PhoneNumber(PhoneType pt, String number)
    {
        this.phoneType = pt;
        this.number = number;
        personHashSet = new HashSet<Person>();
    }

    public PhoneNumber()
    {
        personHashSet = new HashSet<Person>();
    }

    @Override
    protected Object clone()
    {
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.id = this.id;
        phoneNumber.number = new String(this.number);
        phoneNumber.phoneType = (PhoneType) this.phoneType.clone();
        return phoneNumber;
    }



    @Override
    public int compareTo(PhoneNumber phoneNumber)
    {
        if(
                number.compareTo(phoneNumber.number)==0 &&
                        phoneType.compareTo(phoneNumber.phoneType)==0
        )
            return 0;
        else return 2;
    }

    @Override
    public boolean equals(Object o)
    {
        if(!(o instanceof PhoneNumber))
            return false;

        PhoneNumber phoneNumber = (PhoneNumber) o;

        if(this.compareTo(phoneNumber)==0)
            return true;
        else return false;
    }
}
