package uk.co.xeiverse.ssh;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.List;

import uk.co.xeiverse.ssh.helpers.ServerHelper;
import uk.co.xeiverse.ssh.objects.GroceryItem;
import uk.co.xeiverse.ssh.objects.GroceryStore;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    // Checks categories are retrieved correctly
    @Test
    public void testGetCategories() {
        ServerHelper serverHelper = new ServerHelper();
        List<String> categories = serverHelper.getCategories();
        assertNotNull(categories);
        assertEquals(8, categories.size());
        assertTrue(categories.contains("OFFERS"));
        assertTrue(categories.contains("FROZEN"));
    }

    // Checks if random grocery items are created with valid properties
    @Test
    public void testCreateRandomGroceryItems() {
        ServerHelper serverHelper = new ServerHelper();
        List<GroceryItem> items = serverHelper.createRandomGroceryItems(5);
        assertEquals(5, items.size());
        for (GroceryItem item : items) {
            assertNotNull(item.getName());
            assertNotNull(item.getImgUrl());
            assertTrue(item.getBasePrice() >= 1 && item.getBasePrice() <= 10);
        }
    }

    // Checks if the correct number of stores is returned
    @Test
    public void testGetStores() {
        ServerHelper serverHelper = new ServerHelper();
        List<GroceryStore> stores = serverHelper.getStores();
        assertEquals(3, stores.size());
        assertEquals("Tesco", stores.get(0).getName());
        assertEquals("Sainsbury's", stores.get(1).getName());
        assertEquals("Aldi", stores.get(2).getName());
    }

    // Checks if a GroceryItem is instantiated with correct values
    @Test
    public void testGroceryItemCreation() {
        GroceryItem item = new GroceryItem(1, "Apple", "https://example.com/apple.jpg", 1.99, 1.79, 10, 1);
        assertEquals(Integer.valueOf(1), item.getId());
        assertEquals("Apple", item.getName());
        assertEquals("https://example.com/apple.jpg", item.getImgUrl());
        assertEquals(Double.valueOf(1.99), item.getBasePrice());
        assertEquals(Double.valueOf(1.79), item.getOfferPrice());
        assertEquals(Integer.valueOf(10), item.getInStock());
        assertEquals(Integer.valueOf(1), item.getCategory());
    }
}