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
        for (int counter = 0; counter < 60; counter++){
            System.out.println("Waiting for SSH Cloud server to spin up. " + (60 - counter) + " seconds remaining.");
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
    }

    @Test
    @Order(3)
    void testReturnItems() {
        List<Item> items = Client.returnitems(storeId);
        assertNotNull(items);
        assertFalse(items.isEmpty());
    }

    @Test
    @Order(4)
    void testReturnHousemates() {
        List<Housemate> housemates = Client.returnHousemates(houseId);
        assertNotNull(housemates);
        assertFalse(housemates.isEmpty());
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
            if (basketItem.getItem().getItemId() == itemId && basketItem.getItemQuantity() == 5) {
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
            if (basketItem.getItem().getItemId() == itemId && basketItem.getItemQuantity() == 2) {
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
