import org.junit.jupiter.api.*;
import ssh.Client;
import ssh.ReturnedBasket;
import ssh.entities.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IntegrationTests {
    private static int houseId;
    private static int housemateId;
    private static int storeId;
    private static int basketId;
    private static int itemId;

    @BeforeAll
    static void setUp() throws Exception {
        for (int counter = 0; counter < 75; counter++){
            System.out.println("Waiting for SSH Cloud server to spin up. " + (75 - counter) + " seconds remaining.");
            Thread.sleep(1000);
        }

        houseId = 1;
        housemateId = 1;
        storeId = 1;
        basketId = 1;
        itemId = 1;
    }

    @Test
    @Order(1)
    void testReturnStores() {
        List<Store> stores = Client.returnStores(houseId, housemateId);
        assertNotNull(stores);

        boolean storeFound = false;
        for (Store store : stores) {
            if (store.getStoreId() == storeId) {
                assertEquals("Tesco", store.getStoreName());
                assertEquals("https://upload.wikimedia.org/wikipedia/en/thumb/b/b0/Tesco_Logo.svg/2560px-Tesco_Logo.svg.png", store.getStoreLogo());
                storeFound = true;
                break;
            }
        }
        assertTrue(storeFound);
    }

    @Test
    @Order(2)
    void testReturnCategories() {
        List<Category> categories = Client.returnCategories(storeId);
        assertNotNull(categories);
        assertFalse(categories.isEmpty());

        boolean categoryFound = false;
        for (Category category : categories) {
            if (category.getCategoryName().equals("Fruit")) {
                categoryFound = true;
                assertEquals(storeId, category.getStore().getStoreId());
                break;
            }
        }
        assertTrue(categoryFound);
    }

    @Test
    @Order(3)
    void testReturnItems() {
        List<Item> items = Client.returnitems(storeId);
        assertNotNull(items);
        assertFalse(items.isEmpty());

        boolean itemFound = false;
        for (Item item : items) {
            if (item.getItemName().equals("Apples")) {
                itemFound = true;
                assertEquals(3.00, item.getItemBasePrice());
                assertEquals(2.50, item.getItemOfferPrice());
                assertEquals("https://nearlynakedveg.co.uk/cdn/shop/products/Depositphotos_246784090_S_720x.jpg?v=1681394329",item.getItemImg());
                assertEquals(1, item.getStore().getStoreId());
                assertEquals(1, item.getCategory().getCategoryId());
                assertTrue(item.isItemInStock());
                break;
            }
        }
        assertTrue(itemFound);
    }

    @Test
    @Order(4)
    void testReturnHousemates() {
        List<Housemate> housemates = Client.returnHousemates(houseId);
        assertNotNull(housemates);
        assertFalse(housemates.isEmpty());

        boolean housemateFound = false;
        for (Housemate housemate : housemates) {
            if (housemate.getHousemateForename().equals("Nathan") && housemate.getHousemateSurname().equals("Drake")) {
                housemateFound = true;
                assertEquals("nathan.png", housemate.getHousemateImg());
                assertEquals(houseId, housemate.getHouse().getHouseId());
                break;
            }
        }
        assertTrue(housemateFound);
    }
    @Test
    @Order(5)
    void testReturnBasketId() {
        ReturnedBasket returnedBasket = Client.returnBasketId(houseId, storeId);
        assertNotNull(returnedBasket);
        assertEquals(basketId, returnedBasket.getBasketid());
    }

    @Test
    @Order(6)
    void testAddToBasket() {
        boolean success = Client.addToBasket(basketId, storeId, itemId, housemateId, 5);
        assertTrue(success);

        List<BasketItem> basketItems = Client.returnBasketId(houseId, storeId).getBasketItems();
        boolean itemFound = false;
        for (BasketItem basketItem : basketItems) {
            if (basketItem.getItem().getItemId() == itemId && basketItem.getItemQuantity() == 10) {
                itemFound = true;
                break;
            }
        }
        assertTrue(itemFound);
    }

    @Test
    @Order(7)
    void testRemoveFromBasket() {
        boolean success = Client.removeFromBasket(basketId, itemId, housemateId, 3);
        assertTrue(success);

        List<BasketItem> basketItems = Client.returnBasketId(houseId, storeId).getBasketItems();
        boolean quantityUpdated = false;
        for (BasketItem basketItem : basketItems) {
            if (basketItem.getItem().getItemId() == itemId && basketItem.getItemQuantity() == 7) {
                quantityUpdated = true;
                break;
            }
        }
        assertTrue(quantityUpdated);
    }

    @Test
    @Order(8)
    void testSubmitOrder() {
        boolean success = Client.submitOrder(basketId);
        assertTrue(success);
    }

}
