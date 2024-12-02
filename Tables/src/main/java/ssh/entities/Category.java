package ssh.entities;

import javax.persistence.*;

import com.example.entities.embeddables.CategoryId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Category")
public class Category {

    @EmbeddedId
    private CategoryId id;

//    @ManyToOne
//    @MapsId("storeId")
//    @JoinColumn(name = "store_id", nullable = false)
//    private Store store;

    @Column(name = "category_name", nullable = false)
    @JsonProperty("category_name")
    private String categoryName;

    // Getters and Setters
}
