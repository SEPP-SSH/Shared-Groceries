package ssh.handlers;

import ssh.entities.BasketItem;
import com.example.entities.embeddables.BasketItemId;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class BasketItemHandler {
    private final SessionFactory sessionFactory;

    public BasketItemHandler(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(BasketItem basketItem) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(basketItem);
            transaction.commit();
        }
    }

    public List<BasketItem> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from BasketItem", BasketItem.class).list();
        }
    }

    public BasketItem getById(BasketItemId id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(BasketItem.class, id);
        }
    }

    public void deleteById(BasketItemId id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            BasketItem basketItem = session.get(BasketItem.class, id);
            if (basketItem != null) {
                session.delete(basketItem);
            }
            transaction.commit();
        }
    }
}
