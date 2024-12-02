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
        try {
            PopulateTables.populate();
        } catch (Exception e) {
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