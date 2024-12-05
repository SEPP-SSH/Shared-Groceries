package ssh.unitTests.handlers;

import org.junit.jupiter.api.*;
import ssh.entities.Basket;
import ssh.entities.BasketItem;
import ssh.entities.Category;
import ssh.entities.Store;
import ssh.handlers.CategoryHandler;
import ssh.handlers.StoreHandler;
import ssh.utilities.HibernateUtility;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CategoryHandlerTests {

    private CategoryHandler categoryHandler;
    private StoreHandler storeHandler;
    private Category category;
    private Store store;

    @BeforeAll
    void setUp() throws Exception {
        categoryHandler = new CategoryHandler(HibernateUtility.getSessionFactory());
        storeHandler = new StoreHandler(HibernateUtility.getSessionFactory());

        store = new Store();
        store.setStoreName("MoneyBurnerMarket");
        store.setStoreLogo("MoneyBurnerMarket.png");
        storeHandler.create(store);

        category = new Category();
        category.setCategoryName("Fruit");
        category.setStore(store);
        categoryHandler.create(category);
    }


    @Test
    void testCreateCategory() {
        // Arrange
        Category category = new Category();
        category.setCategoryName("Vegetables");
        category.setStore(store);

        // Act
        categoryHandler.create(category);

        // Assert
        Category fetchedCategory = categoryHandler.getById(category.getCategoryId());
        assertNotNull(fetchedCategory);
        assertEquals("Vegetables", fetchedCategory.getCategoryName());
        assertEquals(store.getStoreId(), fetchedCategory.getStore().getStoreId());
    }

    @Test
    void testGetAllCategories() {
        List<Category> categories = categoryHandler.getAll();
//        assertEquals(4, categories.size());
        boolean flag = false;
        for (Category returnedCategory : categories){
            if (category.getCategoryId() == returnedCategory.getCategoryId()){
                flag = true;
                break;
            }
        }
        assertTrue(flag);
    }

    @Test
    void testGetCategoryById() {
        Category fetchedCategory = categoryHandler.getById(category.getCategoryId());
        assertNotNull(fetchedCategory);
        assertEquals(category.getCategoryId(), fetchedCategory.getCategoryId());
    }

    @Test
    void testGetCategoryByStoreId() {
        List<Category> categories = categoryHandler.getByStoreId(store.getStoreId());
        assertEquals(1, categories.size());
        boolean flag = false;
        for (Category returnedCategory : categories){
            if (returnedCategory.getCategoryName().equals("Fruit")){
                if (store.getStoreId() == returnedCategory.getStore().getStoreId()){
                    flag = true;
                    break;
                }
            }
        }
        assertTrue(flag);
    }

    @Test
    void testDeleteCategoryById() {
        assertNotNull(categoryHandler.getById(category.getCategoryId()));
        categoryHandler.deleteById(category.getCategoryId());
        assertNull(categoryHandler.getById(category.getCategoryId()));
        categoryHandler.create(category);
    }
}
