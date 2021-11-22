package Phonebook.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;


@Entity
public class PhoneNumber implements Serializable, Comparable<PhoneNumber>
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int id;

    String number;

    @ManyToOne
    @JoinColumn(name="idtype",referencedColumnName = "id")
    PhoneType phoneType;

    @ManyToMany(mappedBy = "phoneNumberSet")
    HashSet<Person> personHashSet;

    public PhoneNumber(PhoneType pt, String number)
    {
        this.phoneType = pt;
        this.number = number;
        personHashSet = new HashSet<Person>();
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
}
