package com.example;

import com.example.entities.*;
import com.example.repositories.*;
import com.example.utils.HibernateUtil;
import com.example.utils.JsonUtils;
import org.hibernate.SessionFactory;

import java.util.List;

public class PopulateTables {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        try {
            // Repositories
            HouseRepository houseRepository = new HouseRepository(sessionFactory);
            HousemateRepository housemateRepository = new HousemateRepository(sessionFactory);
            StoreRepository storeRepository = new StoreRepository(sessionFactory);
            CategoryRepository categoryRepository = new CategoryRepository(sessionFactory);
            ItemRepository itemRepository = new ItemRepository(sessionFactory);
            BasketRepository basketRepository = new BasketRepository(sessionFactory);
            BasketItemRepository basketItemRepository = new BasketItemRepository(sessionFactory);

            // Populate Houses
            List<House> houses = JsonUtils.readJsonFile("src/main/resources/house.json", House[].class);
            houses.forEach(houseRepository::create);

            // Populate Housemates
            List<Housemate> housemates = JsonUtils.readJsonFile("src/main/resources/housemate.json", Housemate[].class);
            housemates.forEach(housemateRepository::create);

            // Populate Stores
            List<Store> stores = JsonUtils.readJsonFile("src/main/resources/store.json", Store[].class);
            stores.forEach(storeRepository::create);

            // Populate Categories
            List<Category> categories = JsonUtils.readJsonFile("src/main/resources/category.json", Category[].class);
            categories.forEach(categoryRepository::create);

            // Populate Items
            List<Item> items = JsonUtils.readJsonFile("src/main/resources/item.json", Item[].class);
            items.forEach(itemRepository::create);

            // Populate Baskets
            List<Basket> baskets = JsonUtils.readJsonFile("src/main/resources/basket.json", Basket[].class);
            baskets.forEach(basketRepository::create);

            // Populate BasketItems
            List<BasketItem> basketItems = JsonUtils.readJsonFile("src/main/resources/basketitem.json", BasketItem[].class);
            basketItems.forEach(basketItemRepository::create);

            System.out.println("All tables populated successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
        }
    }
}
