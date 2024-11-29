package com.example.repositories;

import com.example.entities.Housemate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class HousemateRepository {
    private final SessionFactory sessionFactory;

    public HousemateRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(Housemate housemate) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(housemate);
            transaction.commit();
        }
    }

    public List<Housemate> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Housemate", Housemate.class).list();
        }
    }

    public Housemate getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Housemate.class, id);
        }
    }

    public void deleteById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Housemate housemate = session.get(Housemate.class, id);
            if (housemate != null) {
                session.delete(housemate);
            }
            transaction.commit();
        }
    }
}
