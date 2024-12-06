package uk.co.xeiverse.ssh.ui.shop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import uk.co.xeiverse.ssh.R;
import uk.co.xeiverse.ssh.adapters.GroceryItemsAdapter;
import uk.co.xeiverse.ssh.helpers.ServerHelper;
import uk.co.xeiverse.ssh.networking.entities.Item;

public class CategoryFragment extends Fragment {

    public static final String ARG_OBJECT = "object";

    private ServerHelper serverHelper;
    private ArrayList<Item> items;

    public CategoryFragment(ServerHelper serverHelper, ArrayList<Item> items) {
        this.serverHelper = serverHelper;
        this.items = items;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_categories, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();

        ListView listView = (ListView) view.findViewById(R.id.listView);

        // Setup the adapter
        GroceryItemsAdapter groceryItemsAdapter = new GroceryItemsAdapter(getContext(), serverHelper, items);
        listView.setAdapter(groceryItemsAdapter);
    }
}
