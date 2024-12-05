package ssh.unitTests.handlers;

import org.junit.jupiter.api.*;
import ssh.entities.*;
import ssh.handlers.*;
import ssh.utilities.HibernateUtility;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BasketItemHandlerTests {

    private BasketItemHandler basketItemHandler;
    private BasketHandler basketHandler;
    private StoreHandler storeHandler;
    private ItemHandler itemHandler;
    private CategoryHandler categoryHandler;
    private HousemateHandler housemateHandler;
    private HouseHandler houseHandler;

    private BasketItem basketItem;
    private Basket basket;
    private Store store;
    private Item item;
    private Category category;
    private Housemate housemate;
    private House house;

    @BeforeAll
    void setUp() throws Exception {
        // Initialize handlers
        basketItemHandler = new BasketItemHandler(HibernateUtility.getSessionFactory());
        basketHandler = new BasketHandler(HibernateUtility.getSessionFactory());
        storeHandler = new StoreHandler(HibernateUtility.getSessionFactory());
        itemHandler = new ItemHandler(HibernateUtility.getSessionFactory());
        categoryHandler = new CategoryHandler(HibernateUtility.getSessionFactory());
        housemateHandler = new HousemateHandler(HibernateUtility.getSessionFactory());
        houseHandler = new HouseHandler(HibernateUtility.getSessionFactory());

        store = new Store();
        store.setStoreName("MoneyBurnerMarket");
        store.setStoreLogo("MoneyBurnerMarket.png");
        storeHandler.create(store);

        category = new Category();
        category.setCategoryName("Fruit");
        category.setStore(store);
        categoryHandler.create(category);

        house = new House();
        house.setHouseAddress("11 Downing Street");
        houseHandler.create(house);

        housemate = new Housemate();
        housemate.setHousemateForename("John");
        housemate.setHousemateSurname("Doe");
        housemate.setHousemateImg("john.png");
        housemate.setHouse(house);
        housemateHandler.create(housemate);

        basket = new Basket();
        basket.setStore(store);
        basket.setHouse(house);
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
        List<BasketItem> allBasketItems = basketItemHandler.getAll();
        for (BasketItem basketItem : allBasketItems) {
            basketItemHandler.deleteById(basketItem.getBasketItemId());
        }

        basketItem = new BasketItem();
        basketItem.setBasket(basket);
        basketItem.setStore(store);
        basketItem.setItem(item);
        basketItem.setHousemate(housemate);
        basketItem.setItemQuantity(5);
        basketItemHandler.create(basketItem);
    }


    @Test
    void testCreateBasketItem() {
        // Arrange
        BasketItem basketItem = new BasketItem();
        basketItem.setBasket(basket);
        basketItem.setStore(store);
        basketItem.setItem(item);
        basketItem.setHousemate(housemate);
        basketItem.setItemQuantity(5);

        // Act
        basketItemHandler.create(basketItem);

        // Assert
        BasketItem fetchedBasketItem = basketItemHandler.getById(basketItem.getBasketItemId());
        assertNotNull(fetchedBasketItem, "The basketItem should be successfully saved.");
        assertEquals(5, fetchedBasketItem.getItemQuantity(), "The quantity should match.");
        assertEquals(basket.getBasketId(), fetchedBasketItem.getBasket().getBasketId(), "The basket ID should match.");
        assertEquals(item.getItemName(), fetchedBasketItem.getItem().getItemName(), "The item name should match.");
    }

    @Test
    void testCreateBasketItemByInfo() {
        basketItemHandler.deleteById(basketItem.getBasketItemId());
        basketItemHandler.createByInfo(basket.getBasketId(), store.getStoreId(), item.getItemId(), housemate.getHousemateId(), basketItem.getItemQuantity());
        List<BasketItem> basketItems = basketItemHandler.getByBasketId(basket.getBasketId());
        assertEquals(1, basketItems.size());
        assertEquals(store.getStoreId(), basketItems.get(0).getStore().getStoreId());
        assertEquals(item.getItemId(), basketItems.get(0).getItem().getItemId());
        assertEquals(housemate.getHousemateId(), basketItems.get(0).getHousemate().getHousemateId());
        assertEquals(basketItem.getItemQuantity(), basketItems.get(0).getItemQuantity());
    }

    @Test
    void testGetAllBasketItems() {
        List<BasketItem> basketItems = basketItemHandler.getAll();
        assertEquals(1, basketItems.size());
        assertEquals(basketItem.getBasketItemId(), basketItems.get(0).getBasketItemId());
    }

    @Test
    void testGetBasketItemById() {
        BasketItem fetchedBasketItem = basketItemHandler.getById(basketItem.getBasketItemId());
        assertNotNull(fetchedBasketItem);
        assertEquals(basketItem.getBasketItemId(), fetchedBasketItem.getBasketItemId());
    }

    @Test
    void testGetBasketItemByBasketId() {
        List<BasketItem> basketItems = basketItemHandler.getByBasketId(basket.getBasketId());
        assertEquals(basket.getBasketId(), basketItems.get(0).getBasket().getBasketId());
    }

    @Test
    void testIncreaseItemQuantity() {
        basketItemHandler.increaseItemQuantity(basketItem.getBasketItemId(), 3);
        BasketItem updatedBasketItem = basketItemHandler.getById(basketItem.getBasketItemId());
        assertEquals(8, updatedBasketItem.getItemQuantity());
    }

    @Test
    void testDecreaseItemQuantity() {
        basketItemHandler.decreaseItemQuantity(basketItem.getBasketItemId(), 3);
        BasketItem updatedBasketItem = basketItemHandler.getById(basketItem.getBasketItemId());
        assertEquals(2, updatedBasketItem.getItemQuantity());
    }

    @Test
    void testDeleteBasketItemById() {
        assertNotNull(basketItemHandler.getById(basketItem.getBasketItemId()));
        basketItemHandler.deleteById(basketItem.getBasketItemId());
        assertNull(basketItemHandler.getById(basketItem.getBasketItemId()));
        basketItemHandler.create(basketItem);
    }
}
