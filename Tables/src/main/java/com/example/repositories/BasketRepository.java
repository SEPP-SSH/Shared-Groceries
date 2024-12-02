package com.example.repositories;

import com.example.entities.Basket;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class BasketRepository {
    private final SessionFactory sessionFactory;

    public BasketRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(Basket basket) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(basket);
            transaction.commit();
        }
    }

    public List<Basket> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Basket", Basket.class).list();
        }
    }

    public Basket getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Basket.class, id);
        }
    }

    public void deleteById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Basket basket = session.get(Basket.class, id);
            if (basket != null) {
                session.delete(basket);
            }
            transaction.commit();
        }
    }
}
