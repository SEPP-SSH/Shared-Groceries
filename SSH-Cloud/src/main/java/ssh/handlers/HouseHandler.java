package ssh.handlers;

import ssh.entities.House;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;

public class HouseHandler {
    private final SessionFactory sessionFactory;

    public HouseHandler(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(House house) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(house);
            transaction.commit();
        }
    }

    public House getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(House.class, id);
        }
    }

}