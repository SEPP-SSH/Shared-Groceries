package uk.co.xeiverse.ssh.ui.shop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

import uk.co.xeiverse.ssh.R;
import uk.co.xeiverse.ssh.adapters.CategoryTabsAdapter;
import uk.co.xeiverse.ssh.helpers.ServerHelper;

public class BrowseFragment extends Fragment {
    private ServerHelper serverHelper;

    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    public BrowseFragment(ServerHelper serverHelper) {
        this.serverHelper = serverHelper;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_browse_groceries, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        viewPager = (ViewPager2) view.findViewById(R.id.viewPager);

        populateTabs();
    }

    public void populateTabs() {
        // Set up the viewpager and adapter
        if (viewPager != null && tabLayout != null) {
            CategoryTabsAdapter categoryTabsAdapter = new CategoryTabsAdapter(this, serverHelper);
            viewPager.setAdapter(categoryTabsAdapter);

            // Link the tabs to the view pager
            new TabLayoutMediator(tabLayout, viewPager,
                    (tab, position) -> tab.setText(serverHelper.getCategoriesList().get(position).getCategoryName())
            ).attach();
        }
    }
}
