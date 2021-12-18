package Phonebook.model;
import org.hibernate.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.*;
import org.hibernate.query.*;
import org.hibernate.query.Query;


import java.util.HashSet;
import java.util.Iterator;
import java.util.List;


public class Model
{
    private SessionFactory sessionFactory;
    private Configuration configuration;
    private HashSet<Person> personHashSet;

    void connect() throws Throwable
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

    public Model() throws Throwable
    {
        try
        {
            connect();
        }
        catch (HibernateException e)
        {
            throw e;
        }
        finally
        {
            personHashSet = new HashSet<Person>();
        }
    }

    //Метод скорее всего нужно будет удалить
    private void bdErr()
    {
        if(sessionFactory.getCurrentSession().getTransaction().isActive())
            sessionFactory.getCurrentSession().getTransaction().rollback();
        disconnect();

        try {connect();}
        catch(Throwable e){}
    }

    public void insertPerson(Person p) throws HibernateException
    {
        try
        {
            Session session = sessionFactory.getCurrentSession();

            session.beginTransaction();
            session.save(p);
            session.getTransaction().commit();
        }
        catch(HibernateException he)
        {
            bdErr();
            throw he;
        }
        finally
        {
            personHashSet.add(p);
        }
    }

    //Поиски
    private Person checkCollection(Person p)
    {
        Iterator<Person> i = personHashSet.iterator();
        Person res;
        boolean flag  = false;
        while (i.hasNext())
        {
            res = i.next();
            if(res.equals(p))
            {
                return res;
            }
        }
        return null;
    }

    private Address insert(Address add)
    {
        Session session = sessionFactory.getCurrentSession();

        add.street = insert(add.street);

        Address finded = find(add);
        if(finded==null)
        {
            session.save(add);
            finded = add;
        }

        return finded;
    }

    private Address find(Address add)
    {
        Session session =  sessionFactory.getCurrentSession();
        //поиск по строке улицы избыточен, достаточно вбить id
        String hql = "from Address as addr " +
                "where " +
                "addr.street.streetname = :street and " +
                "addr.home = :home and " +
                "addr.appartement = :appartement";
        Query q = session.createQuery(hql);
        q.setParameter("street", add.street.streetname);
        q.setParameter("home",add.home);
        q.setParameter("appartement",add.appartement);

        Address res = (Address) q.uniqueResult();
        return res;
    }

    private int countReferences(Address add)
    {
        Session session = sessionFactory.getCurrentSession();

        String hql = "select addr.personHashSet.size " +
                "from Address as addr " +
                "where addr.id = :id";

        Query q = session.createQuery(hql);
        q.setParameter("id",add.id);

        return (int) q.uniqueResult();
    }

    //модификация PersonHashSet делается снаружи
    private Street insert(Street street)
    {
        Session session = sessionFactory.getCurrentSession();
        Street finded = find(street);
        if(finded == null)
        {
            session.save(street);
            finded = street;
        }
        return finded;
    }

    private Street find(Street street)
    {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from Street as s where s.streetname = :name";
        Query q = session.createQuery(hql);
        q.setParameter("name",street.streetname);
        return (Street) q.uniqueResult();
    }

    private int countReferences(Street street)
    {
        Session session = sessionFactory.getCurrentSession();
        String hql = "select street.addressSet.size " +
                "from Street as street " +
                "where street.id = :id";
        Query q = session.createQuery(hql);
        q.setParameter("id", street.id);

        return (int) q.uniqueResult();
    }

    private void update(Street old_, Street new_)
    {
        Session session = sessionFactory.getCurrentSession();
        session.save(new_);
        if(countReferences(old_)==1)
            delete(old_);
    }

    private void delete(Street street)
    {
        Session session = sessionFactory.getCurrentSession();
        session.delete(street);
    }

    //модификация PersonHashSet делается снаружи
    //вспомогательный для обновления контакта метод
    Address update(Address old_, Address new_)
    {
        Session session = sessionFactory.getCurrentSession();
        new_=insert(new_);

        if(countReferences(old_)==0)
            delete(old_);

        return new_;
    }

    /*Методы обновления Person, связанные с адресами*/
    public void changeAddress(Person p, Address add)
    {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
            if(p.address!=null)
            {
                p.address.personHashSet.remove(p);
                p.address = update(p.address,add);
            }
            else
            {
                p.address = insert(add);
                p.address.personHashSet.add(p);
            }
            session.update(p);
        transaction.commit();
    }

    public void deleteAddress(Person p)
    {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
            if(p.address!=null)
            {
                p.address.personHashSet.remove(p);
                Address buf = p.address;
                p.address=null;
                session.update(p);
                if(countReferences(buf)==0)
                    delete(buf);
            }
        transaction.commit();
    }
    /*Методы обновления Person, связанные с телефонами*/
    void addPhone(Person p, PhoneNumber pn)
    {

    }

    //используется в контексте обновления контакта
    private void delete(Address add)
    {
        Session session = sessionFactory.getCurrentSession();
        if(countReferences(add.street)==1)
            delete(add.street);
        session.delete(add);
    }

    private PhoneNumber insert(PhoneNumber pn)
    {
        Session session = sessionFactory.getCurrentSession();

        pn.phoneType=getPhoneType(pn.phoneType);
        PhoneNumber finded = find(pn);
        if(finded == null)
        {
            session.save(pn);
            finded=pn;
        }
        return  finded;
    }

    private PhoneNumber find(PhoneNumber pn)
    {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from PhoneNumber as pn " +
                "where " +
                "pn.number = :number and " +
                "pn.phoneType.id = :pType";
        Query q = session.createQuery(hql);
        q.setParameter("number", pn.number);
        q.setParameter("pType", pn.phoneType.id);
        return (PhoneNumber) q.uniqueResult();
    }

    private int countReferences(PhoneNumber pn)
    {
        Session session = sessionFactory.getCurrentSession();

        String hql = "select pn.personHashSet " +
                "from PhoneNumber as pn " +
                "where pn.id = :id";

        Query q = session.createQuery(hql);
        q.setParameter("id", pn.id);
        return (int) q.uniqueResult();
    }

    private PhoneType getPhoneType(PhoneType phoneType)
    {
        Session session = sessionFactory.getCurrentSession();
        return (PhoneType) session.merge(phoneType);
    }

    private void update(PhoneNumber old_, PhoneNumber new_)
    {
        Session session = sessionFactory.getCurrentSession();
        session.save(new_);
        if(countReferences(old_)==1)
            delete(old_);
    }

    private void delete(PhoneNumber pn)
    {
        Session session = sessionFactory.getCurrentSession();
        session.delete(pn);
    }

    public void deletePerson(Person p) throws HibernateException
    {
//        Person toDel = checkCollection(p);
//
//
//        try
//        {
//            Session session = sessionFactory.getCurrentSession();
//
//            session.beginTransaction();
//                toDel.setAddress(new Address(new Street("ki"),1,2));
//                countReferences(toDel.address);
//                session.remove(toDel);
//            session.getTransaction().commit();
//        }
//        catch(Throwable a)
//        {
//            System.err.println(a.toString());
//        }
////        catch(HibernateException he)
////        {
////            bdErr();
////            throw he;
////        }
//        finally
//        {
//            personHashSet.remove(toDel);
//        }
    }
}
