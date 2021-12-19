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
    public Person findPerson(Person p, boolean isEmpty) throws SearchException
    {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
            String hql;
            if(isEmpty)
                    hql = "from Person as p " +
                            "where p.lastname = :lastname and " +
                            "p.firstname = :firstname and " +
                            "p.fathername = :fathername and " +
                            "p.address is null and " +
                            "p.phoneNumberSet.size = 0";
                else
                    hql = " from Person as p " +
                            "where p.lastname = :lastname and " +
                            "p.firstname = :firstname and " +
                            "p.fathername = :fathername and " +
                            "( p.address is not null or " +
                            "p.phoneNumberSet.size > 0 )";
                Query q = session.createQuery(hql);
                q.setParameter("lastname",p.lastname);
                q.setParameter("firstname", p.firstname);
                q.setParameter("fathername", p.fathername);
                List<Person> list = q.getResultList();

                if(list.size()>1 || list.size() == 0)
                {
                    transaction.commit();
                    throw new SearchException(list.size());
                }
                else
                {
                    Person finded = list.iterator().next();
                    //finded.address.street.addressSet.size();
                    personHashSet.add(finded);

                    transaction.commit();
                    return finded;
                }
    }

    public Person findPerson(Person p, PhoneNumber pn) throws SearchException
    {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction =session.beginTransaction();
            String hql = "from Person as p " +
                    "inner join fetch p.phoneNumberSet as num " +
                    "where " +
                    "p.lastname = :lastname and " +
                    "p.firstname = :firstname and "+
                    "p.fathername = :fathername and "+
                    "num.number = :number and "+
                    "num.phoneType.id = :type";
            Query q = session.createQuery(hql);
            q.setParameter("lastname",p.lastname);
            q.setParameter("firstname", p.firstname);
            q.setParameter("fathername", p.fathername);
            q.setParameter("number", pn.number);
            q.setParameter("type", pn.phoneType.id);
            List<Person> list = q.getResultList();


            if(list.size()>1 || list.size() == 0)
            {
                transaction.commit();
                throw new SearchException(list.size());
            }
            else
            {
                Person finded = list.iterator().next();
                personHashSet.add(finded);
                transaction.commit();
                return finded;
            }
    }

    public Person findPerson(Person p, PhoneNumber pn, Address add) throws SearchException
    {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
            String hql = "from Person as p " +
                    "inner join fetch p.phoneNumberSet as num " +
                    "where " +
                    "p.lastname = :lastname and " +
                    "p.firstname = :firstname and "+
                    "p.fathername = :fathername and "+
                    "num.number = :number and "+
                    "num.phoneType.id = :type and" +
                    "num.address = :address";
            Query q = session.createQuery(hql);
            q.setParameter("lastname",p.lastname);
            q.setParameter("firstname", p.firstname);
            q.setParameter("fathername", p.fathername);
            q.setParameter("number", pn.number);
            q.setParameter("type", pn.phoneType.id);
            q.setParameter(":address", add);
            List<Person> list = q.getResultList();

            if(list.size()>1 || list.size() == 0)
            {
                transaction.commit();
                throw new SearchException(list.size());
            }
            else
            {
                Person finded = list.iterator().next();
                personHashSet.add(finded);
                transaction.commit();
                return finded;
            }
    }

    public List<Person> findFIOAll(String lastName, String firstName, String fatherName)
    {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
            String hql = "from Person as p where " +
                    "p.lastname = :l and " +
                    "p.firstname = :first and " +
                    "p.fathername = :father";
            Query q = session.createQuery(hql);
            q.setParameter("l",lastName);
            q.setParameter("first", firstName);
            q.setParameter("father", fatherName);
            List<Person> result = q.getResultList();
        transaction.commit();
        return result;
    }

    public List<Person> findContactBy4NumberPhone(int a, int b, int c, int d)
    {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
            StringBuffer hql = new StringBuffer("from Person as p " +
                    "inner join fetch p.phoneNumberSet as num " +
                    " where num.number like '%");
            Integer buf = a;
            hql.append(buf.toString());
            buf = b;
            hql.append(buf.toString());
            hql.append("%");
            buf = c;
            hql.append(buf.toString());
            buf = d;
            hql.append(buf.toString());
            hql.append("'");

            Query q = session.createQuery(hql.toString());
            List<Person> res = q.getResultList();
        transaction.commit();
        return res;
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
                Address buf = p.address;
                p.deleteAddress();
                //p.address.personHashSet.remove(p);
                p.address = update(buf,add);
            }
            else
                p.setAddress(insert(add));

            session.update(p);
        transaction.commit();
    }

    public void deleteAddress(Person p) throws Exception
    {
        if(p.address!=null)
        {
            Session session = sessionFactory.getCurrentSession();
            Transaction transaction = session.beginTransaction();
                Address buf = p.address;
                p.deleteAddress();
                session.update(p);
                if(countReferences(buf)==0)
                    delete(buf);
            transaction.commit();
        }
        else throw  new Exception("У контакта нет адреса");
    }
    /*Методы обновления Person, связанные с телефонами*/
    public void addPhone(Person p, PhoneNumber pn)
    {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
            p.addPhoneNumber(insert(pn));
            session.update(p);
        transaction.commit();
    }

    public void changePhone(Person p, int pos, PhoneNumber new_)
    {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
            PhoneNumber buf = p.findElem(pos).next();

            p.setPhoneNumber(pos,new_);
            session.update(p);

            update(buf,new_);


            if(countReferences(buf)==0)
                delete(buf);


        transaction.commit();
    }

    public void deletePhone(Person p, int pos)
    {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
            PhoneNumber buf = p.findElem(pos).next();
            p.deletePhone(pos);
            session.update(p);
            if(countReferences(buf)==0)
                delete(buf);
        transaction.commit();
    }

    //используется в контексте обновления контакта
    private void delete(Address add)
    {
        Session session = sessionFactory.getCurrentSession();
        //add.street.addressSet.remove(add);
        Street buf = add.street;
        add.deleteStreet();
        if(countReferences(buf)==0)
            delete(buf);
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

        String hql = "select pn.personHashSet.size " +
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

    public void changeContact(Person p, String lastname, String firstname, String fathername)
    {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
            p.lastname = lastname;
            p.firstname = firstname;
            p.fathername = fathername;
            session.update(p);
        transaction.commit();
    }

    public void deletePerson(Person p) throws HibernateException
    {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
            Address add = p.address;
            p.deleteAddress();

            Iterator<PhoneNumber> i = p.phoneNumberSet.iterator();
            PhoneNumber elem = null;
            while (i.hasNext())
            {
                elem = i.next();
                elem.personHashSet.remove(p);
            }
            p.phoneNumberSet.clear();

            session.update(p);

            session.delete(p);
        transaction.commit();
    }
}
