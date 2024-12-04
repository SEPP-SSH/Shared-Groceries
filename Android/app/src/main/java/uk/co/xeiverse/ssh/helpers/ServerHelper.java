package uk.co.xeiverse.ssh.helpers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import uk.co.xeiverse.ssh.objects.BasketItem;
import uk.co.xeiverse.ssh.objects.GroceryItem;
import uk.co.xeiverse.ssh.objects.GroceryStore;
import uk.co.xeiverse.ssh.objects.Housemate;

public class ServerHelper {

    // Hardcoded for prototype
    public static final Integer TEMP_HOUSEMATE_ID = 1;
    public static final Integer TEMP_HOUSE_ID = 1;

    private Integer housemateId;
    private Integer houseId;
    private List<GroceryStore> storesList;
    private Integer currentStoreId;
    private List<String> categoriesList;
    private List<GroceryItem> itemsList;
    private Integer basketId;
    private List<BasketItem> basketItemsList;

    public ServerHelper(Integer housemateId, Integer houseId) {
        this.housemateId = housemateId;
        this.houseId = houseId;
    }

    public Boolean start() {
        // Send houseID and housemateID

        // Server returns list of stores
        storesList = new ArrayList<>();

        // Check the list isn't empty
        if (!storesList.isEmpty()) {
            // Load the first store
            loadStore(storesList.get(0).getId());
        }

        return true;
    }

    public void loadStore(Integer storeId) {
        // Send store to server

        // Server returns categories and items for that store,
        // the basket ID for that store and list of items in basket.
        categoriesList = new ArrayList<>();
        itemsList = new ArrayList<>();
        basketId = 1;
        basketItemsList = new ArrayList<>();
    }

    public void addItemToBasket(GroceryItem item, Integer quantity) {
        // TODO: Add item to basket in database

        // Add to local list
        // Check if the item is already in the list
        boolean found = false;
        for (BasketItem current : basketItemsList) {
            if (current.getId().equals(item.getId())) {
                current.setQuantity(current.getQuantity() + quantity);
                found = true;
            }
        }

        if (!found) {
            BasketItem basketItem = (BasketItem) item;
            basketItem.setQuantity(quantity);
            basketItemsList.add(basketItem);
        }

        // Send storeId, basketId, itemId, housemateId and quantity
    }

    public void removeItemFromBasket(GroceryItem item, Integer quantity) {
        // TODO: Remove item from basket in database

        // Remove from local list
        for (BasketItem basketItem : basketItemsList) {
            if (basketItem.getId().equals(item.getId())) {
                if (quantity == 0) {
                    basketItemsList.remove(basketItem);
                }
                else {
                    basketItem.setQuantity(quantity);
                }
            }
        }

        // Send basketId, itemId, housemateId and quantity
    }

    public void submitOrder() {
        // Send basket ID

        // Wait for confirmation
    }

    // GETTERS

    public Integer getHousemateId() {
        return housemateId;
    }

    public Integer getHouseId() {
        return houseId;
    }

    public List<GroceryStore> getStoresList() {
        return storesList;
    }

    public Integer getCurrentStoreId() {
        return currentStoreId;
    }

    public List<String> getCategoriesList() {
        return categoriesList;
    }

    public List<GroceryItem> getItemsList() {
        return itemsList;
    }

    public Integer getBasketId() {
        return basketId;
    }

    public List<BasketItem> getBasketItemsList() {
        return basketItemsList;
    }
}
