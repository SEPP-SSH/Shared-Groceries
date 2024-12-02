package ssh.export;

import ssh.utils.HibernateUtil;
import ssh.entities.*;
import ssh.handlers.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.SessionFactory;


import java.io.File;
import java.util.List;

public class GenerateJsonFiles {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        try {
            // Initialize repositories
            HouseHandler houseHandler = new HouseHandler(sessionFactory);
            HousemateHandler housemateHandler = new HousemateHandler(sessionFactory);
            StoreHandler storeHandler = new StoreHandler(sessionFactory);
            CategoryHandler categoryHandler = new CategoryHandler(sessionFactory);
            ItemHandler itemHandler = new ItemHandler(sessionFactory);
            BasketHandler basketHandler = new BasketHandler(sessionFactory);
            BasketItemHandler basketItemHandler = new BasketItemHandler(sessionFactory);

            // Initialize ObjectMapper
            ObjectMapper mapper = new ObjectMapper();

            // Fetch data and write to JSON files

            // Houses
            List<House> houses = houseHandler.getAll();
            mapper.writeValue(new File("output/house.json"), houses);

            // Housemates
            List<Housemate> housemates = housemateHandler.getAll();
            mapper.writeValue(new File("output/housemate.json"), housemates);

            // Stores
            List<Store> stores = storeHandler.getAll();
            mapper.writeValue(new File("output/store.json"), stores);

            // Categories
            List<Category> categories = categoryHandler.getAll();
            mapper.writeValue(new File("output/category.json"), categories);

            // Items
            List<Item> items = itemHandler.getAll();
            mapper.writeValue(new File("output/item.json"), items);

            // Baskets
            List<Basket> baskets = basketHandler.getAll();
            mapper.writeValue(new File("output/basket.json"), baskets);

            // BasketItems
            List<BasketItem> basketItems = basketItemHandler.getAll();
            mapper.writeValue(new File("output/basketitem.json"), basketItems);

            System.out.println("JSON files generated successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
        }
    }
}
