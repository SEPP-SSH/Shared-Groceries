package uk.co.xeiverse.ssh.helpers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import uk.co.xeiverse.ssh.networking.Client;
import uk.co.xeiverse.ssh.networking.ReturnedBasket;
import uk.co.xeiverse.ssh.networking.entities.Basket;
import uk.co.xeiverse.ssh.networking.entities.BasketItem;
import uk.co.xeiverse.ssh.networking.entities.Category;
import uk.co.xeiverse.ssh.networking.entities.House;
import uk.co.xeiverse.ssh.networking.entities.Housemate;
import uk.co.xeiverse.ssh.networking.entities.Item;
import uk.co.xeiverse.ssh.networking.entities.Store;

public class ServerHelper {

    // Hardcoded for prototype
    public static final Integer TEMP_HOUSEMATE_ID = 2; // 2 is Clara, 1 is Nathan Drake
    public static final Integer TEMP_HOUSE_ID = 1;

    private Integer housemateId;
    private Integer houseId;
    private List<Store> storesList;
    private Integer currentStoreId;
    private List<Category> categoriesList;
    private List<Item> itemsList;
    private Integer basketId;
    private List<BasketItem> basketItemsList;
    private List<Housemate> housemateList;

    public ServerHelper(Integer housemateId, Integer houseId) {
        this.housemateId = housemateId;
        this.houseId = houseId;

        // Initialise variables
        storesList = new ArrayList<>();
        currentStoreId = -1;
        categoriesList = new ArrayList<>();
        itemsList = new ArrayList<>();
        basketId = -1;
        basketItemsList = new ArrayList<>();
    }

    public Boolean start() {
        // Get list of stores from server
        storesList = Client.returnStores(houseId, housemateId);

        // Check the list isn't empty
        if (storesList != null) {
            if (!storesList.isEmpty()) {
                // Load the first store
                loadStore(storesList.get(0).getStoreId());
                return true;
            }
        }

        return false;
    }

    public void loadStore(Integer storeId) {
        // Set current store
        currentStoreId = storeId;


        // Server returns categories and items for that store,
        // the basket ID for that store and list of items in basket.

        categoriesList = new ArrayList<>();
        categoriesList.add(new Category(-1, null, "Offers")); // Add offers tab
        categoriesList.addAll(Client.returnCategories(storeId)); // Add the categories from the server

        itemsList = Client.returnitems(storeId);

        // Get basket information
        ReturnedBasket returnedBasket = Client.returnBasketId(houseId, storeId);
        basketId = returnedBasket.getBasketid();
        basketItemsList = returnedBasket.getBasketItems();

        // Get list of housemates
        housemateList = Client.returnHousemates(houseId);
    }

    public void addItemToBasket(Item item, Integer quantity) {
        // Add to local list
        // Check if the item is already in the list
        boolean found = false;
        for (BasketItem current : basketItemsList) {
            if (current.getItem().getItemId() == item.getItemId() && current.getHousemate().getHousemateId() == housemateId) {
                current.setItemQuantity(current.getItemQuantity() + quantity);
                found = true;
            }
        }

        if (!found) {
            House tempHouse = new House();
            tempHouse.setHouseId(houseId);

            Basket tempBasket = new Basket();
            tempBasket.setBasketId(basketId);
            tempBasket.setHouse(tempHouse);
            tempBasket.setStore(getStore(currentStoreId));

            BasketItem basketItem = new BasketItem();
            basketItem.setBasketItemId(-1); // Assign -1 because its only an offline list
            basketItem.setBasket(tempBasket);
            basketItem.setItem(item);
            basketItem.setStore(getStore(currentStoreId));
            basketItem.setHousemate(getHousemate(housemateId));
            basketItem.setItemQuantity(quantity);

            basketItemsList.add(basketItem);
        }

        // Send storeId, basketId, itemId, housemateId and quantity
        Client.addToBasket(basketId, currentStoreId, item.getItemId(), housemateId, quantity);
    }

    public void removeItemFromBasket(Item item, Integer quantity) {
        // Remove from local list
        for (BasketItem current : basketItemsList) {
            if (current.getItem().getItemId() == item.getItemId() &&
                    current.getHousemate().getHousemateId() == housemateId) {
                // Check if should totally remove item
                if ((current.getItemQuantity() - quantity) > 0) {
                    current.setItemQuantity(current.getItemQuantity() - quantity);
                } else {
                    basketItemsList.remove(current);
                }
            }
        }

        // Send basketId, itemId, housemateId and quantity
        Client.removeFromBasket(basketId, item.getItemId(), housemateId, quantity);
    }

    public Boolean submitOrder() {
        return Client.submitOrder(basketId);
    }

    // GETTERS

    public Integer getHousemateId() {
        return housemateId;
    }

    public Integer getHouseId() {
        return houseId;
    }

    public List<Store> getStoresList() {
        return storesList;
    }

    public Integer getCurrentStoreId() {
        return currentStoreId;
    }

    public List<Category> getCategoriesList() {
        return categoriesList;
    }

    public List<Item> getItemsList() {
        return itemsList;
    }

    public Integer getBasketId() {
        return basketId;
    }

    public List<BasketItem> getBasketItemsList() {
        return basketItemsList;
    }

    public List<Housemate> getHousemateList() {
        return housemateList;
    }

    private Housemate getHousemate(int housemateId) {
        for (Housemate current : housemateList) {
            if (current.getHousemateId() == housemateId) {
                return current;
            }
        }
        return null;
    }

    private Store getStore(int storeId) {
        for (Store current : storesList) {
            if (current.getStoreId() == storeId) {
                return current;
            }
        }
        return null;
    }
}
