package ssh.entities;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("item_id")
    private int itemId;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    @JsonProperty("store")
    private Store store;

    @Column(name = "item_name", nullable = false)
    @JsonProperty("item_name")
    private String itemName;

    @Column(name = "item_img")
    @JsonProperty("item_img")
    private String itemImg;

    @Column(name = "item_base_price", nullable = false)
    @JsonProperty("item_base_price")
    private double itemBasePrice;

    @Column(name = "item_offer_price")
    @JsonProperty("item_offer_price")
    private double itemOfferPrice;

    @Column(name = "is_item_in_stock", nullable = false)
    @JsonProperty("is_item_in_stock")
    private boolean isItemInStock;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @JsonProperty("category")
    private Category category;

}