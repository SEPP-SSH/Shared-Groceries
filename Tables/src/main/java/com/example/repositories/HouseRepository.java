package com.example.repositories;

import com.example.entities.House;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class HouseRepository {
    private final SessionFactory sessionFactory;

    public HouseRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(House house) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(house);
            transaction.commit();
        }
    }

    public List<House> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from House", House.class).list();
        }
    }

    public House getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(House.class, id);
        }
    }

    public void deleteById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            House house = session.get(House.class, id);
            if (house != null) {
                session.delete(house);
            }
            transaction.commit();
        }
    }
}
