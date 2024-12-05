package uk.co.xeiverse.ssh;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.util.ArrayList;

import uk.co.xeiverse.ssh.adapters.BasketAdapter;
import uk.co.xeiverse.ssh.adapters.GroceryItemsAdapter;
import uk.co.xeiverse.ssh.helpers.ServerHelper;
import uk.co.xeiverse.ssh.objects.GroceryItem;

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

    // Tests BasketAdapter creation and item count
    @Test
    public void testBasketAdapterCreation() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ServerHelper serverHelper = new ServerHelper();
        ArrayList<GroceryItem> items = new ArrayList<>(serverHelper.createRandomGroceryItems(3));
        BasketAdapter adapter = new BasketAdapter(context, items, serverHelper, 1);
        assertNotNull(adapter);
        assertEquals(3, adapter.getCount());
    }

    // Checks GroceryItemsAdapter initialization and item count
    @Test
    public void testGroceryItemsAdapterCreation() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ServerHelper serverHelper = new ServerHelper();
        ArrayList<GroceryItem> items = new ArrayList<>(serverHelper.createRandomGroceryItems(5));
        GroceryItemsAdapter adapter = new GroceryItemsAdapter(context, items, serverHelper, 1);
        assertNotNull(adapter);
        assertEquals(5, adapter.getCount());
    }
}
