package com.example.entities.embeddables;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ItemId implements Serializable {

    private int itemId;
    private int storeId;

    public ItemId() {}

    public ItemId(int itemId, int storeId) {
        this.itemId = itemId;
        this.storeId = storeId;
    }

    // Getters, Setters, equals, and hashCode
}
