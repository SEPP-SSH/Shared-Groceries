package uk.co.xeiverse.ssh.ui.shop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import uk.co.xeiverse.ssh.R;
import uk.co.xeiverse.ssh.adapters.TrolleyAdapter;
import uk.co.xeiverse.ssh.helpers.ServerHelper;
import uk.co.xeiverse.ssh.objects.GroceryItem;

public class BasketFragment extends Fragment {

    private ShopFragment shopFragment;
    private ServerHelper serverHelper;
    private Integer storeID;

    public BasketFragment(ShopFragment shopFragment, Integer storeID, ServerHelper serverHelper) {
        this.shopFragment = shopFragment;
        this.serverHelper = serverHelper;
        this.storeID = storeID;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_trolley, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Setup back btn
        Button backBtn = (Button) view.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shopFragment.loadBrowseFragment();
            }
        });

        // Setup checkout button
        Button checkoutBtn = (Button) view.findViewById(R.id.checkoutBtn);
        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Implement checkout
            }
        });

        // Setup listview
        ListView listView = (ListView) view.findViewById(R.id.listView);

        // Get the items in the trolley
        ArrayList<GroceryItem> basketItems = (ArrayList<GroceryItem>) serverHelper.getTrolleyItems(ServerHelper.houseID, storeID);

        if (!basketItems.isEmpty()) {
            // Setup the adapter
            TrolleyAdapter adapter = new TrolleyAdapter(requireActivity(), basketItems, serverHelper, storeID);
            listView.setAdapter(adapter);
        }
        else {
            // TODO: Display no items message
        }
    }
}
