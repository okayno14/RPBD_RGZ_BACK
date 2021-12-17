package Phonebook.model;







import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Street implements Serializable, Comparable<Street>, Cloneable
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int id;

    String streetname;
    @OneToMany(mappedBy = "street")
    Set<Address> addressSet;

    public Street(String streetname)
    {
        this.streetname = streetname;
        addressSet = new HashSet<Address>();
    }

    public Street()
    {
        addressSet = new HashSet<Address>();
    }


    //поля-сущности - глубокая копия
    @Override
    protected Object clone(){

        Street street = new Street();
        street.id = this.id;
        street.streetname  = new String(this.streetname);
        return street;

    }

    @Override
    public int compareTo(Street street)
    {
        return streetname.compareTo(street.streetname);
    }

    @Override
    public boolean equals(Object o)
    {
        if(!(o instanceof Street))
            return false;

        Street street = (Street) o;

        if(this.compareTo(street)==0)
            return true;
        else return false;
    }

    public String getStreetname() {return new String(streetname);}
}
