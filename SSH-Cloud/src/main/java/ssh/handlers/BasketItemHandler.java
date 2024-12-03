package ssh.handlers;

import org.hibernate.query.Query;
import ssh.entities.Basket;
import ssh.entities.BasketItem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ssh.utilities.JsonUtilities;
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

    public void createByInfo(int basketId, int storeId, int itemId, int housmateId, int itemQuantity){
        String basketItemJson = String.format("[{\"basket\" : {\"basket_id\" : ", basketId, " }, \"store\" : { \"store_id\" : ",
                                               storeId, " }, \"item\" : { \"item_id\" : ",
                                               itemId, " }, \"housemate\" : { \"housemate_id\" : ",
                                               housmateId, " }, \"item_quantity\" : ",
                                               itemQuantity, " }]");

        try (Session session = sessionFactory.openSession()) {
            // read json string into object
            List<BasketItem> basketItemObjectList = JsonUtilities.readJsonString(basketItemJson, BasketItem[].class);

            create(basketItemObjectList.getFirst());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<BasketItem> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from BasketItem", BasketItem.class).list();
        }
    }

    public BasketItem getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(BasketItem.class, id);
        }
    }

    public List<BasketItem> getByBasketId(int basketId){
        try (Session session = sessionFactory.openSession()) {
            String queryString = "from BasketItem where basket.basketId = " + basketId;
            return session.createQuery(queryString, BasketItem.class).list();
        }
    }

    public void increaseItemQuantity(int basketItemId, int quantityToAdd){
        try (Session session = sessionFactory.openSession()) {
            BasketItem itemToUpdate = (BasketItem) session.get(BasketItem.class, basketItemId);
            Transaction transaction = session.beginTransaction();
            itemToUpdate.setItemQuantity(itemToUpdate.getItemQuantity() + quantityToAdd);
            transaction.commit();
        }
    }

    public void decreaseItemQuantity(int basketItemId, int quantityToRemove){
        // WARNING: does NOT do checks on whether the subtraction is valid - such checks must be done by the caller prior to invocation of this method
        try (Session session = sessionFactory.openSession()) {
            BasketItem itemToUpdate = (BasketItem) session.get(BasketItem.class, basketItemId);
            Transaction transaction = session.beginTransaction();
            itemToUpdate.setItemQuantity(itemToUpdate.getItemQuantity() - quantityToRemove);
            transaction.commit();
        }
    }

    public void deleteById(int id) {
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