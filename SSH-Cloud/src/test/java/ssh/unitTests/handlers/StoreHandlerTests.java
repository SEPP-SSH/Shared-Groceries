package ssh.unitTests.handlers;

import org.junit.jupiter.api.*;
import ssh.entities.Store;
import ssh.handlers.StoreHandler;
import ssh.utilities.HibernateUtility;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StoreHandlerTests {

    private StoreHandler storeHandler;
    private Store store;

    @BeforeAll
    void setUp() throws Exception {
        storeHandler = new StoreHandler(HibernateUtility.getSessionFactory());

        store = new Store();
        store.setStoreName("MoneyBurnerMarket");
        store.setStoreLogo("MoneyBurnerMarket.png");
        storeHandler.create(store);
    }


    @Test
    void testCreateStore() {
        // Arrange
        Store newStore = new Store();
        newStore.setStoreName("SaveMore");
        newStore.setStoreLogo("SaveMore.png");

        // Act
        storeHandler.create(newStore);

        // Assert
        Store fetchedStore = storeHandler.getById(newStore.getStoreId());
        assertNotNull(fetchedStore);
        assertEquals("SaveMore", fetchedStore.getStoreName());
        assertEquals("SaveMore.png", fetchedStore.getStoreLogo());
    }

    @Test
    void testGetAllStores() {
        List<Store> stores = storeHandler.getAll();
        boolean flag = false;
        for (Store returnedStore : stores){
            if (store.getStoreId() == returnedStore.getStoreId()){
                flag = true;
                break;
            }
        }
        assertTrue(flag);
    }

    @Test
    void testGetStoreById() {
        Store fetchedStore = storeHandler.getById(store.getStoreId());
        assertNotNull(fetchedStore);
        assertEquals(store.getStoreId(), fetchedStore.getStoreId());
    }

    @Test
    void testDeleteStoreById() {
        assertNotNull(storeHandler.getById(store.getStoreId()));
        storeHandler.deleteById(store.getStoreId());
        assertNull(storeHandler.getById(store.getStoreId()));
        storeHandler.create(store);
    }
}
