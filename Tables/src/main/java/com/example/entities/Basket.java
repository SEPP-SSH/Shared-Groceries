package com.example.entities;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Basket")
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("basket_id")
    private int basketId;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne
    @JoinColumn(name = "house_id", nullable = false)
    private House house;

    // Getters and Setters
}
