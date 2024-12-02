package ssh;

import ssh.entities.*;
import ssh.handlers.*;
import ssh.utils.HibernateUtil;
import ssh.utils.JsonUtils;
import org.hibernate.SessionFactory;
import java.util.List;

public class PopulateTables {
    public static void populate() {
        try {
            // get SessionFactory object
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

            // initialise handler objects
            BasketHandler basketHandler = new BasketHandler(sessionFactory);
            BasketItemHandler basketItemHandler = new BasketItemHandler(sessionFactory);
            CategoryHandler categoryHandler = new CategoryHandler(sessionFactory);
            HouseHandler houseHandler = new HouseHandler(sessionFactory);
            HousemateHandler housemateHandler = new HousemateHandler(sessionFactory);
            ItemHandler itemHandler = new ItemHandler(sessionFactory);
            StoreHandler storeHandler = new StoreHandler(sessionFactory);

            // read objects from JSON file, and populate into database
            List<Basket> baskets = JsonUtils.readJsonFile("src/main/resources/basket.json", Basket[].class);
            baskets.forEach(basketHandler::create);

            List<BasketItem> basketItems = JsonUtils.readJsonFile("src/main/resources/basketitem.json", BasketItem[].class);
            basketItems.forEach(basketItemHandler::create);

            List<Category> categories = JsonUtils.readJsonFile("src/main/resources/category.json", Category[].class);
            categories.forEach(categoryHandler::create);

            List<House> houses = JsonUtils.readJsonFile("src/main/resources/house.json", House[].class);
            houses.forEach(houseHandler::create);

            List<Housemate> housemates = JsonUtils.readJsonFile("src/main/resources/housemate.json", Housemate[].class);
            housemates.forEach(housemateHandler::create);

            List<Item> items = JsonUtils.readJsonFile("src/main/resources/item.json", Item[].class);
            items.forEach(itemHandler::create);

            List<Store> stores = JsonUtils.readJsonFile("src/main/resources/store.json", Store[].class);
            stores.forEach(storeHandler::create);


            System.out.println("success: tables populated");

            HibernateUtil.shutdown();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
