package uk.co.xeiverse.ssh.objects;

public class GroceryItem {

    private Integer id;
    private String name;
    private String imgUrl;
    private Double basePrice;
    private Double offerPrice;
    private Integer inStock;
    private Integer category;

    public GroceryItem(Integer id, String name, String imgUrl, Double basePrice, Double offerPrice, Integer inStock, Integer category) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.basePrice = basePrice;
        this.offerPrice = offerPrice;
        this.inStock = inStock;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public Double getOfferPrice() {
        return offerPrice;
    }

    public Integer getInStock() {
        return inStock;
    }

    public Integer getCategory() {
        return category;
    }
}
