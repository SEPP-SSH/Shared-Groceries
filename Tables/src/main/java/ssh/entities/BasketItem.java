package ssh.entities;

import com.example.entities.embeddables.BasketItemId;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "BasketItem")
public class BasketItem {

    @EmbeddedId
    private BasketItemId id;

//    @ManyToOne
//    @MapsId("basketId")
//    private Basket basket;

//    @ManyToOne
//    @MapsId("itemId")
//    private Item item;

    @ManyToOne
    @JoinColumn(name = "housemate_id", nullable = false)
    private Housemate housemate;

    @Column(name = "item_quantity", nullable = false)
    private int itemQuantity;

    // Getters and Setters
}
