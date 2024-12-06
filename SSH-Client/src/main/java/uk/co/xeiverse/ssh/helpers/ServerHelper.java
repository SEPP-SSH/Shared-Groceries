package uk.co.xeiverse.ssh.helpers;

import java.util.ArrayList;
import java.util.List;

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

    public void connect() {
        // Send houseID and housemateID

        // Server returns list of stores
        storesList = null;

        // Choose store and send to server
        if (storesList != null) {
            if (!storesList.isEmpty()) {
                currentStoreId = storesList.getFirst().getId();
            }
        }

        // Server returns categories and items for that store,
        // the basket ID for that store and list of items in basket.
        categoriesList = null;
        itemsList = null;
        basketId = null;
        basketItemsList = null;
    }

    public List<GroceryStore> getStores() {
        // TODO: Fetch stores from database
        List<GroceryStore> storesList = new ArrayList<>();

        // Using houseId and housemateId

        return storesList;
    }

    public List<String> getCategories() {
        // TODO: Fetch categories from database
        List<String> itemCategories = new ArrayList<>();

        // Using storeId

        return itemCategories;
    }

    public void addItemToBasket(GroceryItem item, Integer quantity) {
        // TODO: Add item to basket in database

        // Add to local list
        BasketItem temp = (BasketItem) item;
        temp.setQuantity(quantity);
        basketItemsList.add(temp);

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

    public List<Housemate> getHousemates() {
        // TODO: Fetch housemates from database
        List<Housemate> housematesList = new ArrayList<>();

        return housematesList;
    }
}