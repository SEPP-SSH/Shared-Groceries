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
        basketItemHandler.createByInfo(basket.getBasketId(), store.getStoreId(), item.getItemId(), housemate.getHousemateId(), basketItem.getItemQuantity());
        List<BasketItem> basketItems = basketItemHandler.getByBasketId(basket.getBasketId());
        boolean flag = false;
        for (BasketItem returnedBasketItem : basketItems){
            if (store.getStoreId() == returnedBasketItem.getStore().getStoreId()){
                if (item.getItemId() == returnedBasketItem.getItem().getItemId()){
                    if (housemate.getHousemateId() == returnedBasketItem.getHousemate().getHousemateId()){
                        if (basketItem.getItemQuantity() == returnedBasketItem.getItemQuantity()){
                            flag = true;
                            break;
                        }
                    }
                }
            }
        }
        assertTrue(flag);
    }

    @Test
    void testGetAllBasketItems() {
        List<BasketItem> basketItems = basketItemHandler.getAll();
        boolean flag = false;
        for (BasketItem returnedBasketItem : basketItems){
            if (basketItem.getBasketItemId() == returnedBasketItem.getBasketItemId()){
                flag = true;
                break;
            }
        }
        assertTrue(flag);
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
        boolean flag = false;
        for (BasketItem returnedBasketItem : basketItems){
            if (basket.getBasketId() == returnedBasketItem.getBasketItemId()){
                flag = true;
                break;
            }
        }
        assertTrue(flag);
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
