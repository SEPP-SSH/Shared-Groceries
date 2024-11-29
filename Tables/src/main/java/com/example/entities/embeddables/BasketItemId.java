package com.example.entities.embeddables;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BasketItemId implements Serializable {

    private int basketId;
    private int storeId;
    private int itemId;

    public BasketItemId() {}

    public BasketItemId(int basketId, int storeId, int itemId) {
        this.basketId = basketId;
        this.storeId = storeId;
        this.itemId = itemId;
    }

    // Getters, Setters, equals, and hashCode
}
