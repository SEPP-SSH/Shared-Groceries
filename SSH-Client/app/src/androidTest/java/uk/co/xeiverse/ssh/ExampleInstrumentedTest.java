package uk.co.xeiverse.ssh;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import uk.co.xeiverse.ssh.adapters.BasketAdapter;
import uk.co.xeiverse.ssh.adapters.GroceryItemsAdapter;
import uk.co.xeiverse.ssh.helpers.ServerHelper;
import uk.co.xeiverse.ssh.networking.entities.BasketItem;
import uk.co.xeiverse.ssh.networking.entities.Category;
import uk.co.xeiverse.ssh.networking.entities.Item;
import uk.co.xeiverse.ssh.networking.entities.Store;




/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    // Context of the app under test.
    @Test
    public void useAppContext() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("uk.co.xeiverse.ssh", appContext.getPackageName());
    }

    private BasketAdapter basketAdapter;
    private Context context;
    private ServerHelper serverHelper;
    private List<Item> items;
    private GroceryItemsAdapter GIadapter;


    @Before
    public void setUp() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        serverHelper = new ServerHelper(ServerHelper.TEMP_HOUSEMATE_ID, ServerHelper.TEMP_HOUSE_ID);
        serverHelper.start();
        basketAdapter = new BasketAdapter(context, serverHelper, null, null);
        Store store1 = new Store(1, "Tesco", "https://example.com/store.jpg");
        Category category1 = new Category(1, store1, "Fruits");

        items = new ArrayList<>();
        Item item1 = new Item();
        item1.setItemName("Apple");
        item1.setItemBasePrice(1.0);
        item1.setItemOfferPrice(0.8);
        item1.setItemImg("https://example.com/apple.jpg");
        item1.setCategory(category1);
        item1.setItemInStock(true);

        Item item2 = new Item();
        item2.setItemName("Banana");
        item2.setItemBasePrice(0.5);
        item2.setItemOfferPrice(0.5);
        item2.setItemImg("https://example.com/banana.jpg");
        item2.setCategory(category1);
        item2.setItemInStock(false);

        items.add(item1);
        items.add(item2);

        GIadapter = new GroceryItemsAdapter(context, serverHelper, items);
    }

    @Test
    // Check that adapter item count matches the basket items list size
    public void testAdapterItemCount() {
        int expectedCount = serverHelper.getBasketItemsList().size();
        assertEquals("Adapter item count should match basket items list size", expectedCount, basketAdapter.getCount());
    }

    @Test
    // Check that each item in the adapter matches the corresponding basket item
    public void testAdapterItemsMatch() {
        List<BasketItem> basketItems = serverHelper.getBasketItemsList();
        for (int i = 0; i < basketItems.size(); i++) {
            assertEquals("Item at position " + i + " should match", basketItems.get(i), basketAdapter.getItem(i));
        }
    }

    @Test
    // Check the GroceryItemsAdapter is created correctly with the expected item count and names
    public void testAdapterCreation() {
        assertNotNull(GIadapter);
        assertEquals(2, GIadapter.getCount());
        assertEquals("Apple", GIadapter.getItem(0).getItemName());
        assertEquals("Banana", GIadapter.getItem(1).getItemName());
    }

    @Test
    // Check the price display in the GroceryItemsAdapter including base price, offer price, and strikethrough formatting
    public void testItemPriceDisplay() {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                View itemView = GIadapter.getView(0, null, null);
                TextView basePriceView = itemView.findViewById(R.id.basePriceView);
                TextView offerPriceView = itemView.findViewById(R.id.offerPriceView);

                assertEquals("£1.0", basePriceView.getText().toString());
                assertEquals("£0.8", offerPriceView.getText().toString());
                assertTrue((basePriceView.getPaintFlags() & Paint.STRIKE_THRU_TEXT_FLAG) != 0);
            }
        });
    }

    @Test
    // Check the correct stock status display items in the GroceryItemsAdapter
    public void testItemStockStatus() {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

                View itemView1 = GIadapter.getView(0, null, null);
                View itemView2 = GIadapter.getView(1, null, null);

                TextView stockView1 = itemView1.findViewById(R.id.stockView);
                TextView stockView2 = itemView2.findViewById(R.id.stockView);

                assertEquals(context.getString(R.string.in_stock), stockView1.getText().toString());
                assertEquals(context.getString(R.string.out_of_stock), stockView2.getText().toString());
            }
        });
    }
}
