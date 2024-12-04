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
import uk.co.xeiverse.ssh.objects.BasketItem;
import uk.co.xeiverse.ssh.objects.GroceryItem;

public class BasketAdapter extends ArrayAdapter<BasketItem> {

    private Context context;
    private ServerHelper serverHelper;

    public BasketAdapter(@NonNull Context context, ServerHelper serverHelper) {
        super(context, 0, serverHelper.getBasketItemsList());

        this.context = context;
        this.serverHelper = serverHelper;
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
        titleView.setText(currentBasketItem.getName());

        // Set price text
        if (currentBasketItem.getOfferPrice().equals(currentBasketItem.getBasePrice())) {
            basePriceView.setText("£" + currentBasketItem.getBasePrice().toString());
            offerPriceView.setVisibility(View.GONE);
        }
        else {
            basePriceView.setText("£" + currentBasketItem.getBasePrice().toString());
            basePriceView.setPaintFlags(basePriceView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            offerPriceView.setText("£" + currentBasketItem.getOfferPrice().toString());
        }

        // Check if in stock
        if (currentBasketItem.getInStock() > 0) {
            stockView.setText(context.getString(R.string.in_stock));
        }
        else {
            stockView.setText(context.getString(R.string.out_of_stock));
        }

        // Load the image URL into the imageview
        Glide.with(getContext()).load(currentBasketItem.getImgUrl()).into(imageView);

        // Set user view
        userTextView.setText(currentBasketItem.getUserId().toString());

        // Set the quantity
        quantityView.setText(currentBasketItem.getQuantity().toString());

        // Implement change quantity buttons
        increaseQuantityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer newQuantity = currentBasketItem.getQuantity() + 1;
                quantityView.setText(newQuantity.toString());
                serverHelper.addItemToBasket(currentBasketItem, 1);
            }
        });

        decreaseQuantityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer newQuantity = currentBasketItem.getQuantity() - 1;
                if (newQuantity >= 0) {
                    quantityView.setText(newQuantity.toString());
                }
                serverHelper.removeItemFromBasket(currentBasketItem, 1);
            }
        });

        // then return the recyclable view
        return currentItemView;
    }
}