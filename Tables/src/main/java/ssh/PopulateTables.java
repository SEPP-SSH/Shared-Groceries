package ssh;

import ssh.entities.*;
import ssh.handlers.*;
import ssh.utils.HibernateUtil;
import ssh.utils.JsonUtils;
import org.hibernate.SessionFactory;

import java.util.List;

public class PopulateTables {
    public static void populate() {
        SessionFactory sessionFactory;

        try {
            sessionFactory = HibernateUtil.getSessionFactory();

            // Repositories
            HouseHandler houseHandler = new HouseHandler(sessionFactory);
            HousemateHandler housemateHandler = new HousemateHandler(sessionFactory);
            StoreHandler storeHandler = new StoreHandler(sessionFactory);
            CategoryHandler categoryHandler = new CategoryHandler(sessionFactory);
            ItemHandler itemHandler = new ItemHandler(sessionFactory);
            BasketHandler basketHandler = new BasketHandler(sessionFactory);
            BasketItemHandler basketItemHandler = new BasketItemHandler(sessionFactory);

            // Populate Houses
            List<House> houses = JsonUtils.readJsonFile("src/main/resources/house.json", House[].class);
            houses.forEach(houseHandler::create);

            // Populate Housemates
            List<Housemate> housemates = JsonUtils.readJsonFile("src/main/resources/housemate.json", Housemate[].class);
            housemates.forEach(housemateHandler::create);

            // Populate Stores
            List<Store> stores = JsonUtils.readJsonFile("src/main/resources/store.json", Store[].class);
            stores.forEach(storeHandler::create);

            // Populate Categories
            List<Category> categories = JsonUtils.readJsonFile("src/main/resources/category.json", Category[].class);
            categories.forEach(categoryHandler::create);

            // Populate Items
            List<Item> items = JsonUtils.readJsonFile("src/main/resources/item.json", Item[].class);
            items.forEach(itemHandler::create);

            // Populate Baskets
            List<Basket> baskets = JsonUtils.readJsonFile("src/main/resources/basket.json", Basket[].class);
            baskets.forEach(basketHandler::create);

            // Populate BasketItems
            List<BasketItem> basketItems = JsonUtils.readJsonFile("src/main/resources/basketitem.json", BasketItem[].class);
            basketItems.forEach(basketItemHandler::create);

            System.out.println("All tables populated successfully!");

            HibernateUtil.shutdown();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
