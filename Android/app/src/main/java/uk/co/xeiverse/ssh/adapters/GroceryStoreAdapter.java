package uk.co.xeiverse.ssh.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
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
import java.util.List;

import uk.co.xeiverse.ssh.MainActivity;
import uk.co.xeiverse.ssh.R;
import uk.co.xeiverse.ssh.objects.GroceryItem;
import uk.co.xeiverse.ssh.objects.GroceryStore;

public class GroceryStoreAdapter extends ArrayAdapter<GroceryStore> {

    private Activity activity;
    private List<GroceryStore> data;
    public Resources res;
    GroceryStore tempValues = null;
    LayoutInflater inflater;

    public GroceryStoreAdapter(MainActivity activitySpinner, int textViewResourceId,
                               List<GroceryStore> objects, Resources resLocal) {
        super(activitySpinner, textViewResourceId, objects);

        activity = activitySpinner;
        data = objects;
        res = resLocal;

        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    // This funtion called for each row ( Called data.size() times )
    public View getCustomView(int position, View convertView, ViewGroup parent) {
        View row = inflater.inflate(R.layout.row_spinner, parent, false);

        tempValues = null;
        tempValues = (GroceryStore) data.get(position);

        TextView label = (TextView)row.findViewById(R.id.text);
        ImageView companyLogo = (ImageView)row.findViewById(R.id.icon);

        // Set values for spinner each row
        label.setText(tempValues.getName());
        Glide.with(getContext()).load(tempValues.getLogoUrl()).into(companyLogo);

        return row;
    }
}