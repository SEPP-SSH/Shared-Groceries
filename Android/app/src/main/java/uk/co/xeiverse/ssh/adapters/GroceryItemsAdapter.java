package uk.co.xeiverse.ssh.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import uk.co.xeiverse.ssh.R;
import uk.co.xeiverse.ssh.objects.GroceryItem;

public class GroceryItemsAdapter extends ArrayAdapter<GroceryItem> {

    public GroceryItemsAdapter(@NonNull Context context, ArrayList<GroceryItem> arrayList) {
        super(context, 0, arrayList);
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
        GroceryItem currentGroceryItem = getItem(position);

        ImageView imageView = currentItemView.findViewById(R.id.itemImageView);
        TextView titleView = currentItemView.findViewById(R.id.itemTitleView);
        TextView priceView = currentItemView.findViewById(R.id.itemPriceView);
        TextView quantityView = currentItemView.findViewById(R.id.itemQuantityView);

        // Set the text of the text views
        titleView.setText(currentGroceryItem.getName());
        priceView.setText("£" + currentGroceryItem.getBasePrice().toString());

        // TODO: Set the quantity based on what's in the shopping basket
        quantityView.setText("0");

        // Load the image URL into the imageview
        Glide.with(getContext()).load(currentGroceryItem.getImgUrl()).into(imageView);

        // then return the recyclable view
        return currentItemView;
    }
}