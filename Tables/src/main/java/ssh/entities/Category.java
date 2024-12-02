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
@Table(name = "Category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("category_id")
    private int categoryId;

    @ManyToOne
//    @MapsId("storeId")
    @JoinColumn(name = "store_id", nullable = false)
    @JsonProperty("store")
    private Store store;

    @Column(name = "category_name", nullable = false)
    @JsonProperty("category_name")
    private String categoryName;

    // Getters and Setters
}
