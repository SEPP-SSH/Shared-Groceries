package uk.co.xeiverse.ssh.ui.shop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.co.xeiverse.ssh.R;
import uk.co.xeiverse.ssh.databinding.FragmentShopBinding;

public class ShopFragment extends Fragment {

    private FragmentShopBinding binding;

    private TabLayout tabLayout;
    private FloatingActionButton viewTrolleyBtn;
    private ViewPager2 viewPager;

    private CategoryTabsAdapter categoryTabsAdapter;
    private List<String> itemCategories;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ShopViewModel shopViewModel =
                new ViewModelProvider(this).get(ShopViewModel.class);

        binding = FragmentShopBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialise UI elements
        tabLayout = binding.tabLayout;
        viewTrolleyBtn = binding.viewTrolleyIcon;

        // Fetch categories from database
        String[] categories = {"OFFERS", "FROZEN", "FRUIT & VEG", "MEAT", "BAKERY", "DAIRY", "CLEANING", "PASTA & RICE"};
        itemCategories = new ArrayList<>(Arrays.asList(categories));

        // Set onclick listeners
        viewTrolleyBtn.setOnClickListener(v -> {
            // TODO: Navigate to trolley fragment
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Set up the viewpager and adapter
        categoryTabsAdapter = new CategoryTabsAdapter(this, itemCategories);
        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(categoryTabsAdapter);

        // Link the tabs to the view pager
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(itemCategories.get(position))
        ).attach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

