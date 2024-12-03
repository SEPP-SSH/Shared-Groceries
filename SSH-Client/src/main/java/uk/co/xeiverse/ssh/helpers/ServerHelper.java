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
    public static final Integer housemateID = (Integer) 1;
    public static final Integer houseID = (Integer) 1;

    public ServerHelper() {

    }

    public List<String> getCategories() {
        // TODO: Fetch categories from database
        List<String> itemCategories = new ArrayList<>();

        return itemCategories;
    }

    public List<GroceryItem> getItemsByStore(Integer storeID) {
        // TODO: Fetch items by store from database
        List<GroceryItem> itemsList = new ArrayList<>();

        return itemsList;
    }

    public List<GroceryItem> getBasketItems(Integer houseID, Integer storeID) {
        // TODO: Fetch basket items from database
        List<GroceryItem> basketItemsList = new ArrayList<>();

        return basketItemsList;
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

        return storesList;
    }

    public List<Housemate> getHousemates(Integer houseID) {
        // TODO: Fetch housemates from database
        List<Housemate> housematesList = new ArrayList<>();

        return housematesList;
    }
}