package ssh.unitTests.handlers;

import org.junit.jupiter.api.*;
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
    private Store store;
    private Category category;

    @BeforeAll
    void setUp() throws Exception {
        // Initialize handlers with Hibernate session factory
        categoryHandler = new CategoryHandler(HibernateUtility.getSessionFactory());
        storeHandler = new StoreHandler(HibernateUtility.getSessionFactory());

        // Prepopulate the database with one store
        store = new Store();
        store.setStoreName("MoneyBurnerMarket");
        store.setStoreLogo("MoneyBurnerMarket.png");
        storeHandler.create(store);

        // Prepopulate the database with one category
        category = new Category();
        category.setCategoryName("Fruit");
        category.setStore(store);
        categoryHandler.create(category);
    }

    @BeforeEach
    void cleanDatabase() throws Exception {
        // Clean up categories before each test
        List<Category> categories = categoryHandler.getAll();
        for (Category category : categories) {
            categoryHandler.deleteById(category.getCategoryId());
        }

        // Recreate the single category for prototype constraints
        categoryHandler.create(category);
    }

    @Test
    void testCreateCategory() {
        // Arrange
        Category newCategory = new Category();
        newCategory.setCategoryName("Vegetables");
        newCategory.setStore(store);

        // Act
        categoryHandler.create(newCategory);

        // Assert
        Category fetchedCategory = categoryHandler.getById(newCategory.getCategoryId());
        assertNotNull(fetchedCategory);
        assertEquals("Vegetables", fetchedCategory.getCategoryName());
        assertEquals(store.getStoreId(), fetchedCategory.getStore().getStoreId());

        // Cleanup
        categoryHandler.deleteById(newCategory.getCategoryId());
    }

    @Test
    void testGetAllCategories() {
        // Act
        List<Category> categories = categoryHandler.getAll();

        // Assert
        assertEquals(1, categories.size());
        assertEquals(category.getCategoryId(), categories.get(0).getCategoryId());
    }

    @Test
    void testGetCategoryById() {
        // Act
        Category fetchedCategory = categoryHandler.getById(category.getCategoryId());

        // Assert
        assertNotNull(fetchedCategory);
        assertEquals(category.getCategoryId(), fetchedCategory.getCategoryId());
        assertEquals("Fruit", fetchedCategory.getCategoryName());
    }

    @Test
    void testGetCategoryByStoreId() {
        // Act
        List<Category> categories = categoryHandler.getByStoreId(store.getStoreId());

        // Assert
        assertEquals(1, categories.size());
        assertEquals("Fruit", categories.get(0).getCategoryName());
        assertEquals(store.getStoreId(), categories.get(0).getStore().getStoreId());
    }

    @Test
    void testDeleteCategoryById() {
        // Act
        categoryHandler.deleteById(category.getCategoryId());

        // Assert
        assertNull(categoryHandler.getById(category.getCategoryId()));

        // Recreate the category to maintain prototype constraints
        categoryHandler.create(category);
    }
}
