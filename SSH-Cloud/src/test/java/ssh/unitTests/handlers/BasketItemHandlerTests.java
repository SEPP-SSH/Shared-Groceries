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

    private Basket basket;
    private Store store;
    private Item item;
    private Category category;
    private Housemate housemate;

    @BeforeAll
    void setUp() throws Exception {
        // Initialize handlers
        basketItemHandler = new BasketItemHandler(HibernateUtility.getSessionFactory());
        basketHandler = new BasketHandler(HibernateUtility.getSessionFactory());
        storeHandler = new StoreHandler(HibernateUtility.getSessionFactory());
        itemHandler = new ItemHandler(HibernateUtility.getSessionFactory());
        categoryHandler = new CategoryHandler(HibernateUtility.getSessionFactory());
        housemateHandler = new HousemateHandler(HibernateUtility.getSessionFactory());

        // Prepopulate the database with one store, one category, one basket, one item, and one housemate
        store = new Store();
        store.setStoreName("MoneyBurnerMarket");
        store.setStoreLogo("MoneyBurnerMarket.png");
        storeHandler.create(store);

        category = new Category();
        category.setCategoryName("Fruit");
        category.setStore(store);
        categoryHandler.create(category);

        basket = new Basket();
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

        housemate = new Housemate();
        housemate.setHousemateForename("John");
        housemate.setHousemateSurname("Doe");
        housemate.setHousemateImg("john.png");
        housemateHandler.create(housemate);
    }

    @BeforeEach
    void cleanDatabase() throws Exception {
        // Clean up basket items before each test
        List<BasketItem> basketItems = basketItemHandler.getAll();
        for (BasketItem basketItem : basketItems) {
            basketItemHandler.deleteById(basketItem.getBasketItemId());
        }
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
        assertNotNull(fetchedBasketItem);
        assertEquals(5, fetchedBasketItem.getItemQuantity());
        assertEquals(basket.getBasketId(), fetchedBasketItem.getBasket().getBasketId());
        assertEquals(item.getItemName(), fetchedBasketItem.getItem().getItemName());
    }

    @Test
    void testCreateBasketItemByInfo() {
        // Act
        basketItemHandler.createByInfo(basket.getBasketId(), store.getStoreId(), item.getItemId(), housemate.getHousemateId(), 10);

        // Assert
        List<BasketItem> basketItems = basketItemHandler.getByBasketId(basket.getBasketId());
        assertEquals(1, basketItems.size());
        assertEquals(10, basketItems.get(0).getItemQuantity());
        assertEquals(item.getItemName(), basketItems.get(0).getItem().getItemName());
    }

    @Test
    void testGetAllBasketItems() {
        // Arrange
        BasketItem basketItem = new BasketItem();
        basketItem.setBasket(basket);
        basketItem.setStore(store);
        basketItem.setItem(item);
        basketItem.setHousemate(housemate);
        basketItem.setItemQuantity(5);
        basketItemHandler.create(basketItem);

        // Act
        List<BasketItem> basketItems = basketItemHandler.getAll();

        // Assert
        assertEquals(1, basketItems.size());
        assertEquals(basketItem.getBasketItemId(), basketItems.get(0).getBasketItemId());
    }

    @Test
    void testIncreaseItemQuantity() {
        // Arrange
        BasketItem basketItem = new BasketItem();
        basketItem.setBasket(basket);
        basketItem.setStore(store);
        basketItem.setItem(item);
        basketItem.setHousemate(housemate);
        basketItem.setItemQuantity(5);
        basketItemHandler.create(basketItem);

        // Act
        basketItemHandler.increaseItemQuantity(basketItem.getBasketItemId(), 3);

        // Assert
        BasketItem updatedBasketItem = basketItemHandler.getById(basketItem.getBasketItemId());
        assertEquals(8, updatedBasketItem.getItemQuantity());
    }

    @Test
    void testDecreaseItemQuantity() {
        // Arrange
        BasketItem basketItem = new BasketItem();
        basketItem.setBasket(basket);
        basketItem.setStore(store);
        basketItem.setItem(item);
        basketItem.setHousemate(housemate);
        basketItem.setItemQuantity(5);
        basketItemHandler.create(basketItem);

        // Act
        basketItemHandler.decreaseItemQuantity(basketItem.getBasketItemId(), 2);

        // Assert
        BasketItem updatedBasketItem = basketItemHandler.getById(basketItem.getBasketItemId());
        assertEquals(3, updatedBasketItem.getItemQuantity());
    }

    @Test
    void testDeleteBasketItemById() {
        // Arrange
        BasketItem basketItem = new BasketItem();
        basketItem.setBasket(basket);
        basketItem.setStore(store);
        basketItem.setItem(item);
        basketItem.setHousemate(housemate);
        basketItem.setItemQuantity(5);
        basketItemHandler.create(basketItem);

        // Act
        basketItemHandler.deleteById(basketItem.getBasketItemId());

        // Assert
        assertNull(basketItemHandler.getById(basketItem.getBasketItemId()));
    }
}
