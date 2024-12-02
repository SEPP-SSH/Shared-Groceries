package ssh.entities;

import javax.persistence.*;

import com.example.entities.embeddables.ItemId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Item")
public class Item {

    @EmbeddedId
    private ItemId id;

//    @ManyToOne
//    @MapsId("storeId")
//    @JoinColumn(name = "store_id", nullable = false)
//    private Store store;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "categoryId", nullable = false),
            @JoinColumn(name = "store_id", nullable = false)
    })
    private Category category;

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

    // Getters and Setters
}
