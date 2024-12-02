package com.example.entities;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "House")
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("house_id")
    private int houseId;

    @Column(name = "house_address", nullable = false)
    @JsonProperty("house_address")
    private String houseAddress;

    // Getters and Setters
}
