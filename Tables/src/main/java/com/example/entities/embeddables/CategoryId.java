package com.example.entities.embeddables;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CategoryId implements Serializable {

    private int categoryId;
    private int storeId;

    public CategoryId() {}

    public CategoryId(int categoryId, int storeId) {
        this.categoryId = categoryId;
        this.storeId = storeId;
    }

    // Getters, Setters, equals, and hashCode
}
