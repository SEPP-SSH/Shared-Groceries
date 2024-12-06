package ssh.unitTests.queries;

import org.junit.jupiter.api.*;
import ssh.QueryServicer;
import ssh.ReturnedBasket;
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
        boolean flag = false;
        for (Store returnedStore : stores){
            if (returnedStore.getStoreName().equals("MoneyBurnerMarket")){
                flag = true;
                break;
            }
        }
        assertTrue(flag);
    }

    @Test
    void testReturnCategories() {
        List<Category> categories = QueryServicer.returnCategories(store.getStoreId());
        assertNotNull(categories);
        assertTrue(categories.size() >= 1);
    }

    @Test
    void testReturnBasketId() {
        ReturnedBasket returnedBasket = QueryServicer.returnBasketId(house.getHouseId(), store.getStoreId());
        assertNotNull(returnedBasket);
        assertEquals(returnedBasket.getBasketid(), basket.getBasketId());
        assertTrue(returnedBasket.getBasketItems().isEmpty());
    }

    @Test
    void testAddToBasket() {
        boolean success = QueryServicer.addToBasket(basket.getBasketId(), store.getStoreId(), item.getItemId(), housemate.getHousemateId(), 5);
        assertTrue(success);
        List<BasketItem> basketItems = basketItemHandler.getByBasketId(basket.getBasketId());
        boolean flag = false;
        for (BasketItem returnedBasketItem : basketItems){
            if (item.getItemId() == returnedBasketItem.getItem().getItemId()){
                assertEquals(5, returnedBasketItem.getItemQuantity());
                flag = true;
                break;
            }
        }
        assertTrue(flag);
    }

    @Test
    void testRemoveFromBasket() {
        QueryServicer.addToBasket(basket.getBasketId(), store.getStoreId(), item.getItemId(), housemate.getHousemateId(), 5);

        boolean success = QueryServicer.removeFromBasket(basket.getBasketId(), item.getItemId(), housemate.getHousemateId(), 3);

        assertTrue(success);

        List<BasketItem> basketItems = basketItemHandler.getByBasketId(basket.getBasketId());
        boolean flag = false;
        for (BasketItem returnedBasketItem : basketItems){
            if (returnedBasketItem.getItemQuantity() == 2){
                flag = true;
                break;
            }
        }
        assertTrue(flag);
    }

    @Test
    void testSubmitOrder() {
        boolean success = QueryServicer.submitOrder(basket.getBasketId());
        assertTrue(success);
    }
}
