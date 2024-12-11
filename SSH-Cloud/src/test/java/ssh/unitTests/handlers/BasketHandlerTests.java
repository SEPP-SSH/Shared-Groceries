package ssh.unitTests.handlers;

import org.junit.jupiter.api.*;
import ssh.entities.Basket;
import ssh.entities.House;
import ssh.entities.Store;
import ssh.handlers.BasketHandler;
import ssh.handlers.HouseHandler;
import ssh.handlers.StoreHandler;
import ssh.utilities.HibernateUtility;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BasketHandlerTests {
    private BasketHandler basketHandler;
    private HouseHandler houseHandler;
    private StoreHandler storeHandler;
    private Basket basket;
    private Store store;
    private House house;

    @BeforeAll
    void setUp() throws Exception {
        basketHandler = new BasketHandler(HibernateUtility.getSessionFactory());
        houseHandler = new HouseHandler(HibernateUtility.getSessionFactory());
        storeHandler = new StoreHandler(HibernateUtility.getSessionFactory());

        house = new House();
        house.setHouseAddress("11 Downing Street");
        houseHandler.create(house);

        store = new Store();
        store.setStoreName("MoneyBurnerMarket");
        store.setStoreLogo("MoneyBurnerMarket.png");
        storeHandler.create(store);

        basket = new Basket();
        basket.setHouse(house);
        basket.setStore(store);
        basketHandler.create(basket);

    }

//    @Test
//    void temporaryAlwaysFails(){
//        fail();
//    }

    @Test
    void testCreateBasket() {
        // Arrange
        Basket newBasket = new Basket();
        newBasket.setHouse(house);
        newBasket.setStore(store);

        // Act
        basketHandler.create(newBasket);

        // Assert
        Basket savedBasket = basketHandler.getById(newBasket.getBasketId());
        assertNotNull(savedBasket, "The basket should be successfully saved.");
        assertEquals(house.getHouseId(), savedBasket.getHouse().getHouseId(), "The house ID should match.");
        assertEquals(store.getStoreId(), savedBasket.getStore().getStoreId(), "The store ID should match.");
    }

    @Test
    void testGetAllBaskets() {
        List<Basket> baskets = basketHandler.getAll();
        boolean basketFound = false;
        for (Basket returnedBasket : baskets){
            if (basket.getBasketId() == returnedBasket.getBasketId()){
                basketFound = true;
                break;
            }
        }
        assertTrue(basketFound);
    }

    @Test
    void testGetBasketById() {
        Basket fetchedBasket = basketHandler.getById(basket.getBasketId());
        assertNotNull(fetchedBasket);
        assertEquals(basket.getBasketId(), fetchedBasket.getBasketId());
    }

    @Test
    void testGetBasketByAppInfo() {
        List<Basket> baskets = basketHandler.getByAppInfo(house.getHouseId(), store.getStoreId());
        assertEquals(1, baskets.size());
        boolean flag = false;
        for (Basket returnedBasket : baskets){
            if (basket.getBasketId() == returnedBasket.getBasketId()){
                flag = true;
                break;
            }
        }
        assertTrue(flag);
    }

    @Test
    void testDeleteBasketById() {
        assertNotNull(basketHandler.getById(basket.getBasketId()));
        basketHandler.deleteById(basket.getBasketId());
        assertNull(basketHandler.getById(basket.getBasketId()));
        basketHandler.create(basket); // recreate basket for subsequent test
    }

    @Test
    void testCreateBasketByAppInfo() {
        basketHandler.deleteById(basket.getBasketId());
        basketHandler.createByAppInfo(house.getHouseId(), store.getStoreId());
        List<Basket> baskets = basketHandler.getByAppInfo(house.getHouseId(), store.getStoreId());
        boolean flag = false;
        for (Basket returnedBasket : baskets){
            if (house.getHouseId() == returnedBasket.getHouse().getHouseId()){
                if (store.getStoreId() == returnedBasket.getStore().getStoreId()){
                    flag = true;
                    break;
                }
            }
        }
        assertTrue(flag);
        basket = baskets.get(0);
    }
}
