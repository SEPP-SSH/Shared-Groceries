package ssh.handlers;

import org.hibernate.query.Query;
import ssh.entities.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;

public class CategoryHandler {
    private final SessionFactory sessionFactory;

    public CategoryHandler(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(Category category) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(category);
            transaction.commit();
        }
    }

    public List<Category> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Category", Category.class).list();
        }
    }

    public List<Category> getByStoreId(int storeId) {
        try (Session session = sessionFactory.openSession()) {
            String queryString = "from Category where store.storeId = " + storeId;
            return session.createQuery(queryString, Category.class).list();
        }
    }

}