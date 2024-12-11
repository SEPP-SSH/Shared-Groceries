package uk.co.xeiverse.ssh.networking.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "BasketItem")
public class BasketItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("basket_item_id")
    private int basketItemId;

    @ManyToOne
    @JoinColumn(name = "basket_id", nullable = false)
    @JsonProperty("basket")
    private Basket basket;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    @JsonProperty("store")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    @JsonProperty("item")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "housemate_id", nullable = false)
    @JsonProperty("housemate")
    private Housemate housemate;

    @Column(name = "item_quantity", nullable = false)
    @JsonProperty("item_quantity")
    private int itemQuantity;

}