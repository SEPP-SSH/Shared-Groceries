package uk.co.xeiverse.ssh.helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import uk.co.xeiverse.ssh.objects.GroceryItem;
import uk.co.xeiverse.ssh.objects.GroceryStore;
import uk.co.xeiverse.ssh.objects.Housemate;

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

    public List<GroceryItem> getBasketItems(Integer houseID, Integer storeID) {
        // TODO: Fetch basket items from database
        List<GroceryItem> basketItems = new ArrayList<>();

        if (storeID == 0) {
            // TEMPORARY SOLUTION
            basketItems = createRandomGroceryItems(3);
        }
        else {
            // TEMPORARY SOLUTION
        }

        return basketItems;
    }

    public void addItemToBasket(Integer houseID, Integer storeID, Integer itemID) {
        // TODO: Add item to basket in database
    }

    public void removeItemFromBasket(Integer houseID, Integer storeID, Integer itemID) {
        // TODO: Remove item from basket in database
    }

    public void updateItemQuantity(Integer houseID, Integer storeID, Integer itemID, Integer quantity) {
        // TODO: Update item quantity in database
    }

    public List<GroceryStore> getStores() {
        // TODO: Fetch stores from database
        List<GroceryStore> storesList = new ArrayList<>();

        // TEMPORARY SOLUTION
        storesList.add(new GroceryStore(0, "Tesco", "https://picsum.photos/200/300"));
        storesList.add(new GroceryStore(1, "Sainsbury's", "https://picsum.photos/200/300"));
        storesList.add(new GroceryStore(2, "Aldi", "https://picsum.photos/200/300"));

        return storesList;
    }

    public List<Housemate> getHousemates(Integer houseID) {
        // TODO: Fetch housemates from database
        List<Housemate> housematesList = new ArrayList<>();

        return housematesList;
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

            groceryItems.add(new GroceryItem(0, name, "https://picsum.photos/200/300", price, price, 1, 1 + random.nextInt(5)));
        }

        return groceryItems;
    }
}
