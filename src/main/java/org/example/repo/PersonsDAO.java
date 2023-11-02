package org.example.repo;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import org.example.entity.PersonsEntity;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class PersonsDAO {
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public static void createPerson() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        PersonsEntity personsEntity = new PersonsEntity();
        personsEntity.setName("Lisa");
        personsEntity.setAge(20);
        session.persist(personsEntity);
        session.getTransaction().commit();
    }

    public static PersonsEntity getById(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        PersonsEntity personsEntity = session.get(PersonsEntity.class, id);
        return personsEntity;
    }

    public static void updatePerson(int id, String name, int age) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        PersonsEntity person = session.load(PersonsEntity.class, id);
        //person.setName(name);
        person.setAge(age);
        session.update(person);

        session.getTransaction().commit();
    }

    public static PersonsEntity getUserById(Integer personId) {
        try (Session session = sessionFactory.openSession()) {
            Query<PersonsEntity> query =
                    session.createQuery("from PersonsEntity where id=:id", PersonsEntity.class);
            query.setParameter("id", personId);
            return query.uniqueResult();

        }
    }

    public static void main(String[] args) {
        createPerson();
    }

}
