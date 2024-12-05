package uk.co.xeiverse.ssh.ui.shop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import uk.co.xeiverse.ssh.R;
import uk.co.xeiverse.ssh.adapters.BasketAdapter;
import uk.co.xeiverse.ssh.helpers.ServerHelper;
import uk.co.xeiverse.ssh.objects.BasketItem;

public class BasketFragment extends Fragment {

    private ShopFragment shopFragment;
    private ServerHelper serverHelper;
    private Integer storeID;

    private TextView userTotalView;
    private TextView orderTotalView;

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

        // Setup checkout layout
        LinearLayout checkoutLayout = (LinearLayout) view.findViewById(R.id.checkoutLayout);

        // Setup listview
        ListView listView = (ListView) view.findViewById(R.id.listView);

        if (!serverHelper.getBasketItemsList().isEmpty()) {
            // Hide empty basket layout
            basketEmptyLayout.setVisibility(View.GONE);

            // Setup the adapter
            BasketAdapter adapter = new BasketAdapter(requireActivity(), serverHelper, shopFragment, this);
            listView.setAdapter(adapter);

            // Setup total price display
            userTotalView = (TextView) view.findViewById(R.id.userTotalView);
            orderTotalView = (TextView) view.findViewById(R.id.orderTotalView);
            updatePrices();
        }
        else {
            // Hide checkout button
            checkoutLayout.setVisibility(View.GONE);
        }
    }

    private double calculateTotalPrice() {
        double totalPrice = 0;
        for (BasketItem item : serverHelper.getBasketItemsList()) {
            totalPrice += item.getOfferPrice() * item.getQuantity();
        }
        return totalPrice;
    }

    private double calculateUserPrice() {
        double userPrice = 0;
        for (BasketItem item : serverHelper.getBasketItemsList()) {
            if (item.getUserId().equals(serverHelper.getHousemateId())) {
                userPrice += item.getOfferPrice() * item.getQuantity();
            }
        }
        return userPrice;
    }

    public void updatePrices() {
        // Calculate total price
        double totalPrice = calculateTotalPrice();
        orderTotalView.setText("£" + String.format("%.2f", totalPrice));

        // Calculate user price
        double userPrice = calculateUserPrice();
        userTotalView.setText("£" + String.format("%.2f", userPrice));
    }
}
