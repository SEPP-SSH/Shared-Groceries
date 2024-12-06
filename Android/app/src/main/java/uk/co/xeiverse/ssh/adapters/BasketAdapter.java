package uk.co.xeiverse.ssh.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

import uk.co.xeiverse.ssh.R;
import uk.co.xeiverse.ssh.helpers.ServerHelper;
import uk.co.xeiverse.ssh.networking.entities.BasketItem;
import uk.co.xeiverse.ssh.networking.entities.Housemate;
import uk.co.xeiverse.ssh.ui.shop.ShopFragment;

public class BasketAdapter extends ArrayAdapter<BasketItem> {

    private Context context;
    private ServerHelper serverHelper;
    private ShopFragment shopFragment;

    public BasketAdapter(@NonNull Context context, ServerHelper serverHelper, ShopFragment shopFragment) {
        super(context, 0, serverHelper.getBasketItemsList());

        this.context = context;
        this.serverHelper = serverHelper;
        this.shopFragment = shopFragment;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentItemView = convertView;

        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_basket,
                    parent,
                    false
            );
        }

        // Get the current grocery item
        BasketItem currentBasketItem = getItem(position);

        ImageView imageView = currentItemView.findViewById(R.id.itemImageView);
        TextView titleView = currentItemView.findViewById(R.id.itemTitleView);
        TextView basePriceView = currentItemView.findViewById(R.id.basePriceView);
        TextView offerPriceView = currentItemView.findViewById(R.id.offerPriceView);
        TextView userTextView = currentItemView.findViewById(R.id.userView);
        TextView stockView = currentItemView.findViewById(R.id.stockView);

        TextView quantityView = currentItemView.findViewById(R.id.itemQuantityView);
        ImageView increaseQuantityBtn = currentItemView.findViewById(R.id.increaseQuantityBtn);
        ImageView decreaseQuantityBtn = currentItemView.findViewById(R.id.reduceQuantityBtn);

        // Set the text of the text views
        titleView.setText(currentBasketItem.getItem().getItemName());

        // Set price text
        if (currentBasketItem.getItem().getItemOfferPrice() == currentBasketItem.getItem().getItemBasePrice()) {
            String display = "£" + String.valueOf(currentBasketItem.getItem().getItemBasePrice());
            basePriceView.setText(display);
            offerPriceView.setVisibility(View.GONE);
        }
        else {
            String display = "£" + String.valueOf(currentBasketItem.getItem().getItemBasePrice());
            basePriceView.setText(display);
            basePriceView.setPaintFlags(basePriceView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            display = "£" + String.valueOf(currentBasketItem.getItem().getItemOfferPrice());
            offerPriceView.setText(display);
        }

        // Check if in stock
        if (currentBasketItem.getItem().isItemInStock()) {
            stockView.setText(context.getString(R.string.in_stock));
        }
        else {
            stockView.setText(context.getString(R.string.out_of_stock));
        }

        // Load the image URL into the imageview
        Glide.with(getContext()).load(currentBasketItem.getItem().getItemImg()).into(imageView);

        // Set user view
        for (Housemate current : serverHelper.getHousemateList()) {
            if (current.getHousemateId() == currentBasketItem.getHousemate().getHousemateId()) {
                String display = current.getHousemateForename() + " " + current.getHousemateSurname();
                userTextView.setText(display);
            }
        }

        // Set the quantity
        quantityView.setText(String.valueOf(currentBasketItem.getItemQuantity()));

        // Implement change quantity buttons
        increaseQuantityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if it is this user's item
                if (currentBasketItem.getHousemate().getHousemateId() == serverHelper.getHousemateId()) {
                    int newQuantity = currentBasketItem.getItemQuantity() + 1;
                    quantityView.setText(Integer.toString(newQuantity));
                    serverHelper.addItemToBasket(currentBasketItem.getItem(), 1);
                }
            }
        });

        decreaseQuantityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if it is this user's item
                if (currentBasketItem.getHousemate().getHousemateId() == serverHelper.getHousemateId()) {
                    int newQuantity = currentBasketItem.getItemQuantity() - 1;
                    serverHelper.removeItemFromBasket(currentBasketItem.getItem(), 1);
                    if (newQuantity > 0) {
                        quantityView.setText(Integer.toString(newQuantity));
                    }
                    else {
                        // Reload basket fragment
                        shopFragment.loadBasketFragment();
                    }
                }
            }
        });

        // then return the recyclable view
        return currentItemView;
    }
}