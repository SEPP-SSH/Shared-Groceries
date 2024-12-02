package ssh.handlers;

import ssh.entities.Category;
import com.example.entities.embeddables.CategoryId;
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

    public Category getById(CategoryId id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Category.class, id);
        }
    }

    public void deleteById(CategoryId id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Category category = session.get(Category.class, id);
            if (category != null) {
                session.delete(category);
            }
            transaction.commit();
        }
    }
}
