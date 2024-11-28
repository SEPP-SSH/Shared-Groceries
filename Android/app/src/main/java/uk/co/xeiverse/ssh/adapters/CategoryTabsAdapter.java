package uk.co.xeiverse.ssh.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

import uk.co.xeiverse.ssh.ui.shop.CategoryFragment;
import uk.co.xeiverse.ssh.objects.GroceryItem;

public class CategoryTabsAdapter extends FragmentStateAdapter {

    private List<String> categories;
    private List<GroceryItem> items;

    public CategoryTabsAdapter(@NonNull Fragment fragment, List<String> categories, List<GroceryItem> groceryItems) {
        super(fragment);

        this.categories = categories;
        this.items = groceryItems;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Create the new fragment
        Fragment fragment = new CategoryFragment();

        // Sort through the items by category
        ArrayList<GroceryItem> currentItems = new ArrayList<>();
        for (GroceryItem item : items) {
            if (item.getCategory().equals(position)) {
                currentItems.add(item);
            }
        }

        // Bundle the items list into the fragment
        Bundle args = new Bundle();
        args.putParcelableArrayList(CategoryFragment.ARG_OBJECT, currentItems);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
