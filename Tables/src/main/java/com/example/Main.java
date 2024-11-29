package com.example;

import com.example.entities.*;
import com.example.repositories.*;
import com.example.utils.HibernateUtil;
import com.example.utils.JsonUtils;
import org.hibernate.SessionFactory;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Initialize Hibernate session factory
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        // Initialize repositories
        HouseRepository houseRepository = new HouseRepository(sessionFactory);
        StoreRepository storeRepository = new StoreRepository(sessionFactory);
        CategoryRepository categoryRepository = new CategoryRepository(sessionFactory);
        ItemRepository itemRepository = new ItemRepository(sessionFactory);

        try {
            // Choose a task to perform
            System.out.println("Select an option:");
            System.out.println("1. Populate database from JSON files");
            System.out.println("2. Export database to JSON files");
            System.out.println("3. Exit");

            // Simulate user choice (for simplicity)
            int choice = 1; // Change this for testing purposes

            switch (choice) {
                case 1 -> populateDatabase(houseRepository, storeRepository, categoryRepository, itemRepository);
                case 2 -> exportDatabase(houseRepository, storeRepository, categoryRepository, itemRepository);
                case 3 -> System.out.println("Exiting application.");
                default -> System.out.println("Invalid choice.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
        }
    }

    /**
     * Populates the database using JSON files.
     */
    private static void populateDatabase(
            HouseRepository houseRepository,
            StoreRepository storeRepository,
            CategoryRepository categoryRepository,
            ItemRepository itemRepository) {
        try {
            // Load and save houses
            List<House> houses = JsonUtils.readJsonFile("src/main/resources/house.json", House[].class);
            houses.forEach(houseRepository::create);

            // Load and save stores
            List<Store> stores = JsonUtils.readJsonFile("src/main/resources/store.json", Store[].class);
            stores.forEach(storeRepository::create);

            // Load and save categories
            List<Category> categories = JsonUtils.readJsonFile("src/main/resources/category.json", Category[].class);
            categories.forEach(categoryRepository::create);

            // Load and save items
            List<Item> items = JsonUtils.readJsonFile("src/main/resources/item.json", Item[].class);
            items.forEach(itemRepository::create);

            System.out.println("Database populated successfully!");

        } catch (Exception e) {
            System.err.println("Error populating database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Exports database data to JSON files.
     */
    private static void exportDatabase(
            HouseRepository houseRepository,
            StoreRepository storeRepository,
            CategoryRepository categoryRepository,
            ItemRepository itemRepository) {
        try {
            // Initialize ObjectMapper
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();

            // Ensure output directory exists
            java.io.File outputDir = new java.io.File("output");
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }

            // Export houses
            List<House> houses = houseRepository.getAll();
            mapper.writeValue(new java.io.File("output/house.json"), houses);

            // Export stores
            List<Store> stores = storeRepository.getAll();
            mapper.writeValue(new java.io.File("output/store.json"), stores);

            // Export categories
            List<Category> categories = categoryRepository.getAll();
            mapper.writeValue(new java.io.File("output/category.json"), categories);

            // Export items
            List<Item> items = itemRepository.getAll();
            mapper.writeValue(new java.io.File("output/item.json"), items);

            System.out.println("Data exported to JSON files successfully!");

        } catch (Exception e) {
            System.err.println("Error exporting database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
