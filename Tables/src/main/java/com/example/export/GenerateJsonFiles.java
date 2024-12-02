package com.example.export;

import com.example.utils.HibernateUtil;
import com.example.entities.*;
import com.example.repositories.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.SessionFactory;


import java.io.File;
import java.util.List;

public class GenerateJsonFiles {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        try {
            // Initialize repositories
            HouseRepository houseRepository = new HouseRepository(sessionFactory);
            HousemateRepository housemateRepository = new HousemateRepository(sessionFactory);
            StoreRepository storeRepository = new StoreRepository(sessionFactory);
            CategoryRepository categoryRepository = new CategoryRepository(sessionFactory);
            ItemRepository itemRepository = new ItemRepository(sessionFactory);
            BasketRepository basketRepository = new BasketRepository(sessionFactory);
            BasketItemRepository basketItemRepository = new BasketItemRepository(sessionFactory);

            // Initialize ObjectMapper
            ObjectMapper mapper = new ObjectMapper();

            // Fetch data and write to JSON files

            // Houses
            List<House> houses = houseRepository.getAll();
            mapper.writeValue(new File("output/house.json"), houses);

            // Housemates
            List<Housemate> housemates = housemateRepository.getAll();
            mapper.writeValue(new File("output/housemate.json"), housemates);

            // Stores
            List<Store> stores = storeRepository.getAll();
            mapper.writeValue(new File("output/store.json"), stores);

            // Categories
            List<Category> categories = categoryRepository.getAll();
            mapper.writeValue(new File("output/category.json"), categories);

            // Items
            List<Item> items = itemRepository.getAll();
            mapper.writeValue(new File("output/item.json"), items);

            // Baskets
            List<Basket> baskets = basketRepository.getAll();
            mapper.writeValue(new File("output/basket.json"), baskets);

            // BasketItems
            List<BasketItem> basketItems = basketItemRepository.getAll();
            mapper.writeValue(new File("output/basketitem.json"), basketItems);

            System.out.println("JSON files generated successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
        }
    }
}
