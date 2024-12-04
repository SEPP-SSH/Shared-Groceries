package uk.co.xeiverse.ssh.ui.shop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

import uk.co.xeiverse.ssh.MainActivity;
import uk.co.xeiverse.ssh.R;
import uk.co.xeiverse.ssh.adapters.GroceryStoreAdapter;
import uk.co.xeiverse.ssh.databinding.FragmentShopBinding;
import uk.co.xeiverse.ssh.helpers.ServerHelper;
import uk.co.xeiverse.ssh.objects.GroceryItem;
import uk.co.xeiverse.ssh.objects.GroceryStore;

public class ShopFragment extends Fragment {
    private ServerHelper serverHelper;

    private FragmentShopBinding binding;

    public static final Integer BROWSE_FRAGMENT = 0;
    public static final Integer BASKET_FRAGMENT = 1;
    private Integer currentFragment = BROWSE_FRAGMENT;

    private FloatingActionButton viewBasketBtn;
    private Spinner supermarketSpinner;
    private FragmentContainerView fragmentContainerView;
    private LinearLayout bottomLayout;

    private BrowseFragment browseFragment;
    private BasketFragment basketFragment;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ShopViewModel shopViewModel =
                new ViewModelProvider(this).get(ShopViewModel.class);

        binding = FragmentShopBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialise server helper
        serverHelper = new ServerHelper();
        Boolean started = serverHelper.start();

        // Initialise UI elements
        viewBasketBtn = binding.viewBasketIcon;
        supermarketSpinner = binding.supermarketSpinner;
        fragmentContainerView = binding.fragmentContainerView;
        bottomLayout = binding.bottomLayout;

        // Set onclick listeners
        viewBasketBtn.setOnClickListener(v -> {
            loadBasketFragment();
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Setup the supermarkets spinner
        setupSpinner(storesList);

        // Fetch the items from the database
        storeId = ((GroceryStore) supermarketSpinner.getSelectedItem()).getId();
        itemsList = serverHelper.getItemsByStore(storeId);

        // Load the browse fragment
        loadBrowseFragment();
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
                storeId = ((GroceryStore) supermarketSpinner.getSelectedItem()).getId();
                try {
                    if (Objects.equals(currentFragment, BROWSE_FRAGMENT)) {
                        itemsList = serverHelper.getItemsByStore(storeId);
                        // Repopulate tabs
                        browseFragment.populateTabs(storeId, itemsList);
                    } else {
                        // Reload the basket fragment
                        loadBasketFragment();
                    }
                } catch (Exception e) {
                    // Do nothing
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                supermarketSpinner.setSelection(0);
            }
        });
    }

    void loadBrowseFragment() {
        currentFragment = BROWSE_FRAGMENT;
        bottomLayout.setVisibility(View.VISIBLE);
        browseFragment = new BrowseFragment(storeId, itemCategories, itemsList, serverHelper);
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView, browseFragment);
        transaction.commit();
    }

    private void loadBasketFragment() {
        currentFragment = BASKET_FRAGMENT;
        bottomLayout.setVisibility(View.GONE);
        basketFragment = new BasketFragment(this, storeId, serverHelper);
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView, basketFragment);
        transaction.commit();
    }

    public Integer getCurrentFragment() {
        return currentFragment;
    }
}

