package ssh.handlers;

import ssh.entities.Category;
import ssh.entities.Housemate;
import ssh.entities.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;

public class ItemHandler {
    private final SessionFactory sessionFactory;

    public ItemHandler(SessionFactory sessionFactory) {
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

    public List<Item> getByStoreId(int storeId){
        try (Session session = sessionFactory.openSession()) {
            String queryString = "from Item where store.storeId = " + storeId;
            return session.createQuery(queryString, Item.class).list();
        }
    }

}