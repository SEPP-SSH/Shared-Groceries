package uk.co.xeiverse.ssh;

import org.junit.Before;
import org.junit.Test;

import uk.co.xeiverse.ssh.adapters.BasketAdapter;
import uk.co.xeiverse.ssh.helpers.ServerHelper;
import uk.co.xeiverse.ssh.networking.entities.BasketItem;
import uk.co.xeiverse.ssh.networking.entities.Category;
import uk.co.xeiverse.ssh.networking.entities.Item;
import uk.co.xeiverse.ssh.networking.entities.Store;

import static org.junit.Assert.*;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleUnitTest {

    private BasketAdapter adapter;
    private ServerHelper serverHelper;
    private Context context;

    @Before
    public void setUp() {
        context = new android.content.ContextWrapper(null);
        serverHelper = new ServerHelper(ServerHelper.TEMP_HOUSEMATE_ID, ServerHelper.TEMP_HOUSE_ID);
        serverHelper.start();
        adapter = new BasketAdapter(context, serverHelper, null, null);
    }

    @Test
    // Check the first item in the basket matches the adapter's first item
    public void testGetItem() {
        List<BasketItem> items = serverHelper.getBasketItemsList();
        ArrayList<BasketItem> arrayListItems = new ArrayList<>(items);

        if (!arrayListItems.isEmpty()) {
            BasketItem expectedItem = arrayListItems.get(0);
            assertEquals(expectedItem, adapter.getItem(0));
        } else {
            assertTrue("Basket is empty", arrayListItems.isEmpty());
        }

    }

    @Test
    public void testItemConstructorAndGetters() {
        Category category = new Category();
        Store store = new Store();
        Item item = new Item(1, store, "Apple", "apple.jpg", 1.0, 0.9, true, category);

        assertEquals(1, item.getItemId());
        assertEquals("Apple", item.getItemName());
        assertEquals(1.0, item.getItemBasePrice(), 0.001);
        assertEquals(0.9, item.getItemOfferPrice(), 0.001);
        assertTrue(item.isItemInStock());
        assertEquals("apple.jpg", item.getItemImg());
        assertEquals(category, item.getCategory());
    }

    @Test
    public void testItemSetters() {
        Item item = new Item();
        Category category = new Category();

        item.setItemId(2);
        item.setItemName("Banana");
        item.setItemBasePrice(0.5);
        item.setItemOfferPrice(0.4);
        item.setItemInStock(false);
        item.setItemImg("banana.jpg");
        item.setCategory(category);

        assertEquals(2, item.getItemId());
        assertEquals("Banana", item.getItemName());
        assertEquals(0.5, item.getItemBasePrice(), 0.001);
        assertEquals(0.4, item.getItemOfferPrice(), 0.001);
        assertFalse(item.isItemInStock());
        assertEquals("banana.jpg", item.getItemImg());
        assertEquals(category, item.getCategory());
    }


}