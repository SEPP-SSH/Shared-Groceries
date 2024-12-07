package uk.co.xeiverse.ssh;



import android.content.Context;


import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.core.app.ApplicationProvider;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import static org.junit.Assert.*;

import java.util.List;

import uk.co.xeiverse.ssh.adapters.BasketAdapter;
import uk.co.xeiverse.ssh.helpers.ServerHelper;
import uk.co.xeiverse.ssh.networking.entities.BasketItem;


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

    private BasketAdapter adapter;
    private Context context;
    private ServerHelper serverHelper;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        serverHelper = new ServerHelper(ServerHelper.TEMP_HOUSEMATE_ID, ServerHelper.TEMP_HOUSE_ID);
        serverHelper.start();
        adapter = new BasketAdapter(context, serverHelper, null, null);
    }

    @Test
    // Check that adapter item count matches the basket items list size
    public void testAdapterItemCount() {
        int expectedCount = serverHelper.getBasketItemsList().size();
        assertEquals("Adapter item count should match basket items list size", expectedCount, adapter.getCount());
    }

    @Test
    // Check that each item in the adapter matches the corresponding basket item
    public void testAdapterItemsMatch() {
        List<BasketItem> basketItems = serverHelper.getBasketItemsList();
        for (int i = 0; i < basketItems.size(); i++) {
            assertEquals("Item at position " + i + " should match", basketItems.get(i), adapter.getItem(i));
        }
    }

}
