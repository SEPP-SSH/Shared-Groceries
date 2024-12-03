package ssh.unitTests.integration;

import org.junit.jupiter.api.*;
import ssh.QueryServicer;
import ssh.entities.*;
import ssh.handlers.*;
import ssh.utilities.HibernateUtility;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IntegrationTests {

    private HouseHandler houseHandler;
    private HousemateHandler housemateHandler;
    private StoreHandler storeHandler;
    private CategoryHandler categoryHandler;
    private BasketHandler basketHandler;
    private BasketItemHandler basketItemHandler;
    private ItemHandler itemHandler;

    private House house;
    private Housemate housemate;
    private Store store;
    private Category category;
    private Basket basket;
    private Item item;

    @BeforeAll
    void setUp() throws Exception {
        // Initialize handlers
        houseHandler = new HouseHandler(HibernateUtility.getSessionFactory());
        housemateHandler = new HousemateHandler(HibernateUtility.getSessionFactory());
        storeHandler = new StoreHandler(HibernateUtility.getSessionFactory());
        categoryHandler = new CategoryHandler(HibernateUtility.getSessionFactory());
        basketHandler = new BasketHandler(HibernateUtility.getSessionFactory());
        basketItemHandler = new BasketItemHandler(HibernateUtility.getSessionFactory());
        itemHandler = new ItemHandler(HibernateUtility.getSessionFactory());

        // Prepopulate database
        house = new House();
        house.setHouseAddress("10 Downing Street");
        houseHandler.create(house);

        housemate = new Housemate();
        housemate.setHousemateForename("John");
        housemate.setHousemateSurname("Doe");
        housemate.setHousemateImg("john.png");
        housemate.setHouse(house);
        housemateHandler.create(housemate);

        store = new Store();
        store.setStoreName("MoneyBurnerMarket");
        store.setStoreLogo("MoneyBurnerMarket.png");
        storeHandler.create(store);

        category = new Category();
        category.setCategoryName("Fruit");
        category.setStore(store);
        categoryHandler.create(category);

        basket = new Basket();
        basket.setHouse(house);
        basket.setStore(store);
        basketHandler.create(basket);

        item = new Item();
        item.setItemName("Apple");
        item.setItemImg("apple.png");
        item.setItemBasePrice(3.00);
        item.setItemOfferPrice(2.50);
        item.setItemInStock(true);
        item.setStore(store);
        item.setCategory(category);
        itemHandler.create(item);
    }

    @BeforeEach
    void cleanDatabase() throws Exception {
        // Clean up basket items
        List<BasketItem> basketItems = basketItemHandler.getAll();
        for (BasketItem basketItem : basketItems) {
            basketItemHandler.deleteById(basketItem.getBasketItemId());
        }
    }

    @Test
    void testAddItemToBasketAndRetrieveBasket() {
        // Add an item to the basket
        boolean addSuccess = QueryServicer.addToBasket(basket.getBasketId(), store.getStoreId(), item.getItemId(), housemate.getHousemateId(), 5);
        assertTrue(addSuccess, "Failed to add item to basket");

        // Retrieve the basket and its items
        Map<Integer, List<BasketItem>> basketMap = QueryServicer.returnBasketId(house.getHouseId(), store.getStoreId());
        assertNotNull(basketMap, "Failed to retrieve basket");
        assertEquals(1, basketMap.size(), "Unexpected basket count");

        List<BasketItem> basketItems = basketMap.get(basket.getBasketId());
        assertNotNull(basketItems, "Basket items list is null");
        assertEquals(1, basketItems.size(), "Unexpected number of basket items");

        BasketItem basketItem = basketItems.get(0);
        assertEquals(5, basketItem.getItemQuantity(), "Item quantity mismatch");
        assertEquals(item.getItemId(), basketItem.getItem().getItemId(), "Item ID mismatch");
    }

    @Test
    void testRemoveItemFromBasket() {
        // Add an item to the basket
        QueryServicer.addToBasket(basket.getBasketId(), store.getStoreId(), item.getItemId(), housemate.getHousemateId(), 5);

        // Remove part of the quantity
        boolean removeSuccess = QueryServicer.removeFromBasket(basket.getBasketId(), item.getItemId(), housemate.getHousemateId(), 3);
        assertTrue(removeSuccess, "Failed to remove item from basket");

        // Verify the remaining quantity
        Map<Integer, List<BasketItem>> basketMap = QueryServicer.returnBasketId(house.getHouseId(), store.getStoreId());
        List<BasketItem> basketItems = basketMap.get(basket.getBasketId());
        assertEquals(1, basketItems.size(), "Unexpected number of basket items");

        BasketItem basketItem = basketItems.get(0);
        assertEquals(2, basketItem.getItemQuantity(), "Item quantity mismatch after removal");
    }

    @Test
    void testOrderSubmission() {
        // Add an item to the basket
        QueryServicer.addToBasket(basket.getBasketId(), store.getStoreId(), item.getItemId(), housemate.getHousemateId(), 5);

        // Submit the order
        boolean orderSuccess = QueryServicer.submitOrder(basket.getBasketId());
        assertTrue(orderSuccess, "Order submission failed");
    }

    @Test
    void testRetrieveStoresAndCategories() {
        // Retrieve stores
        List<Store> stores = QueryServicer.returnStores(house.getHouseId(), housemate.getHousemateId());
        assertNotNull(stores, "Failed to retrieve stores");
        assertEquals(1, stores.size(), "Unexpected number of stores");
        assertEquals("MoneyBurnerMarket", stores.get(0).getStoreName(), "Store name mismatch");

        // Retrieve categories for the store
        List<Category> categories = QueryServicer.returnCategories(store.getStoreId());
        assertNotNull(categories, "Failed to retrieve categories");
        assertEquals(1, categories.size(), "Unexpected number of categories");
        assertEquals("Fruit", categories.get(0).getCategoryName(), "Category name mismatch");
    }
}

