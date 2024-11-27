package uk.co.xeiverse.ssh.helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import uk.co.xeiverse.ssh.ui.shop.GroceryItem;

public class ServerHelper {

    // Hardcoded for prototype
    public static final Integer housemateID = 1;
    public static final Integer houseID = 1;

    public ServerHelper() {

    }

    public List<String> getCategories() {
        // TODO: Fetch categories from database
        List<String> itemCategories = new ArrayList<>();

        // TEMPORARY SOLUTION
        String[] categories = {"OFFERS", "FROZEN", "FRUIT & VEG", "MEAT", "BAKERY", "DAIRY", "CLEANING", "PASTA & RICE"};
        itemCategories = new ArrayList<>(Arrays.asList(categories));

        return itemCategories;
    }

    public List<GroceryItem> getItemsByStore(Integer storeID) {
        // TODO: Fetch items by store from database
        List<GroceryItem> itemsList = new ArrayList<>();

        // TEMPORARY SOLUTION
        itemsList = createRandomGroceryItems(10);

        return itemsList;
    }

    public List<GroceryItem> getTrolleyItems(Integer houseID, Integer storeID) {
        // TODO: Fetch trolley items from database

        return null;
    }

    public void addItemToTrolley(Integer houseID, Integer storeID, Integer itemID) {
        // TODO: Add item to trolley in database
    }

    public void removeItemFromTrolley(Integer houseID, Integer storeID, Integer itemID) {
        // TODO: Remove item from trolley in database
    }

    public void updateItemQuantity(Integer houseID, Integer storeID, Integer itemID, Integer quantity) {
        // TODO: Update item quantity in database
    }

    public List<String> getStores() {
        // TODO: Fetch stores from database
        List<String> storesList = new ArrayList<>();

        // TEMPORARY SOLUTION
        String[] supermarkets = {"Tesco", "Sainsbury's", "Asda"};
        storesList = new ArrayList<>(Arrays.asList(supermarkets));

        return storesList;
    }

    public List<String> getHousemates(Integer houseID) {
        // TODO: Fetch housemates from database

        return null;
    }

    public List<GroceryItem> createRandomGroceryItems(int numItems) {
        List<GroceryItem> groceryItems = new ArrayList<>();
        List<String> categories = Arrays.asList("Fruits", "Vegetables", "Dairy", "Bakery", "Snacks");
        List<String> itemNames = Arrays.asList("Apple", "Banana", "Milk", "Bread", "Chips", "Orange", "Carrot", "Yogurt", "Cake", "Cookies");
        Random random = new Random();

        for (int i = 0; i < numItems; i++) {
            String category = categories.get(random.nextInt(categories.size()));
            String name = itemNames.get(random.nextInt(itemNames.size()));
            double price = 1 + (10 - 1) * random.nextDouble(); // Price between 1 and 10
            int quantity = 1 + random.nextInt(5); // Quantity between 1 and 5

            groceryItems.add(new GroceryItem(name, price, category, quantity));
        }

        return groceryItems;
    }
}
