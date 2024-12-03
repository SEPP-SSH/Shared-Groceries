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
        // Initialize handler with Hibernate session factory
        storeHandler = new StoreHandler(HibernateUtility.getSessionFactory());

        // Prepopulate the database with one store
        store = new Store();
        store.setStoreName("MoneyBurnerMarket");
        store.setStoreLogo("MoneyBurnerMarket.png");
        storeHandler.create(store);
    }

    @BeforeEach
    void cleanDatabase() throws Exception {
        // Clean up stores before each test
        List<Store> stores = storeHandler.getAll();
        for (Store store : stores) {
            storeHandler.deleteById(store.getStoreId());
        }

        // Recreate the single store for prototype constraints
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

        // Cleanup
        storeHandler.deleteById(newStore.getStoreId());
    }

    @Test
    void testGetAllStores() {
        // Act
        List<Store> stores = storeHandler.getAll();

        // Assert
        assertEquals(1, stores.size());
        assertEquals(store.getStoreId(), stores.get(0).getStoreId());
        assertEquals("MoneyBurnerMarket", stores.get(0).getStoreName());
    }

    @Test
    void testGetStoreById() {
        // Act
        Store fetchedStore = storeHandler.getById(store.getStoreId());

        // Assert
        assertNotNull(fetchedStore);
        assertEquals(store.getStoreId(), fetchedStore.getStoreId());
        assertEquals("MoneyBurnerMarket", fetchedStore.getStoreName());
        assertEquals("MoneyBurnerMarket.png", fetchedStore.getStoreLogo());
    }

    @Test
    void testDeleteStoreById() {
        // Act
        storeHandler.deleteById(store.getStoreId());

        // Assert
        assertNull(storeHandler.getById(store.getStoreId()));

        // Recreate the store to maintain prototype constraints
        storeHandler.create(store);
    }
}
