package uk.co.xeiverse.ssh.ui.shop;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class CategoryTabsAdapter extends FragmentStateAdapter {

    private List<String> categories = new ArrayList<>();

    public CategoryTabsAdapter(@NonNull Fragment fragment, List<String> categories) {
        super(fragment);

        this.categories = categories;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Return a NEW fragment instance in createFragment(int).
        Fragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        // The object is just an integer.
        args.putInt(CategoryFragment.ARG_OBJECT, position + 1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
