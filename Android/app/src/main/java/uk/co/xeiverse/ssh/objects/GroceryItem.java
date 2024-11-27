package uk.co.xeiverse.ssh.objects;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class GroceryItem implements Parcelable {

    private String name;
    private String imgUrl;
    private Double basePrice;
    private Double offerPrice;
    private Integer inStock;
    private Integer category;

    public GroceryItem(String name, String imgUrl, Double basePrice, Double offerPrice, Integer inStock, Integer category) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.basePrice = basePrice;
        this.offerPrice = offerPrice;
        this.inStock = inStock;
        this.category = category;
    }

    @SuppressLint("NewApi")
    public GroceryItem(Parcel in) {
        this.name = in.readString();
        this.imgUrl = in.readString();
        this.basePrice = in.readDouble();
        this.offerPrice = in.readDouble();
        this.inStock = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(imgUrl);

        if (basePrice == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(basePrice);
        }

        if (offerPrice == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(offerPrice);
        }

        if (inStock == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(inStock);
        }

        if (category == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(category);
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

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public Double getOfferPrice() {
        return offerPrice;
    }

    public Integer getInStock() {
        return inStock;
    }

    public Integer getCategory() {
        return category;
    }
}
