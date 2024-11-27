package uk.co.xeiverse.ssh.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

import uk.co.xeiverse.ssh.ui.shop.CategoryFragment;
import uk.co.xeiverse.ssh.ui.shop.GroceryItem;

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
        // Return a NEW fragment instance in createFragment(int).
        Fragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        // The object is just an integer.
        args.putParcelableArrayList(CategoryFragment.ARG_OBJECT, (ArrayList<GroceryItem>) items);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
