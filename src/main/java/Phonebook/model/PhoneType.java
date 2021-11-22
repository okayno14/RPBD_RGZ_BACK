package Phonebook.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "type_of_phone")
public class PhoneType implements Serializable, Comparable<PhoneType>
{
    String typename;

    @Id
    int id;

    public PhoneType(int type)
    {
        switch(type)
        {
            case 1:
                {
                    id = 1;
                    typename="мобильный";
                    break;
                }
            case 2:
                {
                    id = 2;
                    typename="рабочий";
                    break;
                }
            case 3:
                {
                    id = 3;
                    typename="домашний";
                    break;
                }
        }
    }

    @Override
    public int compareTo(PhoneType phoneType)
    {
        return typename.compareTo(phoneType.typename);
    }
}
