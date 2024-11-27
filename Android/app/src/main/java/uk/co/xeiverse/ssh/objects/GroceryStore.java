package uk.co.xeiverse.ssh.objects;

public class GroceryStore {

    private Integer id;
    private String name;
    private String logoUrl;

    public GroceryStore(Integer id, String name, String logoUrl) {
        this.id = id;
        this.name = name;
        this.logoUrl = logoUrl;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }
}
