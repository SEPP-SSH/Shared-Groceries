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
@Table(name = "Housemate")
public class Housemate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("housemate_id")
    private int housemateId;

    @Column(name = "housemate_forename", nullable = false)
    @JsonProperty("housemate_forename")
    private String housemateForename;

    @Column(name = "housemate_surname", nullable = false)
    @JsonProperty("housemate_surname")
    private String housemateSurname;

    @Column(name = "housemate_img")
    @JsonProperty("housemate_img")
    private String housemateImg;

    @ManyToOne
    @JoinColumn(name = "house_id", nullable = false)
    @JsonProperty("house")
    private House house;

    // Getters and Setters
}
