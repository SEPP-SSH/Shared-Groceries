package com.example.entities;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "Store")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("store_id")
    private int storeId;

    @Column(name = "store_name", nullable = false)
    @JsonProperty("store_name")
    private String storeName;

    @Column(name = "store_logo")
    @JsonProperty("store_logo")
    private String storeLogo;

    // Getters and Setters
}
