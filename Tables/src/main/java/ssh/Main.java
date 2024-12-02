package ssh;

import ssh.entities.*;
import ssh.handlers.*;
import ssh.utils.HibernateUtil;
import ssh.utils.JsonUtils;
import org.hibernate.SessionFactory;
import ssh.handlers.CategoryHandler;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Initialize Hibernate session factory
//        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        // Initialize repositories
//        HouseHandler houseHandler = new HouseHandler(sessionFactory);
//        StoreHandler storeRepository = new StoreHandler(sessionFactory);
//        CategoryHandler categoryRepository = new CategoryHandler(sessionFactory);
//        ItemHandler itemRepository = new ItemHandler(sessionFactory);
//        HousemateHandler housemateRepository = new HousemateHandler(sessionFactory);

//        // add housemate data
//        try{
//            List<House> houses = JsonUtils.readJsonFile("src/main/resources/house.json", House[].class);
//            houses.forEach(houseHandler::create);
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//
//        // get house data
//        List<House> returnedHouses = houseHandler.getAll();
//        System.out.println("num of returned houses = " + Integer.toString(returnedHouses.size()));
//
//        // sanity test it
//        System.out.println(returnedHouses.getFirst().getHouseAddress());

//        HibernateUtil.shutdown();
        try {
            PopulateTables.populate();

            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            HouseHandler houseHandler = new HouseHandler(sessionFactory);
            List<House> returnedHouses = houseHandler.getAll();
            System.out.println("first id = " + Integer.toString(returnedHouses.getFirst().getHouseId()));
            HibernateUtil.shutdown();
            // Choose a task to perform
//            System.out.println("Select an option:");
//            System.out.println("1. Populate database from JSON files");
//            System.out.println("2. Export database to JSON files");
//            System.out.println("3. Exit");
//
//            // Simulate user choice (for simplicity)
//            int choice = 1; // Change this for testing purposes
//
//            switch (choice) {
//                case 1 -> populateDatabase(houseHandler, storeRepository, categoryRepository, itemRepository);
//                case 2 -> exportDatabase(houseHandler, storeRepository, categoryRepository, itemRepository);
//                case 3 -> System.out.println("Exiting application.");
//                default -> System.out.println("Invalid choice.");
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Populates the database using JSON files.
     */
    private static void populateDatabase(
            HouseHandler houseHandler,
            StoreHandler storeHandler,
            CategoryHandler categoryHandler,
            ItemHandler itemHandler) {
        try {
            // Load and save houses
            List<House> houses = JsonUtils.readJsonFile("src/main/resources/house.json", House[].class);
            houses.forEach(houseHandler::create);

            // Load and save stores
            List<Store> stores = JsonUtils.readJsonFile("src/main/resources/store.json", Store[].class);
            stores.forEach(storeHandler::create);

            // Load and save categories
            List<Category> categories = JsonUtils.readJsonFile("src/main/resources/category.json", Category[].class);
            categories.forEach(categoryHandler::create);

            // Load and save items
            List<Item> items = JsonUtils.readJsonFile("src/main/resources/item.json", Item[].class);
            items.forEach(itemHandler::create);

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
            HouseHandler houseHandler,
            StoreHandler storeHandler,
            CategoryHandler categoryHandler,
            ItemHandler itemHandler) {
        try {
            // Initialize ObjectMapper
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();

            // Ensure output directory exists
            java.io.File outputDir = new java.io.File("output");
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }

            // Export houses
            List<House> houses = houseHandler.getAll();
            mapper.writeValue(new java.io.File("output/house.json"), houses);

            // Export stores
            List<Store> stores = storeHandler.getAll();
            mapper.writeValue(new java.io.File("output/store.json"), stores);

            // Export categories
            List<Category> categories = categoryHandler.getAll();
            mapper.writeValue(new java.io.File("output/category.json"), categories);

            // Export items
            List<Item> items = itemHandler.getAll();
            mapper.writeValue(new java.io.File("output/item.json"), items);

            System.out.println("Data exported to JSON files successfully!");

        } catch (Exception e) {
            System.err.println("Error exporting database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
