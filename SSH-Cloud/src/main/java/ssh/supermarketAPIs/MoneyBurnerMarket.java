package ssh.supermarketAPIs;

import ssh.entities.*;
import ssh.handlers.*;
import ssh.utilities.HibernateUtility;
import ssh.utilities.JsonUtilities;
import org.hibernate.SessionFactory;
import java.util.List;

public class MoneyBurnerMarket {
    public static void populateSshDatabase() {
        try {
            // get SessionFactory object
            SessionFactory sessionFactory = HibernateUtility.getSessionFactory();

            // initialise handler objects
            BasketHandler basketHandler = new BasketHandler(sessionFactory);
            BasketItemHandler basketItemHandler = new BasketItemHandler(sessionFactory);
            CategoryHandler categoryHandler = new CategoryHandler(sessionFactory);
            HouseHandler houseHandler = new HouseHandler(sessionFactory);
            HousemateHandler housemateHandler = new HousemateHandler(sessionFactory);
            ItemHandler itemHandler = new ItemHandler(sessionFactory);
            StoreHandler storeHandler = new StoreHandler(sessionFactory);

            // read objects from JSON file, and populate into database
            List<Basket> baskets = JsonUtilities.readJsonString(MoneyBurnerMarketData.baskset, Basket[].class);
            baskets.forEach(basketHandler::create);

            List<BasketItem> basketItems = JsonUtilities.readJsonString(MoneyBurnerMarketData.basketItem, BasketItem[].class);
            basketItems.forEach(basketItemHandler::create);

            List<Category> categories = JsonUtilities.readJsonString(MoneyBurnerMarketData.category, Category[].class);
            categories.forEach(categoryHandler::create);

            List<House> houses = JsonUtilities.readJsonString(MoneyBurnerMarketData.house, House[].class);
            houses.forEach(houseHandler::create);

            List<Housemate> housemates = JsonUtilities.readJsonString(MoneyBurnerMarketData.housemate, Housemate[].class);
            housemates.forEach(housemateHandler::create);

            List<Item> items = JsonUtilities.readJsonString(MoneyBurnerMarketData.item, Item[].class);
            items.forEach(itemHandler::create);

            List<Store> stores = JsonUtilities.readJsonString(MoneyBurnerMarketData.store, Store[].class);
            stores.forEach(storeHandler::create);


            System.out.println("success: tables populated");

            HibernateUtility.shutdown();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
