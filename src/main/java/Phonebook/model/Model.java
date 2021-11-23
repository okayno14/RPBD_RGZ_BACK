package Phonebook.model;

import com.fasterxml.classmate.AnnotationConfiguration;

import org.hibernate.annotations.*;
import org.hibernate.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.*;

import java.util.HashSet;
import java.util.Set;


public class Model
{
    private SessionFactory sessionFactory;
    private Configuration configuration;
    private HashSet<Person> personHashSet;

    void connect()
    {
        configuration = new Configuration();
        configuration.addAnnotatedClass(Person.class);
        configuration.addAnnotatedClass(PhoneNumber.class);
        configuration.addAnnotatedClass(PhoneType.class);
        configuration.addAnnotatedClass(Street.class);
        configuration.addAnnotatedClass(Address.class);

        sessionFactory = configuration.buildSessionFactory();
    }

    public void disconnect(){sessionFactory.close();}

    public Model()
    {
        connect();
        personHashSet = new HashSet<Person>();
    }

    public void insertPerson(Person p)
    {
        personHashSet.add(p);

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();
            session.save(p);
        session.getTransaction().commit();
    }

}
