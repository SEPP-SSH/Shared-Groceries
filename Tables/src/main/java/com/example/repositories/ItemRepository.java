package com.example.repositories;

import com.example.entities.Item;
import com.example.entities.embeddables.ItemId;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class ItemRepository {
    private final SessionFactory sessionFactory;

    public ItemRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(Item item) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(item);
            transaction.commit();
        }
    }

    public List<Item> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Item", Item.class).list();
        }
    }

    public Item getById(ItemId id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Item.class, id);
        }
    }

    public void deleteById(ItemId id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Item item = session.get(Item.class, id);
            if (item != null) {
                session.delete(item);
            }
            transaction.commit();
        }
    }
}
