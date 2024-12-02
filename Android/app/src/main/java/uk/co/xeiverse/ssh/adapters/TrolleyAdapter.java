package uk.co.xeiverse.ssh.adapters;

import android.content.Context;
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
import uk.co.xeiverse.ssh.objects.GroceryItem;

public class TrolleyAdapter extends ArrayAdapter<GroceryItem> {

    private ServerHelper serverHelper;
    private Integer storeId;

    public TrolleyAdapter(@NonNull Context context, ArrayList<GroceryItem> arrayList,
                          ServerHelper serverHelper, Integer storeId) {
        super(context, 0, arrayList);

        this.serverHelper = serverHelper;
        this.storeId = storeId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentItemView = convertView;

        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_trolley,
                    parent,
                    false
            );
        }

        // Get the current grocery item
        GroceryItem currentGroceryItem = getItem(position);

        ImageView imageView = currentItemView.findViewById(R.id.itemImageView);
        TextView titleView = currentItemView.findViewById(R.id.itemTitleView);
        TextView priceView = currentItemView.findViewById(R.id.itemPriceView);

        TextView quantityView = currentItemView.findViewById(R.id.itemQuantityView);
        ImageView increaseQuantityBtn = currentItemView.findViewById(R.id.increaseQuantityBtn);
        ImageView decreaseQuantityBtn = currentItemView.findViewById(R.id.reduceQuantityBtn);

        // Set the text of the text views
        titleView.setText(currentGroceryItem.getName());
        priceView.setText("£" + currentGroceryItem.getBasePrice().toString());

        // TODO: Implement the offer price

        // Load the image URL into the imageview
        Glide.with(getContext()).load(currentGroceryItem.getImgUrl()).into(imageView);

        // Get the items quantity
        // TODO: Fetch quantity from database
        Integer quantity = 1;

        // Set the quantity
        quantityView.setText(quantity.toString());

        // Implement change quantity buttons
        increaseQuantityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Implement increase quantity
            }
        });

        decreaseQuantityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Implement decrease quantity
            }
        });

        // then return the recyclable view
        return currentItemView;
    }
}