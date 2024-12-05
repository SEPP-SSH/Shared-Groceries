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
    }

    @BeforeEach
    void cleanDatabase() throws Exception {
        // Clean up items before each test
        List<Item> items = itemHandler.getAll();
        for (Item item : items) {
            itemHandler.deleteById(item.getItemId());
        }

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
        Item fetchedItem = itemHandler.getById(newItem.getItemId());
        assertNotNull(fetchedItem);
        assertEquals("Banana", fetchedItem.getItemName());
        assertEquals(0.80, fetchedItem.getItemOfferPrice());
        assertTrue(fetchedItem.isItemInStock());

    }

    @Test
    void testGetAllItems() {
        List<Item> items = itemHandler.getAll();
        assertEquals(1, items.size());
        assertEquals(item.getItemId(), items.get(0).getItemId());
    }

    @Test
    void testGetItemById() {
        Item fetchedItem = itemHandler.getById(item.getItemId());
        assertNotNull(fetchedItem);
        assertEquals(item.getItemId(), fetchedItem.getItemId());
    }

    @Test
    void testDeleteItemById() {
        assertNotNull(itemHandler.getById(item.getItemId()));
        itemHandler.deleteById(item.getItemId());
        assertNull(itemHandler.getById(item.getItemId()));
        itemHandler.create(item);
    }
}
