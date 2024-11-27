package uk.co.xeiverse.ssh.objects;

public class GroceryStore {

    private String name;
    private String logoUrl;

    public GroceryStore(String name, String logoUrl) {
        this.name = name;
        this.logoUrl = logoUrl;
    }

    public String getName() {
        return name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }
}
