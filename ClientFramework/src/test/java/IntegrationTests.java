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
        for (int counter = 0; counter < 90; counter++){
            System.out.println("Waiting for SSH Cloud server to spin up. " + (90 - counter) + " seconds remaining.");
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
            if ("MoneyBurnerMarket".equals(store.getStoreName())) {
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
    void testReturnBasketId() {
        ReturnedBasket returnedBasket = Client.returnBasketId(houseId, storeId);
        assertNotNull(returnedBasket);
        assertTrue(returnedBasket.getBasketItems().isEmpty());
    }

    @Test
    @Order(4)
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
    @Order(5)
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
    @Order(6)
    void testSubmitOrder() {
        boolean success = Client.submitOrder(basketId);
        assertTrue(success);
    }

}
