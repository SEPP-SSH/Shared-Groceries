package uk.co.xeiverse.ssh.ui.shop;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class GroceryItem implements Parcelable {

    private String name;
    private Double price;
    private String imgUrl;
    private Integer quantity;

    public GroceryItem(String name, Double price, String imgUrl, Integer quantity) {
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
        this.quantity = quantity;
    }

    public GroceryItem(Parcel in) {
        this.name = in.readString();
        this.price = in.readDouble();
        this.imgUrl = in.readString();
        this.quantity = in.readInt();
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        if (price == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(price);
        }
        parcel.writeString(imgUrl);
        if (quantity == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(quantity);
        }
    }

    public static final Creator<GroceryItem> CREATOR = new Creator<GroceryItem>() {
        @Override
        public GroceryItem createFromParcel(Parcel in) {
            return new GroceryItem(in);
        }

        @Override
        public GroceryItem[] newArray(int size) {
            return new GroceryItem[size];
        }
    };
}
