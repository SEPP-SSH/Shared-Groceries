package uk.co.xeiverse.ssh;

import org.junit.Before;
import org.junit.Test;

import uk.co.xeiverse.ssh.adapters.BasketAdapter;
import uk.co.xeiverse.ssh.adapters.GroceryItemsAdapter;
import uk.co.xeiverse.ssh.helpers.ServerHelper;
import uk.co.xeiverse.ssh.networking.entities.BasketItem;
import uk.co.xeiverse.ssh.networking.entities.Item;

import static org.junit.Assert.*;

import android.content.Context;
import android.content.ContextWrapper;

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
    // Check that the GroceryItemsAdapter correctly handles a single item
    public void testGroceryItemAdapter() {
        Context context = new ContextWrapper(null);
        ServerHelper serverHelper = new ServerHelper(ServerHelper.TEMP_HOUSEMATE_ID, ServerHelper.TEMP_HOUSE_ID);

        Item testItem = new Item();
        testItem.setItemId(1);
        testItem.setItemName("Test Grocery");
        testItem.setItemBasePrice(2.99);
        testItem.setItemOfferPrice(2.49);
        testItem.setItemInStock(true);
        testItem.setItemImg("https://example.com/test-image.jpg");

        List<Item> itemList = new ArrayList<>();
        itemList.add(testItem);

        GroceryItemsAdapter adapter = new GroceryItemsAdapter(context, serverHelper, itemList);

        assertEquals("Adapter should have one item", 1, adapter.getCount());

        Item retrievedItem = adapter.getItem(0);
        assertNotNull("Retrieved item should not be null", retrievedItem);
        assertEquals("Item ID should match", 1, retrievedItem.getItemId());
        assertEquals("Item name should match", "Test Grocery", retrievedItem.getItemName());
        assertEquals("Item base price should match", 2.99, retrievedItem.getItemBasePrice(), 0.001);
        assertEquals("Item offer price should match", 2.49, retrievedItem.getItemOfferPrice(), 0.001);
        assertTrue("Item should be in stock", retrievedItem.isItemInStock());
        assertEquals("Item image URL should match", "https://example.com/test-image.jpg", retrievedItem.getItemImg());
    }


}