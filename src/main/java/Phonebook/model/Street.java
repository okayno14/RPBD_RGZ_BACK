package Phonebook.model;





import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;


@Entity
public class Street implements Serializable, Comparable<Street>
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int id;

    String streetname;
    @OneToMany(mappedBy = "street")
    HashSet<Address> addressSet;

    public Street(String streetname)
    {
        this.streetname = streetname;
        addressSet = new HashSet<Address>();
    }

    public Street(final Street orig)
    {
        streetname  = new String(orig.streetname);
    }

    @Override
    public int compareTo(Street street)
    {
        return streetname.compareTo(street.streetname);
    }

    public String getStreetname() {return new String(streetname);}
}
