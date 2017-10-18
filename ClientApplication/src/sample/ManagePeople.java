package sample;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Iterator;
import java.util.List;

public class ManagePeople {
    private SessionFactory factory;
    public ManagePeople(SessionFactory factory)
    {
        this.factory = factory;
    }
    public Integer addPerson(String firstName, String lastName)
    {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer personID = null;

        try
        {
            tx = session.beginTransaction();
            Person person = new Person(firstName, lastName);
            personID = (Integer) session.save(person);
            tx.commit();
        }catch (HibernateException e)
        {
            if(tx != null) tx.rollback();
            System.out.println("Hibernate exception");
        }finally
        {
            session.close();
        }
        return personID;
    }

    /* Method to  READ all the employees */
    public void listPeople( ){
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List people = session.createQuery("FROM testowa").list();
            for (Iterator iterator = people.iterator(); iterator.hasNext();){
                Person person = (Person) iterator.next();
                System.out.print("First Name: " + person.getFirstName());
                System.out.print("  Last Name: " + person.getLastName());
                }
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }



}
