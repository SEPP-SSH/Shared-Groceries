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
        try {
            // get session factory object
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

            // initialise handler objects
            BasketHandler basketHandler = new BasketHandler(sessionFactory);
            BasketItemHandler basketItemHandler = new BasketItemHandler(sessionFactory);
            CategoryHandler categoryHandler = new CategoryHandler(sessionFactory);
            HouseHandler houseHandler = new HouseHandler(sessionFactory);
            HousemateHandler housemateHandler = new HousemateHandler(sessionFactory);
            ItemHandler itemHandler = new ItemHandler(sessionFactory);
            StoreHandler storeHandler = new StoreHandler(sessionFactory);

            // initialise the ObjectMapper
            ObjectMapper mapper = new ObjectMapper();

            // fetch data from database, and then write to JSON files
            List<Basket> baskets = basketHandler.getAll();
            mapper.writeValue(new File("output/basket.json"), baskets);

            List<BasketItem> basketItems = basketItemHandler.getAll();
            mapper.writeValue(new File("output/basketitem.json"), basketItems);

            List<Category> categories = categoryHandler.getAll();
            mapper.writeValue(new File("output/category.json"), categories);

            List<House> houses = houseHandler.getAll();
            mapper.writeValue(new File("output/house.json"), houses);

            List<Housemate> housemates = housemateHandler.getAll();
            mapper.writeValue(new File("output/housemate.json"), housemates);

            List<Item> items = itemHandler.getAll();
            mapper.writeValue(new File("output/item.json"), items);

            List<Store> stores = storeHandler.getAll();
            mapper.writeValue(new File("output/store.json"), stores);


            System.out.println("success: generated JSON files");

            HibernateUtil.shutdown();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}