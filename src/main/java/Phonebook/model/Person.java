package Phonebook.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


@Entity
public class Person implements Serializable, Comparable<Person>
{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int id;

    String lastname;
    String firstname;
    String fathername;
    @ManyToOne
    @JoinColumn(name = "idaddress", referencedColumnName = "id")
    Address address;

    @ManyToMany
    @JoinTable(name = "persone_number",
    joinColumns = {@JoinColumn(name = "idperson")},
    inverseJoinColumns = {@JoinColumn(name = "idphone")})
    Set<PhoneNumber> phoneNumberSet;

    public Person(String lastname, String firstname, String fathername)
    {
        this.lastname=lastname;
        this.firstname=firstname;
        this.fathername = fathername;
        //phoneNumberSet = new HashSet<PhoneNumber>();
    }

    @Override
    public int compareTo(Person person)
    {
        int flag = 2;
        if(
                address.compareTo(person.address)==0 &&
                lastname.compareTo(person.lastname)==0 &&
                firstname.compareTo(person.firstname)==0 &&
                fathername.compareTo(person.fathername)==0

        )
            flag=0;

        boolean flag2=true;
        Iterator<PhoneNumber> i = phoneNumberSet.iterator();
        Iterator<PhoneNumber> j = person.phoneNumberSet.iterator();
        while(i.hasNext())
            while (j.hasNext())
                flag2 = flag2 && (i.next().compareTo(j.next())==0);

        if(flag==0 && flag2)
            return 0;
        else return 2;
    }

    @Override
    public boolean equals(Object o)
    {
        if(!(o instanceof Person))
            return false;

        Person person = (Person) o;

        if (this.compareTo(person)==0)
            return true;
        else return false;
    }

    //public для теста, вместо модификатора по умолчанию
    public void addPhoneNumber(PhoneNumber pn) {phoneNumberSet.add(pn); }

    private Iterator<PhoneNumber> findElem(int pos)
    {
        Iterator<PhoneNumber> i = phoneNumberSet.iterator();
        int j=0;

        while(i.hasNext() && j < pos+1)
            i.next();

        return i;
    }

    public void deletePhone(int pos)
    {
        Iterator<PhoneNumber> i = findElem(pos);
        i.remove();
    }

    public void setPhoneNumber(int pos, PhoneNumber pn)
    {
        deletePhone(pos);
        phoneNumberSet.add(pn);
    }

    public void setAddress(Address address){this.address=address;}

    public void deleteAddress(){address = null;}

    //а тут у методов должен остаться модификатор public
    //возврат копии, дабы не было возможности как-то вне контроллера повлиять на данные структуры
    public HashSet<PhoneNumber> getPhoneNumberSet(){return new HashSet<PhoneNumber>(phoneNumberSet);}
    public Address getAddress(){return new Address(address);}

}
