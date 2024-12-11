package ssh.handlers;

import ssh.entities.Store;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;

public class StoreHandler {
    private final SessionFactory sessionFactory;

    public StoreHandler(SessionFactory sessionFactory) {
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
}