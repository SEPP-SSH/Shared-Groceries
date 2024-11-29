package com.example.repositories;

import com.example.entities.Store;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class StoreRepository {
    private final SessionFactory sessionFactory;

    public StoreRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(Store store) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(store);
            transaction.commit();
        }
    }

    public List<Store> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Store", Store.class).list();
        }
    }

    public Store getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Store.class, id);
        }
    }

    public void deleteById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Store store = session.get(Store.class, id);
            if (store != null) {
                session.delete(store);
            }
            transaction.commit();
        }
    }
}
