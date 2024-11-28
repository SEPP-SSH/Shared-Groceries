package uk.co.xeiverse.ssh.ui.shop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import uk.co.xeiverse.ssh.MainActivity;
import uk.co.xeiverse.ssh.R;
import uk.co.xeiverse.ssh.adapters.GroceryStoreAdapter;
import uk.co.xeiverse.ssh.databinding.FragmentShopBinding;
import uk.co.xeiverse.ssh.helpers.ServerHelper;
import uk.co.xeiverse.ssh.adapters.CategoryTabsAdapter;
import uk.co.xeiverse.ssh.objects.GroceryItem;
import uk.co.xeiverse.ssh.objects.GroceryStore;

public class ShopFragment extends Fragment {
    private ServerHelper serverHelper;

    private FragmentShopBinding binding;

    private TabLayout tabLayout;
    private FloatingActionButton viewTrolleyBtn;
    private ViewPager2 viewPager;
    private Spinner supermarketSpinner;

    private CategoryTabsAdapter categoryTabsAdapter;
    private List<String> itemCategories;
    private List<GroceryItem> itemsList;
    private List<GroceryStore> storesList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ShopViewModel shopViewModel =
                new ViewModelProvider(this).get(ShopViewModel.class);

        binding = FragmentShopBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialise server helper
        serverHelper = new ServerHelper();

        // Initialise UI elements
        tabLayout = binding.tabLayout;
        viewTrolleyBtn = binding.viewTrolleyIcon;
        supermarketSpinner = binding.supermarketSpinner;

        // Fetch stores from the server
        storesList = serverHelper.getStores();

        // Fetch categories from database
        itemCategories = serverHelper.getCategories();

        // Set onclick listeners
        viewTrolleyBtn.setOnClickListener(v -> {
            // TODO: Navigate to trolley fragment
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewPager = view.findViewById(R.id.viewPager);

        // Setup the supermarkets spinner
        setupSpinner(storesList);

        // Fetch the items from the database
        itemsList = serverHelper.getItemsByStore(((GroceryStore) supermarketSpinner.getSelectedItem()).getId());

        // Populate the tab layout for first time
        populateTabs();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setupSpinner(List<GroceryStore> storesList) {
        GroceryStoreAdapter adapter = new GroceryStoreAdapter((MainActivity) getActivity(), R.layout.row_spinner, storesList, getResources());
        supermarketSpinner.setAdapter(adapter);
        supermarketSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                itemsList = serverHelper.getItemsByStore(((GroceryStore) supermarketSpinner.getSelectedItem()).getId());
                populateTabs();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                supermarketSpinner.setSelection(0);
            }
        });
    }

    private void populateTabs() {
        // Set up the viewpager and adapter
        categoryTabsAdapter = new CategoryTabsAdapter(this, itemCategories, itemsList);
        viewPager.setAdapter(categoryTabsAdapter);

        // Link the tabs to the view pager
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(itemCategories.get(position))
        ).attach();
    }
}

