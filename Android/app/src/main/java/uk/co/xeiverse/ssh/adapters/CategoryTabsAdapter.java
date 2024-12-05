package uk.co.xeiverse.ssh.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

import uk.co.xeiverse.ssh.helpers.ServerHelper;
import uk.co.xeiverse.ssh.ui.shop.CategoryFragment;
import uk.co.xeiverse.ssh.objects.GroceryItem;

public class CategoryTabsAdapter extends FragmentStateAdapter {

    private ServerHelper serverHelper;

    public CategoryTabsAdapter(@NonNull Fragment fragment, ServerHelper serverHelper) {
        super(fragment);

        this.serverHelper = serverHelper;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Create the new fragment
        Fragment fragment = new CategoryFragment(serverHelper);

        // Sort through the items by category
        ArrayList<GroceryItem> currentItems = new ArrayList<>();
        for (GroceryItem item : serverHelper.getItemsList()) {
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
        return serverHelper.getCategoriesList().size();
    }
}
