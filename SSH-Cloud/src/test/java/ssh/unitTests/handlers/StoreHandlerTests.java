package ssh.unitTests.handlers;

import org.junit.jupiter.api.*;
import ssh.entities.Item;
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
        List<Store> stores = storeHandler.getAll();
        boolean storeFound = false;
        for (Store fetchedItem :stores) {
            if (fetchedItem.getStoreName().equals("SaveMore") &&
                    fetchedItem.getStoreLogo().equals("SaveMore.png")) {
                storeFound = true;
            }
        }
        assertTrue(storeFound);
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
}
