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
            List<Basket> baskets = JsonUtilities.readJsonFile("src/main/resources/basket.json", Basket[].class);
            baskets.forEach(basketHandler::create);

            List<BasketItem> basketItems = JsonUtilities.readJsonFile("src/main/resources/basketItem.json", BasketItem[].class);
            basketItems.forEach(basketItemHandler::create);

            List<Category> categories = JsonUtilities.readJsonFile("src/main/resources/category.json", Category[].class);
            categories.forEach(categoryHandler::create);

            List<House> houses = JsonUtilities.readJsonFile("src/main/resources/house.json", House[].class);
            houses.forEach(houseHandler::create);

            List<Housemate> housemates = JsonUtilities.readJsonFile("src/main/resources/housemate.json", Housemate[].class);
            housemates.forEach(housemateHandler::create);

            List<Item> items = JsonUtilities.readJsonFile("src/main/resources/item.json", Item[].class);
            items.forEach(itemHandler::create);

            List<Store> stores = JsonUtilities.readJsonFile("src/main/resources/store.json", Store[].class);
            stores.forEach(storeHandler::create);


            System.out.println("success: tables populated");

            HibernateUtility.shutdown();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
