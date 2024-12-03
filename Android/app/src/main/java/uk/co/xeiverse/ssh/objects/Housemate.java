package uk.co.xeiverse.ssh.objects;

public class Housemate {

    private Integer id;
    private String firstName;
    private String lastName;
    private String profileImageUrl;
    private Integer houseId;

    public Housemate(Integer id, String firstName, String lastName, String profileImageUrl, Integer houseId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profileImageUrl = profileImageUrl;
        this.houseId = houseId;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public Integer getHouseId() {
        return houseId;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
