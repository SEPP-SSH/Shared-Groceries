package uk.co.xeiverse.ssh.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

import uk.co.xeiverse.ssh.helpers.ServerHelper;
import uk.co.xeiverse.ssh.networking.entities.Item;
import uk.co.xeiverse.ssh.ui.shop.CategoryFragment;

public class CategoryTabsAdapter extends FragmentStateAdapter {

    private ServerHelper serverHelper;

    public CategoryTabsAdapter(@NonNull Fragment fragment, ServerHelper serverHelper) {
        super(fragment);

        this.serverHelper = serverHelper;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Sort through the items by category
        ArrayList<Item> currentItems = new ArrayList<>();
        for (Item item : serverHelper.getItemsList()) {
            if (item.getCategory().getCategoryId() == serverHelper.getCategoriesList().get(position).getCategoryId()) {
                currentItems.add(item);
            }
        }

        // Create the new fragment
        CategoryFragment fragment = new CategoryFragment(serverHelper, currentItems);

        return fragment;
    }

    @Override
    public int getItemCount() {
        return serverHelper.getCategoriesList().size();
    }
}
