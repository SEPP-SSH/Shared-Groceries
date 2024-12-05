package ssh.unitTests.queries;

import org.junit.jupiter.api.*;
import ssh.QueryServicer;
import ssh.entities.*;
import ssh.handlers.*;
import ssh.utilities.HibernateUtility;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QueryServicerTests {

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
        houseHandler = new HouseHandler(HibernateUtility.getSessionFactory());
        housemateHandler = new HousemateHandler(HibernateUtility.getSessionFactory());
        storeHandler = new StoreHandler(HibernateUtility.getSessionFactory());
        categoryHandler = new CategoryHandler(HibernateUtility.getSessionFactory());
        basketHandler = new BasketHandler(HibernateUtility.getSessionFactory());
        basketItemHandler = new BasketItemHandler(HibernateUtility.getSessionFactory());
        itemHandler = new ItemHandler(HibernateUtility.getSessionFactory());

        // Prepopulate the database with prototype constraints
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
        List<BasketItem> basketItems = basketItemHandler.getAll();
        for (BasketItem basketItem : basketItems) {
            basketItemHandler.deleteById(basketItem.getBasketItemId());
        }
    }

    @Test
    void testReturnStores() {
        List<Store> stores = QueryServicer.returnStores(house.getHouseId(), housemate.getHousemateId());
        assertNotNull(stores);
        assertEquals(1, stores.size());
        assertEquals("MoneyBurnerMarket", stores.get(0).getStoreName());
    }

    @Test
    void testReturnCategories() {
        List<Category> categories = QueryServicer.returnCategories(store.getStoreId());
        assertNotNull(categories);
        assertEquals(1, categories.size());
    }

    @Test
    void testReturnBasketId() {
        Map<Integer, List<BasketItem>> basketMap = QueryServicer.returnBasketId(house.getHouseId(), store.getStoreId());
        assertNotNull(basketMap);
        assertEquals(1, basketMap.size());
        assertTrue(basketMap.containsKey(basket.getBasketId()));
        assertTrue(basketMap.get(basket.getBasketId()).isEmpty());
    }

    @Test
    void testAddToBasket() {
        boolean success = QueryServicer.addToBasket(basket.getBasketId(), store.getStoreId(), item.getItemId(), housemate.getHousemateId(), 5);
        assertTrue(success);
        List<BasketItem> basketItems = basketItemHandler.getByBasketId(basket.getBasketId());
        assertEquals(1, basketItems.size());
        assertEquals(5, basketItems.get(0).getItemQuantity());
        assertEquals(item.getItemId(), basketItems.get(0).getItem().getItemId());
    }

    @Test
    void testRemoveFromBasket() {
        QueryServicer.addToBasket(basket.getBasketId(), store.getStoreId(), item.getItemId(), housemate.getHousemateId(), 5);

        boolean success = QueryServicer.removeFromBasket(basket.getBasketId(), item.getItemId(), housemate.getHousemateId(), 3);

        assertTrue(success);

        List<BasketItem> basketItems = basketItemHandler.getByBasketId(basket.getBasketId());
        assertEquals(1, basketItems.size());
        assertEquals(2, basketItems.get(0).getItemQuantity());
    }

    @Test
    void testSubmitOrder() {
        boolean success = QueryServicer.submitOrder(basket.getBasketId());
        assertTrue(success);
    }
}
