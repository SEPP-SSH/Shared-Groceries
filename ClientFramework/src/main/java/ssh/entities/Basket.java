package ssh.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Basket")
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("basket_id")
    private int basketId;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    @JsonProperty("store")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "house_id", nullable = false)
    @JsonProperty("house")
    private House house;

}