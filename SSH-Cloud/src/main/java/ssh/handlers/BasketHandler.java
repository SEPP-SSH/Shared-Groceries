package ssh.handlers;

import org.hibernate.query.Query;
import ssh.entities.Basket;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ssh.utilities.JsonUtilities;
import java.util.List;

public class BasketHandler {
    private final SessionFactory sessionFactory;

    public BasketHandler(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(Basket basket) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(basket);
            transaction.commit();
        }
    }

    public void createByAppInfo(int houseId, int storeId){
        String basketJson = "[ { \"store\" : { \"store_id\" : "
                             + Integer.toString(storeId)
                             + " }, \"house\" : { \"house_id\" : "
                             + Integer.toString(houseId)
                             + " } } ]";

        try (Session session = sessionFactory.openSession()) {
            // read json string into object
            List<Basket> basketObjectList =JsonUtilities.readJsonString(basketJson, Basket[].class);

            create(basketObjectList.getFirst());
        }
        catch (Exception e){
            e.printStackTrace();
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

    public List<Basket> getByAppInfo(int houseId, int storeId){
        try (Session session = sessionFactory.openSession()) {
            String queryString = "from Basket where house.houseId = " + houseId + " and store.storeId = " + storeId;
            return session.createQuery(queryString, Basket.class).list();
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