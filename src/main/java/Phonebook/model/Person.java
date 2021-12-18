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
        phoneNumberSet = new HashSet<PhoneNumber>();
    }

    public Person()
    {
        phoneNumberSet = new HashSet<PhoneNumber>();
    }

    @Override
    public int compareTo(Person person)
    {
        int flagFIO = 2;
        if(
                lastname.compareTo(person.lastname)==0 &&
                firstname.compareTo(person.firstname)==0 &&
                fathername.compareTo(person.fathername)==0
        )
            flagFIO=0;

        //Сравнение с учётом адресов
        int flagAddress = 2;
        if(this.address==null && person.address==null)
            flagAddress=0;
        else if(this.address.compareTo(person.address)==0)
            flagAddress=0;

        //Сравнение по телефонам
        boolean flagPhones=false;

        if(phoneNumberSet==null && person.phoneNumberSet==null)
            flagPhones=true;
        else if (phoneNumberSet != null && person.phoneNumberSet != null)
        {
            Iterator<PhoneNumber> i = phoneNumberSet.iterator();
            Iterator<PhoneNumber> j = person.phoneNumberSet.iterator();
            while(i.hasNext())
                while (j.hasNext())
                    flagPhones = flagPhones && (i.next().compareTo(j.next())==0);
        }

        if(flagFIO==0 && flagAddress==0 && flagPhones)
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





    Iterator<PhoneNumber> findElem(int pos)
    {
        Iterator<PhoneNumber> i = phoneNumberSet.iterator();
        int j=0;

        while(i.hasNext() && j < pos)
            i.next();

        return i;
    }

    void addPhoneNumber(PhoneNumber pn)
    {
        phoneNumberSet.add(pn);
        pn.personHashSet.add(this);
    }

    public void setPhoneNumber(int pos, PhoneNumber pn)
    {
        deletePhone(pos);
        addPhoneNumber(pn);
    }

    public void deletePhone(int pos)
    {
        Iterator<PhoneNumber> i = findElem(pos);
        PhoneNumber toDel = i.next();
        toDel.personHashSet.remove(this);
        i.remove();
    }

    public void setAddress(Address address)
    {
        this.address=address;
        address.personHashSet.add(this);
    }

    public void deleteAddress()
    {
        address.personHashSet.remove(this);
        address = null;
    }

    //а тут у методов должен остаться модификатор public
    //возврат копии, дабы не было возможности как-то вне контроллера повлиять на данные структуры
    public HashSet<PhoneNumber> getPhoneNumberSet(){return new HashSet<PhoneNumber>(phoneNumberSet);}
    public Address getAddress(){return (Address) address.clone();}

    public String getLastname() {
        return lastname;
    }

    public String getFirstname(){return firstname;}

    public String getFathername(){return fathername;}
}
