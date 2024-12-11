package uk.co.xeiverse.ssh.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import uk.co.xeiverse.ssh.R;
import uk.co.xeiverse.ssh.helpers.ServerHelper;
import uk.co.xeiverse.ssh.networking.entities.BasketItem;
import uk.co.xeiverse.ssh.networking.entities.Item;
import uk.co.xeiverse.ssh.ui.shop.ShopFragment;

public class GroceryItemsAdapter extends ArrayAdapter<Item> {

    private Context context;
    private ServerHelper serverHelper;

    public GroceryItemsAdapter(@NonNull Context context, ServerHelper serverHelper, List<Item> arrayList) {
        super(context, 0, arrayList);

        this.context = context;
        this.serverHelper = serverHelper;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentItemView = convertView;

        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_grocery,
                    parent,
                    false
            );
        }

        // Get the current grocery item
        Item currentGroceryItem = getItem(position);

        ImageView imageView = currentItemView.findViewById(R.id.itemImageView);
        TextView titleView = currentItemView.findViewById(R.id.itemTitleView);
        TextView basePriceView = currentItemView.findViewById(R.id.basePriceView);
        TextView offerPriceView = currentItemView.findViewById(R.id.offerPriceView);
        TextView stockView = currentItemView.findViewById(R.id.stockView);
        Button addToBasketBtn = currentItemView.findViewById(R.id.addToBasketBtn);

        LinearLayout quantityLayout = currentItemView.findViewById(R.id.quantityView);
        ImageView quantityAddBtn = currentItemView.findViewById(R.id.increaseQuantityBtn);
        ImageView quantityRemoveBtn = currentItemView.findViewById(R.id.reduceQuantityBtn);
        TextView quantityTextView = currentItemView.findViewById(R.id.itemQuantityView);

        // Set the text of the text views
        titleView.setText(currentGroceryItem.getItemName());

        // Set price text
        if (currentGroceryItem.getItemOfferPrice() == currentGroceryItem.getItemBasePrice()) {
            String display = "£" + String.valueOf(currentGroceryItem.getItemBasePrice());
            basePriceView.setText(display);
            offerPriceView.setVisibility(View.GONE);
        }
        else {
            String display = "£" + String.valueOf(currentGroceryItem.getItemBasePrice());
            basePriceView.setText(display);
            basePriceView.setPaintFlags(basePriceView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            display = "£" + String.valueOf(currentGroceryItem.getItemOfferPrice());
            offerPriceView.setText(display);
        }

        // Check if in stock
        if (currentGroceryItem.isItemInStock()) {
            stockView.setText(context.getString(R.string.in_stock));
        }
        else {
            stockView.setText(context.getString(R.string.out_of_stock));
        }

        // Load the image URL into the imageview
        Glide.with(getContext()).load(currentGroceryItem.getItemImg()).into(imageView);

        // Check if item is already in basket
        Integer quantity = -1;
        for (BasketItem basketItem : serverHelper.getBasketItemsList()) {
            if (basketItem.getItem().getItemId()  == currentGroceryItem.getItemId() &&
                    basketItem.getHousemate().getHousemateId() == serverHelper.getHousemateId()) {
                quantity = basketItem.getItemQuantity();
                break;
            }
        }

        if (quantity != -1) {
            addToBasketBtn.setVisibility(View.GONE);
            quantityLayout.setVisibility(View.VISIBLE);

            quantityTextView.setText(quantity.toString());
        }
        else {
            addToBasketBtn.setVisibility(View.VISIBLE);
            quantityLayout.setVisibility(View.GONE);
        }

        // Add to basket btn
        addToBasketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToBasketBtn.setVisibility(View.GONE);
                quantityLayout.setVisibility(View.VISIBLE);

                serverHelper.addItemToBasket(currentGroceryItem, 1);
            }
        });

        // Increase quantity
        quantityAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer newQuantity = Integer.parseInt(quantityTextView.getText().toString()) + 1;
                quantityTextView.setText(newQuantity.toString());
                serverHelper.addItemToBasket(currentGroceryItem, 1);
            }
        });

        // Decrease quantity
        quantityRemoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer newQuantity = Integer.parseInt(quantityTextView.getText().toString()) - 1;
                if (newQuantity > 0) {
                    quantityTextView.setText(newQuantity.toString());
                }
                else {
                    addToBasketBtn.setVisibility(View.VISIBLE);
                    quantityLayout.setVisibility(View.GONE);
                }
                serverHelper.removeItemFromBasket(currentGroceryItem, 1);
            }
        });

        // then return the recyclable view
        return currentItemView;
    }
}