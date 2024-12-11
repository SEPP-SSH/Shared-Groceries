package uk.co.xeiverse.ssh.ui.shop;

import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
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
import uk.co.xeiverse.ssh.networking.entities.Store;

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

    private Boolean networkError = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ShopViewModel shopViewModel =
                new ViewModelProvider(this).get(ShopViewModel.class);

        binding = FragmentShopBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialise server helper
        serverHelper = new ServerHelper(ServerHelper.TEMP_HOUSEMATE_ID, ServerHelper.TEMP_HOUSE_ID);
        networkError = serverHelper.start();

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

    public void runStartup() {
        if (networkError) {
            // Setup the supermarkets spinner
            setupSpinner(serverHelper.getStoresList());

            // Load the browse fragment
            loadBrowseFragment();
        }
        else {
            showError();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        runStartup();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setupSpinner(List<Store> storesList) {
        GroceryStoreAdapter adapter = new GroceryStoreAdapter((MainActivity) getActivity(), R.layout.row_spinner, storesList, getResources());
        supermarketSpinner.setAdapter(adapter);
        supermarketSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Load the new storeId
                Integer newStoreId = ((Store) supermarketSpinner.getSelectedItem()).getStoreId();
                // Load store information
                serverHelper.loadStore(newStoreId);
                try {
                    if (Objects.equals(currentFragment, BROWSE_FRAGMENT)) {
                        // Reload the browse fragment
                        loadBrowseFragment();
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
        browseFragment = new BrowseFragment(serverHelper);
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView, browseFragment);
        transaction.commit();
    }

    public void loadBasketFragment() {
        currentFragment = BASKET_FRAGMENT;
        bottomLayout.setVisibility(View.GONE);
        basketFragment = new BasketFragment(this, serverHelper);
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView, basketFragment);
        transaction.commit();
    }

    public Integer getCurrentFragment() {
        return currentFragment;
    }

    private void showError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Network Error!");
        builder.setMessage("The app can't connect to the SSH Cloud...");
        builder.setCancelable(false);
        builder.setPositiveButton("Exit", (DialogInterface.OnClickListener) (dialog, which) -> {
            requireActivity().finish();
        });
        builder.setNegativeButton("Retry", (DialogInterface.OnClickListener) (dialog, which) -> {
            serverHelper = new ServerHelper(ServerHelper.TEMP_HOUSEMATE_ID, ServerHelper.TEMP_HOUSE_ID);
            networkError = serverHelper.start();
            runStartup();
            dialog.cancel();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}

