package uk.co.xeiverse.ssh.ui.shop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import uk.co.xeiverse.ssh.R;
import uk.co.xeiverse.ssh.adapters.BasketAdapter;
import uk.co.xeiverse.ssh.helpers.ServerHelper;
import uk.co.xeiverse.ssh.objects.BasketItem;
import uk.co.xeiverse.ssh.objects.GroceryItem;

public class BasketFragment extends Fragment {

    private ShopFragment shopFragment;
    private ServerHelper serverHelper;
    private Integer storeID;

    public BasketFragment(ShopFragment shopFragment, ServerHelper serverHelper) {
        this.shopFragment = shopFragment;
        this.serverHelper = serverHelper;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_basket, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Setup basket empty layout
        LinearLayout basketEmptyLayout = (LinearLayout) view.findViewById(R.id.emptyBasketView);

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
                serverHelper.submitOrder();
            }
        });

        // Setup listview
        ListView listView = (ListView) view.findViewById(R.id.listView);

        if (!serverHelper.getBasketItemsList().isEmpty()) {
            // Hide empty basket layout
            basketEmptyLayout.setVisibility(View.GONE);

            // Setup the adapter
            BasketAdapter adapter = new BasketAdapter(requireActivity(), serverHelper, shopFragment);
            listView.setAdapter(adapter);
        }
        else {
            // Hide checkout button
            checkoutBtn.setVisibility(View.INVISIBLE);
        }
    }
}
