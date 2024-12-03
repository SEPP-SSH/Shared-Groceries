package ssh;

import ssh.entities.*;
import ssh.handlers.*;
import ssh.handlers.CategoryHandler;
import ssh.supermarketAPIs.MoneyBurnerMarket;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
            // populates test data from the simulated supermarket API
            MoneyBurnerMarket.populateSshDatabase();

            // TESTING LINES - WILL BE REMOVED
            List<Store> allStores = QueryServicer.returnStores(1,1);
            for (Store store : allStores){
                System.out.println("name of store: " + store.getStoreName());
            }

            List<Category> allCategories = QueryServicer.returnCategories(allStores.getFirst().getStoreId());
            for (Category category : allCategories){
                System.out.println("name of category : " + category.getCategoryName());
            }

            Map<Integer, List<BasketItem>> basket = QueryServicer.returnBasketId(1, 1);
            System.out.println("basket id = " + basket.get(basket.keySet().toArray()[0])); // gets first key
            for (BasketItem basketItem : (List<BasketItem>)basket.values().toArray()[0]){
                System.out.println("basket item name = " + basketItem.getItem().getItemName() + ", quantity = " + basketItem.getItemQuantity());
            }

            boolean addToBasketResult = QueryServicer.addToBasket(1, 1, 1, 1, 26);
            if (addToBasketResult){
                System.out.println("add to basket success");

                basket.clear();
                basket = QueryServicer.returnBasketId(1, 1);
                System.out.println("basket id = " + basket.get(basket.keySet().toArray()[0])); // gets first key
                for (BasketItem basketItem : (List<BasketItem>)basket.values().toArray()[0]){
                    System.out.println("basket item name = " + basketItem.getItem().getItemName() + ", quantity = " + basketItem.getItemQuantity());
                }
            }
            else{
                System.out.println("add to basket failure");
            }

            boolean removeFromBasketResult = QueryServicer.removeFromBasket(1, 1, 1, 35);
            if (addToBasketResult){
                System.out.println("add to basket success");

                basket.clear();
                basket = QueryServicer.returnBasketId(1, 1);
                System.out.println("basket id = " + basket.get(basket.keySet().toArray()[0])); // gets first key
                for (BasketItem basketItem : (List<BasketItem>)basket.values().toArray()[0]){
                    System.out.println("basket item name = " + basketItem.getItem().getItemName() + ", quantity = " + basketItem.getItemQuantity());
                }
            }
            else{
                System.out.println("add to basket failure");
            }


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