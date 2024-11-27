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
import uk.co.xeiverse.ssh.objects.GroceryItem;

public class CategoryFragment extends Fragment {

    public static final String ARG_OBJECT = "object";

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
        assert args != null;
        ArrayList<GroceryItem> groceryItems = args.getParcelableArrayList(ARG_OBJECT);

        // Setup the adapter
        GroceryItemsAdapter groceryItemsAdapter = new GroceryItemsAdapter(getContext(), groceryItems);
        listView.setAdapter(groceryItemsAdapter);
    }
}
