package ssh.unitTests.handlers;

import org.junit.jupiter.api.*;
import ssh.entities.*;
import ssh.handlers.*;
import ssh.utilities.HibernateUtility;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ItemHandlerTests {

    private ItemHandler itemHandler;
    private StoreHandler storeHandler;
    private CategoryHandler categoryHandler;

    private Item item;
    private Store store;
    private Category category;


    @BeforeAll
    void setUp() throws Exception {
        // Initialize handlers
        itemHandler = new ItemHandler(HibernateUtility.getSessionFactory());
        storeHandler = new StoreHandler(HibernateUtility.getSessionFactory());
        categoryHandler = new CategoryHandler(HibernateUtility.getSessionFactory());

        store = new Store();
        store.setStoreName("MoneyBurnerMarket");
        store.setStoreLogo("MoneyBurnerMarket.png");
        storeHandler.create(store);

        category = new Category();
        category.setCategoryName("Fruit");
        category.setStore(store);
        categoryHandler.create(category);

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


    @Test
    void testCreateItem() {
        // Arrange
        Item newItem = new Item();
        newItem.setItemName("Banana");
        newItem.setItemImg("banana.png");
        newItem.setItemBasePrice(1.00);
        newItem.setItemOfferPrice(0.80);
        newItem.setItemInStock(true);
        newItem.setStore(store);
        newItem.setCategory(category);

        // Act
        itemHandler.create(newItem);

        // Assert
        List<Item> items = itemHandler.getAll();
        boolean itemFound = false;
        for (Item fetchedItem :items) {
            if (fetchedItem.getItemName().equals("Banana") &&
                    fetchedItem.getItemOfferPrice() == 0.80 &&
                    fetchedItem.isItemInStock()) {
                itemFound = true;
            }
        }
        assertTrue(itemFound);

    }

    @Test
    void testGetAllItems() {
        List<Item> items = itemHandler.getAll();
        boolean flag = false;
        for (Item returnedItem : items){
            if (item.getItemId() == returnedItem.getItemId()){
                flag = true;
                break;
            }
        }
        assertTrue(flag);
    }

    @Test
    void testGetItemByStoreId() {
        List<Item> items = itemHandler.getByStoreId(store.getStoreId());
        assertEquals(1, items.size());
        boolean flag = false;
        for (Item returnedItem : items){
            if (returnedItem.getItemName().equals("Apple")) {
                if (store.getStoreId() == returnedItem.getStore().getStoreId()){
                    flag = true;
                    break;
                }
            }
        }
        assertTrue(flag);
    }
}
